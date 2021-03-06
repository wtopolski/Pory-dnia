package pl.wtopolski.android.sunsetwidget.core;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import pl.wtopolski.android.sunsetwidget.core.model.Season;
import pl.wtopolski.android.sunsetwidget.core.model.TimeData;
import pl.wtopolski.android.sunsetwidget.core.model.TimeConfig;
import pl.wtopolski.android.sunsetwidget.core.model.TimePackage;

import static java.lang.Math.*;

public class TimePackageCreator {
	private TimePackage winterSolstice;
	private TimePackage autumnalEquinox;
	private TimePackage summerSolstice;
	private TimePackage springEquinox;
	private final TimeConfig config;
	private int year;
	
	public TimePackageCreator(TimeConfig config) {
		this.config = config;
	}

	public Calendar prepareCalendar() {
		return Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.ENGLISH);
	}

	public TimePackage prepareTimePackage(final Calendar calendar) {
		final int year = calendar.get(Calendar.YEAR);
		final int month = calendar.get(Calendar.MONTH) + 1;
		final int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		prepareBorders(year);
		
		TimePackage selectedDay = createTimePackage(year, month, day, config);
		
		int currentDayInYear = selectedDay.getCurrentDayInYear();
		selectedDay.setSeason(determineSeason(year, currentDayInYear));
		
		if (selectedDay.isValid()) {
			long lengthOfSelectedDay = selectedDay.getLengthOfDay();
			long lengthOfShortestDay = winterSolstice.getLengthOfDay();
			
			long lengthOfLongestDay = summerSolstice.getLengthOfDay();
			if (lengthOfLongestDay == 0L) {
				lengthOfLongestDay = 1000*60*60*24;
			}
			
			selectedDay.setLongerThanTheShortestDayOfYear(lengthOfSelectedDay - lengthOfShortestDay);
			selectedDay.setShorterThanTheLongestDayOfYear(lengthOfLongestDay - lengthOfSelectedDay);
		}
		
		return selectedDay;
	}

	private void prepareBorders(int year) {
		if (this.year != year || winterSolstice == null) {
			this.year = year;
			
			this.winterSolstice = createTimePackage(year, 12, 21, config);
			this.autumnalEquinox = createTimePackage(year, 9, 22, config);
			this.summerSolstice = createTimePackage(year, 6, 21, config);
			this.springEquinox = createTimePackage(year, 3, 20, config);
		}
	}

	protected Season determineSeason(int year, int currentDayInYear) {
		Season result = null;
		
		prepareBorders(year);
		
		int winterStartDay = winterSolstice.getCurrentDayInYear();
		int springStartDay = springEquinox.getCurrentDayInYear();
		int summerStartDay = summerSolstice.getCurrentDayInYear();
		int autumnStartDay = autumnalEquinox.getCurrentDayInYear();
		
		if (currentDayInYear >= winterStartDay || currentDayInYear < springStartDay) {
			result = Season.WINTER;
		} else if (currentDayInYear >= springStartDay && currentDayInYear < summerStartDay) {
			result = Season.SPRING;
		} else if (currentDayInYear >= summerStartDay && currentDayInYear < autumnStartDay) {
			result = Season.SUMMER;
		} else if (currentDayInYear >= autumnStartDay && currentDayInYear < winterStartDay) {
			result = Season.AUTUMN;
		}
		
		return result;
	}

	protected TimePackage createTimePackage(final int year, final int month, final int day, final TimeConfig config) {
		TimeData<Double> threePack = createTimeDataDouble(year, month, day, config);
		boolean valid = isThreePackValid(threePack);
		Calendar sunrise = prepareCalendar(year, month, day, threePack.getSunrise());
		Calendar culmination = prepareCalendar(year, month, day, threePack.getCulmination());
		Calendar sunset = prepareCalendar(year, month, day, threePack.getSunset());
		TimeData<Calendar> data = new TimeData<Calendar>(sunrise, culmination, sunset);
		return new TimePackage(data, valid);
	}

	private boolean isThreePackValid(TimeData<Double> threePack) {
		return threePack.getCulmination() != 0d && threePack.getSunrise() != 0d && threePack.getSunset() != 0d;
	}

	protected TimeData<Double> createTimeDataDouble(final int year, final int month, final int day, final TimeConfig config) {
		final double req = config.getTimeZenit().getValue();
		final double J = 367 * year - (int) (7 * (year + (int) ((month + 9) / 12)) / 4) + (int) (275 * month / 9) + day - 730531.5;
		final double Cent = J / 36525;
		final double L = (4.8949504201433 + 628.331969753199 * Cent) % 6.28318530718;
		final double G = (6.2400408 + 628.3019501 * Cent) % 6.28318530718;
		final double O = 0.409093 - 0.0002269 * Cent;
		final double F = 0.033423 * sin(G) + 0.00034907 * sin(2 * G);
		final double E = 0.0430398 * sin(2 * (L + F)) - 0.00092502 * sin(4 * (L + F)) - F;
		final double A = asin(sin(O) * sin(L + F));
		final double C = (sin(0.017453293 * req) - sin(0.017453293 * config.getLatitude()) * sin(A)) / (cos(0.017453293 * config.getLatitude()) * cos(A));

		final Double sunrise = (PI - (E + 0.017453293 * config.getLongitude() + 1 * acos(C))) * 57.29577951 / 15;
		final Double culmination = (PI - (E + 0.017453293 * config.getLongitude() + 0 * acos(C))) * 57.29577951 / 15;
		final Double sunset = (PI - (E + 0.017453293 * config.getLongitude() + (-1) * acos(C))) * 57.29577951 / 15;
		
		if (sunrise.isNaN() || culmination.isNaN() || sunset.isNaN()) {
			return new TimeData<Double>(0d, 0d, 0d);
		}
		
		return new TimeData<Double>(sunrise, culmination, sunset);
	}

	protected Calendar prepareCalendar(final int year, final int month, final int day, final double time) {
		int hour = (int) time;
		int minutes = (int) (60 * (time - hour));
		
		Calendar calendar = prepareCalendar();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minutes);
		calendar.set(Calendar.SECOND, 0);
		return calendar;
	}
}

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
	private final TimePackage winterSolstice;
	private final TimePackage autumnalEquinox;
	private final TimePackage summerSolstice;
	private final TimePackage springEquinox;
	private final TimeConfig config;
	
	public TimePackageCreator(TimeConfig config) {
		this.config = config;
		int year = prepareCalendar().get(Calendar.YEAR);
		
		this.winterSolstice = createTimePackage(year, 12, 21, config);
		this.autumnalEquinox = createTimePackage(year, 9, 22, config);
		this.summerSolstice = createTimePackage(year, 6, 21, config);
		this.springEquinox = createTimePackage(year, 3, 20, config);
	}

	public Calendar prepareCalendar() {
		return Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.ENGLISH);
	}

	public TimePackage prepareTimePackage() {
		return prepareTimePackage(prepareCalendar());
	}

	public TimePackage prepareTimePackage(final Calendar calendar) {
		final int year = calendar.get(Calendar.YEAR);
		final int month = calendar.get(Calendar.MONTH) + 1;
		final int day = calendar.get(Calendar.DAY_OF_MONTH);

		TimePackage selectedDay = createTimePackage(year, month, day, config);
		
		long lengthOfSelectedDay = selectedDay.getLengthOfDay();
		long lengthOfShortestDay = winterSolstice.getLengthOfDay();
		long lengthOfLongestDay = summerSolstice.getLengthOfDay();

		selectedDay.setSeason(determineSeason(selectedDay.getCurrentDayInYear()));
		selectedDay.setLongerThanTheShortestDayOfYear(lengthOfSelectedDay - lengthOfShortestDay);
		selectedDay.setShorterThanTheLongestDayOfYear(lengthOfLongestDay - lengthOfSelectedDay);
		
		return selectedDay;
	}

	protected Season determineSeason(int currentDayInYear) {
		Season result = null;
		
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

	private TimePackage createTimePackage(int year, int month, int day, TimeConfig config) {
		TimeData<Calendar> data = createTimeDataCalendar(year, month, day, config);
		return new TimePackage(data);
	}

	private TimeData<Calendar> createTimeDataCalendar(final int year, final int month, final int day, final TimeConfig config) {
		TimeData<Double> threePack = createTimeDataDouble(year, month, day, config);
		Calendar sunrise = prepareCalendar(year, month, day, threePack.getSunrise());
		Calendar culmination = prepareCalendar(year, month, day, threePack.getCulmination());
		Calendar sunset = prepareCalendar(year, month, day, threePack.getSunset());
		return new TimeData<Calendar>(sunrise, culmination, sunset);
	}

	private TimeData<Double> createTimeDataDouble(final int year, final int month, final int day, final TimeConfig config) {
		final double req = config.getTimeZenit().getValue();
		final double J = 367 * year - (int) (7 * (year + (int) ((month + 9) / 12)) / 4) + (int) (275 * month / 9) + day - 730531.5;
		final double Cent = J / 36525;
		final double L = (4.8949504201433 + 628.331969753199 * Cent) % 6.28318530718; // modulo
		final double G = (6.2400408 + 628.3019501 * Cent) % 6.28318530718;
		final double O = 0.409093 - 0.0002269 * Cent;
		final double F = 0.033423 * sin(G) + 0.00034907 * sin(2 * G);
		final double E = 0.0430398 * sin(2 * (L + F)) - 0.00092502 * sin(4 * (L + F)) - F;
		final double A = asin(sin(O) * sin(L + F));
		final double C = (sin(0.017453293 * req) - sin(0.017453293 * config.getLatitude()) * sin(A)) / (cos(0.017453293 * config.getLatitude()) * cos(A));
		final double sunrise = (PI - (E + 0.017453293 * config.getLongitude() + 1 * acos(C))) * 57.29577951 / 15;
		final double culmination = (PI - (E + 0.017453293 * config.getLongitude() + 0 * acos(C))) * 57.29577951 / 15;
		final double sunset = (PI - (E + 0.017453293 * config.getLongitude() + (-1) * acos(C))) * 57.29577951 / 15;
		return new TimeData<Double>(sunrise, culmination, sunset);
	}

	private Calendar prepareCalendar(final int year, final int month, final int day, final double time) {
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

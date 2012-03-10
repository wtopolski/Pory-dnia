package pl.wtopolski.android.sunsetwidget.core;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import pl.wtopolski.android.sunsetwidget.core.model.TimeData;
import pl.wtopolski.android.sunsetwidget.core.model.TimeConfig;
import pl.wtopolski.android.sunsetwidget.core.model.TimePackage;

import static java.lang.Math.*;

public class TimePackageCreator {
	public TimePackage prepareTimePackage(final TimeConfig location) {
		return prepareTimePackage(prepareCalendar(), location);
	}

	public TimePackage prepareTimePackage(final Calendar calendar, final TimeConfig config) {
		final int year = calendar.get(Calendar.YEAR);
		final int month = calendar.get(Calendar.MONTH) + 1;
		final int day = calendar.get(Calendar.DAY_OF_MONTH);

		TimeData<Calendar> forSelectedDay = createTimeDataCalendar(year, month, day, config);
		TimePackage timePackageForSelectedDay = new TimePackage(forSelectedDay);
		long lengthOfSelectedDay = timePackageForSelectedDay.getLengthOfDay();

		TimeData<Calendar> forShortestDay = createTimeDataCalendar(year, 12, 21, config);
		TimePackage timePackageShortestDay = new TimePackage(forShortestDay);
		long lengthOfShortestDay = timePackageShortestDay.getLengthOfDay();

		TimeData<Calendar> forLongestDay = createTimeDataCalendar(year, 6, 21, config);
		TimePackage timePackageLongestDay = new TimePackage(forLongestDay);
		long lengthOfLongestDay = timePackageLongestDay.getLengthOfDay();

		timePackageForSelectedDay.setLongerThanTheShortestDayOfYear(lengthOfSelectedDay - lengthOfShortestDay);
		timePackageForSelectedDay.setShorterThanTheLongestDayOfYear(lengthOfLongestDay - lengthOfSelectedDay);

		config.getTimeChange().update(timePackageForSelectedDay);
		
		return timePackageForSelectedDay;
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

	public Calendar prepareCalendar() {
		return Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.ENGLISH);
	}
}

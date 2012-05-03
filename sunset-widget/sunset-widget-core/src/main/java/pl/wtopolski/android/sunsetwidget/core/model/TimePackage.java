package pl.wtopolski.android.sunsetwidget.core.model;

import java.util.Calendar;
import java.util.Date;

public class TimePackage {
	private final int currentDayInYear;
	private Date sunrise;
	private Date culmination;
	private Date sunset;
	private final long lengthOfDay;
	private long longerThanTheShortestDayOfYear;
	private long shorterThanTheLongestDayOfYear;

	public TimePackage(TimeData<Calendar> threePack) {
		Calendar sunrise = threePack.getSunrise();
		Calendar culmination = threePack.getCulmination();
		Calendar sunset = threePack.getSunset();

		this.lengthOfDay = sunset.getTimeInMillis() - sunrise.getTimeInMillis();
		this.sunrise = sunrise.getTime();
		this.culmination = culmination.getTime();
		this.sunset = sunset.getTime();
		this.currentDayInYear = culmination.get(Calendar.DAY_OF_YEAR);
	}

	public int getCurrentDayInYear() {
		return currentDayInYear;
	}

	public Date getSunrise() {
		return sunrise;
	}

	public Date getCulmination() {
		return culmination;
	}

	public Date getSunset() {
		return sunset;
	}

	public long getLengthOfDay() {
		return lengthOfDay;
	}

	public long getLongerThanTheShortestDayOfYear() {
		return longerThanTheShortestDayOfYear;
	}

	public void setLongerThanTheShortestDayOfYear(long longerThanTheShortestDayOfYear) {
		this.longerThanTheShortestDayOfYear = longerThanTheShortestDayOfYear;
	}

	public long getShorterThanTheLongestDayOfYear() {
		return shorterThanTheLongestDayOfYear;
	}

	public void setShorterThanTheLongestDayOfYear(long shorterThanTheLongestDayOfYear) {
		this.shorterThanTheLongestDayOfYear = shorterThanTheLongestDayOfYear;
	}

	public void setSunrise(Date sunrise) {
		this.sunrise = sunrise;
	}

	public void setCulmination(Date culmination) {
		this.culmination = culmination;
	}

	public void setSunset(Date sunset) {
		this.sunset = sunset;
	}

	public static String getSunsetSunriseDiffDescribe(long diffInMillis) {
		long diffInMin = diffInMillis / 60000;
		long diffInHours = (diffInMin / 60);
		diffInMin = (diffInMin % 60);
		StringBuffer sb = new StringBuffer();
		sb.append(diffInHours);
		sb.append("h ");
		sb.append(diffInMin);
		sb.append("m");
		return sb.toString();
	}
}
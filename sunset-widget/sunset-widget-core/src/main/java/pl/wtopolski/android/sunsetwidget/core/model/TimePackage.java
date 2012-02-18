package pl.wtopolski.android.sunsetwidget.core.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimePackage {
	private static final String DATE_DESCRIBE_PATTERN = "dd MMMM yyyy";
	private static final String DAY_DESCRIBE_PATTERN = "EEEE";
	
	private final int currentDayInYear;
	private final String describe;
	private final String dayName;
    private final Date sunrise;
    private final Date culmination;
    private final Date sunset;
    private final long lengthOfDay;
    private long longerThanTheShortestDayOfYear;
    private long shorterThanTheLongestDayOfYear;

	public TimePackage(ThreePack<Calendar> threePack) {
    	final SimpleDateFormat dateDescribeFormater = new SimpleDateFormat(DATE_DESCRIBE_PATTERN);
    	final SimpleDateFormat dayDescribeFormater = new SimpleDateFormat(DAY_DESCRIBE_PATTERN);
	
    	Calendar sunrise = threePack.getSunrise();
    	Calendar culmination = threePack.getCulmination();
    	Calendar sunset = threePack.getSunset();
    	
    	this.lengthOfDay = sunset.getTimeInMillis() - sunrise.getTimeInMillis();
    	this.sunrise = sunrise.getTime();
    	this.culmination = culmination.getTime();
    	this.sunset = sunset.getTime();
    	this.currentDayInYear = culmination.get(Calendar.DAY_OF_YEAR);
    	this.describe = dateDescribeFormater.format(culmination.getTime());
    	this.dayName = dayDescribeFormater.format(culmination.getTime());
	}

	public String getDayName() {
		return dayName;
	}

	public String getDescribe() {
		return describe;
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

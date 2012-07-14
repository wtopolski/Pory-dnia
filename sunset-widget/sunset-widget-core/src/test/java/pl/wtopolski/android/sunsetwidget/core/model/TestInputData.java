package pl.wtopolski.android.sunsetwidget.core.model;

import pl.wtopolski.android.sunsetwidget.core.model.TimeZenit;

public class TestInputData {
	final private int year;
	final private int month;
	final private int day;
	final private TimeZenit timeZenit;

	public TestInputData(int year, int month, int day, TimeZenit timeZenit) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.timeZenit = timeZenit;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}
	
	public TimeZenit getTimeZenit() {
		return timeZenit;
	}

	@Override
	public String toString() {
		return "" + year + "-" + month + "-" + day + " " + timeZenit;
	}
}

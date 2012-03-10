package pl.wtopolski.android.sunsetwidget.core.model;

public class TimeConfig {
	private double latitude;
	private double longitude;
	private TimeZenit timeZenit;
	private TimeChange timeChange;

	public TimeConfig(double latitude, double longitude, TimeZenit timeZenit, TimeChange timeChange) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.timeZenit = timeZenit;
		this.timeChange = timeChange;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public TimeZenit getTimeZenit() {
		return timeZenit;
	}

	public void setTimeZenit(TimeZenit timeZenit) {
		this.timeZenit = timeZenit;
	}

	public TimeChange getTimeChange() {
		return timeChange;
	}

	public void setTimeChange(TimeChange timeChange) {
		this.timeChange = timeChange;
	}
}

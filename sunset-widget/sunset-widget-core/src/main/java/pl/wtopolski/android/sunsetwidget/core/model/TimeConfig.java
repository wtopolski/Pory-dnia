package pl.wtopolski.android.sunsetwidget.core.model;

public class TimeConfig {
	private double latitude;
	private double longitude;
	private TimeZenit timeZenit;

	public TimeConfig(double latitude, double longitude, TimeZenit timeZenit) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.timeZenit = timeZenit;
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
}

package pl.wtopolski.android.sunsetwidget.core.model;

public class ThreePack<T> {
	T sunrise;
	T culmination;
	T sunset;

	public ThreePack(T sunrise, T culmination, T sunset) {
		super();
		this.sunrise = sunrise;
		this.culmination = culmination;
		this.sunset = sunset;
	}

	public T getSunrise() {
		return sunrise;
	}

	public void setSunrise(T sunrise) {
		this.sunrise = sunrise;
	}

	public T getCulmination() {
		return culmination;
	}

	public void setCulmination(T culmination) {
		this.culmination = culmination;
	}

	public T getSunset() {
		return sunset;
	}

	public void setSunset(T sunset) {
		this.sunset = sunset;
	}
}

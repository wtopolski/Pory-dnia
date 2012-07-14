package pl.wtopolski.android.sunsetwidget.core.model;

public abstract class TextOutputData {
	private boolean shouldBeZero;
	
	public TextOutputData(boolean shouldBeZero) {
		super();
		this.shouldBeZero = shouldBeZero;
	}

	public boolean execute(double culmination, double sunrise, double sunset) {
		if (shouldBeZero) {
			return culmination == 0d && sunrise == 0d && sunset == 0d;
		} else {
			return culmination != 0d && sunrise != 0d && sunset != 0d;
		}
	}

	@Override
	public String toString() {
		return shouldBeZero ? "0" : "x";
	}
}

package pl.wtopolski.android.sunsetwidget.async;


public interface RestoreNativeStateListener {
	void restoreNativeStateCompleted();
	void showProgressbar();
	void hideProgressbar();
	void infoProgressbar(int percent);
}

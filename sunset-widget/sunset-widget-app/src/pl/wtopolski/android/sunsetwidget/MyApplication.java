package pl.wtopolski.android.sunsetwidget;

import android.app.Application;
import android.graphics.Typeface;

public class MyApplication extends Application {
	private static MyApplication myApplication;
	private static Typeface fontTypeface;
	
	@Override
	public void onCreate() {
		super.onCreate();
		myApplication = this;
		fontTypeface = Typeface.createFromAsset(getAssets(), getString(R.string.font_name));
	}
	
	public static MyApplication getMyApplication() {
		return myApplication;
	}
	
	public Typeface getTypeface() {
		return fontTypeface;
	}
}

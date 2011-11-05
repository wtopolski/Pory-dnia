package pl.wtopolski.android.sunsetwidget;

import roboguice.application.RoboApplication;

public class MyApplication extends RoboApplication {
	private static MyApplication myApplication;
	
	@Override
	public void onCreate() {
		super.onCreate();
		myApplication = this;
	}
	
	public static MyApplication getMyApplication() {
		return myApplication;
	}
}

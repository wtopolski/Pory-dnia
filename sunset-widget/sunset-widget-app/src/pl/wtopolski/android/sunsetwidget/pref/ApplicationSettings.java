package pl.wtopolski.android.sunsetwidget.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import pl.wtopolski.android.sunsetwidget.MyApplication;
import pl.wtopolski.android.sunsetwidget.R;
import pl.wtopolski.android.sunsetwidget.core.model.TimeZenit;
import pl.wtopolski.android.sunsetwidget.view.PresentationView;

public class ApplicationSettings {
	public static TimeZenit getTimeZenitSettings() {
		MyApplication myApplication = MyApplication.getMyApplication();
		Context context = myApplication.getApplicationContext();
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

		String key = context.getString(R.string.preferences_sun_altitude_key);
		String defaultValue = context.getString(R.string.preferences_sun_altitude_default_value);
		String value = sharedPreferences.getString(key, defaultValue);
		
		return TimeZenit.valueOf(value);
	}

	public static PresentationView getPresentationViewSettings() {
		MyApplication myApplication = MyApplication.getMyApplication();
		Context context = myApplication.getApplicationContext();
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

		String key = context.getString(R.string.preferences_presentation_view_key);
		String defaultValue = context.getString(R.string.preferences_presentation_view_default_value);
		String value = sharedPreferences.getString(key, defaultValue);
		
		return PresentationView.valueOf(value);
	}
}

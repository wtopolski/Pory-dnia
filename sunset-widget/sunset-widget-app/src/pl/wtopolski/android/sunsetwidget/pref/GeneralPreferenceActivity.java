package pl.wtopolski.android.sunsetwidget.pref;

import pl.wtopolski.android.sunsetwidget.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class GeneralPreferenceActivity extends PreferenceActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.general_preferences);
	}
}

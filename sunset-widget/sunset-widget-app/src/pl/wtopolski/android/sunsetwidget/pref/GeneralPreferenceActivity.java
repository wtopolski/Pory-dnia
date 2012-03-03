package pl.wtopolski.android.sunsetwidget.pref;

import pl.wtopolski.android.sunsetwidget.R;
import pl.wtopolski.android.sunsetwidget.util.actionbar.ActionBarPreferenceActivity;
import android.os.Bundle;

public class GeneralPreferenceActivity extends ActionBarPreferenceActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.general_preferences);
	}
}

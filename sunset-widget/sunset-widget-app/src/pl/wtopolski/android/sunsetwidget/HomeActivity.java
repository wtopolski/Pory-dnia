package pl.wtopolski.android.sunsetwidget;

import pl.wtopolski.android.sunsetwidget.fragment.HomeFragment;
import pl.wtopolski.android.sunsetwidget.util.actionbar.ActionBarFragmentActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

public class HomeActivity extends ActionBarFragmentActivity {
	protected static final String LOG_TAG = HomeActivity.class.getSimpleName();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		Fragment homeFragment = new HomeFragment();
		transaction.replace(R.id.homeFragment, homeFragment);
		transaction.commit();
	}
}

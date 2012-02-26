package pl.wtopolski.android.sunsetwidget;

import pl.wtopolski.android.sunsetwidget.util.actionbar.ActionBarFragmentActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

public class HomeActivity extends ActionBarFragmentActivity {
    protected static final String LOG_TAG = HomeActivity.class.getSimpleName();

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment productDetails = new HomeFragment();
        fragmentTransaction.replace(R.id.mainFragement, productDetails);
        fragmentTransaction.commit();
    }
}

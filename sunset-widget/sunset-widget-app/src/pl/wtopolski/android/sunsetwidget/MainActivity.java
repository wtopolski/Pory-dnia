package pl.wtopolski.android.sunsetwidget;

import pl.wtopolski.android.sunsetwidget.fragment.MainFragment;
import pl.wtopolski.android.sunsetwidget.provider.SharedPreferencesStorage;
import pl.wtopolski.android.sunsetwidget.util.FlowManager;
import pl.wtopolski.android.sunsetwidget.util.actionbar.ActionBarFragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends ActionBarFragmentActivity {
    protected static final String LOG_TAG = MainActivity.class.getSimpleName();
    
    private MainFragment mainFragment;
    private final static String FRAGMENT_TAG = "MAIN_FRAGMENT_TAG";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setTitle(R.string.place);
        
        boolean isContentLoaded = SharedPreferencesStorage.getBoolean(this, SharedPreferencesStorage.IS_CONTENT_LOADED);
        boolean isMainSelected = SharedPreferencesStorage.getBoolean(this, SharedPreferencesStorage.IS_MAIN_SELECTED);
        if (!isContentLoaded || !isMainSelected) {
        	startActivity(new Intent(this, ConfListActivity.class));
        	finish();
        	return;
        }
        
        Intent intent = getIntent();
        int locationId = intent.getIntExtra(MainFragment.LOCATION_ID, MainFragment.LOCATION_UNKNOWN);
        Bundle bundle = new Bundle();
        bundle.putInt(MainFragment.LOCATION_ID, locationId);
        
        if (savedInstanceState != null) {
        	mainFragment = (MainFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        } else {
        	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        	mainFragment = new MainFragment();
        	mainFragment.setArguments(bundle);
            transaction.replace(R.id.mainFragment, mainFragment, FRAGMENT_TAG);
            transaction.commit();
        }
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
            	FlowManager.goToHome(this);
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }
}

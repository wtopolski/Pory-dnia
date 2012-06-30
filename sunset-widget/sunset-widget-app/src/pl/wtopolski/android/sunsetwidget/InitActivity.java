package pl.wtopolski.android.sunsetwidget;

import pl.wtopolski.android.sunsetwidget.async.InitLoader;
import pl.wtopolski.android.sunsetwidget.fragment.InitListFragment;
import pl.wtopolski.android.sunsetwidget.fragment.InitProgressFragment;
import pl.wtopolski.android.sunsetwidget.fragment.OnLocationItemSelected;
import pl.wtopolski.android.sunsetwidget.model.GPSLocation;
import pl.wtopolski.android.sunsetwidget.util.LocationManager;
import pl.wtopolski.android.sunsetwidget.util.LocationManagerImpl;
import pl.wtopolski.android.sunsetwidget.util.actionbar.ActionBarFragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

public class InitActivity extends ActionBarFragmentActivity implements LoaderManager.LoaderCallbacks<Boolean>, OnLocationItemSelected {
	protected static final String LOG_TAG = InitActivity.class.getSimpleName();
	
	private static final int INIT_LOADER_ID = 0;

	private LoaderManager loaderManager;
	private LocationManager locationManager;
	private FragmentManager fragmentManager;

    private Fragment fragment;
    private final static String FRAGMENT_TAG = "FRAGMENT_TAG";
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init);
        
    	locationManager = new LocationManagerImpl();
        loaderManager = getSupportLoaderManager();
        fragmentManager = getSupportFragmentManager();

    	fragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG);
    	if (fragment == null) {
    		fragment = useProgressFragment();
        }
    }
    
    private Fragment useProgressFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = new InitProgressFragment();
        transaction.replace(R.id.initFragment, fragment, FRAGMENT_TAG);
        transaction.commit();
        return fragment;
	}

	@Override
    protected void onStart() {
    	super.onStart();
        loaderManager.initLoader(INIT_LOADER_ID, null, this);
    }

	public Loader<Boolean> onCreateLoader(int id, Bundle args) {
		if (id == INIT_LOADER_ID) {
			return new InitLoader(this);
		}
		return null;
	}

	public void onLoadFinished(Loader<Boolean> loader, Boolean data) {
		if (shouldReplaceFramgentByList()) {
	    	FragmentTransaction transaction = fragmentManager.beginTransaction();
	    	transaction.remove(fragment);
	    	fragment = new InitListFragment();
	        transaction.replace(R.id.initFragment, fragment, FRAGMENT_TAG);
	        transaction.commitAllowingStateLoss();
		}

	}

	private boolean shouldReplaceFramgentByList() {
		return fragment != null && !(fragment instanceof InitListFragment);
	}

	public void onLoaderReset(Loader<Boolean> loader) {
		// TODO Auto-generated method stub
	}

	public void onLocationItemSelected(int id) {
		GPSLocation location = locationManager.getLocation(id);
		locationManager.selectAsMain(location);
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}
}

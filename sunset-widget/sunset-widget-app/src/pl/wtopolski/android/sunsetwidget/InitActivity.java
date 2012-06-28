package pl.wtopolski.android.sunsetwidget;

import pl.wtopolski.android.sunsetwidget.async.InitLoader;
import pl.wtopolski.android.sunsetwidget.fragment.InitListFragment;
import pl.wtopolski.android.sunsetwidget.fragment.InitProgressFragment;
import pl.wtopolski.android.sunsetwidget.fragment.OnLocationsSelected;
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
import android.util.Log;

public class InitActivity extends ActionBarFragmentActivity implements LoaderManager.LoaderCallbacks<Boolean>, OnLocationsSelected {
	protected static final String LOG_TAG = InitActivity.class.getSimpleName();
	
	private static final int INIT_LOADER_ID = 0;

	private LoaderManager loaderManager;
	private LocationManager locationManager;
	private FragmentManager fragmentManager;

    private Fragment fragment;
    private final static String FRAGMENT_TAG = "INIT_FRAGMENT_TAG";
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init);
        
    	locationManager = new LocationManagerImpl();
        loaderManager = getSupportLoaderManager();
        fragmentManager = getSupportFragmentManager();

    	fragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG);
    	
        if (fragment == null) {
        	FragmentTransaction transaction = fragmentManager.beginTransaction();
        	fragment = new InitProgressFragment();
            transaction.replace(R.id.initFragment, fragment, FRAGMENT_TAG);
            transaction.commit();
        }
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
        loaderManager.initLoader(INIT_LOADER_ID, null, this);
    }

	public Loader<Boolean> onCreateLoader(int id, Bundle args) {
		Log.d("wtopolski", "onCreateLoader");
		if (id == INIT_LOADER_ID) {
			return new InitLoader(this);
		}
		return null;
	}

	public void onLoadFinished(Loader<Boolean> loader, Boolean data) {
		Log.d("wtopolski", "onLoadFinished");
		if (fragment == null || !(fragment instanceof InitListFragment)) {
	    	FragmentTransaction transaction = fragmentManager.beginTransaction();
	    	transaction.remove(fragment);
	    	fragment = new InitListFragment();
	        transaction.replace(R.id.initFragment, fragment, FRAGMENT_TAG);
	        transaction.commitAllowingStateLoss();
		}

	}

	public void onLoaderReset(Loader<Boolean> loader) {
		// TODO Auto-generated method stub
	}

	public void onLocationSelected(int id) {
		GPSLocation location = locationManager.getLocation(id);
		locationManager.selectAsMain(location);
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}
}

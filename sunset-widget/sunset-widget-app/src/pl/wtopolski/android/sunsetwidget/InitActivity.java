package pl.wtopolski.android.sunsetwidget;

import pl.wtopolski.android.sunsetwidget.async.InitLoader;
import pl.wtopolski.android.sunsetwidget.model.GPSLocation;
import pl.wtopolski.android.sunsetwidget.util.LocationManager;
import pl.wtopolski.android.sunsetwidget.util.LocationManagerImpl;
import pl.wtopolski.android.sunsetwidget.util.actionbar.ActionBarFragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.TextView;

public class InitActivity extends ActionBarFragmentActivity implements LoaderManager.LoaderCallbacks<Boolean> {
	protected static final String LOG_TAG = InitActivity.class.getSimpleName();
	
	private static final int INIT_LOADER_ID = 0;

	private TextView message;
	private LoaderManager loaderManager;
	private LocationManager locationManager;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init);
        
        message = (TextView) findViewById(R.id.message);

    	locationManager = new LocationManagerImpl();
    	
        loaderManager = getSupportLoaderManager();
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
        loaderManager.initLoader(INIT_LOADER_ID, null, this);
    }

	private void selectLocationAsMain(int locationId) {
		Log.d("wtopolski", "selectLocationAsMain");
		GPSLocation location = locationManager.getLocation(locationId);
		locationManager.selectAsMain(location);
		startActivity(new Intent(this, MainActivity.class));
		finish();
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
		selectLocationAsMain(1);
	}

	public void onLoaderReset(Loader<Boolean> loader) {
		// TODO Auto-generated method stub
		
	}
}

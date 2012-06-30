package pl.wtopolski.android.sunsetwidget.async;

import pl.wtopolski.android.sunsetwidget.R;
import pl.wtopolski.android.sunsetwidget.util.DataLoader;
import pl.wtopolski.android.sunsetwidget.util.DataLoaderImpl;
import pl.wtopolski.android.sunsetwidget.util.DataLoaderListener;
import pl.wtopolski.android.sunsetwidget.util.LocationManager;
import pl.wtopolski.android.sunsetwidget.util.LocationManagerImpl;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

public class InitLoader extends AsyncTaskLoader<Boolean> implements DataLoaderListener {
	protected static final String LOG_TAG = InitLoader.class.getSimpleName();
	
	public static String INIT_LOADER_BROADCAST_ACTION = "INIT_LOADER_BROADCAST_ACTION";
	public static String INIT_LOADER_KEY = "INIT_LOADER_KEY";
	private static float COUNT_OF_ALL_LOCATIONS = 439f;
	
	private float progressState;
	private boolean result;

	public InitLoader(Context context) {
		super(context);
		progressState = 0;
		result = false;
	}

	@Override
	public Boolean loadInBackground() {
		try {
			LocationManager locationManager = new LocationManagerImpl();
			if (locationManager.getCount() == 0) {
				locationManager.deleteAll();
				DataLoader dataLoader = new DataLoaderImpl(this);
				dataLoader.fill(locationManager, R.xml.places);
			}

    		return true;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error", e);
    		return false;
        }
	}
	
	@Override
	protected void onStartLoading() {
		super.onStartLoading();
		if (result) {
			deliverResult(result);
		} else {
			forceLoad();
		}
	}
	
	@Override
	public void deliverResult(Boolean data) {
		super.deliverResult(data);
		result = data;
	}

	public void locationAdded() {
		int percent = (int)((++progressState / COUNT_OF_ALL_LOCATIONS) * 100f);
		Intent intent = new Intent(INIT_LOADER_BROADCAST_ACTION);
		intent.putExtra(INIT_LOADER_KEY, percent);
		getContext().sendBroadcast(intent);
	}
}

package pl.wtopolski.android.sunsetwidget;

import pl.wtopolski.android.sunsetwidget.adapter.LocationListAdapter;
import pl.wtopolski.android.sunsetwidget.async.RestoreNativeStateAsyncTask;
import pl.wtopolski.android.sunsetwidget.async.RestoreNativeStateListener;
import pl.wtopolski.android.sunsetwidget.model.GPSLocation;
import pl.wtopolski.android.sunsetwidget.provider.SharedPreferencesStorage;
import pl.wtopolski.android.sunsetwidget.util.LocationManager;
import pl.wtopolski.android.sunsetwidget.util.LocationManagerImpl;
import pl.wtopolski.android.sunsetwidget.util.actionbar.ActionBarListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ConfListActivity extends ActionBarListActivity implements RestoreNativeStateListener {
	protected static final String LOG_TAG = ConfListActivity.class.getSimpleName();

	private ProgressBar loadingBar;
	private TextView step;
	private TextView message;
	private TextView loadingBarDesc;
	
	private RestoreNativeStateAsyncTask loadingAsyncTask;
	private LocationManager locationManager;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conf);
        
        loadingBar = (ProgressBar) findViewById(R.id.loadingBar);
        step = (TextView) findViewById(R.id.step);
        message = (TextView) findViewById(R.id.message);
        loadingBarDesc = (TextView) findViewById(R.id.loadingBarDesc);
        
        step.setText("Wstępna konfiguracja - Krok 1");
        message.setText("Proszę czekać, trwa ładowanie listy miast...");
        
        final Object data = getLastNonConfigurationInstance();
        if (data == null) {
        	boolean isContentLoaded = SharedPreferencesStorage.getBoolean(this, SharedPreferencesStorage.IS_CONTENT_LOADED);
            if (isContentLoaded) {
            	showList();
            } else {
            	loadingAsyncTask = new RestoreNativeStateAsyncTask();
            	loadingAsyncTask.setListener(this);
            	loadingAsyncTask.execute();
            }
        } else {
        	showProgressbar();
        	loadingAsyncTask = (RestoreNativeStateAsyncTask) data;
        	loadingAsyncTask.setListener(this);
        }
    }

	@Override
    public Object onRetainNonConfigurationInstance() {
    	if (loadingAsyncTask != null) {
    		loadingAsyncTask.setListener(null);
    	}
        return loadingAsyncTask;
    }
    
    private void showList() {
        step.setText("Wstępna konfiguracja - Krok 2");
        message.setText("Proszę wybrać miast, które znajduje się najbliżej Twojej lokalizacji.");
        
    	locationManager = new LocationManagerImpl();
        locationManager.setContext(this);
        Cursor cursor = locationManager.getAllLocationsIdByCursor();
        startManagingCursor(cursor);
        LocationListAdapter adapter = new LocationListAdapter(this, R.layout.locations_item, cursor, null);
        setListAdapter(adapter);
		getListView().setVisibility(View.VISIBLE);
	}
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	int locationId = (int) id;
		selectLocationAsMain(locationId);
		startActivity(new Intent(this, MainActivity.class));
		finish();
    }

	private void selectLocationAsMain(int locationId) {
		GPSLocation location = locationManager.getLocation(locationId);
		locationManager.selectAsMain(location);
		SharedPreferencesStorage.setBoolean(this, SharedPreferencesStorage.IS_MAIN_SELECTED, true);
	}

	public void restoreNativeStateCompleted() {
		loadingAsyncTask.setListener(null);
		loadingAsyncTask = null;
		showList();
	}

	public void showProgressbar() {
		loadingBar.setVisibility(View.VISIBLE);
		loadingBarDesc.setVisibility(View.VISIBLE);
		getListView().setVisibility(View.GONE);
	}

	public void hideProgressbar() {
		loadingBar.setVisibility(View.GONE);
		loadingBarDesc.setVisibility(View.GONE);
		getListView().setVisibility(View.VISIBLE);
	}

	public void infoProgressbar(int percent) {
		loadingBarDesc.setText(percent + "%");
		loadingBar.setProgress(percent);
	}
}

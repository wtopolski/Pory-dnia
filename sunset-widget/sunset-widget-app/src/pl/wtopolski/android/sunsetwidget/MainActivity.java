package pl.wtopolski.android.sunsetwidget;

import pl.wtopolski.android.sunsetwidget.adapter.PresenterPagerAdapter;
import pl.wtopolski.android.sunsetwidget.model.GPSLocation;
import pl.wtopolski.android.sunsetwidget.provider.SharedPreferencesStorage;
import pl.wtopolski.android.sunsetwidget.util.FlowManager;
import pl.wtopolski.android.sunsetwidget.util.LocationManager;
import pl.wtopolski.android.sunsetwidget.util.LocationManagerImpl;
import pl.wtopolski.android.sunsetwidget.util.actionbar.ActionBarActivity;
import pl.wtopolski.android.sunsetwidget.view.PresenterPageIndicatorView;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
    protected static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ViewPager awesomePager;
    private PresenterPagerAdapter presenterPagerAdapter;
    
    public static final String LOCATION_ID = "LOCATION_ID";
    public static final int LOCATION_UNKNOWN = -1;

	private TextView cityView;
	private TextView provinceView;
	
    private LocationManager locationManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        cityView = (TextView) findViewById(R.id.city);
        provinceView = (TextView) findViewById(R.id.province);
        
        boolean isContentLoaded = SharedPreferencesStorage.getBoolean(this, SharedPreferencesStorage.IS_CONTENT_LOADED);
        boolean isMainSelected = SharedPreferencesStorage.getBoolean(this, SharedPreferencesStorage.IS_MAIN_SELECTED);
        if (!isContentLoaded || !isMainSelected) {
        	startActivity(new Intent(this, ConfListActivity.class));
        	finish();
        	return;
        }
        
        locationManager = new LocationManagerImpl();
        locationManager.setContext(getApplicationContext());
        GPSLocation gpsLocation = null;
        
        Intent intent = getIntent();
        int locationId = intent.getIntExtra(LOCATION_ID, LOCATION_UNKNOWN);
        if (locationId == LOCATION_UNKNOWN) {
        	gpsLocation = locationManager.getMainLocation();
        } else {
        	gpsLocation = locationManager.getLocation(locationId);
        }
        
        String name = gpsLocation.getName();
    	cityView.setText(name);
    	
    	String province = gpsLocation.getProvince();
    	provinceView.setText(province);

        presenterPagerAdapter = new PresenterPagerAdapter(this, gpsLocation);
        awesomePager = (ViewPager) findViewById(R.id.awesomepager);
        awesomePager.setAdapter(presenterPagerAdapter);
        
        PresenterPageIndicatorView indicator = (PresenterPageIndicatorView)findViewById(R.id.indicator);
        
        awesomePager.setOnPageChangeListener(indicator);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    public void showList(View view) {
    	startActivity(new Intent(this, LocationsListActivity.class));
    	finish();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            	FlowManager.goToHome(this);
                break;

            /*
            case R.id.menu_refresh:
                Toast.makeText(this, "Fake refreshing...", Toast.LENGTH_SHORT).show();
                getActionBarHelper().setRefreshActionItemState(true);
                getWindow().getDecorView().postDelayed(
                        new Runnable() {
                            public void run() {
                                getActionBarHelper().setRefreshActionItemState(false);
                            }
                        }, 1000);
                break;
            */
        }
        return super.onOptionsItemSelected(item);
    }
}

package pl.wtopolski.android.sunsetwidget;

import java.text.SimpleDateFormat;

import pl.wtopolski.android.sunsetwidget.adapter.PresenterPagerAdapter;
import pl.wtopolski.android.sunsetwidget.core.model.TimePackage;
import pl.wtopolski.android.sunsetwidget.model.GPSLocation;
import pl.wtopolski.android.sunsetwidget.provider.SharedPreferencesStorage;
import pl.wtopolski.android.sunsetwidget.util.FlowManager;
import pl.wtopolski.android.sunsetwidget.util.LocationManager;
import pl.wtopolski.android.sunsetwidget.util.LocationManagerImpl;
import pl.wtopolski.android.sunsetwidget.util.actionbar.ActionBarActivity;
import pl.wtopolski.android.sunsetwidget.view.PresenterPageIndicatorView;
import pl.wtopolski.android.sunsetwidget.view.PresenterPageIndicatorView.Item;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends ActionBarActivity {
    protected static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ViewPager viewPager;
    private PresenterPagerAdapter presenterPagerAdapter;
    
    public static final String LOCATION_ID = "LOCATION_ID";
    public static final int LOCATION_UNKNOWN = -1;
	
    private LocationManager locationManager;
    private GPSLocation gpsLocation;
    
	private static final String DATE_DESCRIBE_PATTERN = "dd MMMM yyyy";
	private static final String DAY_DESCRIBE_PATTERN = "EEEE";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        boolean isContentLoaded = SharedPreferencesStorage.getBoolean(this, SharedPreferencesStorage.IS_CONTENT_LOADED);
        boolean isMainSelected = SharedPreferencesStorage.getBoolean(this, SharedPreferencesStorage.IS_MAIN_SELECTED);
        if (!isContentLoaded || !isMainSelected) {
        	startActivity(new Intent(this, ConfListActivity.class));
        	finish();
        	return;
        }
        
        locationManager = new LocationManagerImpl();
        locationManager.setContext(getApplicationContext());

        Intent intent = getIntent();
        int locationId = intent.getIntExtra(LOCATION_ID, LOCATION_UNKNOWN);
        if (locationId == LOCATION_UNKNOWN) {
        	gpsLocation = locationManager.getMainLocation();
        } else {
        	gpsLocation = locationManager.getLocation(locationId);
        }
        
        setTitle("Miejsce");

        presenterPagerAdapter = new PresenterPagerAdapter(this, gpsLocation);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(presenterPagerAdapter);
        
        PresenterPageIndicatorView indicator = (PresenterPageIndicatorView)findViewById(R.id.presenterPageIndicator);
        
        TimePackage[] timePackage = presenterPagerAdapter.getTimePackages();
        prapareTabs(timePackage, indicator);
        
        viewPager.setOnPageChangeListener(indicator);
    }

    private String[] prapareTabs(TimePackage[] timePackages, PresenterPageIndicatorView indicator) {
		final SimpleDateFormat dateDescribeFormater = new SimpleDateFormat(DATE_DESCRIBE_PATTERN);
		final SimpleDateFormat dayDescribeFormater = new SimpleDateFormat(DAY_DESCRIBE_PATTERN);
		
		String[] tabs = new String[] {"Dziś", "Jutro", "Tydzień", "Miesiąc", "Kwartał"};
		
		if (timePackages.length != tabs.length) {
			throw new RuntimeException("Wrong size of arrays!");
		}
		
		for (int index = 0; index < timePackages.length; index++) {
	    	String describe = dateDescribeFormater.format(timePackages[index].getCulmination());
	    	String dayName = dayDescribeFormater.format(timePackages[index].getCulmination());
	    	
	    	Item item = indicator.new Item(tabs[index] + " - " + gpsLocation.getName(), dayName + " " + describe); 
	    	indicator.addItem(item);
		}
		
		return tabs;
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

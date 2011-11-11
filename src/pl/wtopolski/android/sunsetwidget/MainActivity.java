package pl.wtopolski.android.sunsetwidget;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pl.wtopolski.android.sunsetwidget.model.Location;
import pl.wtopolski.android.sunsetwidget.model.TimePackage;
import pl.wtopolski.android.sunsetwidget.util.LocationManager;
import pl.wtopolski.android.sunsetwidget.util.LocationManagerImpl;
import pl.wtopolski.android.sunsetwidget.util.TimesCalculator;
import pl.wtopolski.android.sunsetwidget.util.TimesCalculatorImpl;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends RoboActivity {
    protected static final String LOG_TAG = MainActivity.class.getSimpleName();
    
    public static final String LOCATION_ID = "LOCATION_ID";
    public static final int LOCATION_UNKNOWN = -1;

	@InjectView(R.id.city)
	private TextView cityView;

	@InjectView(R.id.province)
	private TextView provinceView;
	
	@InjectView(R.id.sunrise)
	private TextView sunriseView;

	@InjectView(R.id.culmination)
	private TextView culminationView;

	@InjectView(R.id.sunset)
	private TextView sunsetView;
	
    private LocationManager locationManager;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        locationManager = new LocationManagerImpl();
        locationManager.setContext(getApplicationContext());
        Location location = null;
        
        Intent intent = getIntent();
        int locationId = intent.getIntExtra(LOCATION_ID, LOCATION_UNKNOWN);
        if (locationId == LOCATION_UNKNOWN) {
        	location = locationManager.getMainLocation();
        } else {
        	location = locationManager.getLocation(locationId);
        }
            	
    	cityView.setText(location.getName());
    	provinceView.setText(location.getProvince());
    	
    	Calendar calendarNow = TimesCalculator.createCalendarForNow();

        TimesCalculator calculator = new TimesCalculatorImpl();

        android.location.Location gpsLocation = new android.location.Location("my");
        gpsLocation.setLatitude(location.getLatitude());
        gpsLocation.setLongitude(location.getLongitude());

        TimePackage times = calculator.determineForDayAndLocation(calendarNow, gpsLocation);
       
        String sunrise = formatDate(times.getSunrise());
        String culmination = formatDate(times.getCulmination());
        String sunset = formatDate(times.getSunset());
        
        sunriseView.setText(sunrise);
        culminationView.setText(culmination);
        sunsetView.setText(sunset);
    }
    
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }
    
    public void showList(View view) {
    	startActivity(new Intent(this, LocationsListActivity.class));
    	finish();
    }
}

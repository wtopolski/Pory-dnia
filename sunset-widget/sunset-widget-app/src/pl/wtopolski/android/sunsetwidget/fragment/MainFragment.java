package pl.wtopolski.android.sunsetwidget.fragment;

import java.text.SimpleDateFormat;

import pl.wtopolski.android.sunsetwidget.R;
import pl.wtopolski.android.sunsetwidget.adapter.PresenterPagerAdapter;
import pl.wtopolski.android.sunsetwidget.core.model.TimePackage;
import pl.wtopolski.android.sunsetwidget.model.GPSLocation;
import pl.wtopolski.android.sunsetwidget.util.LocationManager;
import pl.wtopolski.android.sunsetwidget.util.LocationManagerImpl;
import pl.wtopolski.android.sunsetwidget.view.PresenterPageIndicatorView;
import pl.wtopolski.android.sunsetwidget.view.PresenterPageIndicatorView.Item;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment {
	protected static final String LOG_TAG = MainFragment.class.getSimpleName();
    
    public static final String LOCATION_ID = "LOCATION_ID";
    public static final int LOCATION_UNKNOWN = -1;
    public static final String DATE_DESCRIBE_PATTERN = "dd MMMM yyyy";
    public static final String DAY_DESCRIBE_PATTERN = "EEEE";
	
    private LocationManager locationManager;
    private GPSLocation gpsLocation;
    private PresenterPagerAdapter presenterPagerAdapter;
    private ViewPager viewPager;
    private PresenterPageIndicatorView pageIndicatorView;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        locationManager = new LocationManagerImpl();
        locationManager.setContext(getActivity().getApplicationContext());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_fragment, container, false);
		viewPager = (ViewPager) view.findViewById(R.id.viewPager);
		pageIndicatorView = (PresenterPageIndicatorView) view.findViewById(R.id.presenterPageIndicator);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		int locationId = getArguments().getInt(LOCATION_ID);
        if (locationId == LOCATION_UNKNOWN) {
        	gpsLocation = locationManager.getMainLocation();
        } else {
        	gpsLocation = locationManager.getLocation(locationId);
        }
        
        presenterPagerAdapter = new PresenterPagerAdapter(gpsLocation);

        TimePackage[] timePackage = presenterPagerAdapter.getTimePackages();
        prapareTabs(timePackage, pageIndicatorView);

        viewPager.setAdapter(presenterPagerAdapter);
        viewPager.setOnPageChangeListener(pageIndicatorView);
	}

    private String[] prapareTabs(TimePackage[] timePackages, PresenterPageIndicatorView indicator) {
		final SimpleDateFormat dateDescribeFormater = new SimpleDateFormat(DATE_DESCRIBE_PATTERN);
		final SimpleDateFormat dayDescribeFormater = new SimpleDateFormat(DAY_DESCRIBE_PATTERN);
		
		String[] tabs = new String[] {"Dziś", "Jutro", "Tydzień", "Miesiąc", "Kwartał", "Pół roku"};
		
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
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
}

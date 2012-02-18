package pl.wtopolski.android.sunsetwidget.adapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pl.wtopolski.android.sunsetwidget.R;
import pl.wtopolski.android.sunsetwidget.core.TimePackageUTCCreator;
import pl.wtopolski.android.sunsetwidget.core.model.TimePackage;
import pl.wtopolski.android.sunsetwidget.model.GPSLocation;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PresenterPagerAdapter extends PagerAdapter {
    protected static final String LOG_TAG = PresenterPagerAdapter.class.getSimpleName();

    private static int NUM_VIEWS = 5;
	private LayoutInflater layoutInflater;
	private GPSLocation gpsLocation;
    
    public PresenterPagerAdapter(Context context, GPSLocation gpsLocation) {
    	this.gpsLocation = gpsLocation;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
	
	@Override
	public int getCount() {
		return NUM_VIEWS;
	}

	@Override
	public Object instantiateItem(View collection, int position) {
        View view = layoutInflater.inflate(R.layout.presenter_page, null);

    	TimePackageUTCCreator calculator = new TimePackageUTCCreator();
    	Calendar calendarNow = calculator.prepareCalendar();
    	
    	if (position == 0 || position == 1) {
    		calendarNow.add(Calendar.HOUR, 24*position);
    	} else if (position == 2) {
    		calendarNow.add(Calendar.HOUR, 24*7);
    	} else if (position == 3) {
    		calendarNow.add(Calendar.MONTH, 1);
    	} else {
    		calendarNow.add(Calendar.MONTH, 3);
    	}
        
    	TimePackage times = calculator.prepareTimePackage(calendarNow, gpsLocation.convertToTimeLocation());
       
        String sunrise = formatDate(times.getSunrise());
        String culmination = formatDate(times.getCulmination());
        String sunset = formatDate(times.getSunset());

        TextView sunriseView = (TextView) view.findViewById(R.id.sunrise);
        TextView culminationView = (TextView) view.findViewById(R.id.culmination);
        TextView sunsetView = (TextView) view.findViewById(R.id.sunset);
        TextView dateDescribe = (TextView) view.findViewById(R.id.dateDescribe);
        TextView dayLength = (TextView) view.findViewById(R.id.dayLength);
        TextView dayInYear = (TextView) view.findViewById(R.id.dayInYear);
        TextView longerThanTheShortestDayOfYear = (TextView) view.findViewById(R.id.longerThanTheShortestDayOfYear);
        TextView shorterThanTheLongestDayOfYear = (TextView) view.findViewById(R.id.shorterThanTheLongestDayOfYear);
        
        sunriseView.setText(sunrise);
        culminationView.setText(culmination);
        sunsetView.setText(sunset);
        dateDescribe.setText(times.getDayName() + ", " + times.getDescribe());
        dayLength.setText(TimePackage.getSunsetSunriseDiffDescribe(times.getLengthOfDay()));
        dayInYear.setText(""+ times.getCurrentDayInYear());
        longerThanTheShortestDayOfYear.setText(TimePackage.getSunsetSunriseDiffDescribe(times.getLongerThanTheShortestDayOfYear()));
        shorterThanTheLongestDayOfYear.setText(TimePackage.getSunsetSunriseDiffDescribe(times.getShorterThanTheLongestDayOfYear()));
        
		((ViewPager) collection).addView(view, 0);

		return view;
	}
    
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }

	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewPager) collection).removeView((LinearLayout) view);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((LinearLayout) object);
	}

	@Override
	public void finishUpdate(View arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public Parcelable saveState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
		// TODO Auto-generated method stub
	}
}
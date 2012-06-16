package pl.wtopolski.android.sunsetwidget.adapter;

import java.util.Calendar;

import pl.wtopolski.android.sunsetwidget.core.TimePackageCreator;
import pl.wtopolski.android.sunsetwidget.core.model.TimePackage;
import pl.wtopolski.android.sunsetwidget.model.GPSLocation;
import pl.wtopolski.android.sunsetwidget.pref.ApplicationSettings;
import pl.wtopolski.android.sunsetwidget.view.PresentationView;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

public class PresenterPagerAdapter extends PagerAdapter {
	protected static final String LOG_TAG = PresenterPagerAdapter.class.getSimpleName();

	private static int NUM_VIEWS = 6;
	private TimePackage[] timePackages;

	private PresentationView presentationView;

	public PresenterPagerAdapter(GPSLocation gpsLocation) {
		this.presentationView = ApplicationSettings.getPresentationViewSettings();

		timePackages = new TimePackage[NUM_VIEWS];

		TimePackageCreator calculator = new TimePackageCreator(gpsLocation.convertToTimeLocation());
		for (int position = 0; position < NUM_VIEWS; position++) {
			Calendar calendarNow = calculator.prepareCalendar();
			if (position == 0 || position == 1) {
				calendarNow.add(Calendar.HOUR, 24 * position);
			} else if (position == 2) {
				calendarNow.add(Calendar.HOUR, 24 * 7);
			} else if (position == 3) {
				calendarNow.add(Calendar.MONTH, 1);
			} else if (position == 4) {
				calendarNow.add(Calendar.MONTH, 3);
			} else {
				calendarNow.add(Calendar.MONTH, 6);
			}
			timePackages[position] = calculator.prepareTimePackage(calendarNow);
		}
	}

	@Override
	public int getCount() {
		return NUM_VIEWS;
	}

	public TimePackage[] getTimePackages() {
		return timePackages;
	}

	@Override
	public Object instantiateItem(View collection, int position) {
		View view = presentationView.getView(timePackages[position]);
		((ViewPager) collection).addView(view, 0);
		return view;
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
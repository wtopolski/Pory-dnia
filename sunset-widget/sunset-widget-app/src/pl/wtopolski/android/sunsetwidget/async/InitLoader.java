package pl.wtopolski.android.sunsetwidget.async;

import pl.wtopolski.android.sunsetwidget.Const;
import pl.wtopolski.android.sunsetwidget.MyApplication;
import pl.wtopolski.android.sunsetwidget.R;
import pl.wtopolski.android.sunsetwidget.util.DataLoader;
import pl.wtopolski.android.sunsetwidget.util.DataLoaderImpl;
import pl.wtopolski.android.sunsetwidget.util.LocationManager;
import pl.wtopolski.android.sunsetwidget.util.LocationManagerImpl;
import pl.wtopolski.android.sunsetwidget.util.DataLoaderListener;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

public class InitLoader extends AsyncTaskLoader<Boolean> implements DataLoaderListener {
	protected static final String LOG_TAG = InitLoader.class.getSimpleName();
	private float progressState;
	private Boolean result;

	public InitLoader(Context context) {
		super(context);
		Log.d("wtopolski", "InitLoader");
		// http://developer.android.com/reference/android/content/AsyncTaskLoader.html
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
		Log.d("wtopolski", "onStartLoading");
		
		if (result != null) {
			deliverResult(result);
		} else {
			forceLoad();
		}
	}
	
	@Override
	protected void onAbandon() {
		super.onAbandon();
	}
	
	@Override
	protected void onStopLoading() {
		super.onStopLoading();
		Log.d("wtopolski", "onStopLoading");
	}
	
	@Override
	public void deliverResult(Boolean data) {
		Log.d("wtopolski", "deliverResult");
		super.deliverResult(data);
		result = data;
	}

	public void locationAdded() {
		progressState += 1;
		int percent = (int)((progressState / 439f) * 100f);
        Log.d(LOG_TAG, "percent: " + percent);
		Log.d("wtopolski", "percent: " + percent);
	}
	
	@SuppressWarnings("unused")
	private void notifyByStatusBar() {
		MyApplication myApplication = MyApplication.getMyApplication();
		Context applicationContext = myApplication.getApplicationContext();
		
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) applicationContext.getSystemService(ns);
		
		CharSequence tickerText = applicationContext.getString(R.string.error_during_restoring_state_ticker);
		long when = System.currentTimeMillis();
		
		Notification notification = new Notification(android.R.drawable.stat_sys_warning, tickerText, when);
		
		CharSequence contentTitle = applicationContext.getString(R.string.error_during_restoring_state_title);
		CharSequence contentText = applicationContext.getString(R.string.error_during_restoring_state_msg);

		Intent intent = new Intent("pl.wtopolski.android.sunsetwidget.LocationsListActivity");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent contentIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0);
		
		notification.setLatestEventInfo(applicationContext, contentTitle, contentText, contentIntent);
		
		mNotificationManager.notify(Const.Notification.ERROR_DURING_RESTORING_STATE_ID, notification);
	}
}

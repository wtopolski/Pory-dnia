package pl.wtopolski.android.sunsetwidget.async;

import pl.wtopolski.android.sunsetwidget.Const;
import pl.wtopolski.android.sunsetwidget.MyApplication;
import pl.wtopolski.android.sunsetwidget.R;
import pl.wtopolski.android.sunsetwidget.provider.SharedPreferencesStorage;
import pl.wtopolski.android.sunsetwidget.util.DataLoader;
import pl.wtopolski.android.sunsetwidget.util.DataLoaderImpl;
import pl.wtopolski.android.sunsetwidget.util.LocationManager;
import pl.wtopolski.android.sunsetwidget.util.LocationManagerImpl;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class RestoreNativeStateAsyncTask extends AsyncTask<Void, Void, Boolean> {
	private static final String LOG_TAG = RestoreNativeStateAsyncTask.class.getSimpleName();
	
	private RestoreNativeStateListener listener;
	private boolean completed;
	
	
	public RestoreNativeStateAsyncTask() {
		completed = false;
	}
	
	public void setListener(RestoreNativeStateListener listener) {
		this.listener = listener;
		if (completed) {
			notifyCompleted();
    	}
	}
	
	private void notifyCompleted() {
		completed = true;
		try {
			if (listener != null) {
				listener.restoreNativeStateCompleted();
			}
		} catch (Exception e) {
			Log.e(LOG_TAG, "Error", e);
		}
	}
	
	@Override
    protected void onPreExecute() {
		try {
	    	if (listener != null) {
	    		listener.showProgressbar();
	    	}
		} catch (Exception e) {
			Log.e(LOG_TAG, "Error", e);
		}
    }
	
	@Override
	protected Boolean doInBackground(Void... arg0) {
		try {
			MyApplication myApplication = MyApplication.getMyApplication();
			Context applicationContext = myApplication.getApplicationContext();

            SharedPreferencesStorage.setBoolean(applicationContext, SharedPreferencesStorage.IS_CONTENT_LOADED, false);
			
			LocationManager locationManager = new LocationManagerImpl();
			locationManager.setContext(applicationContext);
			locationManager.deleteAll();
		
            DataLoader dataLoader = new DataLoaderImpl();
            dataLoader.fill(applicationContext, locationManager, R.xml.places);
            
            SharedPreferencesStorage.setBoolean(applicationContext, SharedPreferencesStorage.IS_CONTENT_LOADED, true);
            
    		return true;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error", e);
    		return false;
        }
	}

	@Override
    protected void onPostExecute(Boolean success) {
		try {
	    	if (listener != null) {
	    		listener.hideProgressbar();
	    	}
		} catch (Exception e) {
			Log.e(LOG_TAG, "Error", e);
		}
    	
    	if (success == false) {
    		notifyByStatusBar();
    	}
    	
    	notifyCompleted();
    }
	
	private void notifyByStatusBar() {
		MyApplication myApplication = MyApplication.getMyApplication();
		Context applicationContext = myApplication.getApplicationContext();
		
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) applicationContext.getSystemService(ns);
		
		CharSequence tickerText = applicationContext.getString(R.string.error_during_restoring_state_ticker);
		long when = System.currentTimeMillis();

		Notification notification = new Notification(R.drawable.icon, tickerText, when);
		
		CharSequence contentTitle = applicationContext.getString(R.string.error_during_restoring_state_title);
		CharSequence contentText = applicationContext.getString(R.string.error_during_restoring_state_msg);

		Intent intent = new Intent("pl.wtopolski.android.sunsetwidget.LocationsListActivity");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent contentIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0);
		
		notification.setLatestEventInfo(applicationContext, contentTitle, contentText, contentIntent);
		
		mNotificationManager.notify(Const.Notification.ERROR_DURING_RESTORING_STATE_ID, notification);
	}
}
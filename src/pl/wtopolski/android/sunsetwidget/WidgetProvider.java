package pl.wtopolski.android.sunsetwidget;

import static java.lang.Math.PI;
import static java.lang.Math.acos;
import static java.lang.Math.asin;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import android.content.Intent;
import android.app.PendingIntent;

/**
 * Widget shows time of sunrise and sunset in Poland.
 * 
 * @author Wojciech Topolski - code
 * @author Piotr Ilski - graphic (sun, sunrise, sunset, moon)
 */
public class WidgetProvider extends AppWidgetProvider {
	@SuppressWarnings("unused")
	private static final String LOG_TAG = WidgetProvider.class.getName();
	
	private TimeZone timeZone = TimeZone.getTimeZone("Europe/Warsaw");
	private Locale locale = new Locale("pl", "pl_PL");
	private enum DayMode {SUNRISE, DAY, SUNSET, NIGHT}
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);

		Calendar calendar = Calendar.getInstance(timeZone, locale);
		
		// Calculate time of sunrise and sunset.
		int[] times = determineSunsetAndSunriseForLocation(calendar, 19.480556, 52.069167);
		
		int sunriseHour = times[0];
		int sunriseMinutes = times[1];
		int sunsetHour = times[2];
		int sunsetMinutes = times[3];

		// Get border days on time change in Poland.
		Date lastSundayinMarch = getLastSundayInMonth(Calendar.MARCH);
		Date lastSundayinOctober = getLastSundayInMonth(Calendar.OCTOBER);
		Date currentDay = calendar.getTime();
		
		if (currentDay.after(lastSundayinMarch) && currentDay.before(lastSundayinOctober)) {
			// Last Sunday in March ... last Sunday in October
			sunriseHour += 2;
			sunsetHour += 2;
		} else {
			// last Sunday in October ... Last Sunday in March (exclusive)
			sunriseHour += 1;
			sunsetHour += 1;
		}
		
		// Times in string value.
		String sunrise = String.format("%02d:%02d", sunriseHour, sunriseMinutes);
		String sunset = String.format("%02d:%02d", sunsetHour, sunsetMinutes);

		// Determine which image should be displayed.
		DayMode imageMode = determineImageMode(calendar, sunriseHour, sunriseMinutes, sunsetHour, sunsetMinutes);
		
        // Perform this loop procedure for each widget that belongs to this provider.
		final int numberOfWidgets = appWidgetIds.length;
        for (int i = 0; i < numberOfWidgets; i++) {
            int appWidgetId = appWidgetIds[i];

            // Get the layout for the widget.
    		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
    		
    		// Set times.
    		remoteViews.setTextViewText(R.id.SunriseTextView, sunrise);
    		remoteViews.setTextViewText(R.id.SunsetTextView, sunset);
    		
    		// Set image.
    		switch (imageMode) {
    		case SUNRISE:
        		remoteViews.setImageViewResource(R.id.ImageImageView, R.drawable.sunrise);
    			break;
    		case SUNSET:
        		remoteViews.setImageViewResource(R.id.ImageImageView, R.drawable.sunset);
    			break;
    		case DAY:
        		remoteViews.setImageViewResource(R.id.ImageImageView, R.drawable.sun);
    			break;
    		case NIGHT:
        		remoteViews.setImageViewResource(R.id.ImageImageView, R.drawable.moon);
    			break;
    		}

            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            remoteViews.setOnClickPendingIntent(R.id.layout, pendingIntent);
    		
            // Tell the AppWidgetManager to perform an update on the current widget.
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
	}

	/**
	 * Determine which image should be displayed.
	 * 
	 * @param calendar
	 * @param sunriseHour
	 * @param sunriseMinutes
	 * @param sunsetHour
	 * @param sunsetMinutes
	 * @return
	 */
	private DayMode determineImageMode(Calendar calendar, int sunriseHour, int sunriseMinutes, int sunsetHour, int sunsetMinutes) {
		long currentTime = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);
		long sunriseTime = sunriseHour * 60 + sunriseMinutes;
		long sunsetTime = sunsetHour * 60 + sunsetMinutes;
		
		long bufforSunrise = currentTime - sunriseTime;
		bufforSunrise = (bufforSunrise > 0) ? bufforSunrise : -bufforSunrise;
		
		long bufforSunset = currentTime - sunsetTime;
		bufforSunset = (bufforSunset > 0) ? bufforSunset : -bufforSunset;
		
		if (bufforSunrise < 30L) {
			return DayMode.SUNRISE;
		} else if (bufforSunset < 30L) {
			return DayMode.SUNSET;
		} else if (sunriseTime < currentTime && currentTime < sunsetTime) {
			return DayMode.DAY;
		} else {
			return DayMode.NIGHT;
		}
	}

	private Date getLastSundayInMonth(int month) {
		// Create instance of calendar with parameters.
		Calendar calendar = Calendar.getInstance(timeZone, locale);
		
		// Set date on 1st of November.
		calendar.set(calendar.get(Calendar.YEAR), month + 1, 1, 0, 0, 0);
		
		Date lastSunday = null;
		do {
			// Go back one day.
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			
			// If it is Sunday.
			if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				lastSunday = calendar.getTime();
			}
		} while (lastSunday == null);
		
		return lastSunday;
	}

	/**
	 * Determine sunset and sunrise for location.
	 * @param calendar 
	 * 
	 * @param longitude
	 * @param latitude
	 * @return Array with hours and minutes.
	 */
	private int[] determineSunsetAndSunriseForLocation(Calendar calendar, final double longitude, final double latitude) {
		final int year = calendar.get(Calendar.YEAR);
		final int month = calendar.get(Calendar.MONTH)+1;
		final int day = calendar.get(Calendar.DAY_OF_MONTH);
		final double req = -0.833d;

		final double J = 367 * year - (int) (7 * (year + (int) ((month + 9) / 12)) / 4) + (int) (275 * month / 9) + day - 730531.5;
		final double Cent = J / 36525;
		final double L = (4.8949504201433 + 628.331969753199 * Cent) % 6.28318530718; // modulo
		final double G = (6.2400408 + 628.3019501 * Cent) % 6.28318530718;
		final double O = 0.409093 - 0.0002269 * Cent;
		final double F = 0.033423 * sin(G) + 0.00034907 * sin(2 * G);
		final double E = 0.0430398 * sin(2 * (L + F)) - 0.00092502 * sin(4 * (L + F)) - F;
		final double A = asin(sin(O) * sin(L + F));
		final double C = (sin(0.017453293 * req) - sin(0.017453293 * latitude) * sin(A)) / (cos(0.017453293 * latitude) * cos(A));

		final double sunrise = (PI - (E + 0.017453293 * longitude + 1 * acos(C))) * 57.29577951 / 15;
		final double sunset = (PI - (E + 0.017453293 * longitude + (-1) * acos(C))) * 57.29577951 / 15;

		int sunriseHour = (int) sunrise;
		int sunsetHour = (int) sunset;

		final int sunriseMinutes = (int)(60 * (sunrise - sunriseHour));
		final int sunsetMinutes = (int)(60 * (sunset - sunsetHour));

		int[] values = new int[4];
		values[0] = sunriseHour;
		values[1] = sunriseMinutes;
		values[2] = sunsetHour;
		values[3] = sunsetMinutes;
		return values;
	}
}
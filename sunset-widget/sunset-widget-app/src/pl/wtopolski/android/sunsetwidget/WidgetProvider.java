package pl.wtopolski.android.sunsetwidget;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import android.content.Intent;
import android.app.PendingIntent;
import pl.wtopolski.android.sunsetwidget.core.TimePackageCreator;
import pl.wtopolski.android.sunsetwidget.core.model.TimePackage;
import pl.wtopolski.android.sunsetwidget.model.DayMode;
import pl.wtopolski.android.sunsetwidget.model.GPSLocation;
import pl.wtopolski.android.sunsetwidget.util.LocationManager;
import pl.wtopolski.android.sunsetwidget.util.LocationManagerImpl;

public class WidgetProvider extends AppWidgetProvider {
    protected static final String LOG_TAG = WidgetProvider.class.getName();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        LocationManager locationManager = new LocationManagerImpl();
        GPSLocation mainLocation = locationManager.getMainLocation();

        TimePackageCreator calculator = new TimePackageCreator(mainLocation.convertToTimeLocation());
    	Calendar calendarNow = calculator.prepareCalendar();
        TimePackage times = calculator.prepareTimePackage(calendarNow);

        String sunrise = formatDate(times.getSunrise());
        String culmination = formatDate(times.getCulmination());
        String sunset = formatDate(times.getSunset());

        DayMode dayMode = DayMode.determineDayMode(calendarNow, times);

        final int numberOfWidgets = appWidgetIds.length;
        for (int i = 0; i < numberOfWidgets; i++) {
            int appWidgetId = appWidgetIds[i];

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

            remoteViews.setTextViewText(R.id.SunriseTextView, sunrise);
            remoteViews.setTextViewText(R.id.CulminationTextView, culmination);
            remoteViews.setTextViewText(R.id.SunsetTextView, sunset);

            int imageId = dayMode.getDrawableId();
            remoteViews.setImageViewResource(R.id.ImageImageView, imageId);

            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            remoteViews.setOnClickPendingIntent(R.id.layout, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }
}
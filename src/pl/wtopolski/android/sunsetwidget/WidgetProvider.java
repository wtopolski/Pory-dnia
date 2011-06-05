package pl.wtopolski.android.sunsetwidget;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.location.Location;
import android.widget.RemoteViews;

import android.content.Intent;
import android.app.PendingIntent;
import pl.wtopolski.android.sunsetwidget.model.DayMode;
import pl.wtopolski.android.sunsetwidget.model.TimePackage;
import pl.wtopolski.android.sunsetwidget.util.TimesCalculator;
import pl.wtopolski.android.sunsetwidget.util.TimesCalculatorImpl;

public class WidgetProvider extends AppWidgetProvider {
    protected static final String LOG_TAG = WidgetProvider.class.getName();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        Calendar calendarNow = TimesCalculator.createCalendarForNow();

        TimesCalculator calculator = new TimesCalculatorImpl();

        Location location = new Location("my");
        location.setLatitude(52.069167);
        location.setLongitude(19.480556);

        TimePackage times = calculator.determineForDayAndLocation(calendarNow, location);

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
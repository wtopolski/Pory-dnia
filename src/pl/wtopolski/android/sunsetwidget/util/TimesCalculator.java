package pl.wtopolski.android.sunsetwidget.util;

import android.location.Location;
import pl.wtopolski.android.sunsetwidget.model.TimePackage;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public abstract class TimesCalculator {

    public static Calendar createCalendarForNow() {
        TimeZone timeZone = TimeZone.getTimeZone("Europe/Warsaw");
        Locale locale = new Locale("pl", "pl_PL");
        return Calendar.getInstance(timeZone, locale);
    }

    public abstract TimePackage determineForDayAndLocation(Calendar calendar, Location location);
}

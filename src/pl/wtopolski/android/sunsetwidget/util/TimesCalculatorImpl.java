package pl.wtopolski.android.sunsetwidget.util;

import android.location.Location;
import pl.wtopolski.android.sunsetwidget.model.TimePackage;

import java.util.Calendar;
import java.util.Date;

import static java.lang.Math.*;

public class TimesCalculatorImpl extends TimesCalculator {

    public TimePackage determineForDayAndLocation(final Calendar calendar, final Location location) {
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH) + 1;
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
        final double C = (sin(0.017453293 * req) - sin(0.017453293 * location.getLatitude()) * sin(A)) / (cos(0.017453293 * location.getLatitude()) * cos(A));

        final double sunrise = (PI - (E + 0.017453293 * location.getLongitude() + 1 * acos(C))) * 57.29577951 / 15;
        final double culmination = (PI - (E + 0.017453293 * location.getLongitude() + 0 * acos(C))) * 57.29577951 / 15;
        final double sunset = (PI - (E + 0.017453293 * location.getLongitude() + (-1) * acos(C))) * 57.29577951 / 15;

        Calendar sunriseCalendar = prepareCalendar(calendar, sunrise);
        Calendar culminationCalendar = prepareCalendar(calendar, culmination);
        Calendar sunsetCalendar = prepareCalendar(calendar, sunset);

        TimePackage times = new TimePackage();
        times.setSunrise(sunriseCalendar.getTime());
        times.setCulmination(culminationCalendar.getTime());
        times.setSunset(sunsetCalendar.getTime());

        changeTimeDependsOnMonth(calendar, times);

        return times;
    }

    private Calendar prepareCalendar(final Calendar calendar, final double time) {
        int hour = (int) time;
        int minutes = (int) (60 * (time - hour));

        Calendar preparedCalendar = (Calendar) calendar.clone();
        preparedCalendar.set(Calendar.HOUR_OF_DAY, hour);
        preparedCalendar.set(Calendar.MINUTE, minutes);
        preparedCalendar.set(Calendar.SECOND, 0);

        return preparedCalendar;
    }

    private void changeTimeDependsOnMonth(Calendar calendar, TimePackage times) {
        // Get border days on time change in Poland.
        Date lastSundayInMarch = getLastSundayInMonth(Calendar.MARCH);
        Date lastSundayInOctober = getLastSundayInMonth(Calendar.OCTOBER);
        Date currentDay = calendar.getTime();

        if (currentDay.after(lastSundayInMarch) && currentDay.before(lastSundayInOctober)) {
            // Last Sunday in March ... last Sunday in October
            times.addTwoHour();
        } else {
            // last Sunday in October ... Last Sunday in March (exclusive)
            times.addOneHour();
        }
    }

    private Date getLastSundayInMonth(int month) {
        Calendar calendar = createCalendarForNow();

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
}

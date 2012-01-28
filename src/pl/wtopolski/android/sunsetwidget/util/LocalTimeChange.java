package pl.wtopolski.android.sunsetwidget.util;

public class LocalTimeChange {

    /*
    private void changeTimeDependsOnMonth(Calendar calendar, TimePackage times) {
        // Get border days on time change in Poland.
        Date lastSundayInMarch = getLastSundayInMonth(Calendar.MARCH);
        Date lastSundayInOctober = getLastSundayInMonth(Calendar.OCTOBER);
        Date currentDay = calendar.getTime();

        if (currentDay.after(lastSundayInMarch) && currentDay.before(lastSundayInOctober)) {
            // Last Sunday in March ... last Sunday in October
            addHour(times, 2);
        } else {
            // last Sunday in October ... Last Sunday in March (exclusive)
            addHour(times, 1);
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

    private void addHour(TimePackage times, int count) {
        times.setSunrise(addXHour(times.getSunrise(), count));
        times.setCulmination(addXHour(times.getCulmination(), count));
        times.setSunset(addXHour(times.getSunset(), count));
    }

    private Date addXHour(Date sunrise, int count) {
        Calendar calendar = TimePackageCreator.createCalendarForNow();
        calendar.setTime(sunrise);
        calendar.add(Calendar.HOUR_OF_DAY, count);
        return calendar.getTime();
    }
    */
}

package pl.wtopolski.android.sunsetwidget.model;

import pl.wtopolski.android.sunsetwidget.R;

import java.util.Calendar;

public enum DayMode {
    SUNRISE {
        @Override
        public int getDrawableId() {
            return R.drawable.sunrise;
        }
    }, DAY {
        @Override
        public int getDrawableId() {
            return R.drawable.day;
        }
    }, SUNSET {
        @Override
        public int getDrawableId() {
            return R.drawable.sunset;
        }
    }, NIGHT {
        @Override
        public int getDrawableId() {
            return R.drawable.night;
        }
    };

    private static final long BUFFER = 30L;

    public abstract int getDrawableId();

    public static DayMode determineDayMode(Calendar calendar, TimePackage times) {
        Calendar sunrise = Calendar.getInstance();
        sunrise.setTime(times.getSunrise());
        long sunriseTime = sunrise.get(Calendar.HOUR_OF_DAY) * 60 + sunrise.get(Calendar.MINUTE);

        Calendar sunset = Calendar.getInstance();
        sunset.setTime(times.getSunset());
        long sunsetTime = sunset.get(Calendar.HOUR_OF_DAY) * 60 + sunset.get(Calendar.MINUTE);

        long currentTime = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);

        long sunriseDiff = countDiff(currentTime, sunriseTime);
        long sunsetDiff = countDiff(currentTime, sunsetTime);

        if (sunriseDiff < BUFFER) {
            return DayMode.SUNRISE;
        } else if (sunsetDiff < BUFFER) {
            return DayMode.SUNSET;
        } else if (sunriseTime < currentTime && currentTime < sunsetTime) {
            return DayMode.DAY;
        } else {
            return DayMode.NIGHT;
        }
    }

    private static long countDiff(long currentTime, long sunriseTime) {
        long diff = 0L;
        diff = currentTime - sunriseTime;
        diff = (diff > 0) ? diff : -diff;
        return diff;
    }
}

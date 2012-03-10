package pl.wtopolski.android.sunsetwidget.core.model;

import java.util.Calendar;
import java.util.Date;

import pl.wtopolski.android.sunsetwidget.core.TimePackageCreator;

public enum TimeChange {
	NONE {
		@Override
		public void update(TimePackage timePackage) {
		}
	},
	EUROPEAN_TIME {
		@Override
		public void update(TimePackage timePackage) {
			if (isEuropeanSummerTime(timePackage.getCulmination())) {
				final int hourDiff = 1;
				timePackage.setSunrise(addHour(timePackage.getSunrise(), hourDiff));
				timePackage.setCulmination(addHour(timePackage.getCulmination(), hourDiff));
				timePackage.setSunset(addHour(timePackage.getSunset(), hourDiff));
			}
		}

	    private boolean isEuropeanSummerTime(Date selectedDay) {
	        // Get border days on time change in Europe.
	        Date lastSundayInMarch = getLastSundayInMonth(Calendar.MARCH);
	        Date lastSundayInOctober = getLastSundayInMonth(Calendar.OCTOBER);

	        // Last Sunday in March ... last Sunday in October
	        return selectedDay.after(lastSundayInMarch) && selectedDay.before(lastSundayInOctober);
	    }
	    
	    private Date getLastSundayInMonth(int month) {
	    	TimePackageCreator creator = new TimePackageCreator();
	        Calendar calendar = creator.prepareCalendar();

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

	    private Date addHour(Date time, int count) {
	    	TimePackageCreator creator = new TimePackageCreator();
	        Calendar calendar = creator.prepareCalendar();
	        calendar.setTime(time);
	        calendar.add(Calendar.HOUR_OF_DAY, count);
	        return calendar.getTime();
	    }
	};
	
	public abstract void update(TimePackage timePackage);
}

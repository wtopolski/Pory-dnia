package pl.wtopolski.android.sunsetwidget.core;

import java.util.Calendar;

import junit.framework.Assert;
import junitparams.JUnitParamsRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class PrepareCalendarTest {
	@Test
	public void prepareCalendarAction() {
		// given
		final int inputYear = 2012;
		final int inputMonth = 12;
		final int inputDay = 30;
		final float inputTime = 1.75f;
		
		final int outputTimeHour = 1;
		final int outputTimeMinute = 45;
		final int outputTimeSecond = 0;
		
		// when
		TimePackageCreator creator = new TimePackageCreator(null);
		Calendar calendar = creator.prepareCalendar(inputYear, inputMonth, inputDay, inputTime);
		
		// then
		Assert.assertEquals(inputYear, calendar.get(Calendar.YEAR));
		Assert.assertEquals(inputMonth - 1, calendar.get(Calendar.MONTH));
		Assert.assertEquals(inputDay, calendar.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(outputTimeHour, calendar.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(outputTimeMinute, calendar.get(Calendar.MINUTE));
		Assert.assertEquals(outputTimeSecond, calendar.get(Calendar.SECOND));
	}
}

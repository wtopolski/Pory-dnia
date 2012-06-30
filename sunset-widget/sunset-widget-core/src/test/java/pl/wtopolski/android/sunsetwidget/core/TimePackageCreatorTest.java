package pl.wtopolski.android.sunsetwidget.core;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Test;

import pl.wtopolski.android.sunsetwidget.core.model.TimeConfig;
import pl.wtopolski.android.sunsetwidget.core.model.TimeData;
import pl.wtopolski.android.sunsetwidget.core.model.TimeZenit;

public class TimePackageCreatorTest {
	@Test
	public void createTimeDataDoubleForAstronomicalFor_24_07_2012_Action() {
		// given
		final int year = 2012;
		final int month = 7;
		final int day = 24;
		final TimeConfig config = new TimeConfig(52.259f, 21.020f, TimeZenit.ASTRONOMICAL);
		
		// when
		TimePackageCreator creator = new TimePackageCreator(null);
		TimeData<Double> result = creator.createTimeDataDouble(year, month, day, config);
		
		// then
		Assert.assertEquals(0d, result.getCulmination());
		Assert.assertEquals(0d, result.getSunrise());
		Assert.assertEquals(0d, result.getSunset());
	}
	
	@Test
	public void createTimeDataDoubleForAstronomicalFor_25_07_2012_Action() {
		// given
		final int year = 2012;
		final int month = 7;
		final int day = 25;
		final TimeConfig config = new TimeConfig(52.259f, 21.020f, TimeZenit.ASTRONOMICAL);
		
		// when
		TimePackageCreator creator = new TimePackageCreator(null);
		TimeData<Double> result = creator.createTimeDataDouble(year, month, day, config);
		
		// then
		Assert.assertTrue(result.getCulmination() != 0d);
		Assert.assertTrue(result.getSunrise() != 0d);
		Assert.assertTrue(result.getSunset() != 0d);
	}
	
	@Test
	public void prepareCalendarAction() {
		// given
		final int year = 2012;
		final int month = 12;
		final int day = 30;
		final float time = 1.75f;
		final int timeHour = 1;
		final int timeMinute = 45;
		final int timeSecond = 0;
		
		// when
		TimePackageCreator creator = new TimePackageCreator(null);
		Calendar calendar = creator.prepareCalendar(year, month, day, time);
		
		// then
		Assert.assertEquals(year, calendar.get(Calendar.YEAR));
		Assert.assertEquals(month - 1, calendar.get(Calendar.MONTH));
		Assert.assertEquals(day, calendar.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(timeHour, calendar.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(timeMinute, calendar.get(Calendar.MINUTE));
		Assert.assertEquals(timeSecond, calendar.get(Calendar.SECOND));
	}
}

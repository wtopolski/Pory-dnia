package pl.wtopolski.android.sunsetwidget.core;

import junit.framework.Assert;

import org.junit.Test;

import pl.wtopolski.android.sunsetwidget.core.model.Season;
import pl.wtopolski.android.sunsetwidget.core.model.TimeConfig;
import pl.wtopolski.android.sunsetwidget.core.model.TimeZenit;

public class SeasonInLeapYearTest {
	private static final int year = 2012;
	private static final double latitude = 51f;
	private static final double longitude = 21f;
	private static final TimeZenit timeZenit = TimeZenit.OFFICIAL;

	@Test
	public void determineEndWinterTest() {
		Season season = determineSeason(79);
		Assert.assertEquals(season, Season.WINTER);
	}

	@Test
	public void determineStartSpringTest() {
		Season season = determineSeason(80);
		Assert.assertEquals(season, Season.SPRING);
	}
	
	@Test
	public void determineEndSpringTest() {
		Season season = determineSeason(172);
		Assert.assertEquals(season, Season.SPRING);
	}
	
	@Test
	public void determineStartSummerTest() {
		Season season = determineSeason(173);
		Assert.assertEquals(season, Season.SUMMER);
	}
	
	@Test
	public void determineEndSummerTest() {
		Season season = determineSeason(265);
		Assert.assertEquals(season, Season.SUMMER);
	}
	
	@Test
	public void determineStartAutumnTest() {
		Season season = determineSeason(266);
		Assert.assertEquals(season, Season.AUTUMN);
	}
	
	@Test
	public void determineEndAutumnTest() {
		Season season = determineSeason(355);
		Assert.assertEquals(season, Season.AUTUMN);
	}
	
	@Test
	public void determineStartWinterTest() {
		Season season = determineSeason(356);
		Assert.assertEquals(season, Season.WINTER);
	}
	
	private Season determineSeason(int dayInYear) {
		TimeConfig config = new TimeConfig(latitude, longitude, timeZenit);
		TimePackageCreator creator = new TimePackageCreator(config);
		return creator.determineSeason(year, dayInYear);
	}
}

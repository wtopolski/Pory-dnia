package pl.wtopolski.android.sunsetwidget.core;

import junit.framework.Assert;

import org.junit.Test;

import pl.wtopolski.android.sunsetwidget.core.model.Season;
import pl.wtopolski.android.sunsetwidget.core.model.TimeConfig;
import pl.wtopolski.android.sunsetwidget.core.model.TimeZenit;

public class SeasonTest {
	@Test
	public void determineStartSummerTest() {
		Season season = determineSeasonSupport(173);
		Assert.assertEquals(season, Season.SUMMER);
	}
	
	@Test
	public void determineStartAutumnTest() {
		Season season = determineSeasonSupport(267);
		Assert.assertEquals(season, Season.AUTUMN);
	}
	
	private Season determineSeasonSupport(int day) {
		TimeConfig config = new TimeConfig(51f, 21f, TimeZenit.OFFICIAL);
		TimePackageCreator creator = new TimePackageCreator(config);
		return creator.determineSeason(day);
	}
}

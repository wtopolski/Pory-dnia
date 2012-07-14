package pl.wtopolski.android.sunsetwidget.core;

import static junitparams.JUnitParamsRunner.$;
import junit.framework.Assert;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

import pl.wtopolski.android.sunsetwidget.core.model.ShouldBeKnown;
import pl.wtopolski.android.sunsetwidget.core.model.ShouldBeUnknown;
import pl.wtopolski.android.sunsetwidget.core.model.TestInputData;
import pl.wtopolski.android.sunsetwidget.core.model.TextOutputData;
import pl.wtopolski.android.sunsetwidget.core.model.TimeConfig;
import pl.wtopolski.android.sunsetwidget.core.model.TimeData;
import pl.wtopolski.android.sunsetwidget.core.model.TimeZenit;

@RunWith(JUnitParamsRunner.class)
public class CreateTimeDataDoubleWarsawTest {
	private static final double LATITUDE = 52.259f;
	private static final double LONGITUDE = 21.020f;
	
	@Test
	@Parameters(method = "parametersData")
	public void createTimeDataDoubleWarsaw(TestInputData input, TextOutputData output) {
		// given
		final TimeConfig config = new TimeConfig(LATITUDE, LONGITUDE, input.getTimeZenit());
		
		// when
		TimePackageCreator creator = new TimePackageCreator(null);
		TimeData<Double> result = creator.createTimeDataDouble(input.getYear(), input.getMonth(), input.getDay(), config);
		
		// then
		Assert.assertTrue(output.execute(result.getCulmination(), result.getSunrise(), result.getSunset()));
	}
	
	@SuppressWarnings("unused")
	private Object[] parametersData() {
		return $(
			$(new TestInputData(2012, 3, 22, TimeZenit.OFFICIAL), new ShouldBeKnown()),
			$(new TestInputData(2012, 6, 21, TimeZenit.OFFICIAL), new ShouldBeKnown()),
			$(new TestInputData(2012, 9, 22, TimeZenit.OFFICIAL), new ShouldBeKnown()),
			$(new TestInputData(2012, 12, 21, TimeZenit.OFFICIAL), new ShouldBeKnown()),

			$(new TestInputData(2013, 3, 22, TimeZenit.OFFICIAL), new ShouldBeKnown()),
			$(new TestInputData(2013, 6, 21, TimeZenit.OFFICIAL), new ShouldBeKnown()),
			$(new TestInputData(2013, 9, 22, TimeZenit.OFFICIAL), new ShouldBeKnown()),
			$(new TestInputData(2013, 12, 21, TimeZenit.OFFICIAL), new ShouldBeKnown()),

			$(new TestInputData(2012, 3, 22, TimeZenit.CIVIL), new ShouldBeKnown()),
			$(new TestInputData(2012, 6, 21, TimeZenit.CIVIL), new ShouldBeKnown()),
			$(new TestInputData(2012, 9, 22, TimeZenit.CIVIL), new ShouldBeKnown()),
			$(new TestInputData(2012, 12, 21, TimeZenit.CIVIL), new ShouldBeKnown()),

			$(new TestInputData(2013, 3, 22, TimeZenit.CIVIL), new ShouldBeKnown()),
			$(new TestInputData(2013, 6, 21, TimeZenit.CIVIL), new ShouldBeKnown()),
			$(new TestInputData(2013, 9, 22, TimeZenit.CIVIL), new ShouldBeKnown()),
			$(new TestInputData(2013, 12, 21, TimeZenit.CIVIL), new ShouldBeKnown()),

			$(new TestInputData(2012, 3, 22, TimeZenit.NAUTICAL), new ShouldBeKnown()),
			$(new TestInputData(2012, 6, 21, TimeZenit.NAUTICAL), new ShouldBeKnown()),
			$(new TestInputData(2012, 9, 22, TimeZenit.NAUTICAL), new ShouldBeKnown()),
			$(new TestInputData(2012, 12, 21, TimeZenit.NAUTICAL), new ShouldBeKnown()),

			$(new TestInputData(2013, 3, 22, TimeZenit.NAUTICAL), new ShouldBeKnown()),
			$(new TestInputData(2013, 6, 21, TimeZenit.NAUTICAL), new ShouldBeKnown()),
			$(new TestInputData(2013, 9, 22, TimeZenit.NAUTICAL), new ShouldBeKnown()),
			$(new TestInputData(2013, 12, 21, TimeZenit.NAUTICAL), new ShouldBeKnown()),
			
			$(new TestInputData(2012, 5, 18, TimeZenit.ASTRONOMICAL), new ShouldBeKnown()),
			$(new TestInputData(2012, 5, 19, TimeZenit.ASTRONOMICAL), new ShouldBeUnknown()),
			$(new TestInputData(2012, 7, 24, TimeZenit.ASTRONOMICAL), new ShouldBeUnknown()),
			$(new TestInputData(2012, 7, 25, TimeZenit.ASTRONOMICAL), new ShouldBeKnown()),

			$(new TestInputData(2013, 5, 18, TimeZenit.ASTRONOMICAL), new ShouldBeKnown()),
			$(new TestInputData(2013, 5, 19, TimeZenit.ASTRONOMICAL), new ShouldBeUnknown()),
			$(new TestInputData(2013, 7, 24, TimeZenit.ASTRONOMICAL), new ShouldBeUnknown()),
			$(new TestInputData(2013, 7, 25, TimeZenit.ASTRONOMICAL), new ShouldBeKnown())
		);
	}
}

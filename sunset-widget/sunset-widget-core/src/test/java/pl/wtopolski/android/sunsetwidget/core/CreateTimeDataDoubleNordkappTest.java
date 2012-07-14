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
public class CreateTimeDataDoubleNordkappTest {
	private static final double LATITUDE = 71.10f;
	private static final double LONGITUDE = 25.47f;
	
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
			$(new TestInputData(2012, 6, 21, TimeZenit.OFFICIAL), new ShouldBeUnknown()),
			$(new TestInputData(2012, 9, 22, TimeZenit.OFFICIAL), new ShouldBeKnown()),
			$(new TestInputData(2012, 12, 21, TimeZenit.OFFICIAL), new ShouldBeUnknown()),
				
			$(new TestInputData(2012, 1, 22, TimeZenit.OFFICIAL), new ShouldBeUnknown()),
			$(new TestInputData(2012, 1, 23, TimeZenit.OFFICIAL), new ShouldBeKnown()),
			$(new TestInputData(2012, 5, 11, TimeZenit.OFFICIAL), new ShouldBeKnown()),
			$(new TestInputData(2012, 5, 12, TimeZenit.OFFICIAL), new ShouldBeUnknown()),
			$(new TestInputData(2012, 7, 31, TimeZenit.OFFICIAL), new ShouldBeUnknown()),
			$(new TestInputData(2012, 8, 1, TimeZenit.OFFICIAL), new ShouldBeKnown()),
			$(new TestInputData(2012, 11, 20, TimeZenit.OFFICIAL), new ShouldBeKnown()),
			$(new TestInputData(2012, 11, 21, TimeZenit.OFFICIAL), new ShouldBeUnknown()),

			$(new TestInputData(2012, 3, 22, TimeZenit.CIVIL), new ShouldBeKnown()),
			$(new TestInputData(2012, 6, 21, TimeZenit.CIVIL), new ShouldBeUnknown()),
			$(new TestInputData(2012, 9, 22, TimeZenit.CIVIL), new ShouldBeKnown()),
			$(new TestInputData(2012, 12, 21, TimeZenit.CIVIL), new ShouldBeKnown()),
				
			$(new TestInputData(2012, 4, 23, TimeZenit.CIVIL), new ShouldBeKnown()),
			$(new TestInputData(2012, 4, 24, TimeZenit.CIVIL), new ShouldBeUnknown()),
			$(new TestInputData(2012, 8, 18, TimeZenit.CIVIL), new ShouldBeUnknown()),
			$(new TestInputData(2012, 8, 19, TimeZenit.CIVIL), new ShouldBeKnown()),
			
			$(new TestInputData(2012, 3, 22, TimeZenit.NAUTICAL), new ShouldBeKnown()),
			$(new TestInputData(2012, 6, 21, TimeZenit.NAUTICAL), new ShouldBeUnknown()),
			$(new TestInputData(2012, 9, 22, TimeZenit.NAUTICAL), new ShouldBeKnown()),
			$(new TestInputData(2012, 12, 21, TimeZenit.NAUTICAL), new ShouldBeKnown()),

			$(new TestInputData(2012, 4, 7, TimeZenit.NAUTICAL), new ShouldBeKnown()),
			$(new TestInputData(2012, 4, 8, TimeZenit.NAUTICAL), new ShouldBeUnknown()),
			$(new TestInputData(2012, 9, 4, TimeZenit.NAUTICAL), new ShouldBeUnknown()),
			$(new TestInputData(2012, 9, 5, TimeZenit.NAUTICAL), new ShouldBeKnown()),

			$(new TestInputData(2012, 3, 22, TimeZenit.ASTRONOMICAL), new ShouldBeKnown()),
			$(new TestInputData(2012, 6, 21, TimeZenit.ASTRONOMICAL), new ShouldBeUnknown()),
			$(new TestInputData(2012, 9, 22, TimeZenit.ASTRONOMICAL), new ShouldBeKnown()),
			$(new TestInputData(2012, 12, 21, TimeZenit.ASTRONOMICAL), new ShouldBeKnown()),
			
			$(new TestInputData(2012, 3, 22, TimeZenit.ASTRONOMICAL), new ShouldBeKnown()),
			$(new TestInputData(2012, 3, 23, TimeZenit.ASTRONOMICAL), new ShouldBeUnknown()),
			$(new TestInputData(2012, 9, 20, TimeZenit.ASTRONOMICAL), new ShouldBeUnknown()),
			$(new TestInputData(2012, 9, 21, TimeZenit.ASTRONOMICAL), new ShouldBeKnown())
		);
	}
}

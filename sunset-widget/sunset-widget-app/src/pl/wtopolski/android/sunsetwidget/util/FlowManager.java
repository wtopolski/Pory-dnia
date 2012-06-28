package pl.wtopolski.android.sunsetwidget.util;

import pl.wtopolski.android.sunsetwidget.InitActivity;
import pl.wtopolski.android.sunsetwidget.util.actionbar.ActionBarFragmentActivity;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;

public class FlowManager {
	public static void goToParent(Activity ctx, Class<?> cls) {
		Intent upIntent = new Intent(ctx, cls);
		if (NavUtils.shouldUpRecreateTask(ctx, upIntent)) {
			TaskStackBuilder.from(ctx)
//			.addNextIntent(new Intent(this, MyGreatGrandParentActivity.class))
//			.addNextIntent(new Intent(this, MyGrandParentActivity.class))
			.addNextIntent(upIntent).startActivities();
			ctx.finish();
		} else {
			NavUtils.navigateUpTo(ctx, upIntent);
		}
	}
	
	public static boolean shouldGoToInitActivity(ActionBarFragmentActivity activity) {
		if (activity instanceof InitActivity) {
			return false;
		}
		
		LocationManager locationManager = new LocationManagerImpl();
		
		boolean isContentLoaded = locationManager.getCount() > 0;
		boolean isMainSelected = locationManager.getMainLocation() != null;
		
        return (!isContentLoaded || !isMainSelected);
	}
}

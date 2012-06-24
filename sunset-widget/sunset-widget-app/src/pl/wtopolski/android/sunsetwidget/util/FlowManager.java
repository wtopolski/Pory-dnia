package pl.wtopolski.android.sunsetwidget.util;

import pl.wtopolski.android.sunsetwidget.MyApplication;
import pl.wtopolski.android.sunsetwidget.provider.SharedPreferencesStorage;
import android.app.Activity;
import android.content.Context;
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
	
	public static boolean shouldGoToInitActivity() {
		Context context = MyApplication.getMyApplication().getApplicationContext();
		boolean isContentLoaded = SharedPreferencesStorage.getBoolean(context, SharedPreferencesStorage.IS_CONTENT_LOADED);
        boolean isMainSelected = SharedPreferencesStorage.getBoolean(context, SharedPreferencesStorage.IS_MAIN_SELECTED);
        return (!isContentLoaded || !isMainSelected);
	}
}

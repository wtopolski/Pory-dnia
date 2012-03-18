package pl.wtopolski.android.sunsetwidget.util;

import pl.wtopolski.android.sunsetwidget.HomeActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class FlowManager {
	public static void finishAndGoTo(Activity ctx, Class<?> cls) {
		finishAndGoTo(ctx, cls, null);
	}
	
	public static void finishAndGoTo(Activity ctx, Class<?> cls, Bundle bundle) {
		Intent intent = new Intent(ctx, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		ctx.finish();
		ctx.startActivity(intent);
	}
	
	public static void goToHome(Activity ctx) {
		Intent intent = new Intent(ctx, HomeActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		ctx.finish();
		ctx.startActivity(intent);
	}
}

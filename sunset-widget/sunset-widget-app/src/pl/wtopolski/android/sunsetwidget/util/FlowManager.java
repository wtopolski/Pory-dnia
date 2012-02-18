package pl.wtopolski.android.sunsetwidget.util;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

public class FlowManager {
	public static void finishAndGoTo(Activity ctx, Class<?> cls, Bundle... bundle) {
		Intent intent = new Intent(ctx, cls);
		if (bundle.length > 0) {
			intent.putExtras(bundle[0]);
		}
		ctx.startActivity(intent);
		ctx.finish();
	}

	public static void finishAndGoTo(ListActivity ctx, Class<?> cls, Bundle... bundle) {
		Intent intent = new Intent(ctx, cls);
		if (bundle.length > 0) {
			intent.putExtras(bundle[0]);
		}
		ctx.startActivity(intent);
		ctx.finish();
	}
}

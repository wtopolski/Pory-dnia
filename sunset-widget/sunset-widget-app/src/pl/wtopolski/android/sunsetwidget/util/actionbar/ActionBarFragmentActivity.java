package pl.wtopolski.android.sunsetwidget.util.actionbar;

import pl.wtopolski.android.sunsetwidget.InitActivity;
import pl.wtopolski.android.sunsetwidget.util.FlowManager;
import pl.wtopolski.android.sunsetwidget.util.actionbar.helper.ActionBarHelper;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;

public abstract class ActionBarFragmentActivity extends FragmentActivity {
    final ActionBarHelper helper = ActionBarHelper.createInstance(this);

    protected ActionBarHelper getActionBarHelper() {
        return helper;
    }

    @Override
    public MenuInflater getMenuInflater() {
        return helper.getMenuInflater(super.getMenuInflater());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper.onCreate(savedInstanceState);
        
        if (FlowManager.shouldGoToInitActivity(this)) {
        	startActivity(new Intent(this, InitActivity.class));
        	finish();
        	return;
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        helper.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean retValue = false;
        retValue |= helper.onCreateOptionsMenu(menu);
        retValue |= super.onCreateOptionsMenu(menu);
        return retValue;
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        helper.onTitleChanged(title, color);
        super.onTitleChanged(title, color);
    }
}

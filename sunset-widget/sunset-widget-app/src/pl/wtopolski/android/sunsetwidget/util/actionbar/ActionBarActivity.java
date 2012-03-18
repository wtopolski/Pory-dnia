package pl.wtopolski.android.sunsetwidget.util.actionbar;

import pl.wtopolski.android.sunsetwidget.util.actionbar.helper.ActionBarHelper;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

public abstract class ActionBarActivity extends Activity {
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

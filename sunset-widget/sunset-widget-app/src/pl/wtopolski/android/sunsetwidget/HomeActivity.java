package pl.wtopolski.android.sunsetwidget;

import pl.wtopolski.android.sunsetwidget.util.FlowManager;
import pl.wtopolski.android.sunsetwidget.util.actionbar.ActionBarFragmentActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class HomeActivity extends ActionBarFragmentActivity {
    protected static final String LOG_TAG = HomeActivity.class.getSimpleName();

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment productDetails = new HomeFragment();
        fragmentTransaction.replace(R.id.mainFragement, productDetails);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    
    public void mainLocation(View view) {
    	finishAndGoTo(MainActivity.class);
    }
    
    public void listFavorite(View view) {
    	Bundle bundle = new Bundle();
    	bundle.putBoolean(LocationsListActivity.SHOW_ACTION, LocationsListActivity.SHOW_FAVOURITES);
    	finishAndGoTo(LocationsListActivity.class, bundle);
    }
    
    public void listAll(View view) {
    	Bundle bundle = new Bundle();
    	bundle.putBoolean(LocationsListActivity.SHOW_ACTION, LocationsListActivity.SHOW_ALL);
    	finishAndGoTo(LocationsListActivity.class, bundle);
    }
    
    public void settings(View view) {
    	// TODO
    }
    
    public void finishAndGoTo(Class<?> cls, Bundle... bundle) {
    	FlowManager.finishAndGoTo(this, cls, bundle);
    }
}

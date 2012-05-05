package pl.wtopolski.android.sunsetwidget;

import pl.wtopolski.android.sunsetwidget.LocationsListFragment.Mode;
import pl.wtopolski.android.sunsetwidget.util.FlowManager;
import pl.wtopolski.android.sunsetwidget.util.actionbar.ActionBarFragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class FavoritesListActivity extends ActionBarFragmentActivity {
    protected static final String LOG_TAG = FavoritesListActivity.class.getSimpleName();
    private final static String FRAGMENT_TAG = "FAVORITES_FRAGMENT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locations);
        setTitle(R.string.dashboard_favorites);
        
        if (savedInstanceState != null) {
        	@SuppressWarnings("unused")
			ListFragment listFragment = (ListFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        } else {
        	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        	LocationsListFragment locationsListFragment = new LocationsListFragment();
        	locationsListFragment.setMode(Mode.FAVOURITES);
            transaction.replace(R.id.locationsListFragement, locationsListFragment, FRAGMENT_TAG);
            transaction.commit();
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.favorites, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            	FlowManager.goToHome(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
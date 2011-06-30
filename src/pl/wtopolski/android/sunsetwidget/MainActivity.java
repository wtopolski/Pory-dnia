package pl.wtopolski.android.sunsetwidget;

import static pl.wtopolski.android.sunsetwidget.provider.SharedPreferencesStorage.CITY_SAVED_ID;
import pl.wtopolski.android.sunsetwidget.provider.SharedPreferencesStorage;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
        bindData();
        restoreContent();
    }

    private void findViews() {
    }

    private void bindData() {
        bindDataToSpinner();
    }

    private void bindDataToSpinner() {
        /*
        LocationManager locationManager = new LocationManagerImpl();
        locationManager.setContext(getApplicationContext());
        locationManager.addLocation(new Location("WWA", 1f, 2f, "Maz"));
        List<Location> list = locationManager.getAllLocations();
        Log.d("-------------------->", list.size() + " " + list.get(0).getName());
        */
    }

    private void restoreContent() {
        //int citySavedId = SharedPreferencesStorage.getInt(getApplicationContext(), CITY_SAVED_ID);
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        SharedPreferencesStorage.setInt(getApplicationContext(), CITY_SAVED_ID, position);
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

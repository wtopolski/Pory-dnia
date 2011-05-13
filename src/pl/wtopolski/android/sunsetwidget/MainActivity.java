package pl.wtopolski.android.sunsetwidget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import pl.wtopolski.android.sunsetwidget.util.SharedPreferencesStorage;
import static pl.wtopolski.android.sunsetwidget.util.SharedPreferencesStorage.*;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private Spinner locationSpinner;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
        bindData();
        restoreContent();
    }

    private void findViews() {
        locationSpinner = (Spinner) findViewById(R.id.location_spinner);
    }

    private void bindData() {
        bindDataToSpinner();
    }

    private void bindDataToSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.city_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);
        locationSpinner.setOnItemSelectedListener(this);
    }

    private void restoreContent() {
        int citySavedId = SharedPreferencesStorage.getInt(getApplicationContext(), CITY_SAVED_ID);
        locationSpinner.setSelection(citySavedId);
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        SharedPreferencesStorage.setInt(getApplicationContext(), CITY_SAVED_ID, position);
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

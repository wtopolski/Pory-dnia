package pl.wtopolski.android.sunsetwidget;

import pl.wtopolski.android.sunsetwidget.pref.GeneralPreferenceActivity;
import pl.wtopolski.android.sunsetwidget.view.DashboardItemView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {
	private DashboardItemView myPlace;
	private DashboardItemView favorites;
	private DashboardItemView locations;
	private DashboardItemView settings;
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.home_fragment, container, false);
    	
    	myPlace = (DashboardItemView) view.findViewById(R.id.my_place);
    	myPlace.setText(R.string.dashboard_my_place);
    	myPlace.setIconSelector(R.drawable.dashboard_location_selector);
    	myPlace.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Activity activity = getActivity();
				Intent intent = new Intent(activity, MainActivity.class);
				activity.startActivity(intent);
			}
		});
    	
    	favorites = (DashboardItemView) view.findViewById(R.id.favorites);
    	favorites.setText(R.string.dashboard_favorites);
    	favorites.setIconSelector(R.drawable.dashboard_favorite_selector);
    	favorites.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Activity activity = getActivity();
				Intent intent = new Intent(activity, LocationsListActivity.class);
		    	intent.putExtra(LocationsListActivity.SHOW_ACTION, LocationsListActivity.SHOW_FAVOURITES);
				activity.startActivity(intent);
			}
		});
    	
    	locations = (DashboardItemView) view.findViewById(R.id.locations);
    	locations.setText(R.string.dashboard_locations);
    	locations.setIconSelector(R.drawable.dashboard_list_selector);
    	locations.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Activity activity = getActivity();
				Intent intent = new Intent(activity, LocationsListActivity.class);
		    	intent.putExtra(LocationsListActivity.SHOW_ACTION, LocationsListActivity.SHOW_ALL);
				activity.startActivity(intent);
			}
		});
    	
    	settings = (DashboardItemView) view.findViewById(R.id.settings);
    	settings.setText(R.string.dashboard_settings);
    	settings.setIconSelector(R.drawable.dashboard_settings_selector);
    	settings.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Activity activity = getActivity();
				Intent intent = new Intent(activity, GeneralPreferenceActivity.class);
				activity.startActivity(intent);
			}
		});
    	
        return view;
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    }
}

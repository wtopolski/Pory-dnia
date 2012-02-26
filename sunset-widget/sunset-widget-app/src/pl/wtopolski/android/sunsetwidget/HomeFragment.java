package pl.wtopolski.android.sunsetwidget;

import pl.wtopolski.android.sunsetwidget.view.DashboardItemView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

public class HomeFragment extends Fragment {
	private DashboardItemView location;
	private DashboardItemView favoriteList;
	private DashboardItemView allList;
	private DashboardItemView settings;
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.home_fragment, container, false);
    	
    	location = (DashboardItemView) view.findViewById(R.id.location);
    	location.setText("Moje miejsce");
    	location.setIconSelector(R.drawable.dashboard_item_selector);
    	location.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Activity activity = getActivity();
				Intent intent = new Intent(activity, MainActivity.class);
				activity.startActivity(intent);
			}
		});
    	
    	favoriteList = (DashboardItemView) view.findViewById(R.id.favorite_list);
    	favoriteList.setText("Ulubione");
    	favoriteList.setIconSelector(R.drawable.dashboard_item_selector);
    	favoriteList.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Activity activity = getActivity();
				Intent intent = new Intent(activity, LocationsListActivity.class);
		    	intent.putExtra(LocationsListActivity.SHOW_ACTION, LocationsListActivity.SHOW_FAVOURITES);
				activity.startActivity(intent);
			}
		});
    	
    	allList = (DashboardItemView) view.findViewById(R.id.all_list);
    	allList.setText("Miasta");
    	allList.setIconSelector(R.drawable.dashboard_item_selector);
    	allList.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Activity activity = getActivity();
				Intent intent = new Intent(activity, LocationsListActivity.class);
		    	intent.putExtra(LocationsListActivity.SHOW_ACTION, LocationsListActivity.SHOW_ALL);
				activity.startActivity(intent);
			}
		});
    	
    	settings = (DashboardItemView) view.findViewById(R.id.settings);
    	settings.setText("Ustawienia");
    	settings.setIconSelector(R.drawable.dashboard_item_selector);
    	settings.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Toast.makeText(getActivity(), "settings", 500).show();
			}
		});
    	
        return view;
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    }
}

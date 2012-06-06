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
	private DashboardItemView place;
	private DashboardItemView favorites;
	private DashboardItemView locations;
	private DashboardItemView settings;

	private OnClickListener placeClickListener = new OnClickListener() {
		public void onClick(View view) {
			Activity activity = getActivity();
			Intent intent = new Intent(activity, MainActivity.class);
			activity.startActivity(intent);
		}
	};

	private OnClickListener favoritesClickListener = new OnClickListener() {
		public void onClick(View view) {
			Activity activity = getActivity();
			Intent intent = new Intent(activity, FavoritesListActivity.class);
			activity.startActivity(intent);
		}
	};

	private OnClickListener locationsClickListener = new OnClickListener() {
		public void onClick(View view) {
			Activity activity = getActivity();
			Intent intent = new Intent(activity, LocationsListActivity.class);
			activity.startActivity(intent);
		}
	};

	private OnClickListener settingsClickListener = new OnClickListener() {
		public void onClick(View view) {
			Activity activity = getActivity();
			Intent intent = new Intent(activity, GeneralPreferenceActivity.class);
			activity.startActivity(intent);
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_fragment, container, false);

		place = (DashboardItemView) view.findViewById(R.id.place);
		place.setText(R.string.dashboard_my_place);
		place.setIconSelector(R.drawable.dashboard_location_selector);
		place.setOnClickListener(placeClickListener);

		favorites = (DashboardItemView) view.findViewById(R.id.favorites);
		favorites.setText(R.string.dashboard_favorites);
		favorites.setIconSelector(R.drawable.dashboard_favorite_selector);
		favorites.setOnClickListener(favoritesClickListener);

		locations = (DashboardItemView) view.findViewById(R.id.locations);
		locations.setText(R.string.dashboard_locations);
		locations.setIconSelector(R.drawable.dashboard_list_selector);
		locations.setOnClickListener(locationsClickListener);

		settings = (DashboardItemView) view.findViewById(R.id.settings);
		settings.setText(R.string.dashboard_settings);
		settings.setIconSelector(R.drawable.dashboard_settings_selector);
		settings.setOnClickListener(settingsClickListener);

		return view;
	}
}

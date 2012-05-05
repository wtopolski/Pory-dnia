package pl.wtopolski.android.sunsetwidget;

import pl.wtopolski.android.sunsetwidget.adapter.LocationListAdapter;
import pl.wtopolski.android.sunsetwidget.adapter.OnStarClickable;
import pl.wtopolski.android.sunsetwidget.model.GPSLocation;
import pl.wtopolski.android.sunsetwidget.util.LocationManager;
import pl.wtopolski.android.sunsetwidget.util.LocationManagerImpl;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

public class LocationsListFragment extends ListFragment implements OnStarClickable {
	protected static final String LOG_TAG = LocationsListFragment.class.getSimpleName();

	private LocationListAdapter adapter;
	private LocationManager locationManager;
	private Mode mode;

	public enum Mode {
		LOCATIONS, FAVOURITES, SEARCHED
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (mode == null) {
			mode = Mode.LOCATIONS;
		}
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.locations_fragment, container, false);
		locationManager = new LocationManagerImpl();
		locationManager.setContext(this.getActivity().getApplicationContext());
		showLocations();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		registerForContextMenu(getListView());
	}

	private void showLocations() {
		Cursor cursor = null;

		switch (mode) {
		case LOCATIONS:
			cursor = locationManager.getAllLocationsByCursor();
			break;
		case FAVOURITES:
			cursor = locationManager.getAllFavouritesByCursor();
			break;
		}

		this.getActivity().startManagingCursor(cursor);
		adapter = new LocationListAdapter(this.getActivity(), R.layout.locations_item, cursor, this);
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View view, int position, long id) {
		super.onListItemClick(l, view, position, id);
		Intent intent = new Intent(this.getActivity(), MainActivity.class);
		intent.putExtra(MainActivity.LOCATION_ID, (int) id);
		startActivity(intent);
	}

	public void onStarClicked(int locationId) {
		GPSLocation location = locationManager.getLocation(locationId);
		switch (location.getType()) {
		case FAVOURITE:
			locationManager.selectAsMain(location);
			break;
		case NONE:
			locationManager.makeFavourite(location);
			break;
		case MAIN:
			break;
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
		MenuInflater inflater = this.getActivity().getMenuInflater();
		inflater.inflate(R.menu.locations_context_menu, menu);
		super.onCreateContextMenu(menu, view, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		int locationId = (int) info.id;
		GPSLocation location = locationManager.getLocation(locationId);

		switch (item.getItemId()) {
		case R.id.set_as_main:
			locationManager.selectAsMain(location);
			return true;
		case R.id.add_to_favourites:
			locationManager.makeFavourite(location);
			return true;
		case R.id.remove_from_favourites:
			locationManager.makeNoFavourite(location);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
}

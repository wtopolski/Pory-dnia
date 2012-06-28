package pl.wtopolski.android.sunsetwidget.fragment;

import pl.wtopolski.android.sunsetwidget.R;
import pl.wtopolski.android.sunsetwidget.adapter.LocationListAdapter;
import pl.wtopolski.android.sunsetwidget.adapter.OnStarClickable;
import pl.wtopolski.android.sunsetwidget.model.GPSLocation;
import pl.wtopolski.android.sunsetwidget.util.LocationManager;
import pl.wtopolski.android.sunsetwidget.util.LocationManagerImpl;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

public class LocationsListFragment extends ListFragment implements OnStarClickable {
	protected static final String LOG_TAG = LocationsListFragment.class.getSimpleName();
	
	public static final String MODE_KEY = "MODE_KEY";
	public static final String QUERY_KEY = "QUERY_KEY";

	private LocationListAdapter adapter;
	private LocationManager locationManager;
	private OnLocationsSelected listener;
	private Mode mode;
	private String query;

	public enum Mode {
		LOCATIONS {
			@Override
			public Cursor getCursor(LocationManager locationManager, String query) {
				return locationManager.getAllLocationsByCursor(query);
			}
		}, 
		FAVOURITES {
			@Override
			public Cursor getCursor(LocationManager locationManager, String query) {
				return locationManager.getAllFavouritesByCursor(query);
			}
		};
		
		public abstract Cursor getCursor(LocationManager locationManager, String query);
	};
    
    @Override
    public void onAttach(Activity activity) {
    	super.onAttach(activity);
    	if (activity instanceof OnLocationsSelected) {
    		listener = (OnLocationsSelected) activity;
    	} else {
    		throw new ClassCastException(activity.toString() + " must implement " + OnLocationsSelected.class.getName());
    	}
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void setQuery(String query) {
		this.query = query;
		if (locationManager != null) {
			showLocations();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		locationManager = new LocationManagerImpl();
		return inflater.inflate(R.layout.locations_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		registerForContextMenu(getListView());

		Bundle bundle = getArguments();
		if (savedInstanceState != null) {
			bundle = savedInstanceState;
		}

		mode = (Mode) bundle.getSerializable(MODE_KEY);
		query = bundle.getString(QUERY_KEY);
		
		if (mode == null) {
			mode = Mode.LOCATIONS;
		}
		
		if (TextUtils.isEmpty(query)) {
			query = "";
		}
		
		showLocations();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(MODE_KEY, mode);
		outState.putSerializable(QUERY_KEY, query);
	}

	private void showLocations() {
		Cursor cursor = mode.getCursor(locationManager, query);
		this.getActivity().startManagingCursor(cursor);
		adapter = new LocationListAdapter(this.getActivity(), R.layout.locations_item, cursor, this);
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View view, int position, long id) {
		super.onListItemClick(l, view, position, id);
		getListView().setItemChecked(position, true);
		listener.onLocationSelected((int)id);
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

package pl.wtopolski.android.sunsetwidget.fragment;

import pl.wtopolski.android.sunsetwidget.R;
import pl.wtopolski.android.sunsetwidget.adapter.LocationListAdapter;
import pl.wtopolski.android.sunsetwidget.util.LocationManager;
import pl.wtopolski.android.sunsetwidget.util.LocationManagerImpl;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class InitListFragment extends ListFragment {
	protected static final String LOG_TAG = InitListFragment.class.getSimpleName();
	
	private LocationManager locationManager;
	private OnLocationsSelected listener;
    
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		locationManager = new LocationManagerImpl();
		return inflater.inflate(R.layout.init_list_fragment, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Cursor cursor = locationManager.getAllLocationsByCursor("");
		getActivity().startManagingCursor(cursor);
		setListAdapter(new LocationListAdapter(getActivity(), R.layout.locations_init_item, cursor, null));
	}

	@Override
	public void onListItemClick(ListView l, View view, int position, long id) {
		super.onListItemClick(l, view, position, id);
		getListView().setItemChecked(position, true);
		listener.onLocationSelected((int)id);
	}
}

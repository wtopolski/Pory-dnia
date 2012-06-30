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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class InitListFragment extends ListFragment implements OnClickListener {
	protected static final String LOG_TAG = InitListFragment.class.getSimpleName();
	
	private LocationManager locationManager;
	private OnLocationItemSelected listener;
	private Button gpsButton;
    
    @Override
    public void onAttach(Activity activity) {
    	super.onAttach(activity);
    	if (activity instanceof OnLocationItemSelected) {
    		listener = (OnLocationItemSelected) activity;
    	} else {
    		throw new ClassCastException(activity.toString() + " must implement " + OnLocationItemSelected.class.getName());
    	}
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.init_list_fragment, container, false);
		gpsButton = (Button) view.findViewById(R.id.gpsButton);
		gpsButton.setOnClickListener(this);
		locationManager = new LocationManagerImpl();
		return view;
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
		listener.onLocationItemSelected((int)id);
	}

	public void onClick(View v) {
		if (v.getId() != gpsButton.getId()) {
			return;
		}
		
		// TODO
		Toast.makeText(getActivity(), "TO DO", 500).show();
	}
}

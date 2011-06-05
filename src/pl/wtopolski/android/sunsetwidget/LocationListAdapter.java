package pl.wtopolski.android.sunsetwidget;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import pl.wtopolski.android.sunsetwidget.model.Location;
import pl.wtopolski.android.sunsetwidget.provider.LocationData;
import pl.wtopolski.android.sunsetwidget.util.LocationManager;

public class LocationListAdapter extends CursorAdapter {
	protected static final String LOG_TAG = LocationListAdapter.class.getName();
	private LayoutInflater layoutInflater;
    private int idColumn;
    private int layout;
    private LocationManager locationManager;

    public LocationListAdapter(Context context, int layout, Cursor cursor) {
        super(context, cursor);
        this.layout = layout;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        idColumn = cursor.getColumnIndex(LocationData.Locations._ID);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = layoutInflater.inflate(layout, parent, false);
        bindView(view, context, cursor);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final int locationId = cursor.getInt(idColumn);
        Location location = locationManager.getLocation(locationId);

        setTextOnView(view, cursor, R.id.firstLine, location.getName());
        setTextOnView(view, cursor, R.id.secondLine, location.getProvince());
        setStarOnView(view, cursor, R.id.icon, location);
    }

    private void setTextOnView(View view, Cursor cursor, int resource, String value) {
        TextView line = (TextView) view.findViewById(resource);
        line.setText(value);
    }

    private void setStarOnView(View view, Cursor cursor, int resource, final Location location) {
        final ImageView image = (ImageView) view.findViewById(resource);
        image.setImageResource(location.getImageResourse());

        image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                locationManager.revertSelection(location);
                image.setImageResource(location.getImageResourse());
            }
        });
    }

    public void setLocationManager(LocationManager locationManager) {
        this.locationManager = locationManager;
    }
}

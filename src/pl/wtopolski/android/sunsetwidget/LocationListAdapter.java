package pl.wtopolski.android.sunsetwidget;

import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations._ID;
import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.COLUMN_NAME_LATITUDE;
import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.COLUMN_NAME_LONGITUDE;
import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.COLUMN_NAME_NAME;
import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.COLUMN_NAME_PROVINCE;
import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.COLUMN_NAME_SELECTION;
import pl.wtopolski.android.sunsetwidget.model.Location;
import pl.wtopolski.android.sunsetwidget.model.SelectionType;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class LocationListAdapter extends CursorAdapter implements SectionIndexer {
	protected static final String LOG_TAG = LocationListAdapter.class.getName();
	private LayoutInflater layoutInflater;
    
	private int idColumn;
    private int nameColumn;
    private int latitudeColumn;
    private int longitudeColumn;
    private int provinceColumn;
    private int selectionColumn;
    
    private int layout;
    private AlphabetIndexer alphaIndexer;

    public LocationListAdapter(Context context, int layout, Cursor cursor) {
        super(context, cursor);
        this.layout = layout;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        idColumn = cursor.getColumnIndex(_ID);
        nameColumn = cursor.getColumnIndex(COLUMN_NAME_NAME);
        latitudeColumn = cursor.getColumnIndex(COLUMN_NAME_LATITUDE);
        longitudeColumn = cursor.getColumnIndex(COLUMN_NAME_LONGITUDE);
        provinceColumn = cursor.getColumnIndex(COLUMN_NAME_PROVINCE);
        selectionColumn = cursor.getColumnIndex(COLUMN_NAME_SELECTION);
        
        alphaIndexer = new AlphabetIndexer(cursor, nameColumn, " ABCDEFGHIJKLŁMNOPRSŚTUWZŻ");
        alphaIndexer.setCursor(cursor);
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
        String name = cursor.getString(nameColumn);
        double latitude = cursor.getDouble(latitudeColumn);
        double longitude = cursor.getDouble(longitudeColumn);
        String province = cursor.getString(provinceColumn);
        int selection = cursor.getInt(selectionColumn);

        Location location = new Location(locationId, name, latitude, longitude, province);
        location.setType(SelectionType.getSelectionType(selection));

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
    }

	public Object[] getSections() {
		return alphaIndexer.getSections();
	}

	public int getPositionForSection(int section) {
		return alphaIndexer.getPositionForSection(section);
	}

	public int getSectionForPosition(int position) {
		return alphaIndexer.getSectionForPosition(position);
	}
}

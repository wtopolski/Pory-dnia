package pl.wtopolski.android.sunsetwidget;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import pl.wtopolski.android.sunsetwidget.provider.LocationData;

public class LocationListAdapter extends CursorAdapter {
	protected static final String LOG_TAG = LocationListAdapter.class.getName();
	private LayoutInflater layoutInflater;
    private int nameColumn;
    private int provinceColumn;
    private int selectedColumn;
    private int layout;

    public LocationListAdapter(Context context, int layout, Cursor cursor) {
        super(context, cursor);
        this.layout = layout;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        nameColumn = cursor.getColumnIndex(LocationData.Locations.COLUMN_NAME_NAME);
        provinceColumn = cursor.getColumnIndex(LocationData.Locations.COLUMN_NAME_PROVINCE);
        selectedColumn = cursor.getColumnIndex(LocationData.Locations.COLUMN_NAME_SELECTED);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = layoutInflater.inflate(layout, parent, false);
        bindView(view, context, cursor);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        setTextOnView(view, cursor, R.id.firstLine, nameColumn);
        setTextOnView(view, cursor, R.id.secondLine, provinceColumn);
        setStarOnView(view, cursor, R.id.icon, selectedColumn);
    }

    private void setTextOnView(View view, Cursor cursor, int resource, int columnIndex) {
        TextView line = (TextView) view.findViewById(resource);
        String value = cursor.getString(columnIndex);
        line.setText(value);
    }

    private void setStarOnView(View view, Cursor cursor, int resource, int columnIndex) {
        ImageView image = (ImageView) view.findViewById(resource);
        int value = cursor.getInt(columnIndex);
        int resId = (value > 0) ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off;
        image.setImageResource(resId);
    }
}

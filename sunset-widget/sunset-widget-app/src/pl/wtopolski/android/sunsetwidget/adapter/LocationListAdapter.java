package pl.wtopolski.android.sunsetwidget.adapter;

import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.*;
import pl.wtopolski.android.sunsetwidget.MyApplication;
import pl.wtopolski.android.sunsetwidget.R;
import pl.wtopolski.android.sunsetwidget.model.SelectionType;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
    private OnStarClickable onStarClickable;

    public LocationListAdapter(Context context, int layout, Cursor cursor, OnStarClickable onStarClickable) {
        super(context, cursor);
        this.layout = layout;
        this.onStarClickable = onStarClickable;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        idColumn = cursor.getColumnIndex(COLUMN_ID);
        nameColumn = cursor.getColumnIndex(COLUMN_NAME);
        latitudeColumn = cursor.getColumnIndex(COLUMN_LATITUDE);
        longitudeColumn = cursor.getColumnIndex(COLUMN_LONGITUDE);
        provinceColumn = cursor.getColumnIndex(COLUMN_PROVINCE);
        selectionColumn = cursor.getColumnIndex(COLUMN_SELECTION);
        
        alphaIndexer = new AlphabetIndexer(cursor, nameColumn, " ABCDEFGHIJKLŁMNOPRSŚTUWZŻ");
        alphaIndexer.setCursor(cursor);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = layoutInflater.inflate(layout, parent, false);
        bindView(view, context, cursor);
        return view;
    }

    @SuppressWarnings("unused")
	@Override
    public void bindView(View view, Context context, Cursor cursor) {
        final int locationId = cursor.getInt(idColumn);
        String name = cursor.getString(nameColumn);
        double latitude = cursor.getDouble(latitudeColumn);
        double longitude = cursor.getDouble(longitudeColumn);
        String province = cursor.getString(provinceColumn);
        int selection = cursor.getInt(selectionColumn);

        SelectionType selectionType = SelectionType.getSelectionType(selection);
        Typeface fontTypeface = MyApplication.getMyApplication().getTypeface();

        setImageOnView(view, R.id.imageItem, selectionType.getImage(), locationId);
        setTextOnView(view, R.id.firstLine, fontTypeface, name);
        setTextOnView(view, R.id.secondLine, fontTypeface, province);
    }

    private void setImageOnView(View view, int resource, int image, final int locationId) {
        ImageView imageItem = (ImageView) view.findViewById(resource);
        imageItem.setImageResource(image);
        imageItem.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				onStarClickable.onStarClicked(locationId);
			}
		});
	}

	private void setTextOnView(View view, int resource, Typeface fontTypeface, String value) {
        TextView line = (TextView) view.findViewById(resource);
        line.setTypeface(fontTypeface);
        line.setText(value);
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

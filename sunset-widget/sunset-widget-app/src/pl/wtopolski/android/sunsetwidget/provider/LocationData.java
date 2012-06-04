package pl.wtopolski.android.sunsetwidget.provider;

import pl.wtopolski.android.sunsetwidget.R;
import android.app.SearchManager;
import android.net.Uri;
import android.provider.BaseColumns;

public class LocationData {
	public static final String AUTHORITY = "pl.wtopolski.sunsetwidget.contentprovider";

	private LocationData() {
	}

	public static final class Locations implements BaseColumns {
		private Locations() {
		}

		// TABLE
		public static final String TABLE_NAME = "locations";

		// URI
		public static final String SCHEME = "content://";
		public static final String PATH_SEGMENT = "locations";

		// POSITION OF ID IN URI
		public static final int LOCATION_ID_PATH_POSITION = 1;

		// FOR GLOBAL REQUESTS
		public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + "/" + PATH_SEGMENT);

		// FOR MATCHER
		public static final String MATCHER_FOR_CONTENT = PATH_SEGMENT;
		public static final String MATCHER_FOR_CONTENT_ID = MATCHER_FOR_CONTENT + "/#";
		public static final String MATCHER_FOR_SEARCH_LOCATIONS = "quick_search_locations/" + SearchManager.SUGGEST_URI_PATH_QUERY;
		public static final String MATCHER_FOR_SEARCH_LOCATIONS_ID = MATCHER_FOR_SEARCH_LOCATIONS + "/*";
		public static final String MATCHER_FOR_SEARCH_FAVORITES = "quick_search_favorites/" + SearchManager.SUGGEST_URI_PATH_QUERY;
		public static final String MATCHER_FOR_SEARCH_FAVORITES_ID = MATCHER_FOR_SEARCH_FAVORITES + "/*";

		// COLUMN NAMES
		public static final String COLUMN_ID = BaseColumns._ID;
		public static final String COLUMN_NAME = "name";
		public static final String COLUMN_SIMPLE_NAME = "simple_name";
		public static final String COLUMN_LATITUDE = "latitude";
		public static final String COLUMN_LONGITUDE = "longitude";
		public static final String COLUMN_PROVINCE = "province";
		public static final String COLUMN_SELECTION = "selection";

		// TYPES
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.locations.place";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.locations.place";

		// SORT
		public static final String DEFAULT_SORT_ORDER = COLUMN_NAME + " COLLATE LOCALIZED ASC";

		// STANDARD PROJECTION
		public static final String[] STANDARD_LOCATION_PROJECTION = new String[] {
			COLUMN_ID,
			COLUMN_NAME,
			COLUMN_SIMPLE_NAME,
			COLUMN_LATITUDE,
			COLUMN_LONGITUDE,
			COLUMN_PROVINCE,
			COLUMN_SELECTION
		};

		// SEARCH PROJECTION
		public static final String[] SEARCH_LOCATION_PROJECTION = new String[] {
			COLUMN_ID,
			COLUMN_ID + " AS " + SearchManager.SUGGEST_COLUMN_INTENT_DATA,
			COLUMN_NAME + " AS " + SearchManager.SUGGEST_COLUMN_TEXT_1,
			COLUMN_PROVINCE + " AS " + SearchManager.SUGGEST_COLUMN_TEXT_2,
			R.drawable.location + " AS " + SearchManager.SUGGEST_COLUMN_ICON_1
		};
	}
}

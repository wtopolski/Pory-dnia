package pl.wtopolski.android.sunsetwidget.provider;

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
        public static final String MATCHER_FOR_SEARCH = "quicksearch/" + SearchManager.SUGGEST_URI_PATH_QUERY;
        public static final String MATCHER_FOR_SEARCH_ID = MATCHER_FOR_SEARCH + "/*";

        // COLUMN NAMES
        public static final String COLUMN_NAME_ID = BaseColumns._ID;
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
        public static final String COLUMN_NAME_PROVINCE = "province";
        public static final String COLUMN_NAME_SELECTION = "selection";

        // TYPES
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.locations.place";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.locations.place";
        
        // SORT
        public static final String DEFAULT_SORT_ORDER = COLUMN_NAME_NAME + " COLLATE LOCALIZED ASC";

        // STANDARD PROJECTION
        public static final String[] STANDARD_LOCATION_PROJECTION = new String[]{
                COLUMN_NAME_ID,
                COLUMN_NAME_NAME,
                COLUMN_NAME_LATITUDE,
                COLUMN_NAME_LONGITUDE,
                COLUMN_NAME_PROVINCE,
                COLUMN_NAME_SELECTION
        };
    }
}

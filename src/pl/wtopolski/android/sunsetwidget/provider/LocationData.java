package pl.wtopolski.android.sunsetwidget.provider;

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
        public static final String MATCHER_FOR_CONTENT_ID = PATH_SEGMENT + "/#";

        // COLUMN NAMES
        public static final String COLUMN_NAME_ID = BaseColumns._ID;
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
        public static final String COLUMN_NAME_PROVINCE = "province";
        public static final String COLUMN_NAME_FAVOURITES = "favourites";
        public static final String COLUMN_NAME_SELECTED = "selected";

        // SORT
        public static final String DEFAULT_SORT_ORDER = COLUMN_NAME_NAME + " ASC";

        // STANDARD PROJECTION
        public static final String[] STANDARD_LOCATION_PROJECTION = new String[] {
                COLUMN_NAME_ID,
                COLUMN_NAME_NAME,
                COLUMN_NAME_LATITUDE,
                COLUMN_NAME_LONGITUDE,
                COLUMN_NAME_PROVINCE,
                COLUMN_NAME_FAVOURITES,
                COLUMN_NAME_SELECTED
        };

        // ID ONLY PROJECTION
        public static final String[] ID_ONLY_LOCATION_PROJECTION = new String[] {
                COLUMN_NAME_ID
        };
    }
}

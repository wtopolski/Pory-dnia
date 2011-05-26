package pl.wtopolski.android.sunsetwidget.provider;

import android.content.*;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import static pl.wtopolski.android.sunsetwidget.provider.LocationData.*;
import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.*;

import java.util.HashMap;

public class LocationContentProvider extends ContentProvider {
    private static final String LOG_TAG = LocationContentProvider.class.getSimpleName();

    private static final String DATABASE_NAME = "locations.db";
    private static final int DATABASE_VERSION = 1;

    private static HashMap<String, String> locationsProjectionMap;

    private static final int LOCATIONS = 1;
    private static final int LOCATION_ID = 2;

    private static final UriMatcher uriMatcher;
    private DatabaseHelper mOpenHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, MATCHER_FOR_CONTENT, LOCATIONS);
        uriMatcher.addURI(AUTHORITY, MATCHER_FOR_CONTENT_ID, LOCATION_ID);

        locationsProjectionMap = new HashMap<String, String>();
        locationsProjectionMap.put(_ID, _ID);
        locationsProjectionMap.put(COLUMN_NAME_NAME, COLUMN_NAME_NAME);
        locationsProjectionMap.put(COLUMN_NAME_LATITUDE, COLUMN_NAME_LATITUDE);
        locationsProjectionMap.put(COLUMN_NAME_LONGITUDE, COLUMN_NAME_LONGITUDE);
        locationsProjectionMap.put(COLUMN_NAME_PROVINCE, COLUMN_NAME_PROVINCE);
        locationsProjectionMap.put(COLUMN_NAME_FAVOURITES, COLUMN_NAME_FAVOURITES);
        locationsProjectionMap.put(COLUMN_NAME_SELECTED, COLUMN_NAME_SELECTED);
    }

    static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME_NAME + " TEXT,"
                    + COLUMN_NAME_LATITUDE + " REAL,"
                    + COLUMN_NAME_LONGITUDE + " REAL,"
                    + COLUMN_NAME_PROVINCE + " TEXT,"
                    + COLUMN_NAME_FAVOURITES + " INTEGER,"
                    + COLUMN_NAME_SELECTED + " INTEGER"
                    + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case LOCATIONS:
                qb.setProjectionMap(locationsProjectionMap);
                break;
            case LOCATION_ID:
                qb.setProjectionMap(locationsProjectionMap);
                qb.appendWhere(_ID + "=" + uri.getPathSegments().get(LOCATION_ID_PATH_POSITION));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        String orderBy = (TextUtils.isEmpty(sortOrder)) ? DEFAULT_SORT_ORDER : sortOrder;

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        if (uriMatcher.match(uri) != LOCATIONS) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        ContentValues values = (contentValues != null) ? new ContentValues(contentValues) : new ContentValues();

        if (values.containsKey(COLUMN_NAME_NAME) == false) {
            throw new SQLException("No name");
        }

        if (values.containsKey(COLUMN_NAME_PROVINCE) == false) {
            throw new SQLException("No province");
        }

        if (values.containsKey(COLUMN_NAME_LATITUDE) == false) {
            throw new SQLException("No latitude");
        }

        if (values.containsKey(COLUMN_NAME_LONGITUDE) == false) {
            throw new SQLException("No longitude");
        }

        if (values.containsKey(COLUMN_NAME_FAVOURITES) == false) {
            throw new SQLException("No favourites");
        }

        if (values.containsKey(COLUMN_NAME_SELECTED) == false) {
            throw new SQLException("No selected");
        }

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        long rowId = db.insertOrThrow(TABLE_NAME, null, values);
        if (rowId > 0) {
            Uri pattern = Uri.parse(CONTENT_URI + "/");
            Uri noteUri = ContentUris.withAppendedId(pattern, rowId);
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }

        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;

        switch (uriMatcher.match(uri)) {

            case LOCATIONS:
                count = db.delete(TABLE_NAME, where, whereArgs);
                break;
            case LOCATION_ID:
                String finalWhere = _ID + " = " + uri.getPathSegments().get(LOCATION_ID_PATH_POSITION);

                if (where != null) {
                    finalWhere = finalWhere + " AND " + where;
                }

                count = db.delete(TABLE_NAME, finalWhere, whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String where, String[] whereArgs) {// Opens the database object in "write" mode.
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;

        switch (uriMatcher.match(uri)) {
            case LOCATIONS:
                count = db.update(TABLE_NAME, contentValues, where, whereArgs);
                break;
            case LOCATION_ID:
                String locationId = uri.getPathSegments().get(LOCATION_ID_PATH_POSITION);

                String finalWhere = _ID + " = " + uri.getPathSegments().get(LOCATION_ID_PATH_POSITION);

                if (where != null) {
                    finalWhere = finalWhere + " AND " + where;
                }

                count = db.update(TABLE_NAME, contentValues, finalWhere, whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}

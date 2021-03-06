package pl.wtopolski.android.sunsetwidget.provider;

import static pl.wtopolski.android.sunsetwidget.provider.LocationData.AUTHORITY;
import static pl.wtopolski.android.sunsetwidget.provider.LocationData.Locations.*;

import java.util.HashMap;

import pl.wtopolski.android.sunsetwidget.model.SelectionType;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class LocationContentProvider extends ContentProvider {
	protected static final String LOG_TAG = LocationContentProvider.class.getSimpleName();

	private HashMap<String, String> locationsProjectionMap;
	private UriMatcher uriMatcher;
	private DatabaseHelper mOpenHelper;

	private static final int LOCATIONS = 1;
	private static final int LOCATION_ID = 2;
	private static final int SEARCH_LOCATIONS_QUERY = 3;
	private static final int SEARCH_LOCATIONS_QUERY_ID = 4;
	private static final int SEARCH_FAVORITES_QUERY = 5;
	private static final int SEARCH_FAVORITES_QUERY_ID = 6;

	@Override
	public boolean onCreate() {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, MATCHER_FOR_CONTENT, LOCATIONS);
		uriMatcher.addURI(AUTHORITY, MATCHER_FOR_CONTENT_ID, LOCATION_ID);
		uriMatcher.addURI(AUTHORITY, MATCHER_FOR_SEARCH_LOCATIONS, SEARCH_LOCATIONS_QUERY);
		uriMatcher.addURI(AUTHORITY, MATCHER_FOR_SEARCH_LOCATIONS_ID, SEARCH_LOCATIONS_QUERY_ID);
		uriMatcher.addURI(AUTHORITY, MATCHER_FOR_SEARCH_FAVORITES, SEARCH_FAVORITES_QUERY);
		uriMatcher.addURI(AUTHORITY, MATCHER_FOR_SEARCH_FAVORITES_ID, SEARCH_FAVORITES_QUERY_ID);

		locationsProjectionMap = new HashMap<String, String>();
		locationsProjectionMap.put(COLUMN_ID, COLUMN_ID);
		locationsProjectionMap.put(COLUMN_NAME, COLUMN_NAME);
		locationsProjectionMap.put(COLUMN_SIMPLE_NAME, COLUMN_SIMPLE_NAME);
		locationsProjectionMap.put(COLUMN_LATITUDE, COLUMN_LATITUDE);
		locationsProjectionMap.put(COLUMN_LONGITUDE, COLUMN_LONGITUDE);
		locationsProjectionMap.put(COLUMN_PROVINCE, COLUMN_PROVINCE);
		locationsProjectionMap.put(COLUMN_SELECTION, COLUMN_SELECTION);
		
		mOpenHelper = new DatabaseHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(locationsProjectionMap);

		switch (uriMatcher.match(uri)) {
		case LOCATIONS:
			break;
		case LOCATION_ID:
			qb.appendWhere(COLUMN_ID + "=" + uri.getPathSegments().get(LOCATION_ID_PATH_POSITION));
			break;
		case SEARCH_LOCATIONS_QUERY:
		case SEARCH_LOCATIONS_QUERY_ID:
			projection = LocationData.Locations.SEARCH_LOCATION_PROJECTION;
			selection = COLUMN_SIMPLE_NAME + " LIKE ?";
			selectionArgs = new String[] { uri.getLastPathSegment() + "%" };
			break;
		case SEARCH_FAVORITES_QUERY:
		case SEARCH_FAVORITES_QUERY_ID:
			projection = LocationData.Locations.SEARCH_LOCATION_PROJECTION;
			selection = COLUMN_SIMPLE_NAME + " LIKE ? AND " + COLUMN_SELECTION + ">=?";
			selectionArgs = new String[] { uri.getLastPathSegment() + "%", String.valueOf(SelectionType.FAVOURITE.getValue())};
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
		switch (uriMatcher.match(uri)) {
		case LOCATIONS:
			return LocationData.Locations.CONTENT_TYPE;
		case LOCATION_ID:
			return LocationData.Locations.CONTENT_ITEM_TYPE;
		case SEARCH_LOCATIONS_QUERY:
		case SEARCH_LOCATIONS_QUERY_ID:
			return SearchManager.SUGGEST_MIME_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues contentValues) {
		if (uriMatcher.match(uri) != LOCATIONS) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		ContentValues values = (contentValues != null) ? new ContentValues(contentValues) : new ContentValues();

		if (values.containsKey(COLUMN_NAME) == false) {
			throw new SQLException("No name");
		}

		if (values.containsKey(COLUMN_SIMPLE_NAME) == false) {
			throw new SQLException("No simple name");
		}

		if (values.containsKey(COLUMN_PROVINCE) == false) {
			throw new SQLException("No province");
		}

		if (values.containsKey(COLUMN_LATITUDE) == false) {
			throw new SQLException("No latitude");
		}

		if (values.containsKey(COLUMN_LONGITUDE) == false) {
			throw new SQLException("No longitude");
		}

		if (values.containsKey(COLUMN_SELECTION) == false) {
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
			String finalWhere = COLUMN_ID + " = " + uri.getPathSegments().get(LOCATION_ID_PATH_POSITION);

			if (!TextUtils.isEmpty(where)) {
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
	public int update(Uri uri, ContentValues contentValues, String where, String[] whereArgs) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count;

		switch (uriMatcher.match(uri)) {
		case LOCATIONS:
			count = db.update(TABLE_NAME, contentValues, where, whereArgs);
			break;
		case LOCATION_ID:
			String finalWhere = COLUMN_ID + " = " + uri.getPathSegments().get(LOCATION_ID_PATH_POSITION);

			if (!TextUtils.isEmpty(where)) {
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

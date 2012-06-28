package pl.wtopolski.android.sunsetwidget.util;

import pl.wtopolski.android.sunsetwidget.model.GPSLocation;
import android.database.Cursor;
import android.net.Uri;

public interface LocationManager {
    void deleteAll();
    Uri addLocation(GPSLocation loc);
    Cursor getAllLocationsByCursor(String query);
    Cursor getAllFavouritesByCursor(String query);
    int getCount();
    GPSLocation getLocation(int id);
    boolean makeNoFavourite(GPSLocation location);
    boolean makeFavourite(GPSLocation location);
    boolean selectAsMain(GPSLocation location);
    GPSLocation getMainLocation();
}

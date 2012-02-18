package pl.wtopolski.android.sunsetwidget.util;

import pl.wtopolski.android.sunsetwidget.model.GPSLocation;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public interface LocationManager {
	
    void setContext(Context context);

    void setContext(Context context, LocationManagerListener listener);

    void deleteAll();

    Uri addLocation(GPSLocation loc);

    Cursor getAllLocationsIdByCursor();

    Cursor getAllFavouritesLocationsIdByCursor();

    GPSLocation getLocation(int id);
    
    boolean makeNoFavourite(GPSLocation location);
    
    boolean makeFavourite(GPSLocation location);
    
    boolean selectAsMain(GPSLocation location);
    
    GPSLocation getMainLocation();
}

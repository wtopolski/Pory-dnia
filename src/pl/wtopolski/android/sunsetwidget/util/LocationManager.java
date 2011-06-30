package pl.wtopolski.android.sunsetwidget.util;

import pl.wtopolski.android.sunsetwidget.model.Location;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public interface LocationManager {

    void setContext(Context context);

    void deleteAll();

    Uri addLocation(Location loc);

    Cursor getAllLocationsIdByCursor();

    Cursor getAllFavouritesLocationsIdByCursor();

    Location getLocation(int id);

    void revertSelection(Location loc);
}

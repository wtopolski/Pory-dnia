package pl.wtopolski.android.sunsetwidget.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import pl.wtopolski.android.sunsetwidget.model.Location;

import java.util.List;

public interface LocationManager {
    void setContext(Context context);

    Uri addLocation(Location loc);

    List<Location> getAllLocationsByList();

    Cursor getAllLocationsByCursor();
}

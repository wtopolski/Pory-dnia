package pl.wtopolski.android.sunsetwidget.util;

import android.content.Context;
import pl.wtopolski.android.sunsetwidget.model.Location;

import java.util.List;

public interface LocationManager {
    void setContext(Context context);

    boolean loadLocations();

    List<Location> getAllLocations();
}

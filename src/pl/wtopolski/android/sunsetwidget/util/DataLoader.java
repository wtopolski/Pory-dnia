package pl.wtopolski.android.sunsetwidget.util;

import android.content.Context;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public interface DataLoader {
    public boolean fill(Context context, LocationManager locationManager, int xmlResId) throws IOException, XmlPullParserException;
}

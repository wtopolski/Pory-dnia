package pl.wtopolski.android.sunsetwidget.util;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public interface DataLoader {
    public boolean fill(LocationManager locationManager, int xmlResId) throws IOException, XmlPullParserException;
}

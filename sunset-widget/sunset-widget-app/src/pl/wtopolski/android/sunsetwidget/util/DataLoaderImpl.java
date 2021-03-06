package pl.wtopolski.android.sunsetwidget.util;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import pl.wtopolski.android.sunsetwidget.MyApplication;
import pl.wtopolski.android.sunsetwidget.R;
import pl.wtopolski.android.sunsetwidget.model.GPSLocation;
import android.content.Context;
import android.content.res.XmlResourceParser;

public class DataLoaderImpl implements DataLoader {
    private static final String PLACE_TAG = "place";
    private static final String NAME_TAG = "name";
    private static final String LATITUDE_TAG = "latitude";
    private static final String LONGITUDE_TAG = "longitude";
    private static final String PROVINCE_TAG = "province";
    
	private DataLoaderListener listener;
	private Context context;
	
	public DataLoaderImpl(DataLoaderListener listener) {
		MyApplication myApplication = MyApplication.getMyApplication();
		context = myApplication.getApplicationContext();
		this.listener = listener;
	}

    public boolean fill(LocationManager locationManager, int xmlResId) throws IOException, XmlPullParserException {
        XmlResourceParser xmlResourceParser = context.getResources().getXml(R.xml.places);
        xmlResourceParser.next();

        int eventType = xmlResourceParser.getEventType();
        while (eventType != XmlResourceParser.END_DOCUMENT) {
            if (eventType == XmlResourceParser.START_TAG) {
                if (isStartTag(PLACE_TAG, xmlResourceParser)) {
                    GPSLocation location = processWithPlace(xmlResourceParser);
                    locationManager.addLocation(location);
                    if (listener != null) {
                    	listener.locationAdded();
                    }
                }
            }

            eventType = xmlResourceParser.next();
        }

        return true;
    }

    private GPSLocation processWithPlace(final XmlResourceParser xmlResourceParser) throws IOException, XmlPullParserException {
        String locationName = null;
        Double locationLatitude = null;
        Double locationLongitude = null;
        String locationProvince = null;

        while (keepFilling(locationName, locationLatitude, locationLongitude, locationProvince)) {
            int eventType = xmlResourceParser.next();
            if (eventType == XmlResourceParser.START_TAG) {
                if (isStartTag(NAME_TAG, xmlResourceParser)) {
                    locationName = processWithElement(xmlResourceParser);
                } else if (isStartTag(LATITUDE_TAG, xmlResourceParser)) {
                    String tmp = processWithElement(xmlResourceParser);
                    locationLatitude = Double.parseDouble(tmp);
                } else if (isStartTag(LONGITUDE_TAG, xmlResourceParser)) {
                    String tmp = processWithElement(xmlResourceParser);
                    locationLongitude = Double.parseDouble(tmp);
                } else if (isStartTag(PROVINCE_TAG, xmlResourceParser)) {
                    locationProvince = processWithElement(xmlResourceParser);
                }
            }
        }

        return new GPSLocation(0, locationName, locationLatitude, locationLongitude, locationProvince);
    }

    private boolean keepFilling(Object... objects) {
        for (Object obj : objects) {
            if (obj == null) {
                return true;
            }
        }

        return false;
    }

    private boolean isStartTag(final String tag, final XmlResourceParser xmlResourceParser) {
        return tag.equals(xmlResourceParser.getName());
    }

    private String processWithElement(final XmlResourceParser xmlResourceParser) throws IOException, XmlPullParserException {
        int eventType = xmlResourceParser.next();

        while (true) {
            if (eventType == XmlResourceParser.TEXT) {
                return xmlResourceParser.getText();
            }
        }
    }
}

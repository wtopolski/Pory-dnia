package pl.wtopolski.android.sunsetwidget.model;

import pl.wtopolski.android.sunsetwidget.core.model.TimeChange;
import pl.wtopolski.android.sunsetwidget.core.model.TimeConfig;
import pl.wtopolski.android.sunsetwidget.core.model.TimeZenit;
import pl.wtopolski.android.sunsetwidget.pref.ApplicationSettings;

public class GPSLocation {
    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private String province;
    private SelectionType type;

    public GPSLocation(int id, String name, double latitude, double longitude, String province) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.province = province;
        this.type = SelectionType.NONE;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
    
    public SelectionType getType() {
		return type;
	}

	public void setType(SelectionType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return name + ", " + province;
	}
	
	public TimeConfig convertToTimeLocation() {
    	TimeChange timeChange = ApplicationSettings.getTimeChangeSettings();
    	TimeZenit timeZenit = ApplicationSettings.getTimeZenitSettings();
		return new TimeConfig(latitude, longitude, timeZenit, timeChange);
	}
}

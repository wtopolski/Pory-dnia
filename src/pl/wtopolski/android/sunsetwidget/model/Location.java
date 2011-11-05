package pl.wtopolski.android.sunsetwidget.model;

public class Location {
    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private String province;
    private SelectionType type;

    public Location(int id, String name, double latitude, double longitude, String province) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.province = province;
        this.type = SelectionType.NONE;
    }

    public int getImageResourse() {
    	return type.getImage();
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
}

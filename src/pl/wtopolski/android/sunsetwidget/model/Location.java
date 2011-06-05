package pl.wtopolski.android.sunsetwidget.model;

public class Location {
    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private String province;
    private int favourites;
    private int selected;

    public Location(int id, String name, double latitude, double longitude, String province) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.province = province;
        this.setFavourites(0);
        this.setSelected(0);
    }

    public int getImageResourse() {
        return (favourites > 0) ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off;
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

    public int getFavourites() {
        return favourites;
    }

    public void setFavourites(int favourites) {
        this.favourites = favourites;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public void revertFavouriteState() {
        favourites = (favourites > 0) ? 0 : 1;
    }
}

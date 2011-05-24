package pl.wtopolski.android.sunsetwidget.model;

import java.util.Calendar;
import java.util.Date;

import pl.wtopolski.android.sunsetwidget.util.TimesCalculator;

public class TimePackage {
    private Date sunrise;
    private Date culmination;
    private Date sunset;

    public Date getSunrise() {
        return sunrise;
    }

    public void setSunrise(Date sunrise) {
        this.sunrise = sunrise;
    }

    public Date getCulmination() {
        return culmination;
    }

    public void setCulmination(Date culmination) {
        this.culmination = culmination;
    }

    public Date getSunset() {
        return sunset;
    }

    public void setSunset(Date sunset) {
        this.sunset = sunset;
    }

    public void addTwoHour() {
        final int count = 2;
        sunrise = addXHour(sunrise, count);
        culmination = addXHour(culmination, count);
        sunset = addXHour(sunset, count);
    }

    public void addOneHour() {
        final int count = 1;
        sunrise = addXHour(sunrise, count);
        culmination = addXHour(culmination, count);
        sunset = addXHour(sunset, count);
    }

    private Date addXHour(Date sunrise, int count) {
        Calendar calendar = TimesCalculator.createCalendarForNow();
        calendar.setTime(sunrise);
        calendar.add(Calendar.HOUR_OF_DAY, count);
        return calendar.getTime();
    }
}

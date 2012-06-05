package pl.wtopolski.android.sunsetwidget.model;

import pl.wtopolski.android.sunsetwidget.R;
import pl.wtopolski.android.sunsetwidget.core.model.Season;

public class SeasonAdapter {
	public static int getSeasonName(Season season) {
		switch (season) {
		case WINTER:
			return R.string.winter;
		case AUTUMN:
			return R.string.autumn;
		case SPRING:
			return R.string.spring;
		case SUMMER:
			return R.string.summer;
		default:
			return 0;
		}
	}
}

package pl.wtopolski.android.sunsetwidget.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import pl.wtopolski.android.sunsetwidget.MyApplication;
import pl.wtopolski.android.sunsetwidget.R;
import pl.wtopolski.android.sunsetwidget.core.model.TimePackage;
import pl.wtopolski.android.sunsetwidget.model.SeasonAdapter;

public enum PresentationView {
	TABLE {
		@Override
		public View getView(TimePackage timePackage) {
			Context context = MyApplication.getMyApplication().getApplicationContext();
			Typeface tf = MyApplication.getMyApplication().getTypeface();
			
			LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = layoutInflater.inflate(R.layout.presenter_page, null);

			String sunrise = "---";
			String culmination = "---";
			String sunset = "---";

			if (timePackage.isValid()) {
				sunrise = formatDate(timePackage.getSunrise());
				culmination = formatDate(timePackage.getCulmination());
				sunset = formatDate(timePackage.getSunset());
			}
			
			TextView seasonView = (TextView) view.findViewById(R.id.season);
			seasonView.setTypeface(tf);

			TextView sunriseView = (TextView) view.findViewById(R.id.sunrise);
			sunriseView.setTypeface(tf);

			TextView culminationView = (TextView) view.findViewById(R.id.culmination);
			culminationView.setTypeface(tf);

			TextView sunsetView = (TextView) view.findViewById(R.id.sunset);
			sunsetView.setTypeface(tf);

			TextView dayLength = (TextView) view.findViewById(R.id.dayLength);
			dayLength.setTypeface(tf);

			TextView dayInYear = (TextView) view.findViewById(R.id.dayInYear);
			dayInYear.setTypeface(tf);

			TextView longerThanTheShortestDayOfYear = (TextView) view.findViewById(R.id.longerThanTheShortestDayOfYear);
			longerThanTheShortestDayOfYear.setTypeface(tf);

			TextView shorterThanTheLongestDayOfYear = (TextView) view.findViewById(R.id.shorterThanTheLongestDayOfYear);
			shorterThanTheLongestDayOfYear.setTypeface(tf);

			int seasonName = SeasonAdapter.getSeasonName(timePackage.getSeason());

			seasonView.setText(seasonName);
			sunriseView.setText(sunrise);
			culminationView.setText(culmination);
			sunsetView.setText(sunset);
			
			String tmp = "---";
			if (timePackage.isValid()) {
				tmp = TimePackage.getSunsetSunriseDiffDescribe(timePackage.getLengthOfDay());
			}
			dayLength.setText(tmp);
			
			dayInYear.setText("" + timePackage.getCurrentDayInYear());

			tmp = "---";
			if (timePackage.isValid()) {
				tmp = TimePackage.getSunsetSunriseDiffDescribe(timePackage.getLongerThanTheShortestDayOfYear());
			}
			longerThanTheShortestDayOfYear.setText(tmp);
			
			tmp = "---";
			if (timePackage.isValid()) {
				tmp = TimePackage.getSunsetSunriseDiffDescribe(timePackage.getShorterThanTheLongestDayOfYear());
			}
			shorterThanTheLongestDayOfYear.setText(tmp);
			
			return view;
		}

		private String formatDate(Date date) {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			// TODO In v3 use LocaleTimeZone
			sdf.setTimeZone(TimeZone.getTimeZone("Europe/Warsaw"));
			return sdf.format(date);
		}
	},
	CLOCK {
		@Override
		public View getView(TimePackage timePackage) {
			View view = TABLE.getView(timePackage);
			view.setBackgroundColor(Color.GREEN);
			return view;
		}
	},
	TIME_LINE {
		@Override
		public View getView(TimePackage timePackage) {
			View view = TABLE.getView(timePackage);
			view.setBackgroundColor(Color.BLUE);
			return view;
		}
	};

	public abstract View getView(TimePackage timePackage);
}

package pl.wtopolski.android.sunsetwidget.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import pl.wtopolski.android.sunsetwidget.R;
import pl.wtopolski.android.sunsetwidget.core.model.TimePackage;

public enum PresentationView {
	TABLE {
		@Override
		public View getView(Context context, TimePackage timePackage) {
			LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View view = layoutInflater.inflate(R.layout.presenter_page, null);
	        
	        String sunrise = formatDate(timePackage.getSunrise());
	        String culmination = formatDate(timePackage.getCulmination());
	        String sunset = formatDate(timePackage.getSunset());

	        TextView sunriseView = (TextView) view.findViewById(R.id.sunrise);
	        TextView culminationView = (TextView) view.findViewById(R.id.culmination);
	        TextView sunsetView = (TextView) view.findViewById(R.id.sunset);
	        TextView dateDescribe = (TextView) view.findViewById(R.id.dateDescribe);
	        TextView dayLength = (TextView) view.findViewById(R.id.dayLength);
	        TextView dayInYear = (TextView) view.findViewById(R.id.dayInYear);
	        TextView longerThanTheShortestDayOfYear = (TextView) view.findViewById(R.id.longerThanTheShortestDayOfYear);
	        TextView shorterThanTheLongestDayOfYear = (TextView) view.findViewById(R.id.shorterThanTheLongestDayOfYear);

			final SimpleDateFormat dateDescribeFormater = new SimpleDateFormat(DATE_DESCRIBE_PATTERN);
			final SimpleDateFormat dayDescribeFormater = new SimpleDateFormat(DAY_DESCRIBE_PATTERN);

	    	final String describe = dateDescribeFormater.format(timePackage.getCulmination());
	    	final String dayName = dayDescribeFormater.format(timePackage.getCulmination());
	        
	        sunriseView.setText(sunrise);
	        culminationView.setText(culmination);
	        sunsetView.setText(sunset);
	        dateDescribe.setText(dayName + ", " + describe);
	        dayLength.setText(TimePackage.getSunsetSunriseDiffDescribe(timePackage.getLengthOfDay()));
	        dayInYear.setText(""+ timePackage.getCurrentDayInYear());
	        longerThanTheShortestDayOfYear.setText(TimePackage.getSunsetSunriseDiffDescribe(timePackage.getLongerThanTheShortestDayOfYear()));
	        shorterThanTheLongestDayOfYear.setText(TimePackage.getSunsetSunriseDiffDescribe(timePackage.getShorterThanTheLongestDayOfYear()));

			return view;
		}

	    private String formatDate(Date date) {
	        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	        return sdf.format(date);
	    }
	},
	CLOCK {
		@Override
		public View getView(Context context, TimePackage timePackage) {
			View view = TABLE.getView(context, timePackage);
			view.setBackgroundColor(Color.GREEN);
			return view;
		}
	},
	TIME_LINE {
		@Override
		public View getView(Context context, TimePackage timePackage) {
			View view = TABLE.getView(context, timePackage);
			view.setBackgroundColor(Color.BLUE);
			return view;
		}
	};

	private static final String DATE_DESCRIBE_PATTERN = "dd MMMM yyyy";
	private static final String DAY_DESCRIBE_PATTERN = "EEEE";
	
	public abstract View getView(Context context, TimePackage timePackage);
}

package pl.wtopolski.android.sunsetwidget.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import pl.wtopolski.android.sunsetwidget.MyApplication;
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

	        Typeface tf = MyApplication.getMyApplication().getTypeface();
	        
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
	        
	        sunriseView.setText(sunrise);
	        culminationView.setText(culmination);
	        sunsetView.setText(sunset);
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
	
	public abstract View getView(Context context, TimePackage timePackage);
}
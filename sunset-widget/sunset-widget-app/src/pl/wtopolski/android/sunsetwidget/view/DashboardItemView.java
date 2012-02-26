package pl.wtopolski.android.sunsetwidget.view;

import pl.wtopolski.android.sunsetwidget.R;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DashboardItemView extends LinearLayout {
	
	private LinearLayout dashboardItemRoot;
	private ImageButton dashboardItemIcon;
	private TextView dashboardItemDescription;

	public DashboardItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dashboard_item, this);
		
		dashboardItemRoot = (LinearLayout) view.findViewById(R.id.dashboardItemRoot);
		dashboardItemIcon = (ImageButton) view.findViewById(R.id.dashboardItemIcon);

    	Typeface font = Typeface.createFromAsset(context.getAssets(), "DroidSans.ttf");
		dashboardItemDescription = (TextView) view.findViewById(R.id.dashboardItemDescription);
		dashboardItemDescription.setTypeface(font);
	}
	
	public void setText(String describe) {
		dashboardItemDescription.setText(describe);
	}
	
	public void setIconSelector(int iconSelector) {
		dashboardItemIcon.setImageResource(iconSelector);
	}
	
	@Override
	public void setOnClickListener(OnClickListener l) {
		super.setOnClickListener(l);
		dashboardItemIcon.setOnClickListener(l);
		dashboardItemDescription.setOnClickListener(l);
	}
}

package pl.wtopolski.android.sunsetwidget.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class PresenterPageIndicatorView extends TextView implements OnPageChangeListener{
    protected static final String LOG_TAG = PresenterPageIndicatorView.class.getSimpleName();

	public PresenterPageIndicatorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setText("init");
	}

	public void onPageScrollStateChanged(int state) {
		switch (state) {
		case ViewPager.SCROLL_STATE_IDLE:
			Log.d(LOG_TAG, "==============> onPageScrollStateChanged: SCROLL_STATE_IDLE");
			break;
		case ViewPager.SCROLL_STATE_DRAGGING:
			Log.d(LOG_TAG, "==============> onPageScrollStateChanged: SCROLL_STATE_DRAGGING");
			break;
		case ViewPager.SCROLL_STATE_SETTLING:
			Log.d(LOG_TAG, "==============> onPageScrollStateChanged: SCROLL_STATE_SETTLING");
			break;
		}
	}

	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		// TODO Auto-generated method stub
		Log.d(LOG_TAG, "onPageScrolled: " + position + ", " + positionOffset + ", " + positionOffsetPixels);
		
		//setText(String.format("%s (%02.2f)", getTitle(position) ,positionOffset));
		setText(getTitle(position));
	}

	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		Log.d(LOG_TAG, "---------------------------> onPageSelected: " + position);
		setText(getTitle(position));
	}

	public String getTitle(int position) {
		String[] tab = {"Dziś", "Jutro", "Tydzień", "Miesiąc", "Kwartał"};
		return tab[position];
	}
}

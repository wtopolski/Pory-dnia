package pl.wtopolski.android.sunsetwidget.view;

import pl.wtopolski.android.sunsetwidget.MyApplication;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public class PresenterPageIndicatorView extends View implements OnPageChangeListener {
	protected static final String LOG_TAG = PresenterPageIndicatorView.class.getSimpleName();

	private String[] tabNames;
	private int currenPageNumber;
	private DisplayMetrics metrics;

	private Paint primaryTextPaint;
	private Paint secondaryTextPaint;
	private int positionOffsetPixels;

	public PresenterPageIndicatorView(Context context, AttributeSet attrs) {
		super(context, attrs);

		currenPageNumber = 0;
		positionOffsetPixels = 0;

		prepareDisplayMetrics();

		primaryTextPaint = new Paint();
		primaryTextPaint.setStyle(Style.FILL);
		primaryTextPaint.setTypeface(MyApplication.getMyApplication().getTypeface());
		primaryTextPaint.setTextSize(20 * metrics.scaledDensity);
		primaryTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		primaryTextPaint.setTextScaleX(1f);
		primaryTextPaint.setColor(Color.parseColor("#000000"));
		primaryTextPaint.setTextAlign(Paint.Align.CENTER);

		secondaryTextPaint = new Paint();
		secondaryTextPaint.setStyle(Style.FILL);
		secondaryTextPaint.setTypeface(MyApplication.getMyApplication().getTypeface());
		secondaryTextPaint.setTextSize(13 * metrics.scaledDensity);
		secondaryTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		secondaryTextPaint.setTextScaleX(1f);
		secondaryTextPaint.setColor(Color.parseColor("#666666"));
		secondaryTextPaint.setTextAlign(Paint.Align.CENTER);

		invalidate();
	}

	private void prepareDisplayMetrics() {
		metrics = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(metrics);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measureWidth(widthMeasureSpec),
				measureHeight(heightMeasureSpec));
	}

	protected int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else {
			result = getWidth();
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}

		return result;
	}

	protected int measureHeight(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else {
			result = getHeight();
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}

		return result;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawTitles(canvas);
	}

	private void drawTitles(Canvas canvas) {
		float xOffset = countXOffset();
		drawText(canvas, currenPageNumber, xOffset);
		if (currenPageNumber + 1 < tabNames.length) {
			xOffset += getWidth();
			drawText(canvas, currenPageNumber + 1, xOffset);
		}
	}

	private void drawText(Canvas canvas, int pageNumber, float xOffset) {
		String[] value = getTitle(pageNumber).split(":");

		float primaryTextYOffset = 23f * metrics.density;
		canvas.drawText(value[0], xOffset, primaryTextYOffset, primaryTextPaint);

		float secondaryTextYOffset = getHeight() - (3f * metrics.density);
		canvas.drawText(value[1], xOffset, secondaryTextYOffset,
				secondaryTextPaint);
	}

	private float countXOffset() {
		return (getWidth() / 2) - positionOffsetPixels;
	}

	public void onPageScrollStateChanged(int state) {
		switch (state) {
		case ViewPager.SCROLL_STATE_IDLE:
			break;
		case ViewPager.SCROLL_STATE_DRAGGING:
			break;
		case ViewPager.SCROLL_STATE_SETTLING:
			break;
		}
	}

	public void setTabNames(String[] tabNames) {
		this.tabNames = tabNames;
		invalidate();
	}

	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		this.currenPageNumber = position;
		this.positionOffsetPixels = positionOffsetPixels;
		invalidate();
	}

	public void onPageSelected(int position) {
	}

	protected Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();
		PresenterPageIndicatorViewMemory memory = new PresenterPageIndicatorViewMemory(superState);
		memory.setPageNumber(currenPageNumber);
		return memory;
	}

	protected void onRestoreInstanceState(Parcelable state) {
		if (!(state instanceof PresenterPageIndicatorViewMemory)) {
			super.onRestoreInstanceState(state);
			return;
		}

		PresenterPageIndicatorViewMemory memory = (PresenterPageIndicatorViewMemory) state;
		super.onRestoreInstanceState(memory.getSuperState());
		currenPageNumber = memory.getPageNumber();
		invalidate();
	}

	public String getTitle(int position) {
		if (tabNames != null) {
			return tabNames[position];
		} else {
			return "";
		}
	}
}

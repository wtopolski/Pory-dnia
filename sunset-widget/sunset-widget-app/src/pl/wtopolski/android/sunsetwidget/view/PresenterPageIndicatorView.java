package pl.wtopolski.android.sunsetwidget.view;

import java.util.LinkedList;
import java.util.List;

import pl.wtopolski.android.sunsetwidget.MyApplication;
import pl.wtopolski.android.sunsetwidget.R;
import android.content.Context;
import android.content.res.TypedArray;
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
	
	private List<Item> items;
	private int currentPageNumber;
	private int positionOffsetPixels;
	private float positionOffset;
	
	private DisplayMetrics metrics;
	
	private Paint dotPaint;
	private Paint dotStrokePaint;
	private Paint primaryTextPaint;
	private Paint secondaryTextPaint;

	public PresenterPageIndicatorView(Context context, AttributeSet attrs) {
		super(context, attrs);

		items = new LinkedList<Item>();
		prepareDisplayMetrics();
		
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PresenterPageIndicatorView);
		int primaryTextPaintColor = typedArray.getColor(R.styleable.PresenterPageIndicatorView_primaryTextColor, Color.BLACK);
		int secondaryTextPaintColor = typedArray.getColor(R.styleable.PresenterPageIndicatorView_secondaryTextColor, Color.BLACK);
		typedArray.recycle();
		
		dotPaint = new Paint();
		dotPaint.setStyle(Style.FILL);
		dotPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		dotPaint.setColor(Color.WHITE);
		
		dotStrokePaint = new Paint();
		dotStrokePaint.setStyle(Style.STROKE);
		dotStrokePaint.setStrokeWidth(1f * metrics.density);
		dotStrokePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		dotStrokePaint.setColor(Color.BLACK);
		
		primaryTextPaint = new Paint();
		primaryTextPaint.setStyle(Style.FILL);
		primaryTextPaint.setTypeface(MyApplication.getMyApplication().getTypeface());
		primaryTextPaint.setTextSize(20f * metrics.scaledDensity);
		primaryTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		primaryTextPaint.setTextScaleX(1f);
		primaryTextPaint.setColor(primaryTextPaintColor);
		primaryTextPaint.setTextAlign(Paint.Align.CENTER);

		secondaryTextPaint = new Paint();
		secondaryTextPaint.setStyle(Style.FILL);
		secondaryTextPaint.setTypeface(MyApplication.getMyApplication().getTypeface());
		secondaryTextPaint.setTextSize(13f * metrics.scaledDensity);
		secondaryTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		secondaryTextPaint.setTextScaleX(1f);
		secondaryTextPaint.setColor(secondaryTextPaintColor);
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
		setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
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
		if (!items.isEmpty()) {
			drawTitles(canvas);
			
			int alpha = countAlpha();
			dotPaint.setAlpha(alpha);
			dotStrokePaint.setAlpha(alpha);
			drawDots(canvas);
		}
	}

	private void drawTitles(Canvas canvas) {
		float xOffset = countXOffset();
		drawText(canvas, currentPageNumber, xOffset);
		if (currentPageNumber + 1 < items.size()) {
			xOffset += getWidth();
			drawText(canvas, currentPageNumber + 1, xOffset);
		}
	}

	private void drawText(Canvas canvas, int pageNumber, float xOffset) {
		Item item = items.get(pageNumber);

		float primaryTextYOffset = 21f * metrics.density;
		canvas.drawText(item.getPrimaryText(), xOffset, primaryTextYOffset, primaryTextPaint);

		float secondaryTextYOffset = getHeight() - (3f * metrics.density);
		canvas.drawText(item.getSecondaryText(), xOffset, secondaryTextYOffset, secondaryTextPaint);
	}

	private int countAlpha() {
		return (int)(Math.abs(0.5f - positionOffset) * 240f);
	}

	private void drawDots(Canvas canvas) {
		int localCurrentPageNumber = currentPageNumber;
		if (shouldMoveDot()) {
			localCurrentPageNumber += 1;
		}
		
		for (int index = 0; index < localCurrentPageNumber; index++) {
			drawDot(canvas, index + 1, 0);
		}
		
		for (int index = items.size() - localCurrentPageNumber - 1; index > 0; index--) {
			drawDot(canvas, index, getWidth());
		}
	}

	private boolean shouldMoveDot() {
		return (0.5f - positionOffset) < 0;
	}

	private void drawDot(Canvas canvas, int dotNumber, int xOffset) {
		float radius = 3f * metrics.density;
		float len = 3f * radius;
		
		float cy = getHeight() - len;
		float cx = Math.abs((float)xOffset - (len + metrics.density) * (float)dotNumber);
		
		canvas.drawCircle(cx, cy, radius, dotPaint);
		canvas.drawCircle(cx, cy, radius, dotStrokePaint);
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

	public void addItem(Item value) {
		items.add(value);
		invalidate();
	}

	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		this.currentPageNumber = position;
		this.positionOffset = positionOffset;
		this.positionOffsetPixels = positionOffsetPixels;
		invalidate();
	}

	public void onPageSelected(int position) {
	}

	protected Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();
		PresenterPageIndicatorViewMemory memory = new PresenterPageIndicatorViewMemory(superState);
		memory.setPageNumber(currentPageNumber);
		return memory;
	}

	protected void onRestoreInstanceState(Parcelable state) {
		if (!(state instanceof PresenterPageIndicatorViewMemory)) {
			super.onRestoreInstanceState(state);
			return;
		}

		PresenterPageIndicatorViewMemory memory = (PresenterPageIndicatorViewMemory) state;
		super.onRestoreInstanceState(memory.getSuperState());
		currentPageNumber = memory.getPageNumber();
		invalidate();
	}

	public class Item {
		private final String primaryText;
		private final String secondaryText;

		public Item(String primaryText, String secondaryText) {
			super();
			this.primaryText = primaryText;
			this.secondaryText = secondaryText;
		}

		public String getPrimaryText() {
			return primaryText;
		}

		public String getSecondaryText() {
			return secondaryText;
		}
	}
}

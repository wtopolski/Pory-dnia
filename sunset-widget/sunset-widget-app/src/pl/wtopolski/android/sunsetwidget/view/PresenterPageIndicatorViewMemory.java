package pl.wtopolski.android.sunsetwidget.view;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

public class PresenterPageIndicatorViewMemory extends View.BaseSavedState {
	private int pageNumber;

	public PresenterPageIndicatorViewMemory(Parcelable arg0) {
		super(arg0);
	}

	public PresenterPageIndicatorViewMemory(Parcel in) {
		super(in);
		pageNumber = in.readInt();
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void writeToParcel(Parcel out, int flags) {
		super.writeToParcel(out, flags);
		out.writeInt(pageNumber);
	}

	public static final Parcelable.Creator<PresenterPageIndicatorViewMemory> CREATOR = new Parcelable.Creator<PresenterPageIndicatorViewMemory>() {
		public PresenterPageIndicatorViewMemory createFromParcel(Parcel in) {
			return new PresenterPageIndicatorViewMemory(in);
		}

		public PresenterPageIndicatorViewMemory[] newArray(int size) {
			return new PresenterPageIndicatorViewMemory[size];
		}
	};
}
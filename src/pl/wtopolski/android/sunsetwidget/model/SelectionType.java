package pl.wtopolski.android.sunsetwidget.model;

import pl.wtopolski.android.sunsetwidget.R;

public enum SelectionType {
	NONE {
		@Override
		public int getValue() {
			return 0;
		}

		@Override
		public int getImage() {
			return R.drawable.star_off;
		}
	},
	FAVOURITE {
		@Override
		public int getValue() {
			return 1;
		}

		@Override
		public int getImage() {
			return R.drawable.star_on;
		}
	},
	SELECTED {
		@Override
		public int getValue() {
			return 2;
		}

		@Override
		public int getImage() {
			return R.drawable.star_check;
		}
	};
	
	public abstract int getValue();
	public abstract int getImage();
	
	public static SelectionType getSelectionType(int value) {
		switch (value) {
		case 0:
			return NONE;
		case 1:
			return FAVOURITE;
		case 2:
			return SELECTED;
		default:
			return NONE;
		}
	}
}

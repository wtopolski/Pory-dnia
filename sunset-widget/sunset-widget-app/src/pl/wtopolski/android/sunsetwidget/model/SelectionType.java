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
			return R.drawable.favorite_list_none;
		}
	},
	FAVOURITE {
		@Override
		public int getValue() {
			return 1;
		}

		@Override
		public int getImage() {
			return R.drawable.favorite_list_yes;
		}
	},
	SELECTED {
		@Override
		public int getValue() {
			return 2;
		}

		@Override
		public int getImage() {
			return R.drawable.favorite_list_main;
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

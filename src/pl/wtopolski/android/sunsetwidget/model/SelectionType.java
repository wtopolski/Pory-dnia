package pl.wtopolski.android.sunsetwidget.model;

import pl.wtopolski.android.sunsetwidget.R;

public enum SelectionType {
	NONE {
		@Override
		public int getValue() {
			return 0;
		}

		@Override
		public int getBackgroundResource() {
			return R.drawable.locations_item_selector_none;
		}
	},
	FAVOURITE {
		@Override
		public int getValue() {
			return 1;
		}

		@Override
		public int getBackgroundResource() {
			return R.drawable.locations_item_selector_favorite;
		}
	},
	SELECTED {
		@Override
		public int getValue() {
			return 2;
		}

		@Override
		public int getBackgroundResource() {
			return R.drawable.locations_item_selector_selected;
		}
	};
	
	public abstract int getValue();
	public abstract int getBackgroundResource();
	
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

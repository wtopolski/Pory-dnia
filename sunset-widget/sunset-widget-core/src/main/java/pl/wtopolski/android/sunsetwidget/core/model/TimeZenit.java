package pl.wtopolski.android.sunsetwidget.core.model;

public enum TimeZenit {
	OFFICIAL {
		@Override
		public double getValue() {
			return -0.833d;
		}
	},
	CIVIL {
		@Override
		public double getValue() {
			return -6d;
		}
	},
	NAUTICAL {
		@Override
		public double getValue() {
			return -12d;
		}
	},
	ASTRONOMICAL {
		@Override
		public double getValue() {
			return -18d;
		}
	};
	
	public abstract double getValue();
}

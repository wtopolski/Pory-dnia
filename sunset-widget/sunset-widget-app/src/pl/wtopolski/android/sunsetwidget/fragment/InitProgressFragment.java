package pl.wtopolski.android.sunsetwidget.fragment;

import pl.wtopolski.android.sunsetwidget.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InitProgressFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.init_progress_fragment, container, false);
	}
}

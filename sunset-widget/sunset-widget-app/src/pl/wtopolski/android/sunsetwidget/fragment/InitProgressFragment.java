package pl.wtopolski.android.sunsetwidget.fragment;

import pl.wtopolski.android.sunsetwidget.MyApplication;
import pl.wtopolski.android.sunsetwidget.R;
import pl.wtopolski.android.sunsetwidget.async.InitLoader;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

public class InitProgressFragment extends Fragment {
	protected static final String LOG_TAG = InitProgressFragment.class.getSimpleName();
	
	private ProgressBar progress;
	private TextView message;
	private InitLoaderReceiver receiver;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.init_progress_fragment, container, false);
		progress = (ProgressBar) view.findViewById(R.id.progress);
		
		message = (TextView) view.findViewById(R.id.message);
		Typeface typeface = MyApplication.getMyApplication().getTypeface();
		message.setTypeface(typeface);
		
		receiver = new InitLoaderReceiver();
		getActivity().registerReceiver(receiver, receiver.getIntentFilter());
		return view;
	}
	
	@Override
	public void onDestroyView() {
		if (receiver != null) {
			getActivity().unregisterReceiver(receiver);
			receiver = null;
		}
		super.onDestroyView();
	}
	
	private class InitLoaderReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			int value = intent.getIntExtra(InitLoader.INIT_LOADER_KEY, 0);
			progress.setProgress(value);
		}
		
		public IntentFilter getIntentFilter() {
			return new IntentFilter(InitLoader.INIT_LOADER_BROADCAST_ACTION);
		}
	}
}

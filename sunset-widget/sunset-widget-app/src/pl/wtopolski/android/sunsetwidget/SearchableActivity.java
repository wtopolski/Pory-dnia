package pl.wtopolski.android.sunsetwidget;

import pl.wtopolski.android.sunsetwidget.provider.LocationData;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SearchableActivity extends Activity {
    private TextView mTextView;
    private ListView mListView;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);

        mTextView = (TextView) findViewById(R.id.text);
        mListView = (ListView) findViewById(R.id.list);
		
		handleIntent(getIntent());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
		Log.d("wtopolski", "intent.getAction(): " + intent.getAction());
		
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			doMySearch(query);
		} else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
			Intent outputIntent = new Intent(this, MainActivity.class);
			outputIntent.putExtra(MainActivity.LOCATION_ID, Integer.valueOf(intent.getDataString()));
			finish();
			startActivity(outputIntent);
		}
	}

	private void doMySearch(String query) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "query: " + query, Toast.LENGTH_SHORT).show();
		showResults(query);
	}
	
	private void showResults(String query) {

        Cursor cursor = managedQuery(LocationData.Locations.CONTENT_URI, null, null, new String[] {query}, null);

        if (cursor == null) {
            // There are no results
            mTextView.setText(getString(R.string.empty, new Object[] {query}));
        } else {
            // Display the number of results
//            int count = cursor.getCount();
//            String countString = getResources().getQuantityString(R.plurals.search_results, count, new Object[] {count, query});
            mTextView.setText("countString");

            // Specify the columns we want to display in the result
            String[] from = new String[] { LocationData.Locations.COLUMN_NAME_NAME, LocationData.Locations.COLUMN_NAME_PROVINCE };

            int[] to = new int[] { R.id.firstLine, R.id.secondLine };

            SimpleCursorAdapter words = new SimpleCursorAdapter(this, R.layout.locations_item, cursor, from, to);
            mListView.setAdapter(words);
            mListView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    // Build the Intent used to open WordActivity with a specific word Uri
//                    Intent wordIntent = new Intent(getApplicationContext(), WordActivity.class);
//                    Uri data = Uri.withAppendedPath(DictionaryProvider.CONTENT_URI,
//                                                    String.valueOf(id));
//                    wordIntent.setData(data);
//                    startActivity(wordIntent);
                }
            });
        }
    }
}

package example.android.contentprovidersample2;

import android.os.Bundle;
import android.provider.ContactsContract.Contacts;

import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ContentProviderSample2Activity extends Activity {

	TableLayout tablelayout = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content_provider_sample2);

		tablelayout = (TableLayout)findViewById(R.id.tl_tablelayout);

		try {
			Cursor cur = getContentResolver().query(Contacts.CONTENT_URI,
					null,
					null,
					null,
					null);
			if (cur.getCount() != 0) {
				while (cur.moveToNext()) {
					String name = cur.getString(
							cur.getColumnIndex(Contacts.DISPLAY_NAME));
					setItems(name);
				}
			} else {
				TextView message = new TextView(this);
				message.setText("データが取得できませんでした。");
				LinearLayout linearlayout = (LinearLayout) findViewById(R.id.ll_linearlayout);
				linearlayout.addView(message);

			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("ERROR", e.getMessage());
		}
	}

	private void setItems(String name){
		TableRow row = new TableRow(this);
		TextView displayName = new TextView(this);
		displayName.setText(name);
		row.addView(displayName);
		tablelayout.addView(row);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.content_provider_sample2, menu);
		return true;
	}

}

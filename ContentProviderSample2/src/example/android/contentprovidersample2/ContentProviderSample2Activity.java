//https://github.com/7799/ContentProviderSample2
//refer http://junkcode.aakaka.com/archives/720
package example.android.contentprovidersample2;


import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ContentProviderSample2Activity extends Activity {

	//コメント1行追加 by kurata 20140522

	// private ListView mListView; // リストビュー
	// private ArrayAdapter<String> mAdapter; // リストビュー用のアダプタ

	TableLayout tablelayout = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content_provider_sample2);

		tablelayout = (TableLayout) findViewById(R.id.tl_tablelayout);

		// 一番単純なアダプタ
		// this.mAdapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1);

		// リストビューを取り出す
		// this.mListView = (ListView)this.findViewById(R.id.listView1);

		// アダプターをセットする
		// this.mListView.setAdapter(this.mAdapter);

		// メールアドレスあるやつだけ取り出す
		//
		ContentResolver cr = this.getContentResolver();

		// SQLiteにアクセスする様な感じ？
		Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);

		// 表示用の入れ物
		String output = "";

		// 何かとれてるときだけ実行
		if (cursor.moveToFirst()) {

			try {

				// すべての電話帳に登録されているデータ
				while (cursor.moveToNext()) {



					// 表示名
					String dispName = cursor
							.getString(cursor
									.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

					// コンタクトID
					String contactId = cursor.getString(cursor
							.getColumnIndex(BaseColumns._ID));
					Log.i(dispName + "コンタクトID", contactId);

					// 表示バッファに入れる
					output += "\n"+ dispName + "(" + contactId + ")";

					// 電話番号
					//

					// 電話番号取り出し用のカーソルを用意
					Cursor phoneCursor = cr.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = ?", new String[] { contactId }, null);

					output += "\n 電話番号 :";

					// 順番に表示
					while (phoneCursor.moveToNext()) {

						// 電話番号取り出す
						String phoneNumber = phoneCursor
								.getString(phoneCursor
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA1));

						output += " " + phoneNumber + "\n";
						// 電話番号
						Log.i(dispName + "の番号", phoneNumber);
					}

					// カーソルクローズは忘れずに！
					phoneCursor.close();

					// メールアドレス
					//

					output += "メアド :";

					// メールアドレス取り出し用のカーソルを用意
					Cursor mailCursor = cr.query(
							ContactsContract.CommonDataKinds.Email.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Email.CONTACT_ID
									+ " = ?", new String[] { contactId }, null);
					// 順番に表示
					while (mailCursor.moveToNext()) {
						String emailAddr = mailCursor
								.getString(mailCursor
										.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA1));

						output += " " + emailAddr + "\n";

						// メールアドレス
						Log.i(dispName + "のメアド", emailAddr);


					}

					// カーソルクローズは忘れずに！
					mailCursor.close();

					// リストビューに登録
					// this.mAdapter.add(output);

				}

				output += "\n"+"電話履歴"+"\n";
				Cursor cur = getContentResolver().query(CallLog.Calls.CONTENT_URI,
						null, null, null, null);

				while(cur.moveToNext()){
					String name = cur.getString(cur.getColumnIndex(CallLog.Calls.NUMBER));
					output += name+"\n";
				}



				// メインのカーソルも閉じる
				cursor.close();
				setItems(output);

			} catch (Exception exp) {
				exp.printStackTrace();
			}

		}

	}

	private void setItems(String output) {
		TableRow row = new TableRow(this);
		TextView displayName = new TextView(this);
		displayName.setText(output);
		row.addView(displayName);
		tablelayout.addView(row);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.content_provider_sample2, menu);
		return true;
	}

}


//
//package example.android.contentprovidersample2;
//
//import android.os.Bundle;
//import android.provider.ContactsContract.Contacts;
//
//import android.app.Activity;
//import android.database.Cursor;
//import android.util.Log;
//import android.view.Menu;
//import android.widget.LinearLayout;
//import android.widget.TableLayout;
//import android.widget.TableRow;
//import android.widget.TextView;
//
//public class ContentProviderSample2Activity extends Activity {
//
//	TableLayout tablelayout = null;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_content_provider_sample2);
//
//		tablelayout = (TableLayout) findViewById(R.id.tl_tablelayout);
//
//		try {
//			Cursor cur = getContentResolver().query(Contacts.CONTENT_URI, null,
//					null, null, null);
//			if (cur.getCount() != 0) {
//				while (cur.moveToNext()) {
//					String name = cur.getString(cur
//							.getColumnIndex(Contacts.DISPLAY_NAME));
//					setItems(name);
//				}
//			} else {
//				TextView message = new TextView(this);
//				message.setText("データが取得できませんでした。");
//				LinearLayout linearlayout = (LinearLayout) findViewById(R.id.ll_linearlayout);
//				linearlayout.addView(message);
//
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			Log.e("ERROR", e.getMessage());
//		}
//	}
//
//	private void setItems(String name) {
//		TableRow row = new TableRow(this);
//		TextView displayName = new TextView(this);
//		displayName.setText(name);
//		row.addView(displayName);
//		tablelayout.addView(row);
//
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.content_provider_sample2, menu);
//		return true;
//	}
//
//}
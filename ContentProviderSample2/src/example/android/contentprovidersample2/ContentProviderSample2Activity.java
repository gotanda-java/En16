//https://github.com/7799/ContentProviderSample2
//refer http://junkcode.aakaka.com/archives/720

//editit on githud
package example.android.contentprovidersample2;


import android.os.Bundle;
import android.provider.BaseColumns;
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

	// private ListView mListView; // ���X�g�r���[
	// private ArrayAdapter<String> mAdapter; // ���X�g�r���[�p�̃A�_�v�^

	TableLayout tablelayout = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content_provider_sample2);

		tablelayout = (TableLayout) findViewById(R.id.tl_tablelayout);

		// ���ԒP���ȃA�_�v�^
		// this.mAdapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1);

		// ���X�g�r���[�������o��
		// this.mListView = (ListView)this.findViewById(R.id.listView1);

		// �A�_�v�^�[���Z�b�g����
		// this.mListView.setAdapter(this.mAdapter);

		// ���[���A�h���X���������������o��
		//
		ContentResolver cr = this.getContentResolver();

		// SQLite�ɃA�N�Z�X�����l�Ȋ����H
		Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);

		// �\���p�̓��ꕨ
		String output = "";

		// �����Ƃ��Ă��Ƃ��������s
		if (cursor.moveToFirst()) {

			try {

				// ���ׂĂ̓d�b���ɓo�^�����Ă����f�[�^
				while (cursor.moveToNext()) {



					// �\����
					String dispName = cursor
							.getString(cursor
									.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

					// �R���^�N�gID
					String contactId = cursor.getString(cursor
							.getColumnIndex(BaseColumns._ID));
					Log.i(dispName + "�R���^�N�gID", contactId);

					// �\���o�b�t�@�ɓ�����
					output += "\n"+ dispName + "(" + contactId + ")";

					// �d�b�ԍ�
					//

					// �d�b�ԍ������o���p�̃J�[�\�����p��
					Cursor phoneCursor = cr.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = ?", new String[] { contactId }, null);

					output += "\n �d�b�ԍ� :";

					// ���Ԃɕ\��
					while (phoneCursor.moveToNext()) {

						// �d�b�ԍ������o��
						String phoneNumber = phoneCursor
								.getString(phoneCursor
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA1));

						output += " " + phoneNumber + "\n";
						// �d�b�ԍ�
						Log.i(dispName + "�̔ԍ�", phoneNumber);
					}

					// �J�[�\���N���[�Y�͖Y�ꂸ�ɁI
					phoneCursor.close();

					// ���[���A�h���X
					//

					output += "���A�h :";

					// ���[���A�h���X�����o���p�̃J�[�\�����p��
					Cursor mailCursor = cr.query(
							ContactsContract.CommonDataKinds.Email.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Email.CONTACT_ID
									+ " = ?", new String[] { contactId }, null);
					// ���Ԃɕ\��
					while (mailCursor.moveToNext()) {
						String emailAddr = mailCursor
								.getString(mailCursor
										.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA1));

						output += " " + emailAddr + "\n";

						// ���[���A�h���X
						Log.i(dispName + "�̃��A�h", emailAddr);


					}

					// �J�[�\���N���[�Y�͖Y�ꂸ�ɁI
					mailCursor.close();

					// ���X�g�r���[�ɓo�^
					// this.mAdapter.add(output);

				}

				// ���C���̃J�[�\��������
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
//				message.setText("�f�[�^���擾�ł��܂����ł����B");
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

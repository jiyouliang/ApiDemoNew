package com.example.android.apis.mydemo.provider;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.provider.ContactsContract.Profile;
import android.widget.Toast;

import com.example.android.apis.R;

public class ContactProviderActivity extends Activity implements View.OnClickListener {

    private static final int CODE_CONTACT = 1;
    private Button mBtnReadContact;
    private String[] projection = new String[]{ContactsContract.Data.DATA1,ContactsContract.Data.DISPLAY_NAME};
    private static final String TAG = "ContactProviderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_provider);
        initView();
    }

    private void initView() {
        mBtnReadContact = (Button) findViewById(R.id.btnReadContact);

        mBtnReadContact.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnReadContact:
                readContacts();
                break;
        }
    }

    private void readContacts() {
        int checkSelfPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if(checkSelfPermission == PackageManager.PERMISSION_DENIED){
            Toast.makeText(this, "用户拒绝访问", Toast.LENGTH_SHORT).show();
            return;
        }
        if(checkSelfPermission != PackageManager.PERMISSION_GRANTED){
            requestContactPermission();
            return;
        }

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Data.CONTENT_URI, projection, null, null, null);
//        boolean moveToFirst = cursor.moveToFirst();
        while (cursor.moveToNext()){
            String data = cursor.getString(0);
            String name = cursor.getString(1);
            Log.d(TAG, "data="+data+",name="+name);
        }
        cursor.close();
    }

    private void requestContactPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, CODE_CONTACT);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == CODE_CONTACT && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            readContacts();
        }
    }
}

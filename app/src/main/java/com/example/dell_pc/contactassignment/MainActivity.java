package com.example.dell_pc.contactassignment;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.net.URI;

public class MainActivity extends AppCompatActivity {
   public TextView outputText;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        outputText=(TextView) findViewById(R.id.info);
        fetchContacts();

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void fetchContacts() {

        String phoneNumber =null;
        String email=null;
         Uri CONTACT_URI = ContactsContract.Contacts.CONTENT_URI ;
         String _ID=ContactsContract.Contacts._ID;
         String DISPLAY_NAME=ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER=ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Uri PhoneCONTACT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI ;
        String Phone_CONTAC_ID=ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER=ContactsContract.CommonDataKinds.Phone.NUMBER;

        Uri EmailCONTACT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI ;
        String EmailCONTAC_ID=ContactsContract.CommonDataKinds.Email.CONTACT_ID;
        String DATA=ContactsContract.CommonDataKinds.Email.DATA;

        StringBuffer output= new StringBuffer();
        ContentResolver contentResolver=getContentResolver();
        Cursor cursor=contentResolver.query(CONTACT_URI,null,null,null,null);

        if(cursor.getCount()>0)
        {
            while (cursor.moveToNext())
            {
                String contact_id=cursor.getString(cursor.getColumnIndex(_ID));
                String name=cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                int hasPhoneNumber=Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
                if (hasPhoneNumber>0)
                {
                    output.append("\n First Name:" +name);

                    Cursor phneCursor=contentResolver.query(PhoneCONTACT_URI,null,Phone_CONTAC_ID+"=?",new String[]{contact_id},null);

                    while (phneCursor.moveToNext())
                    {
                        phoneNumber=phneCursor.getString(phneCursor.getColumnIndex(NUMBER));
                        output.append("\n Phone Number:"+phoneNumber);
                        phneCursor.close();
                        Cursor  EmailCursor =contentResolver.query(EmailCONTACT_URI,null,EmailCONTAC_ID+"=?",new String[]{contact_id},null);
                        while (phneCursor.moveToNext())
                        {
                            email=EmailCursor.getString(EmailCursor.getColumnIndex(DATA));
                            output.append("\n EMAIL:"+email);
                        }
                        EmailCursor.close();
                    }
                      output.append("\n");
            }
    }
}



}
}
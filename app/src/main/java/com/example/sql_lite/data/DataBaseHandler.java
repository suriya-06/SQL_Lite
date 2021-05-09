package com.example.sql_lite.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.LongDef;
import androidx.annotation.Nullable;

import com.example.sql_lite.R;
import com.example.sql_lite.model.Contact;
import com.example.sql_lite.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {


    public DataBaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    //Creating DB
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

       sqLiteDatabase.execSQL(" CREATE TABLE "+ Util.TABLE_NAME +"("+Util.TABLE_ID+" INTEGER PRIMARY KEY," +Util.NAME+ "TEXT," +Util.PHONE_NUMBER+ "TEXT" +")" );
    }
    //Upgrading DB
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP = String.valueOf((R.string.drop_db));
        sqLiteDatabase.execSQL(DROP, new String[]{Util.DATABASE_NAME});

        onCreate(sqLiteDatabase);
    }

    //Adding contact
    public void addContact(Contact contact) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //Creating contentvalue pairs
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.NAME, contact.getName());
        contentValues.put(Util.PHONE_NUMBER, contact.getContact());

        //Inserting the contentvalues
        sqLiteDatabase.insert(Util.TABLE_NAME, null, contentValues);
        Log.d("AddContact", "addContact: "+"item added");
        sqLiteDatabase.close();
    }

    //Getting Contact
    public Contact getContact(int id){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();

        //Creating Cursor
        Cursor cursor=sqLiteDatabase.query(Util.TABLE_NAME,new String[]{Util.NAME,Util.PHONE_NUMBER},
        Util.TABLE_ID+"=?",new String[]{String.valueOf(id)},null,null,null);

        //initializing the Contact Variables
        Contact contact=new Contact();
        contact.setId(Integer.parseInt(cursor.getString(0)));
        contact.setName(cursor.getString(1));
        contact.setContact(cursor.getString(2));

        //returning contact
        return contact;
    }

    //Get All Contact
    public List<Contact> getAllContact(){

        //Creating list of Contact type
        List<Contact> contactList=new ArrayList<>();

        //getting readable instance
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();

        String all="SELECT * FROM"+Util.TABLE_NAME;
        Cursor cursor=sqLiteDatabase.rawQuery(all,null);

        //Adding cursor data to contact
        if (cursor.moveToFirst()){
            do {

                //Creating instance of contact object
                Contact contact=new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setContact(cursor.getString(1));

                //Adding contact data to Contactlist
                contactList.add(contact);
            }while (cursor.moveToNext());
        }
        return contactList;
    }

    //Update Contact
    public int updateContact(Contact contact){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(Util.NAME,contact.getName());
        contentValues.put(Util.PHONE_NUMBER,contact.getContact());

        return sqLiteDatabase.update(Util.TABLE_NAME,contentValues,Util.TABLE_ID+"=?",
                new String[]{String.valueOf(contact.getId())});
    }

    //Deleting Contact
    public void deleteContact(Contact contact){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        sqLiteDatabase.delete(Util.TABLE_NAME,Util.TABLE_ID+"=?",
                new String[]{String.valueOf(contact.getId())});

        sqLiteDatabase.close();
    }
}

package com.example.sql_lite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.sql_lite.data.DataBaseHandler;
import com.example.sql_lite.model.Contact;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataBaseHandler handler=new DataBaseHandler(this);
        Contact contact=new Contact();
        contact.setName("Suriya");
        contact.setContact("9003697953");

        handler.addContact(contact);


    }
}
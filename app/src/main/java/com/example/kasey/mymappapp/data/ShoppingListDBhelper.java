//package com.example.kasey.mymappapp.data;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
///**
// * Created by Kasey on 11/29/2016.
// */
//
//public class ShoppingListDBhelper extends SQLiteOpenHelper {
//    public static final int DATABASE_VERSION =1;
//    public static final String DATABASE_NAME = "shoppinglist.db";
//
//    public ShoppingListDBhelper(Context context){
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//    public void onCreate(SQLiteDatabase db){
//        db.execSQL(SQL_CREATE_ENTRIES);
//    }
//
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
//        db.execSQL(SQL_DELETE_ENTRIES);
//        onCreate(db);
//    }
//
//    public void onDowngrase(SQLiteDatabase db, int oldVersion, int newVersion){
//        onUpgrade(db, oldVersion, newVersion);
//    }
//}
//

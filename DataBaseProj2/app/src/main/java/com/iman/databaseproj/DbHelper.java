package com.iman.databaseproj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;




public class DbHelper {

    private SQLiteDatabase db;

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "DataBase.db";

    public DbHelper() {

        String path = Environment.getExternalStorageDirectory().getPath().toString();
        db = SQLiteDatabase.openDatabase(path + "/DataBase.db",null, SQLiteDatabase.OPEN_READWRITE);
        
    }

    public Cursor Select (String TableName, String[] columns,String selection, String[] selectionArgs, String groupBy, String having, String orderBy,String limit){

        return db.query(TableName,columns,selection,selectionArgs,groupBy,having, orderBy,limit);


    }

    public long insert(String tableName, ContentValues cv){

        long dn = db.insert(tableName,null,cv);
        return dn;
    }


    public int getID(String tableName){
        Cursor c = db.rawQuery("SELECT MAX(ID) FROM " + tableName, null);
        c.moveToFirst();
        int id = c.getInt(0)+1;
        c.close();
        return id;
    }

    public int update(String tableName, ContentValues cv, String whereClause, String[] whereArgs){
        return db.update(tableName,cv, whereClause, whereArgs);
    }

    public void closeBD(){
        db.close();
    }

    public final class Names {
        private Names() {
        }

        public class Clients implements BaseColumns {
            public static final String NAME_TABLE_CLIENTS = "Clients";
            public static final String ID = "ID";
            public static final String NAME = "Name";
            public static final String PHONENUMBER = "PhoneNumber";
            public static final String EMAILADDRESS = "EmailAddress";
            public static final String ADDRESS = "Address";
        }

        public class Services implements BaseColumns{
            public static final String NAME_TABLE_SERVICES = "Services";
            public static final String ID = "ID";
            public static final String TITLE = "Title";
            public static final String WAGE = "Wage";
        }

        public class Orders implements BaseColumns{
            public static final String NAME_TABLE_SERVICES = "Orders";
            public static final String ID = "ID";
            public static final String CLIENTID = "ClientID";
            public static final String SERVICEID = "ServiceID";
            public static final String HOURS = "Hours";
            public static final String NUMBEROFCLEANERS = "NumberOfCleaners";
            public static final String COST = "Cost";
            public static final String ORDERDATE = "OrderDate";
            public static final String ISCHECKED = "IsChecked";
        }

    }
}

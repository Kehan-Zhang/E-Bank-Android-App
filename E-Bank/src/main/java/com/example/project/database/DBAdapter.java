package com.example.project.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.project.entity.User;

public class DBAdapter {
    private static final String DB_Name="Bank.db";
    private static final String DB_TABlE="user";
    private static final int DB_VERSION=9;

    public static final String KEY_ID="id";
    public static final String KEY_ACCOUNT="account";
    public static final String KEY_PASSWORD="password";
    public static final String KEY_BALANCE="balance";
    public static final String KEY_PAY="pay";

    private SQLiteDatabase db;
    private final Context context;
    private DBOpenHelper dbOpenHelper;

    private static class DBOpenHelper extends SQLiteOpenHelper{
        public DBOpenHelper(Context context,String name, SQLiteDatabase.CursorFactory factory,
                            int version){
            super(context, name, factory, version);
        }

        private static final String DB_CREATE="create table "+DB_TABlE+"("+
                KEY_ID+" integer primary key autoincrement, "+
                KEY_ACCOUNT+" text not null, "+
                KEY_PASSWORD+" text not null, "+
                KEY_PAY+" interger, "+
                KEY_BALANCE+" integer"+
                ");";

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+DB_TABlE);
            onCreate(db);
        }
    }

    public DBAdapter(Context _context){
        context=_context;
    }

    public void open()throws SQLException{
        dbOpenHelper=new DBOpenHelper(context,DB_Name,null,DB_VERSION);
        try{
            db=dbOpenHelper.getWritableDatabase();
        }catch(SQLException ex){
            db=dbOpenHelper.getReadableDatabase();
        }
    }

    public void close(){
        if(db!=null){
            db.close();
            db=null;
        }
    }

    public void insert(User user){
        ContentValues newValues=new ContentValues();
        newValues.put(KEY_ACCOUNT,user.Account);
        newValues.put(KEY_PASSWORD,user.Password);
        newValues.put(KEY_BALANCE,user.Balance);
        newValues.put(KEY_PAY,user.Pay);
        db.insert(DB_TABlE,null,newValues);
    }

    public void updatePay(String account,User user){
        ContentValues updateValues=new ContentValues();
        updateValues.put(KEY_PAY,user.Pay);
        db.update(DB_TABlE,updateValues,KEY_ACCOUNT+"="+account,null);
    }

    public void updateBalance(String account,User user){
        ContentValues updateValues=new ContentValues();
        updateValues.put(KEY_BALANCE,user.Balance);
        db.update(DB_TABlE,updateValues,KEY_ACCOUNT+"="+account,null);
    }

    private User[] ConvertToUser(Cursor cursor){
        int resultCounts=cursor.getCount();
        if(resultCounts==0||!cursor.moveToFirst()){
            return null;
        }
        User[] users=new User[resultCounts];
        for(int i=0;i<resultCounts;i++){
            users[i]=new User();
            users[i].ID=cursor.getInt(0);
            users[i].Account=cursor.getString(cursor.getColumnIndex(KEY_ACCOUNT));
            users[i].Password=cursor.getString(cursor.getColumnIndex(KEY_PASSWORD));
            users[i].Pay=cursor.getString(cursor.getColumnIndex(KEY_PAY));
            users[i].Balance=cursor.getInt(cursor.getColumnIndex(KEY_BALANCE));
            cursor.moveToNext();
        }
        return users;
    }

    public User[] getOneData(String a){
        Cursor results=db.query(DB_TABlE,null,KEY_ACCOUNT+"=?",new String[]{a},null,null,null);
        return ConvertToUser(results);
    }


}


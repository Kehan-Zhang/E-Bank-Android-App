package com.example.project.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.project.entity.Record;

public class DBHelper {
    private static final String DB_Name="Bill.db";
    private static final String DB_TABlE="record";
    private static final int DB_VERSION=9;

    public static final String KEY_ID="id";
    public static final String KEY_ACCOUNT="account";
    public static final String KEY_DETAIL="detail";
    public static final String KEY_TIME="time";
    public static final String KEY_AMOUNT="amount";

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
                KEY_DETAIL+" text not null, "+
                KEY_TIME+" text not null, "+
                KEY_AMOUNT+" integer"+
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

    public DBHelper(Context _context){
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

    public void insert(Record record){
        ContentValues newValues=new ContentValues();
        newValues.put(KEY_ACCOUNT,record.Account);
        newValues.put(KEY_DETAIL,record.Detail);
        newValues.put(KEY_TIME,record.Time);
        newValues.put(KEY_AMOUNT,record.Amount);
        db.insert(DB_TABlE,null,newValues);
    }

    private Record[] ConvertToRecord(Cursor cursor){
        int resultCounts=cursor.getCount();
        if(resultCounts==0||!cursor.moveToFirst()){
            return null;
        }
        Record[] records=new Record[resultCounts];
        for(int i=0;i<resultCounts;i++){
            records[i]=new Record();
            records[i].ID=cursor.getInt(0);
            records[i].Account=cursor.getString(cursor.getColumnIndex(KEY_ACCOUNT));
            records[i].Detail=cursor.getString(cursor.getColumnIndex(KEY_DETAIL));
            records[i].Time=cursor.getString(cursor.getColumnIndex(KEY_TIME));
            records[i].Amount=cursor.getInt(cursor.getColumnIndex(KEY_AMOUNT));
            cursor.moveToNext();
        }
        return records;
    }

    public Record[] getOneData(String a){
        Cursor results=db.query(DB_TABlE,null,KEY_ACCOUNT+"=?",new String[]{a},null,null,null);
        return ConvertToRecord(results);
    }


}



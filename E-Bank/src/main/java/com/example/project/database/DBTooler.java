package com.example.project.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.project.entity.Card;

public class DBTooler {
    private static final String DB_Name="Card.db";
    private static final String DB_TABlE="credit";
    private static final int DB_VERSION=9;

    public static final String KEY_ACCOUNT="account";
    public static final String KEY_OWNERNAME="ownername";
    public static final String KEY_TYPE="type";
    public static final String KEY_CARDNUMBER="cardnumber";
    public static final String KEY_BANKNAME="bankname";
    public static final String KEY_IDNUMBER="idnumber";
    public static final String KEY_CREDIT="credit";

    private SQLiteDatabase db;
    private final Context context;
    private DBTooler.DBOpenHelper dbOpenHelper;

    private static class DBOpenHelper extends SQLiteOpenHelper {
        public DBOpenHelper(Context context,String name, SQLiteDatabase.CursorFactory factory,
                            int version){
            super(context, name, factory, version);
        }

        private static final String DB_CREATE="create table "+DB_TABlE+"("+
                KEY_ACCOUNT+" text not null, "+
                KEY_OWNERNAME+" text not null, "+
                KEY_TYPE+" text not null, "+
                KEY_CARDNUMBER+" text not null, "+
                KEY_BANKNAME+" text not null, "+
                KEY_IDNUMBER+" text not null, "+
                KEY_CREDIT+" integer"+
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

    public DBTooler(Context _context){
        context=_context;
    }

    public void open()throws SQLException {
        dbOpenHelper=new DBTooler.DBOpenHelper(context,DB_Name,null,DB_VERSION);
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

    public void insert(Card card){
        ContentValues newValues=new ContentValues();
        newValues.put(KEY_ACCOUNT,card.Account);
        newValues.put(KEY_BANKNAME,card.BankName);
        newValues.put(KEY_CARDNUMBER,card.CardNumber);
        newValues.put(KEY_CREDIT,card.Credit);
        newValues.put(KEY_IDNUMBER,card.IDNumber);
        newValues.put(KEY_OWNERNAME,card.OwnerName);
        newValues.put(KEY_TYPE,card.Type);
        db.insert(DB_TABlE,null,newValues);
    }

    private Card[] ConvertToCard(Cursor cursor){
        int resultCounts=cursor.getCount();
        if(resultCounts==0||!cursor.moveToFirst()){
            return null;
        }
        Card[] cards=new Card[resultCounts];
        for(int i=0;i<resultCounts;i++){
            cards[i]=new Card();
            cards[i].Account=cursor.getString(cursor.getColumnIndex(KEY_ACCOUNT));
            cards[i].BankName=cursor.getString(cursor.getColumnIndex(KEY_BANKNAME));
            cards[i].CardNumber=cursor.getString(cursor.getColumnIndex(KEY_CARDNUMBER));
            cards[i].IDNumber=cursor.getString(cursor.getColumnIndex(KEY_IDNUMBER));
            cards[i].OwnerName=cursor.getString(cursor.getColumnIndex(KEY_OWNERNAME));
            cards[i].Type=cursor.getString(cursor.getColumnIndex(KEY_TYPE));
            cards[i].Credit=cursor.getInt(cursor.getColumnIndex(KEY_CREDIT));
            cursor.moveToNext();
        }
        return cards;
    }

    public Card[] getOneData(String a){
        Cursor results=db.query(DB_TABlE,null,KEY_ACCOUNT+"=?",new String[]{a},null,null,null);
        return ConvertToCard(results);
    }
}

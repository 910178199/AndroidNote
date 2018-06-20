package cn.handanlutong.parking.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ww on 2017/4/24.
 */

public class HistoryHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "history.db";
    private static final int DB_VERSTION = 1;

    /**表名*/
    public static final String TABLE_NAME = "record";
    /**id */
    public static final String ID_FIELD = "_id";
    /**地址*/
    public static final String ADDRESS_FIELD = "address";
    /**城市*/
    public static final String CITY_FIELD = "city";
    /**地区（县）*/
    public static final String DISCRIT_FIELD = "district";
    private static SQLiteOpenHelper mInstance;
    public static synchronized SQLiteOpenHelper getInstance(Context context){
        if(mInstance == null){
            mInstance = new HistoryHelper(context, DB_NAME, null, DB_VERSTION);
        }
        return mInstance;
    }

    private HistoryHelper(Context context, String name,
                          SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //	NULL，值是NULL
//	INTEGER，值是有符号整形，根据值的大小以1,2,3,4,6或8字节存放
//	REAL，值是浮点型值，以8字节IEEE浮点数存放
//	TEXT，值是文本字符串，使用数据库编码（UTF-8，UTF-16BE或者UTF-16LE）存放
//	BLOB，只是一个数据块，完全按照输入存放（即没有准换）
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists "+TABLE_NAME+" (" +
                ID_FIELD+" integer primary key autoincrement, " +
                ADDRESS_FIELD+" TEXT, "+CITY_FIELD+" TEXT, "+DISCRIT_FIELD+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_NAME);
        db.execSQL("create table if not exists "+TABLE_NAME+" (" +
                ID_FIELD+" integer primary key autoincrement, " +
                ADDRESS_FIELD+" TEXT, "+CITY_FIELD+" TEXT, "+DISCRIT_FIELD+" TEXT)");
    }
}

package cn.handanlutong.parking.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.handanlutong.parking.bean.HistoryBean;

public class HistoryDao extends BaseDao<HistoryBean> {
    private static final String TAG = "HistoryDao";
    private Context context;
    private SQLiteOpenHelper helper;

    public HistoryDao(Context context) {
        super();
        this.context = context;
        helper = HistoryHelper.getInstance(context);
    }

    @Override
    public void addOne(HistoryBean bean) {
        SQLiteDatabase db = helper.getWritableDatabase();
        // 判断是否存储到数据库过
        boolean addressHasSave = queryAddressHasSave(bean.address, db);
        if (!addressHasSave) {
            try {
                db.beginTransaction();
                ContentValues values = new ContentValues();
                values.put(HistoryHelper.DISCRIT_FIELD, bean.district);
                values.put(HistoryHelper.ADDRESS_FIELD, bean.address);
                values.put(HistoryHelper.CITY_FIELD, bean.city);
                db.insert(HistoryHelper.TABLE_NAME, null, values);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
            }
        }
        db.close();
    }

    @Override
    public void addAll(List<HistoryBean> lists) {
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            db.beginTransaction();
            for (HistoryBean bean : lists) {
                ContentValues values = new ContentValues();
                values.put(HistoryHelper.ADDRESS_FIELD, bean.address);
                values.put(HistoryHelper.CITY_FIELD, bean.city);
                values.put(HistoryHelper.DISCRIT_FIELD, bean.district);
                db.insert(HistoryHelper.TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        db.close();
    }

    @Override
    public List<HistoryBean> queryAll() {
        ArrayList<HistoryBean> beans = new ArrayList<HistoryBean>();
        HistoryBean bean = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        String [] columns={HistoryHelper.ID_FIELD,HistoryHelper.ADDRESS_FIELD,HistoryHelper.CITY_FIELD,HistoryHelper.DISCRIT_FIELD};
//        Cursor cursor = db.query(HistoryHelper.TABLE_NAME, new String[] { "*" }, null, null, null, null, null, null);
//        Cursor cursor = db.rawQuery("select * from "+HistoryHelper.TABLE_NAME, null);
        Cursor cursor = db.query(HistoryHelper.TABLE_NAME, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            bean = new HistoryBean();
            bean._id = cursor.getInt(0);
            bean.address = cursor.getString(1);
            bean.city = cursor.getString(2);
            bean.district = cursor.getString(3);
            beans.add(bean);
        }
        cursor.close();
        db.close();
        return beans;
    }

    /**
     * 查询地址是否存储过
     *
     * @return
     */
    private boolean queryAddressHasSave(String address, SQLiteDatabase db) {
        int count = -1;
        Cursor cursor = db.query(HistoryHelper.TABLE_NAME, new String[] { "count(_id) as count" }, HistoryHelper.ADDRESS_FIELD + " = ?",
                new String[] { address }, null, null, null, null);
        if (cursor.moveToNext()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count > 0;
    }

//	@Override
//	public List<HistoryBean> queryAll() {
//		ArrayList<HistoryBean> beans = new ArrayList<HistoryBean>();
//		HistoryBean bean = null;
//		SQLiteDatabase db = helper.getReadableDatabase();
//		Cursor cursor = db.query(HistoryHelper.TABLE_NAME, new String[] { "*" }, null, null, null, null, null, null);
//		while (cursor.moveToNext()) {
//			bean = new HistoryBean();
//			bean._id = cursor.getInt(cursor.getColumnIndex(HistoryHelper.ID_FIELD));
//			bean.address = cursor.getString(cursor.getColumnIndex(HistoryHelper.ADDRESS_FIELD));
//			bean.isHistory = true;
//			bean.lat = cursor.getDouble(cursor.getColumnIndex(HistoryHelper.LAT_FIELD));
//			bean.lng = cursor.getDouble(cursor.getColumnIndex(HistoryHelper.LNG_FIELD));
//			beans.add(bean);
//		}
//		cursor.close();
//		db.close();
//		returnn beans;
//	}

    /**
     * 模糊查询地址信息
     * @return
     */
    public List<HistoryBean> vagueQueryByAddress(String str) {
        ArrayList<HistoryBean> beans = new ArrayList<HistoryBean>();
        HistoryBean bean = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        String condition = "select * from "+HistoryHelper.TABLE_NAME+" where "+HistoryHelper.ADDRESS_FIELD+" LIKE \'%" + str +"%\'";
        Cursor cursor = db.rawQuery(condition, null);
        while (cursor.moveToNext()) {
            bean = new HistoryBean();
            bean._id = cursor.getInt(cursor.getColumnIndex(HistoryHelper.ID_FIELD));
            bean.address = cursor.getString(cursor.getColumnIndex(HistoryHelper.ADDRESS_FIELD));
            beans.add(bean);
        }
        cursor.close();
        db.close();
        return beans;
    }

    /**
     * 删除所有数据
     */
    @Override
    public void deleteAll() {
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            db.beginTransaction();
            db.delete(HistoryHelper.TABLE_NAME, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        db.close();
    }
}

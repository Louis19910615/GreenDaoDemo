package louis.greendaodemo.greendao;

import android.database.sqlite.SQLiteDatabase;

import louis.greendaodemo.GreenDaoApplication;
import louis.greendaodemo.greendao.gen.DaoMaster;
import louis.greendaodemo.greendao.gen.DaoSession;
import louis.greendaodemo.greendao.upgrade.GreenDaoOpenHelper;

/**
 * Created by louis on 2017/4/12.
 */

public class GreenDaoHelper {
    private static GreenDaoOpenHelper mHelper;
    private static SQLiteDatabase db;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

    /**
     * 初始化greenDao，这个操作建议在Application初始化的时候添加；
     */
    public static void initDatabase() {

        mHelper = new GreenDaoOpenHelper(GreenDaoApplication.mContext, "lianluo-db", null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
    public static DaoSession getDaoSession() {
        return mDaoSession;
    }
    public static SQLiteDatabase getDb() {
        return db;
    }
}

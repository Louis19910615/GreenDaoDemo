package louis.greendaodemo.greendao.upgrade;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.yuweiguocn.library.greendao.MigrationHelper;

import louis.greendaodemo.greendao.entity.SyncManager;
import louis.greendaodemo.greendao.gen.AreaDao;
import louis.greendaodemo.greendao.gen.DaoMaster;
import louis.greendaodemo.greendao.gen.SyncManagerDao;
import louis.greendaodemo.greendao.gen.UserDao;

/**
 * Created by louis on 2017/4/13.
 */

public class GreenDaoOpenHelper extends DaoMaster.OpenHelper {

    public GreenDaoOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db,UserDao.class, AreaDao.class, SyncManagerDao.class);
    }
}

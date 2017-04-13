package louis.greendaodemo;

import android.app.Application;
import android.content.Context;

import louis.greendaodemo.greendao.GreenDaoHelper;

/**
 * Created by louis on 2017/4/12.
 */

public class GreenDaoApplication extends Application {

    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
        GreenDaoHelper.initDatabase();

    }
}

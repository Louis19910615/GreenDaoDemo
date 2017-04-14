package louis.greendaodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import louis.greendaodemo.greendao.GreenDaoHelper;
import louis.greendaodemo.greendao.entity.Area;
import louis.greendaodemo.greendao.entity.SyncManager;
import louis.greendaodemo.greendao.gen.AreaDao;
import louis.greendaodemo.greendao.gen.SyncManagerDao;
import louis.greendaodemo.greendao.utils.Util;

public class MainActivity extends AppCompatActivity {

    private AreaDao mAreaDao;
    private SyncManagerDao mSyncManagerDao;

    private List<String> areaId = new ArrayList<String>();
    private List<String> areaNameCN = new ArrayList<String>();
    private List<String> areaNameEN = new ArrayList<String>();
    private List<String> country = new ArrayList<String>();
    private List<String> attributionCN = new ArrayList<String>();
    private List<String> attributionEN = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    private void initData() {
        mAreaDao = GreenDaoHelper.getDaoSession().getAreaDao();
        mSyncManagerDao = GreenDaoHelper.getDaoSession().getSyncManagerDao();

//        initDataBase();
        testDatabase();

        if (isInitDataBase()) {
            queryTest();
        }

    }

    private void initDataBase() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                readAssetsFileOnLine("cityId", areaId);
                readAssetsFileOnLine("areaNameCN",areaNameCN);
                readAssetsFileOnLine("areaNameEN",areaNameEN);
                readAssetsFileOnLine("country",country);
                readAssetsFileOnLine("attributionCN",attributionCN);
                readAssetsFileOnLine("attributionEN",attributionEN);

//        for(String str : areaNameCN) {
//            Log.d("MainActivity", str);
//        }

                mAreaDao.deleteAll();
                for (int i = 0; i < areaId.size(); i++) {
                    Area area = new Area(areaId.get(i), areaNameCN.get(i), areaNameEN.get(i), country.get(i), attributionCN.get(i), attributionEN.get(i));
                    mAreaDao.insert(area);
                }


                Log.d("MainActivity", String.valueOf(mAreaDao.count()));

                queryTest();

                Util.copyDataBaseToSD();


            }
        }).start();

    }

    private void testDatabase() {
        if (isInitDataBase()) {
            return;
        }
        try {
            Util.copyDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SyncManager syncManager = new SyncManager("InitDataBase", true);
        mSyncManagerDao.insert(syncManager);
        queryTest();
    }

    private boolean isInitDataBase() {
        SyncManager syncManager = mSyncManagerDao.queryBuilder().where(SyncManagerDao.Properties.Key.eq("InitDataBase")).unique();
        if (syncManager != null && syncManager.getValue()) {
            return true;
        }


        return false;
    }

    private void queryTest() {

        List<Area> areaList = (List<Area>) mAreaDao.queryBuilder().where(AreaDao.Properties.AreaNameCN.like("朝阳%")).build().list();
        for (Area area:areaList) {
            Log.d("MainActivity", area.getAreaNameCN());
            Log.d("MainActivity", area.getAttributionCN());
        }

    }

    public void readAssetsFileOnLine(String fileName, List<String> list) {

        BufferedReader reader;
        try {

            BufferedInputStream in = new BufferedInputStream(getResources().getAssets().open(fileName));
            reader = new BufferedReader(new InputStreamReader(in, "utf-8"));

            String strLine = null;
            while((strLine =  reader.readLine() ) != null) {
                list.add(strLine);
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

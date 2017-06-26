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
import louis.greendaodemo.greendao.utils.Cn2Spell;
import louis.greendaodemo.greendao.utils.Util;

public class MainActivity extends AppCompatActivity {

    private AreaDao mLocationAreaDao;
    private SyncManagerDao mSyncManagerDao;

    private List<String> areaId = new ArrayList<String>();
    private List<String> areaNameCN = new ArrayList<String>();
    private List<String> areaNameEN = new ArrayList<String>();
//    private List<String> countryCode = new ArrayList<String>();
    private List<String> attributionCNOne = new ArrayList<String>();
//    private List<String> attributionENOne = new ArrayList<String>();
    private List<String> attributionCNTwo = new ArrayList<String>();
//    private List<String> attributionENTwo = new ArrayList<String>();
    private List<String> areaProperty = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    private void initData() {

        GreenDaoHelper.initDatabase();
        mSyncManagerDao = GreenDaoHelper.getDaoSession().getSyncManagerDao();

//        forceUpdateDB();

        GreenDaoHelper.initLocationDatabase();
        mLocationAreaDao = GreenDaoHelper.getLocationDaoSession().getAreaDao();
        initDataBase();


    }

    private void forceUpdateDB() {
        if (isInitDataBase(GreenDaoHelper.DB_VERSION)) {
            Log.d("MainActivity", "init database.");
            GreenDaoHelper.initLocationDatabase();
            mLocationAreaDao = GreenDaoHelper.getLocationDaoSession().getAreaDao();

            queryTest();
        } else {
            Log.d("MainActivity", "uninit database.");

            forceSubstitute();

        }
    }

    private void initDataBase() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                readAssetsFileOnLine("areaId",areaId);
                readAssetsFileOnLine("areaNameCN",areaNameCN);
                readAssetsFileOnLine("areaNameEN",areaNameEN);
//                readAssetsFileOnLine("countryCode",countryCode);
                readAssetsFileOnLine("attributionCNOne",attributionCNOne);
//                readAssetsFileOnLine("attributionENOne",attributionENOne);
                readAssetsFileOnLine("attributionCNTwo",attributionCNTwo);
//                readAssetsFileOnLine("attributionENTwo",attributionENTwo);
                readAssetsFileOnLine("areaProperty",areaProperty);


                mLocationAreaDao.deleteAll();
//                String attributionCNOneStr;
//                String attributionENOneStr;
//                String attributionCNTwoStr;
//                String attributionENTwoStr;
                for (int i = 0; i < areaNameCN.size(); i++) {
//                    if (attributionCNTwo.size() > i && attributionCNTwo.get(i) != null && attributionCNTwo.get(i).length() > 0 && !attributionCNTwo.get(i).equals(areaNameCN.get(i)) ) {
////                        Log.d("MainActivity", "attributionCNTwo" + attributionCNTwo.get(i) + String.valueOf(i));
//                        attributionCNOneStr = null;
//                        attributionENOneStr = null;
//                        attributionCNTwoStr = attributionCNTwo.get(i);
//                        attributionENTwoStr = attributionENTwo.get(i);
//                    } else {
////                        Log.d("MainActivity", "attributionCNOne" + attributionCNOne.get(i));
//                        attributionCNOneStr = attributionCNOne.get(i);
//                        attributionENOneStr = attributionENOne.get(i);
//                        attributionCNTwoStr = null;
//                        attributionENTwoStr = null;
//                    }
                    Log.d("MainActivity", Cn2Spell.getPinYinHeadChar(areaNameCN.get(i)).toUpperCase() + String.valueOf(i));
                    String attributionCNOneStr = null;
                    if (areaProperty.get(i).equals("中国其他")) {
                        if (attributionCNOne.get(i) != null && attributionCNOne.get(i).length() > 0) {
                            attributionCNOneStr = attributionCNOne.get(i);
                        } else {
                            attributionCNOneStr = attributionCNTwo.get(i);
                        }
                    }
                    Area area = new Area(areaId.get(i), areaNameCN.get(i), areaNameEN.get(i), Cn2Spell.getPinYinHeadChar(areaNameCN.get(i)).toUpperCase(), attributionCNOneStr);
                    mLocationAreaDao.insert(area);
                }

                Log.d("MainActivity", String.valueOf(mLocationAreaDao.count()));

                queryTest();

                Util.copyDataBaseToSD();


            }
        }).start();

    }

//    private void testDatabase() {
//        if (isInitDataBase(GreenDaoHelper.DB_VERSION)) {
//            return;
//        }
//        try {
//            Util.copyDataBase();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        SyncManager syncManager = new SyncManager("InitDataBase", "1.2");
//        mLocationSyncManagerDao.insert(syncManager);
//        queryTest();
//    }

    private void forceSubstitute() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Util.copyDataBase();
                    setInitDataBase(GreenDaoHelper.DB_VERSION);

                    GreenDaoHelper.initLocationDatabase();
                    mLocationAreaDao = GreenDaoHelper.getLocationDaoSession().getAreaDao();
                    queryTest();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private boolean isInitDataBase(String version) {
        SyncManager syncManager = mSyncManagerDao.queryBuilder().where(SyncManagerDao.Properties.Key.eq("InitDataBase")).unique();
        if (syncManager != null && version.equals(syncManager.getValue())) {
            return true;
        }

        return false;
    }

    private void setInitDataBase(String version) {
        SyncManager syncManager = mSyncManagerDao.queryBuilder().where(SyncManagerDao.Properties.Key.eq("InitDataBase")).unique();

        if (syncManager == null) {
            syncManager = new SyncManager("InitDataBase", version);
            mSyncManagerDao.insert(syncManager);
        } else {
            if (version.equals(syncManager.getValue())){
                return;
            } else {
                mSyncManagerDao.deleteAll();
                SyncManager syncMangerNew = new SyncManager("InitDataBase", version);
                mSyncManagerDao.insert(syncMangerNew);
            }
        }
    }

    private void queryTest() {

        List<Area> areaList2 = (List<Area>) mLocationAreaDao.queryBuilder().where(AreaDao.Properties.AreaNameCN.like("朝阳%")).build().list();
        for (Area area:areaList2) {
            Log.d("MainActivity", area.getAreaNameCN());
            Log.d("MainActivity", "" + area.getAttributionCNOne());
//            Log.d("MainActivity", "" + area.getAttributionCNTwo());
            Log.d("MainActivity", "" + area.getAreaId());
        }

        List<Area> areaList1 = (List<Area>) mLocationAreaDao.queryBuilder().where(AreaDao.Properties.AreaNameShorter.like("CY%")).build().list();
        for (Area area:areaList1) {
            Log.d("MainActivity", area.getAreaNameCN());
            Log.d("MainActivity", "" + area.getAttributionCNOne());
//            Log.d("MainActivity", "" + area.getAttributionCNTwo());
            Log.d("MainActivity", "" + area.getAreaId());
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

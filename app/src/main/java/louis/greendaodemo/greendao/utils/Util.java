package louis.greendaodemo.greendao.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

import louis.greendaodemo.GreenDaoApplication;
import louis.greendaodemo.R;

/**
 * Created by louis on 2017/4/14.
 */

public class Util {
    /**
     * 拷贝数据库到sd卡
     *
     * @deprecated <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     */
    public static void copyDataBaseToSD(){
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return ;
        }
        File dbFile = GreenDaoApplication.mContext.getDatabasePath("lianluo-db");
        File file  = new File(Environment.getExternalStorageDirectory(), "lianluo-db");
        Log.d("Util", file.getAbsolutePath());
        FileChannel inChannel = null,outChannel = null;

        try {
            file.createNewFile();
            inChannel = new FileInputStream(dbFile).getChannel();
            outChannel = new FileOutputStream(file).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (Exception e) {
            Log.e("Util", "copy dataBase to SD error.");
            e.printStackTrace();
        }finally{
            try {
                if (inChannel != null) {
                    inChannel.close();
                    inChannel = null;
                }
                if(outChannel != null){
                    outChannel.close();
                    outChannel = null;
                }
            } catch (IOException e) {
                Log.e("Util", "file close error.");
                e.printStackTrace();
            }
        }
    }

    /**
     * 复制数据库到手机指定文件夹下
     *
     * @throws IOException
     */
    public static void copyDataBase() throws IOException {

        String packageName = GreenDaoApplication.mContext.getPackageName();
        String DATABASE_PATH = "/data/data/" + packageName + "/databases/";
        String databaseFilenames = DATABASE_PATH + "lianluo-db";
        File dir = new File(DATABASE_PATH);
        if (!dir.exists())// 判断文件夹是否存在，不存在就新建一个
            dir.mkdir();
        FileOutputStream os = new FileOutputStream(databaseFilenames);// 得到数据库文件的写入流
        InputStream is = GreenDaoApplication.mContext.getResources().openRawResource(R.raw.lianluodb);
        byte[] buffer = new byte[8192];
        int count = 0;
        while ((count = is.read(buffer)) > 0) {
            os.write(buffer, 0, count);
            os.flush();
        }
        is.close();
        os.close();
    }


}

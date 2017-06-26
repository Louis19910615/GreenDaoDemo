package louis.greendaodemo.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by louis on 2017/4/13.
 */

@Entity
public class SyncManager {

    private String Key;

    private String value;

    @Generated(hash = 2111415463)
    public SyncManager(String Key, String value) {
        this.Key = Key;
        this.value = value;
    }

    @Generated(hash = 2125598750)
    public SyncManager() {
    }

    public String getKey() {
        return this.Key;
    }

    public void setKey(String Key) {
        this.Key = Key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

   
}

package louis.greendaodemo.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by louis on 2017/4/13.
 */

@Entity
public class SyncManager {
    @Unique
    private String Key;

    private Boolean value;

    @Generated(hash = 563950225)
    public SyncManager(String Key, Boolean value) {
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

    public Boolean getValue() {
        return this.value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }
}

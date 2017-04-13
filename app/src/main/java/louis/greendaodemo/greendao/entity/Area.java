package louis.greendaodemo.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by louis on 2017/4/12.
 */

@Entity
public class Area {

    @Unique
    private String areaId;
    private String areaNameCN;
    private String areaNameEN;
    private String country;
    private String attributionCN;
    private String attributionEN;
    @Generated(hash = 1928851353)
    public Area(String areaId, String areaNameCN, String areaNameEN, String country,
            String attributionCN, String attributionEN) {
        this.areaId = areaId;
        this.areaNameCN = areaNameCN;
        this.areaNameEN = areaNameEN;
        this.country = country;
        this.attributionCN = attributionCN;
        this.attributionEN = attributionEN;
    }
    @Generated(hash = 179626505)
    public Area() {
    }
    public String getAreaId() {
        return this.areaId;
    }
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
    public String getAreaNameCN() {
        return this.areaNameCN;
    }
    public void setAreaNameCN(String areaNameCN) {
        this.areaNameCN = areaNameCN;
    }
    public String getAreaNameEN() {
        return this.areaNameEN;
    }
    public void setAreaNameEN(String areaNameEN) {
        this.areaNameEN = areaNameEN;
    }
    public String getCountry() {
        return this.country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getAttributionCN() {
        return this.attributionCN;
    }
    public void setAttributionCN(String attributionCN) {
        this.attributionCN = attributionCN;
    }
    public String getAttributionEN() {
        return this.attributionEN;
    }
    public void setAttributionEN(String attributionEN) {
        this.attributionEN = attributionEN;
    }
}

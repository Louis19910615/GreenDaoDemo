package louis.greendaodemo.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by louis on 2017/4/12.
 */

@Entity
public class Area {

    private String areaId;
    private String areaNameCN;
    private String areaNameEN;
    private String areaNameShorter;
//    private String countryCode;
    // 存在行政归属2的地方，不进行行政归属区1的存储
    private String attributionCNOne;
//    private String attributionENOne;
//    private String attributionCNTwo;
//    private String attributionENTwo;
    @Generated(hash = 75560069)
    public Area(String areaId, String areaNameCN, String areaNameEN,
            String areaNameShorter, String attributionCNOne) {
        this.areaId = areaId;
        this.areaNameCN = areaNameCN;
        this.areaNameEN = areaNameEN;
        this.areaNameShorter = areaNameShorter;
        this.attributionCNOne = attributionCNOne;
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
    public String getAreaNameShorter() {
        return this.areaNameShorter;
    }
    public void setAreaNameShorter(String areaNameShorter) {
        this.areaNameShorter = areaNameShorter;
    }
    public String getAttributionCNOne() {
        return this.attributionCNOne;
    }
    public void setAttributionCNOne(String attributionCNOne) {
        this.attributionCNOne = attributionCNOne;
    }
   
   
}

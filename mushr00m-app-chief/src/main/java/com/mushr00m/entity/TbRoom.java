package com.mushr00m.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_room")
public class TbRoom implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private Integer productid;

    private Integer startprice;

    private Integer stepprice;

    private Double maxprice;

    private Date createtime;

    private Date endtime;

    private Integer state;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return productid
     */
    public Integer getProductid() {
        return productid;
    }

    /**
     * @param productid
     */
    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    /**
     * @return startprice
     */
    public Integer getStartprice() {
        return startprice;
    }

    /**
     * @param startprice
     */
    public void setStartprice(Integer startprice) {
        this.startprice = startprice;
    }

    /**
     * @return stepprice
     */
    public Integer getStepprice() {
        return stepprice;
    }

    /**
     * @param stepprice
     */
    public void setStepprice(Integer stepprice) {
        this.stepprice = stepprice;
    }

    /**
     * @return maxprice
     */
    public Double getMaxprice() {
        return maxprice;
    }

    /**
     * @param maxprice
     */
    public void setMaxprice(Double maxprice) {
        this.maxprice = maxprice;
    }

    /**
     * @return createtime
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * @return endtime
     */
    public Date getEndtime() {
        return endtime;
    }

    /**
     * @param endtime
     */
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    /**
     * @return state
     */
    public Integer getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(Integer state) {
        this.state = state;
    }
}
package com.mushr00m.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_finalorder")
public class TbFinalorder implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private Integer uid;

    private Double price;

    private Integer pid;

    private String account;

    private Date createtime;

    private Integer state;

    private String pname;

    private String pimg;

    private Integer ownerid;

    private Integer address;

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
     * @return uid
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * @param uid
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * @return price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * @return pid
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * @param pid
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * @return account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account
     */
    public void setAccount(String account) {
        this.account = account;
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

    /**
     * @return pname
     */
    public String getPname() {
        return pname;
    }

    /**
     * @param pname
     */
    public void setPname(String pname) {
        this.pname = pname;
    }

    /**
     * @return pimg
     */
    public String getPimg() {
        return pimg;
    }

    /**
     * @param pimg
     */
    public void setPimg(String pimg) {
        this.pimg = pimg;
    }

    /**
     * @return ownerid
     */
    public Integer getOwnerid() {
        return ownerid;
    }

    /**
     * @param ownerid
     */
    public void setOwnerid(Integer ownerid) {
        this.ownerid = ownerid;
    }

    /**
     * @return address
     */
    public Integer getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(Integer address) {
        this.address = address;
    }
}
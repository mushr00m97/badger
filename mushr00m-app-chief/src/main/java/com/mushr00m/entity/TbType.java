package com.mushr00m.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "tb_type")
public class TbType implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String type;

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
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }
}
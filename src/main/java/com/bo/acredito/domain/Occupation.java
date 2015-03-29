package com.bo.acredito.domain;

import javax.persistence.*;

/**
 * Created by aralco on 2/11/14.
 */
@Entity
public class Occupation {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    private Long id;
    @Basic
    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 250, precision = 0)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

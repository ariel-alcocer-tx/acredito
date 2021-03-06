package com.bo.acredito.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by aralco on 6/14/15.
 */
@Entity
public class Office {
    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private String id;

    @Basic
    @NotNull
    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 250)
    private String name;

    @Basic
    @Lob
    @Size(min = 1)
    @Column(name = "description", nullable = true, insertable = true, updatable = true, length = 65535)
    private String description;

    @Basic
    @Lob
    @NotNull
    @Size(min = 1)
    @Column(name = "address", nullable = false, insertable = true, updatable = true, length = 65535)
    private String address;

    @Basic
    @NotNull
    @Column(name = "phone", nullable = false, insertable = true, updatable = true, length = 45)
    private String phone;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "companyId", referencedColumnName = "id", nullable = false)
    private Company company;

    public Office() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}

package com.ewha.aptinfocollector.VO;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="APT_INFO")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int APT_CODE;

    @Column(name = "APT_NM")
    private String name;
    private String APT_BUILD_Y;

    @Column(name = "APT_SQM")
    private double sqm;

    @Column(name = "APT_FLOOR")
    private int floor;

    private int GU_CODE;
    private int DONG_CODE;

    @Temporal(TemporalType.TIMESTAMP)
    private Date REG_DATE = new Date();

    @OneToMany(mappedBy="apartment",cascade = CascadeType.PERSIST)
    private Set<Transaction> transactions = new HashSet<>();

    public int getAPT_CODE() {
        return APT_CODE;
    }

    public void setAPT_CODE(int APT_CODE) {
        this.APT_CODE = APT_CODE;
    }

    public String getName() {
        return name;
    }

    public void setName(String APT_NM) {
        this.name = APT_NM;
    }

    public String getAPT_BUILD_Y() {
        return APT_BUILD_Y;
    }

    public void setAPT_BUILD_Y(String APT_BUILD_Y) {
        this.APT_BUILD_Y = APT_BUILD_Y;
    }

    public double getSqm() {
        return sqm;
    }

    public void setSqm(double sqm) {
        this.sqm = sqm;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getGU_CODE() {
        return GU_CODE;
    }

    public void setGU_CODE(int GU_CODE) {
        this.GU_CODE = GU_CODE;
    }

    public int getDONG_CODE() {
        return DONG_CODE;
    }

    public void setDONG_CODE(int DONG_CODE) {
        this.DONG_CODE = DONG_CODE;
    }

    public Date getREG_DATE() {
        return REG_DATE;
    }

    public void setREG_DATE(Date REG_DATE) {
        this.REG_DATE = REG_DATE;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}

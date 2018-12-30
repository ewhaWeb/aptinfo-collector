package com.ewha.aptinfocollector.VO;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name="APT_INFO")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int APT_CODE;

    private String APT_NM;
    private String APT_BUILD_Y;
    private double APT_SQM;
    private int APT_FLOOR;
    private int GU_CODE;
    private int DONG_CODE;

    @Temporal(TemporalType.TIMESTAMP)
    private Date REG_DATE = new Date();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="APT_CODE")
    private Collection<Transaction> transactions;

    public int getAPT_CODE() {
        return APT_CODE;
    }

    public void setAPT_CODE(int APT_CODE) {
        this.APT_CODE = APT_CODE;
    }

    public String getAPT_NM() {
        return APT_NM;
    }

    public void setAPT_NM(String APT_NM) {
        this.APT_NM = APT_NM;
    }

    public String getAPT_BUILD_Y() {
        return APT_BUILD_Y;
    }

    public void setAPT_BUILD_Y(String APT_BUILD_Y) {
        this.APT_BUILD_Y = APT_BUILD_Y;
    }

    public double getAPT_SQM() {
        return APT_SQM;
    }

    public void setAPT_SQM(double APT_SQM) {
        this.APT_SQM = APT_SQM;
    }

    public int getAPT_FLOOR() {
        return APT_FLOOR;
    }

    public void setAPT_FLOOR(int APT_FLOOR) {
        this.APT_FLOOR = APT_FLOOR;
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

    public Collection<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Collection<Transaction> transactions) {
        this.transactions = transactions;
    }
}

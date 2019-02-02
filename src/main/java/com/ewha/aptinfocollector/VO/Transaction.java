package com.ewha.aptinfocollector.VO;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="apt_trxn_info")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int TRXN_ID;

    @ManyToOne
    @JoinColumn(name="apt_code", referencedColumnName = "apt_code")
    private Apartment apartment;

    private int TRXN_PRICE;
    private String TRXN_Y;
    private String TRXN_M;

    @Temporal(TemporalType.TIMESTAMP)
    private Date REG_DATE = new Date();

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Date getREG_DATE() {
        return REG_DATE;
    }

    public void setREG_DATE(Date REG_DATE) {
        this.REG_DATE = REG_DATE;
    }

    public int getTRXN_ID() {
        return TRXN_ID;
    }

    public void setTRXN_ID(int TRXN_ID) {
        this.TRXN_ID = TRXN_ID;
    }

    public int getTRXN_PRICE() {
        return TRXN_PRICE;
    }

    public void setTRXN_PRICE(int TRXN_PRICE) {
        this.TRXN_PRICE = TRXN_PRICE;
    }

    public String getTRXN_Y() {
        return TRXN_Y;
    }

    public void setTRXN_Y(String TRXN_Y) {
        this.TRXN_Y = TRXN_Y;
    }

    public String getTRXN_M() {
        return TRXN_M;
    }

    public void setTRXN_M(String TRXN_M) {
        this.TRXN_M = TRXN_M;
    }


}


package com.ewha.aptinfocollector.VO;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="APT_TRXN_INFO")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int TRXN_ID;
    private int APT_CODE;

    private int TRXN_PRICE;
    private String TRXN_Y;
    private String TRXN_M;

    @Temporal(TemporalType.TIMESTAMP)
    private Date REG_DATE = new Date();



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


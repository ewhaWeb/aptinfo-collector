package com.ewha.aptinfocollector;

import com.ewha.aptinfocollector.service.APIsetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;


@Component
@Transactional
public class JpaRunner implements ApplicationRunner {
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    APIsetter APIsetter;

    int[] GU_list = {11110,11140,11170,11200,11215,11230,11260,11290,11305,11320,11350,11380,11410,11440,11470,11500,11530,11545,11560,11590,11620,11650,11680,11710,11740};
    int[] PeriodList = {201801,201802,201803,201804,201805,201806,201807,201808,201809,201810,201811,201812};
    @Override
    public void run (ApplicationArguments args) throws Exception {
//        APIsetter.main("11110","201801");
        for (int period: PeriodList) {
            String date = String.valueOf(period);
            for (int GU: GU_list) {
                String locationCode = String.valueOf(GU);
                APIsetter.main(locationCode,date);
            }
        }


    }
}

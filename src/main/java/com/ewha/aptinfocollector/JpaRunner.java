package com.ewha.aptinfocollector;

import com.ewha.aptinfocollector.VO.Apartment;
import com.ewha.aptinfocollector.VO.Transaction;
import com.ewha.aptinfocollector.service.APIsetter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;


@Component
@Transactional
public class JpaRunner implements ApplicationRunner {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void run (ApplicationArguments args) throws Exception {


    }
}

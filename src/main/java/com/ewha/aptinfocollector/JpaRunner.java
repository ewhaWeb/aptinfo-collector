package com.ewha.aptinfocollector;

import com.ewha.aptinfocollector.VO.Apartment;
import com.ewha.aptinfocollector.VO.Transaction;
import com.ewha.aptinfocollector.repository.ApartmentRepository;
import com.ewha.aptinfocollector.service.APIsetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.ArrayList;


@Component
@Transactional
public class JpaRunner implements ApplicationRunner {
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    ApartmentRepository apartmentRepository;

    @Override
    public void run (ApplicationArguments args) throws Exception {
        ArrayList<Apartment> apartments = new ArrayList<>();
        apartments = APIsetter.main();
        for (Apartment apartment: apartments) {
            apartmentRepository.save(apartment);
        }

    }
}

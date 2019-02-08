package com.ewha.aptinfocollector.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Service;

@Service
public class Batch {
	@PersistenceContext
	private EntityManager em;
	
	// 데이터 계산 프로시저 호출 (매월)
	public void calculateData(String yyyymm) {
	
		StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("calData");
		query.setParameter("in_y", yyyymm.substring(0, 4));
		//query.setParameter("in_m", "8");
		
		query.setParameter("in_m", yyyymm.substring(4, 6).replace("0", ""));
		query.execute();
	}
}

package com.B1team.b01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManagerFactory;

@SpringBootTest

class B01ApplicationTestSSS {
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	/*
	@Test

	void contextLoads() {

		EntityManager entityManager = entityManagerFactory.createEntityManager();

		String name = "발주" + entityManager.createNativeQuery("SELECT bom_seq.nextval FROM DUAL").getSingleResult();

		BOM bom  = new BOM();

		bom.setVolume(10L);
		bom.setMtrId("D12");
		bom.setProductId("C12");
		bom.setMtrName(name);

		entityManager.persist(bom);

		System.out.println("test1111112" + bom.getId());
		System.out.println("test1111111" + bom.getMtrName());


	}

	 */

}

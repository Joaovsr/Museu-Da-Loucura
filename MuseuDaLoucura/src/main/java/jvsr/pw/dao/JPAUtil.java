package jvsr.pw.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("museudaloucura");
	
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
}

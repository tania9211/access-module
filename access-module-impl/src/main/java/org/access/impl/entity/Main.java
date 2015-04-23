package org.access.impl.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.access.impl.bean.PlayerBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		/*EntityManagerFactory emfactory = Persistence
				.createEntityManagerFactory("movie-unit");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();

		Player player = new Player();
		player.setDateCreate("2345");
		player.setDateModify("456");
		player.setDeleted(false);
		player.setEmail("myKot");
		player.setPassword("12345656");
		player.setVersion(2L);
		player.setNickname("kotic");

		entitymanager.persist(player);
		entitymanager.getTransaction().commit();*/

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"spring.xml");
		PlayerBean registrationBean = (PlayerBean) context
				.getBean("playerBean");
		
		Player player = new Player();
		player.setDateCreate("2345");
		player.setDateModify("456");
		player.setDeleted(false);
		player.setEmail("myKot");
		player.setPassword("12345656");
		player.setVersion(2L);
		player.setNickname("kotic");
		
		registrationBean.getPlayerRepository().save(player);
		
		Player player2 = registrationBean.getPlayerRepository().findByEmail("myKot");
		System.out.println(player.getNickname());
	}

}

package org.access.impl.entity;

import org.access.impl.bean.UserBean;
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
		UserBean registrationBean = (UserBean) context
				.getBean("userBean");
		
		User player = new User();
		player.setDateCreate("2345");
		player.setDateModify("456");
		player.setDeleted(false);
		player.setEmail("myKot");
		player.setPassword("12345656");
		player.setVersion(2L);
		player.setNickname("kotic");
		
		registrationBean.getUserRepository().save(player);
		
		User player2 = registrationBean.getUserRepository().findByEmail("myKot");
		System.out.println(player.getNickname());
	}

}

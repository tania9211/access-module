package org.access.impl.entity;

import org.access.impl.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	@Autowired
	private PermissionRepository permissionRepository;
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"spring_config.xml");
		context.getBean(PermissionRepository.class);
	}



}

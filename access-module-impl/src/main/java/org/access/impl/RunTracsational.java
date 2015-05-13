package org.access.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.regex.Pattern;

import org.access.api.annotation.Transactional;
import org.access.impl.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.stereotype.Service;

@Service
public class RunTracsational {

	@Autowired
	private PermissionRepository permissionRepository;

	public void run() {
		final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
				false);
		provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern
				.compile(".*")));

		final Set<BeanDefinition> classes = provider
				.findCandidateComponents("org.access.impl");

		for (BeanDefinition bean : classes) {
			try {
				Class<?> class1 = Class.forName(bean.getBeanClassName());

				for (Method method : class1.getDeclaredMethods()) {
					if (method.isAnnotationPresent(Transactional.class)) {
						Annotation annotation = method
								.getAnnotation(Transactional.class);
						Transactional transactional = (Transactional) annotation;
						if (transactional.enabled()) {
							System.out.println(permissionRepository.findAll());
						}
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}

package service.config;

import org.access.ApplicationConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import service.Student;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "service")
@Import(ApplicationConfig.class)
public class ApplicationConfigWeb {

	@Bean
	public Student getStudent() {
		return new Student();
	}
}

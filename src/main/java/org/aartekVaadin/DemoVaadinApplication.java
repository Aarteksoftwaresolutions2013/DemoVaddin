package org.aartekVaadin;

import javax.sql.DataSource;

import org.dataVaadin.RegistrationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class DemoVaadinApplication {

	public static JdbcTemplate jdbcTemplate;

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate;
	}

	@Bean
	public RegistrationService serviceRegistration() {
		return new RegistrationService();
	}

	@Bean
	public RegistrationForm registrationForm() {
		return new RegistrationForm();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoVaadinApplication.class, args);
	}

}

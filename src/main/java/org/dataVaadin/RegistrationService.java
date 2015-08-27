package org.dataVaadin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.aartekVaadin.DemoVaadinApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class RegistrationService {

	public JdbcTemplate jdbctemplate = DemoVaadinApplication.jdbcTemplate;

	public void save(Registration registration) {
		String sql;
		Long id = registration.getId();
		if (id == null) {
			sql = "INSERT INTO vaadin.registration (firstname, lastname, mobileno, email)VALUES('"
							+ registration.getFirstName() + "', '" + registration.getLastName() + "', '"
							+ registration.getPhone() + "', '" + registration.getEmail() + "')";
		} else {
			sql = "UPDATE vaadin.registration SET firstname = '" + registration.getFirstName() + "' , lastname = '"
							+ registration.getLastName() + "' , mobileno = '" + registration.getPhone()
							+ "' , email = '" + registration.getEmail() + "'  where id = '" + registration.getId()
							+ "'";
		}
		jdbctemplate.update(sql);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public synchronized List<Registration> findAll(String stringFilter) {
		ArrayList arrayList = new ArrayList();
		String sql;
		if (stringFilter == null) {
			sql = "select * from registration";
			arrayList = (ArrayList) jdbctemplate.query(sql, new RowMapper<Registration>() {

				@Override
				public Registration mapRow(ResultSet rs, int rowNum) throws SQLException {
					Registration registration = new Registration();
					registration.setId(rs.getLong("id"));
					registration.setFirstName(rs.getString("firstname"));
					registration.setLastName(rs.getString("lastname"));
					registration.setEmail(rs.getString("email"));
					registration.setPhone(rs.getString("mobileno"));
					return registration;
				}
			});
		} else {
			sql = "select * from registration where firstname LIKE '" + stringFilter + "%'";
			arrayList = (ArrayList) jdbctemplate.query(sql, new RowMapper<Registration>() {

				@Override
				public Registration mapRow(ResultSet rs, int rowNum) throws SQLException {
					Registration registration = new Registration();
					registration.setId(rs.getLong("id"));
					registration.setFirstName(rs.getString("firstname"));
					registration.setLastName(rs.getString("lastname"));
					registration.setEmail(rs.getString("email"));
					registration.setPhone(rs.getString("mobileno"));
					return registration;
				}
			});
		}
		return arrayList;
	}

}

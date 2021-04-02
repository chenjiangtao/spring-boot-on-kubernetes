package cn.tosptream.data.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HiveJdbcBaseDaoImpl {

	@Autowired
	protected JdbcTemplate hiveJdbcTemplate;


	public JdbcTemplate getJdbcTemplate() {
		return hiveJdbcTemplate;
	}

	/***/
}

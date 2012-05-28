package uib.info323.twitterAWSM.io.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import uib.info323.twitterAWSM.io.TrendDAO;
import uib.info323.twitterAWSM.model.interfaces.Trends;
import uib.info323.twitterAWSM.utils.DateParser;
import uib.info323.twitterAWSM.utils.JsonTrendParser;

@Component
public class MySQLTrendingFactory implements TrendDAO {

	private static final String INSERT_TRENDS = "INSERT INTO trends(trend_date, json_trend) "
			+ "values(:trend_date, :json_trend)";
	private static final String SELECT_TRENDS_BY_DATE = "SELECT * FROM trends WHERE trend_date = :trend_date";

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	public MySQLTrendingFactory() {
	}

	/**
	 * @param jdbcTemplate
	 */
	@Autowired
	public void setNamedParameterJdbcTemplate(
			NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public boolean insertTrends(String json) {
		Map<String, Object> paramsMap = trendToMap(json);
		int inserted = -1;
		try {
			inserted = jdbcTemplate.update(INSERT_TRENDS, paramsMap);
		} catch (DuplicateKeyException d) {
			inserted = 0;
		}
		System.out.println("inserted Trend: " + inserted);
		return inserted == 1;
	}

	private Map<String, Object> trendToMap(String json) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("trend_date",
				DateParser.formatDate(new Date()).substring(0, 13));
		params.put("json_trend", json);
		return params;
	}

	public Trends selectTrendsByDate(String date) {
		System.out.println("selectedTrendByDAte " + date);
		SqlParameterSource paramMap = new MapSqlParameterSource("trend_date",
				date);
		Trends t = null;
		try {
			t = jdbcTemplate.queryForObject(SELECT_TRENDS_BY_DATE, paramMap,
					new RowMapper<Trends>() {

						@Override
						public Trends mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							Trends trends = JsonTrendParser.jsonToTrends(
									rs.getString("json_trend"),
									rs.getString("trend_date"));

							return trends;
						}
					});
			System.out.println("Trends.::: " + t.getTrends().size());
		} catch (DataAccessException e) {

		}
		return t;
	}
}

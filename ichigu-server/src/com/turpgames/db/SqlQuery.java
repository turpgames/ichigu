package com.turpgames.db;

import java.util.ArrayList;
import java.util.List;

public class SqlQuery {
	private String query;
	private final List<SqlParameter> parameters;

	public SqlQuery() {
		this("");
	}

	public SqlQuery(String query) {
		this.query = query;
		this.parameters = new ArrayList<SqlParameter>();
	}

	public SqlQuery(String query, SqlParameter... params) {
		this(query);
		if (params != null) {
			for (SqlParameter param : params) {
				this.parameters.add(param);
			}
		}
	}

	public SqlParameter[] getParameters() {
		return parameters.toArray(new SqlParameter[0]);
	}

	public String getQuery() {
		return query;
	}
	
	public SqlQuery setQuery(String query) {
		this.query = query;
		return this;
	}
	
	public SqlQuery append(String query) {
		this.query += query;
		return this;
	}

	/**
	 * 
	 * @param value
	 * @param sqlType: java.sql.Types.*
	 * @return
	 */
	public SqlQuery addParameter(Object value, int sqlType) {
		addParameter(new SqlParameter(value, sqlType));
		return this;
	}

	public SqlQuery addParameter(SqlParameter param) {
		parameters.add(param);
		return this;
	}

	public SqlQuery addParameters(SqlParameter[] params) {
		if (params != null) {
			for (SqlParameter param : params) {
				addParameter(param);
			}
		}
		return this;
	}

	public void removeParameter(int index) {
		parameters.remove(index);
	}

	public void clearParameters() {
		parameters.clear();
	}
}

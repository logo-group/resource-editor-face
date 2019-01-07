package com.lbs.re.app.routing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.context.request.RequestContextHolder;

public class DataSourceRouter extends AbstractRoutingDataSource {

	@Autowired
	private PreferredDatabaseSession db;

	@Override
	protected Object determineCurrentLookupKey() {
		if (RequestContextHolder.getRequestAttributes() == null) {
			return DatabaseEnvironment.TIGER;
		}
		return db.getPreferredDb();
	}
}
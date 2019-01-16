/*
 * Copyright 2014-2019 Logo Business Solutions
 * (a.k.a. LOGO YAZILIM SAN. VE TIC. A.S)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.lbs.re.app.security;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lbs.re.app.routing.DatabaseEnvironment;
import com.lbs.re.app.routing.PreferredDatabaseSession;
import com.lbs.re.data.service.REUserService;
import com.lbs.re.localization.LocaleConstants;
import com.lbs.re.model.ReUser;
import com.lbs.re.util.Constants;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final REUserService userService;
	private HttpServletRequest request;
	private PreferredDatabaseSession userDatabaseSession;

	@Autowired
	public UserDetailsServiceImpl(REUserService userService, HttpServletRequest request, PreferredDatabaseSession userDatabaseSession) {
		this.userService = userService;
		this.request = request;
		this.userDatabaseSession = userDatabaseSession;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		setPreferredDatabase();
		ReUser reUser;
		reUser = userService.getUserListByUsername(username);
		if (null == reUser) {
			throw new UsernameNotFoundException("No user present with username: " + username);
		}
		Locale locale = getLocale();
		return new UserSessionAttr(reUser, locale);
	}

	// TODO: the default should come.
	private Locale getLocale() {
		Locale locale = LocaleConstants.LOCALE_TRTR;
		return locale;
	}

	private void setPreferredDatabase() throws DataAccessException {
		// String prefferedDb = (String) request.getSession().getAttribute("prefferedDb");
		String prefferedDb = request.getParameter("prefferedDb");
		if (prefferedDb.equals(Constants.JPLATFORM)) {
			userDatabaseSession.setPreferredDb(DatabaseEnvironment.JPLATFORM);
		} else if (prefferedDb.equals(Constants.TIGER)) {
			userDatabaseSession.setPreferredDb(DatabaseEnvironment.TIGER);
		} else if (prefferedDb.equals(Constants.DICTIONARY)) {
			userDatabaseSession.setPreferredDb(DatabaseEnvironment.DICTIONARY);
		}
	}
}
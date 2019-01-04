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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lbs.re.data.service.REUserService;
import com.lbs.re.localization.LocaleConstants;
import com.lbs.re.model.ReUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final REUserService userService;

	@Autowired
	public UserDetailsServiceImpl(REUserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
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

}
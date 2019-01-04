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

import java.util.Collections;
import java.util.Locale;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.lbs.re.model.ReUser;
import com.lbs.re.util.EnumsV2.ResourceEditorUserRole;

public class UserSessionAttr extends User {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private ReUser reUser;

	private Locale locale;

	public UserSessionAttr(ReUser reUser, Locale locale) {
		super(reUser.getUsername(), reUser.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(ResourceEditorUserRole.values().toString())));
		this.setReUser(reUser);
		this.setLocale(locale);
	}

	public ReUser getReUser() {
		return reUser;
	}

	public void setReUser(ReUser tedamUser) {
		this.reUser = tedamUser;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

}

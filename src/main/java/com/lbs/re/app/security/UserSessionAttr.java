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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.lbs.re.model.ReUser;
import com.lbs.re.ui.components.grid.GridFilterValue;
import com.lbs.re.util.EnumsV2.ResourceEditorUserRole;

public class UserSessionAttr extends User {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private ReUser reUser;

	private Locale locale;

	private Map<String, List<GridFilterValue>> userFilterValues = new HashMap<>();

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

	public void saveFilterValue(String viewName, GridFilterValue gridFilterValue) {
		if (!userFilterValues.containsKey(viewName)) {
			userFilterValues.put(viewName, new ArrayList<>());
		}
		List<GridFilterValue> filterList = userFilterValues.get(viewName);
		Iterator<GridFilterValue> iterator = filterList.iterator();
		while (iterator.hasNext()) {
			GridFilterValue next = iterator.next();
			if (next.getGridId().equals(gridFilterValue.getGridId())) {
				iterator.remove();
			}
		}
		filterList.add(gridFilterValue);
	}

	public GridFilterValue loadFilterValue(String viewName, String filterId) {
		List<GridFilterValue> filterList = userFilterValues.get(viewName);
		if (filterList != null) {
			for (GridFilterValue gridFilterValue : filterList) {
				if (gridFilterValue.getGridId().equals(filterId)) {
					return gridFilterValue;
				}
			}
		}
		return null;
	}

	public void clearFilterValue(String viewName, String filterId) {
		List<GridFilterValue> filterList = userFilterValues.get(viewName);
		if (filterList != null) {
			Iterator<GridFilterValue> iterator = filterList.iterator();
			while (iterator.hasNext()) {
				GridFilterValue next = iterator.next();
				if (next.getGridId().equals(filterId)) {
					iterator.remove();
				}
			}
		}
	}

}

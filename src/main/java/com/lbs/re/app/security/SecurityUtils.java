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

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.lbs.re.data.service.REUserService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.exception.localized.OperationNotAuthedException;
import com.lbs.re.model.ReUser;
import com.lbs.re.ui.view.Operation;

/**
 * SecurityUtils takes care of all such static operations that have to do with security and querying rights from different beans of the UI.
 */
public class SecurityUtils {

	private SecurityUtils() {
		// Util methods only
	}

	private static UserSessionAttr getUserSessionAttr() {
		SecurityContext context = SecurityContextHolder.getContext();
		UserSessionAttr userSessionAttr = (UserSessionAttr) context.getAuthentication().getPrincipal();
		return userSessionAttr;
	}

	/**
	 * Gets the roles the currently signed-in user belongs to.
	 *
	 * @return a set of all roles the currently signed-in user belongs to.
	 */
	public static Set<String> getUserRoles() {
		SecurityContext context = SecurityContextHolder.getContext();
		return context.getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
	}

	public static UserSessionAttr getCurrentUser(REUserService userService) throws LocalizedException {
		UserSessionAttr userSessionAttr = getUserSessionAttr();
		ReUser reUser = userService.getUserListByUsername((userSessionAttr.getUsername()));
		userSessionAttr.setReUser(reUser);
		return userSessionAttr;
	}

	public static ReUser getUser() {
		UserSessionAttr userSessionAttr = getUserSessionAttr();
		return userSessionAttr.getReUser();
	}

	public static void checkForOperation(REUserService userService, String operationName) throws LocalizedException {
		if (operationName.equals(Operation.NO_CHECK)) {
			return;
		}
		Integer userId = SecurityUtils.getCurrentUser(userService).getReUser().getId();
		boolean userAuth = userService.isUserAuth(userId, operationName);
		if (!userAuth) {
			throw new OperationNotAuthedException();
		}
	}

}

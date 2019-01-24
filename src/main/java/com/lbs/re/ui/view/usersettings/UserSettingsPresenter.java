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

package com.lbs.re.ui.view.usersettings;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.vaadin.spring.events.EventBus.ViewEventBus;

import com.lbs.re.data.service.REUserService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReUser;
import com.lbs.re.ui.navigation.NavigationManager;
import com.lbs.re.ui.util.Enums.UIParameter;
import com.lbs.re.ui.util.Enums.ViewMode;
import com.lbs.re.ui.view.AbstractEditPresenter;
import com.lbs.re.ui.view.resource.ResourceGridView;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class UserSettingsPresenter extends AbstractEditPresenter<ReUser, REUserService, UserSettingsPresenter, UserSettingsView> {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserSettingsPresenter(ViewEventBus viewEventBus, NavigationManager navigationManager, REUserService userService, BeanFactory beanFactory,
			BCryptPasswordEncoder passwordEncoder) {
		super(viewEventBus, navigationManager, userService, ReUser.class, beanFactory, userService);
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void enterView(Map<UIParameter, Object> parameters) throws LocalizedException {
		ReUser reUser = getService().getById((Integer) parameters.get(UIParameter.ID));
		ViewMode mode = (ViewMode) parameters.get(UIParameter.MODE);
		if (reUser == null) {
			getView().showNotFound();
			return;
		}
		checkAuthority(reUser);
		loadCreatedAndModifiedInformations(reUser);
		refreshView(reUser, mode);
		organizeComponents(getView().getAccordion(), mode == ViewMode.VIEW);
	}

	@PostConstruct
	public void init() {
		subscribeToEventBus();
	}

	public void setNewPassword(String value) {
		getBinder().getBean().setPassword(passwordEncoder.encode(value));
	}

	@Override
	protected Class<? extends View> getGridView() {
		return ResourceGridView.class;
	}

	private void checkAuthority(ReUser reUser) throws LocalizedException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ReUser user = getService().getUserListByUsername(auth.getName());
		if (!user.getId().equals(reUser.getId())) {
			getView().showNotAuthorized();
			return;
		}
	}
}

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

package com.lbs.re.ui.view.user.edit;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.vaadin.spring.events.EventBus.ViewEventBus;

import com.lbs.re.data.service.REUserService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReUser;
import com.lbs.re.ui.navigation.NavigationManager;
import com.lbs.re.ui.util.Enums.UIParameter;
import com.lbs.re.ui.util.Enums.ViewMode;
import com.lbs.re.ui.view.AbstractEditPresenter;
import com.lbs.re.ui.view.user.UserGridView;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class UserEditPresenter extends AbstractEditPresenter<ReUser, REUserService, UserEditPresenter, UserEditView> {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserEditPresenter(ViewEventBus viewEventBus, NavigationManager navigationManager, REUserService userService, BeanFactory beanFactory,
			BCryptPasswordEncoder passwordEncoder) {
		super(viewEventBus, navigationManager, userService, ReUser.class, beanFactory, userService);
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void enterView(Map<UIParameter, Object> parameters) throws LocalizedException {
		ReUser reUser;
		if ((Integer) parameters.get(UIParameter.ID) == 0) {
			reUser = new ReUser();
		} else {
			reUser = getService().getById((Integer) parameters.get(UIParameter.ID));
			if (reUser == null) {
				getView().showNotFound();
				return;
			}
		}
		refreshView(reUser, (ViewMode) parameters.get(UIParameter.MODE));
		getTitleForHeader();
		organizeComponents(getView().getAccordion(), (ViewMode) parameters.get(UIParameter.MODE) == ViewMode.VIEW);
	}

	@PostConstruct
	public void init() {
		subscribeToEventBus();
	}

	@Override
	protected ReUser save(ReUser item) throws LocalizedException {
		return super.save(item);
	}

	public void setNewPassword(String value) {
		getBinder().getBean().setPassword(passwordEncoder.encode(value));
	}

	@Override
	protected Class<? extends View> getGridView() {
		return UserGridView.class;
	}

	@Override
	protected void getTitleForHeader() {
		if (getItem().getUsername() != null) {
			getView().setTitle(getView().getTitle() + ": " + getItem().getUsername());
		}
	}

}

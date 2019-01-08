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

package com.lbs.re.ui;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.lbs.re.app.security.SecurityUtils;
import com.lbs.re.app.security.UserSessionAttr;
import com.lbs.re.data.service.REUserService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.localization.ResourceEditorLocalizerWrapper;
import com.lbs.re.ui.components.basic.REButton;
import com.lbs.re.ui.components.basic.RELabel;
import com.lbs.re.ui.components.layout.RECssLayout;
import com.lbs.re.ui.components.layout.REVerticalLayout;
import com.lbs.re.ui.navigation.NavigationManager;
import com.lbs.re.ui.view.resource.ResourceGridView;
import com.lbs.re.ui.view.user.UserGridView;
import com.lbs.re.ui.view.usersettings.UserSettingsView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.navigator.ViewLeaveAction;
import com.vaadin.spring.access.SecuredViewAccessControl;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

/**
 * The main view containing the menu and the content area where actual views are
 * shown.
 * <p>
 * Created as a single View class because the logic is so simple that using a
 * pattern like MVP would add much overhead for little gain. If more complexity
 * is added to the class, you should consider splitting out a presenter.
 */
@SpringViewDisplay
@UIScope
public class MainView extends HorizontalLayout implements ViewDisplay, ResourceEditorLocalizerWrapper {

	private static final long serialVersionUID = 1L;
	private final Map<Class<? extends View>, Button> navigationButtons = new HashMap<>();

	private final NavigationManager navigationManager;
	private final SecuredViewAccessControl viewAccessControl;
	private final REUserService userService;
	private UserSessionAttr userSession;

	private REVerticalLayout content;
	private RECssLayout menu;

	private REButton users;
	private REButton resources;
	private REButton userSettings;
	private REButton logout;

	@Autowired
	public MainView(NavigationManager navigationManager, SecuredViewAccessControl viewAccessControl, REUserService userService) throws LocalizedException {
		this.navigationManager = navigationManager;
		this.viewAccessControl = viewAccessControl;
		this.userService = userService;
		userSession = SecurityUtils.getCurrentUser(userService);
	}

	@PostConstruct
	public void init() throws LocalizedException {
		initComponents();
		attachNavigation(userSettings, UserSettingsView.class, SecurityUtils.getCurrentUser(userService).getReUser().getId());
		attachNavigation(users, UserGridView.class, "");
		attachNavigation(resources, ResourceGridView.class, "");
	}

	private void initComponents() throws LocalizedException {
		setStyleName("app-shell");
		setSpacing(false);
		setSizeFull();
		setResponsive(true);

		content = new REVerticalLayout();
		content.setStyleName("content-container v-scrollable");
		content.setSizeFull();
		content.setMargin(false);

		addComponent(buildNavigationBar());
		addComponent(content);
		setExpandRatio(content, 1);

	}

	private RECssLayout buildNavigationBar() throws LocalizedException {
		RECssLayout navigationContainer = new RECssLayout();
		navigationContainer.setStyleName("navigation-bar-container");
		navigationContainer.setWidth("200px");
		navigationContainer.setHeight("100%");
		navigationContainer.addComponent(buildNavigation());
		return navigationContainer;
	}

	private RECssLayout buildNavigation() throws LocalizedException {
		RECssLayout navigation = new RECssLayout();
		navigation.setStyleName("navigation-bar");
		navigation.setSizeFull();

		REButton menuButton = new REButton("general.button.menu");
		menuButton.setIcon(VaadinIcons.ALIGN_JUSTIFY);
		menuButton.setStyleName("menu borderless");
		menuButton.setWidthUndefined();
		navigation.setWidthUndefined();
		navigation.addComponents(buildHeader(), menuButton, buildMenu());

		return navigation;
	}

	private Component buildHeader() {
		RELabel header = new RELabel();
		header.addStyleName("logo");
		header.setWidth("100%");
		header.setValue(getLocaleValue("view.mainview.header"));
		return header;
	}

	private Component buildMenu() throws LocalizedException {
		menu = new RECssLayout();
		menu.setStyleName("navigation");

		RELabel userLabel = new RELabel(SecurityUtils.getCurrentUser(userService).getReUser().getUsername());
		userLabel.setStyleName("menuLabel");
		userLabel.setWidth("100%");

		resources = new REButton("view.mainview.resources", VaadinIcons.FOLDER);
		resources.addStyleName(ValoTheme.BUTTON_BORDERLESS);

		users = new REButton("view.mainview.usersview", VaadinIcons.USERS);
		users.addStyleName(ValoTheme.BUTTON_BORDERLESS);

		userSettings = new REButton("view.mainview.usersettingsview", VaadinIcons.USER);
		userSettings.addStyleName(ValoTheme.BUTTON_BORDERLESS);

		logout = new REButton("view.mainview.logout", VaadinIcons.EXIT);
		logout.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		logout.addClickListener(e -> logout());

		menu.addComponents(resources, userLabel, userSettings, users, logout);
		return menu;
	}

	private void attachNavigation(Button navigationButton, Class<? extends View> targetView, Object parameter) {
		boolean hasAccessToView = viewAccessControl.isAccessGranted(targetView);
		navigationButton.setVisible(hasAccessToView);

		if (hasAccessToView) {
			navigationButtons.put(targetView, navigationButton);
			navigationButton.addClickListener(e -> navigationManager.navigateTo(targetView, parameter));
		}
	}

	@Override
	public void showView(View view) {
		content.removeAllComponents();
		content.addComponent(view.getViewComponent());
		navigationButtons.forEach((viewClass, button) -> button.setStyleName("selected", viewClass == view.getClass()));
	}

	/**
	 * Logs the user out after ensuring the currently open view has no unsaved changes.
	 */
	public void logout() {
		ViewLeaveAction doLogout = () -> {
			UI ui = getUI();
			ui.getSession().getSession().invalidate();
			ui.getPage().reload();
		};

		navigationManager.runAfterLeaveConfirmation(doLogout);
	}

}

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

package com.lbs.re.ui.view;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.vaadin.spring.events.EventBus.ViewEventBus;

import com.lbs.re.app.security.SecurityUtils;
import com.lbs.re.data.service.BaseService;
import com.lbs.re.data.service.REUserService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.localization.ResourceEditorLocalizerWrapper;
import com.lbs.re.model.AbstractBaseEntity;
import com.lbs.re.ui.components.ConfirmPopup;
import com.lbs.re.ui.components.basic.REButton;
import com.lbs.re.ui.components.basic.REDateField;
import com.lbs.re.ui.components.basic.REDateTimeField;
import com.lbs.re.ui.components.basic.REMenuBar;
import com.lbs.re.ui.components.basic.REPasswordField;
import com.lbs.re.ui.components.basic.RETextField;
import com.lbs.re.ui.components.checkbox.RECheckBoxGroup;
import com.lbs.re.ui.components.combobox.REComboBox;
import com.lbs.re.ui.components.grid.REGrid;
import com.lbs.re.ui.navigation.NavigationManager;
import com.lbs.re.ui.util.Enums.UIParameter;
import com.lbs.re.ui.util.Enums.ViewMode;
import com.lbs.re.ui.util.RENotification;
import com.lbs.re.ui.util.RENotification.NotifyType;
import com.lbs.re.ui.util.REStatic;
import com.lbs.re.util.HasLogger;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewBeforeLeaveEvent;
import com.vaadin.navigator.ViewLeaveAction;
import com.vaadin.ui.Component;
import com.vaadin.ui.Component.Focusable;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.MultiFileUpload;

public abstract class AbstractEditPresenter<T extends AbstractBaseEntity, S extends BaseService<T, Integer>, P extends AbstractEditPresenter<T, S, P, V>, V extends AbstractEditView<T, S, P, V>>
		implements Serializable, HasLogger, ResourceEditorLocalizerWrapper {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private transient final S service;
	private final REUserService userService;
	private final NavigationManager navigationManager;
	private final BeanFactory beanFactory;
	private final Class<T> entityType;
	private transient V view;
	private BeanValidationBinder<T> binder;
	private ViewEventBus viewEventBus;
	private boolean hasChanges;

	protected AbstractEditPresenter(ViewEventBus viewEventBus, NavigationManager navigationManager, S service,
			Class<T> entityType, BeanFactory beanFactory, REUserService userService) {
		this.service = service;
		this.navigationManager = navigationManager;
		this.entityType = entityType;
		this.beanFactory = beanFactory;
		this.viewEventBus = viewEventBus;
		this.userService = userService;
		createBinder();
	}

	public void subscribeToEventBus() {
		viewEventBus.subscribe(this);
	}

	public void destroy() {
		viewEventBus.unsubscribe(this);
	}

	public void beforeLeavingView(ViewBeforeLeaveEvent event) {
		destroy();
		runWithConfirmation(event::navigate, () -> {
		});
	}

	private void runWithConfirmation(Runnable onConfirmation, Runnable onCancel) {
		if (hasUnsavedChanges()) {
			ConfirmPopup confirmPopup = beanFactory.getBean(ConfirmPopup.class);
			confirmPopup.showLeaveViewConfirmDialog(view, onConfirmation, onCancel);
		} else {
			onConfirmation.run();
		}
	}

	protected void createBinder() {
		binder = new BeanValidationBinder<>(getEntityType());
		binder.setRequiredConfigurator(null);
		binder.addValueChangeListener(e -> hasChanges = true);
	}

	protected BeanValidationBinder<T> getBinder() {
		return binder;
	}

	protected Class<T> getEntityType() {
		return entityType;
	}

	protected boolean hasUnsavedChanges() {
		return hasChanges;
	}

	public void setHasChanges(boolean hasChanges) {
		this.hasChanges = hasChanges;
	}

	protected S getService() {
		return service;
	}

	public ViewEventBus getViewEventBus() {
		return viewEventBus;
	}

	public V getView() {
		return view;
	}

	public void setView(V view) {
		this.view = view;
		view.bindFormFields(getBinder());
		view.getAccordion().setSelectedTab(0);
	}

	protected abstract void enterView(Map<UIParameter, Object> parameters) throws LocalizedException;

	protected abstract Class<? extends View> getGridView();

	protected void delete(T item) throws LocalizedException {
		getService().delete(item);
	}

	protected T save(T item) throws LocalizedException {
		if (!item.isNew()) {
			item.setModifiedon(LocalDateTime.now());
			item.setModifiedby(SecurityUtils.getCurrentUser(userService).getReUser().getId());
		} else {
			item.setCreatedon(LocalDateTime.now());
			item.setCreatedby(SecurityUtils.getCurrentUser(userService).getReUser().getId());
		}
		return getService().save(item);
	}

	protected void refreshView(T item, ViewMode mode) throws LocalizedException {
		getView().setViewMode(mode);
		setItem(item);
		setHasChanges(false);
	}

	public T getItem() {
		return binder.getBean();
	}

	protected void setItem(T item) throws LocalizedException {
		binder.setBean(item);
	}

	public void backPressed() {
		if (getView().getViewMode().equals(ViewMode.EDIT)) {
			beforeLeavingView(new ViewBeforeLeaveEvent(getNavigationManager(), new ViewLeaveAction() {

				/** long serialVersionUID */
				private static final long serialVersionUID = 1L;

				@Override
				public void run() {
					try {
						enterView(REStatic.getUIParameterMap(getBinder().getBean().getId(), ViewMode.VIEW));
						setHasChanges(false);
					} catch (LocalizedException e) {
						getLogger().error(e.getLocalizedMessage(), e);
					}
				}
			}));
		} else {
			getNavigationManager().navigateTo(getGridView());
		}
	}

	public void okPressed() throws LocalizedException {
		try {
			T item = getBinder().getBean();

			if (focusFirstErrorField()) {
				return;
			}
			if (getView().getViewMode() == ViewMode.NEW) {
				item = save(item);
				if (item != null) {
					// Navigate to edit view so URL is updated correctly
					getNavigationManager().updateViewParameter("" + item.getId());
					enterView(REStatic.getUIParameterMap(item.getId(), ViewMode.VIEW));
					getView().showSuccessfulSave();
				}
			} else if (getView().getViewMode() == ViewMode.EDIT) {
				item = save(item);
				if (item != null) {
					enterView(REStatic.getUIParameterMap(item.getId(), ViewMode.VIEW));
					getView().showSuccessfulUpdate();
				}
			} else if (getView().getViewMode().equals(ViewMode.VIEW)) {
				enterView(REStatic.getUIParameterMap(item.getId(), ViewMode.EDIT));
			}
		} catch (DataIntegrityViolationException e) {
			getView().showDataIntegrityException();
		}
	}

	public boolean focusFirstErrorField() {
		Optional<Object> firstErrorField = getView().validate().findFirst();
		if (firstErrorField.isPresent()) {
			String emptyField = ((Component) firstErrorField.get()).getCaption();
			RENotification.showNotification(emptyField + getLocaleValue("view.jobedit.messages.emptyField"),
					NotifyType.ERROR);
			((Focusable) firstErrorField.get()).focus();
			return true;
		}
		return false;
	}

	public NavigationManager getNavigationManager() {
		return navigationManager;
	}

	public REUserService getUserService() {
		return userService;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void organizeComponents(HasComponents components, boolean isReadOnly) {
		for (Component component : components) {
			if (component instanceof TabSheet) {
				TabSheet tabSheet = (TabSheet) component;
				organizeComponents(tabSheet, isReadOnly);
			} else if (component instanceof HorizontalLayout) {
				HorizontalLayout hLayout = (HorizontalLayout) component;
				organizeComponents(hLayout, isReadOnly);
			} else if (component instanceof VerticalLayout) {
				VerticalLayout vLayout = (VerticalLayout) component;
				organizeComponents(vLayout, isReadOnly);
			} else if (component instanceof CssLayout) {
				CssLayout cssLayout = (CssLayout) component;
				organizeComponents(cssLayout, isReadOnly);
			} else if (component instanceof REGrid<?>) {
				REGrid<?> tedamGrid = (REGrid<?>) component;
				if (isReadOnly) {
					tedamGrid.setSelectionMode(SelectionMode.NONE);
				} else {
					tedamGrid.setSelectionMode(tedamGrid.getSelectionMode());
				}
				if (tedamGrid.getRUDMenuColumn() != null) {
					tedamGrid.getRUDMenuColumn().setHidden(isReadOnly);
					displayTestRunsOperationsColumn(tedamGrid);
				}

			} else if (component instanceof REButton) {
				REButton tedamButton = (REButton) component;
				if (!tedamButton.getId().equals("general.button.cancel")
						&& !tedamButton.getId().equals("general.button.save")) {
					tedamButton.setEnabled(!isReadOnly);
				}
			} else if (component instanceof REMenuBar) {
				REMenuBar menuBar = (REMenuBar) component;
				menuBar.setEnabled(!isReadOnly);
			} else if (component instanceof REComboBox) {
				REComboBox<?> comboBox = (REComboBox<?>) component;
				comboBox.setReadOnly(isReadOnly);
			} else if (component instanceof RETextField) {
				RETextField textField = (RETextField) component;
				textField.setReadOnly(isReadOnly);
			} else if (component instanceof REPasswordField) {
				REPasswordField passwordField = (REPasswordField) component;
				passwordField.setReadOnly(isReadOnly);
			} else if (component instanceof REDateTimeField) {
				REDateTimeField tedamDateTimeField = (REDateTimeField) component;
				tedamDateTimeField.setReadOnly(isReadOnly);
			} else if (component instanceof REDateField) {
				REDateField tedamDateField = (REDateField) component;
				tedamDateField.setReadOnly(isReadOnly);
			} else if (component instanceof Upload) {
				((Upload) component).setEnabled(!isReadOnly);
			} else if (component instanceof MultiFileUpload) {
				((MultiFileUpload) component).setEnabled(isReadOnly);
			} else if (component instanceof RECheckBoxGroup<?>) {
				((RECheckBoxGroup<?>) component).setEnabled(!isReadOnly);
			}
		}
	}

	public void setGridEditorAttributes(Component component, boolean isEnabled) {
		REGrid<?> REGrid = (REGrid<?>) component;
		if (REGrid.getEditor().isOpen()) {
			REGrid.getEditor().cancel();
		}
		REGrid.getEditor().setEnabled(isEnabled);
	}

	protected void displayTestRunsOperationsColumn(REGrid<?> tedamGrid) {
	}

	protected void getTitleForHeader() {
	}

	public void checkForEditOperation() throws LocalizedException {
		SecurityUtils.checkForOperation(userService, getView().getEditOperationName());
	}

	public void checkForViewOperation() throws LocalizedException {
		SecurityUtils.checkForOperation(userService, getView().getViewOperationName());
	}

}

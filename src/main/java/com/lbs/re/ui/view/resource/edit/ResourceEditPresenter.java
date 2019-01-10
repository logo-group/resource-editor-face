package com.lbs.re.ui.view.resource.edit;

import java.time.LocalDateTime;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.vaadin.spring.events.EventBus.ViewEventBus;

import com.lbs.re.app.security.SecurityUtils;
import com.lbs.re.data.service.REUserService;
import com.lbs.re.data.service.ResourceService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReResource;
import com.lbs.re.model.ReResourceitem;
import com.lbs.re.ui.components.grid.REFilterGrid;
import com.lbs.re.ui.navigation.NavigationManager;
import com.lbs.re.ui.util.Enums.UIParameter;
import com.lbs.re.ui.util.Enums.ViewMode;
import com.lbs.re.ui.view.AbstractEditPresenter;
import com.lbs.re.ui.view.resource.ResourceGridView;
import com.lbs.re.ui.view.resourceitem.edit.ResourceItemDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class ResourceEditPresenter extends AbstractEditPresenter<ReResource, ResourceService, ResourceEditPresenter, ResourceEditView> {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private ResourceItemDataProvider resourceItemDataProvider;

	@Autowired
	public ResourceEditPresenter(ViewEventBus viewEventBus, NavigationManager navigationManager, ResourceService resourceService, REUserService userService,
			BeanFactory beanFactory, BCryptPasswordEncoder passwordEncoder, ResourceItemDataProvider resourceItemDataProvider) {
		super(viewEventBus, navigationManager, resourceService, ReResource.class, beanFactory, userService);
		this.resourceItemDataProvider = resourceItemDataProvider;
	}

	@Override
	protected void enterView(Map<UIParameter, Object> parameters) throws LocalizedException {
		ReResource resource;
		if ((Integer) parameters.get(UIParameter.ID) == 0) {
			resource = new ReResource();
		} else {
			resource = getService().getById((Integer) parameters.get(UIParameter.ID));
			if (resource == null) {
				getView().showNotFound();
				return;
			}
		}
		refreshView(resource, (ViewMode) parameters.get(UIParameter.MODE));
		resourceItemDataProvider.provideResourceItems(resource);
		getView().organizeResourceItemsGrid(resourceItemDataProvider);
		getTitleForHeader();
		organizeComponents(getView().getAccordion(), (ViewMode) parameters.get(UIParameter.MODE) == ViewMode.VIEW);
	}

	@PostConstruct
	public void init() {
		subscribeToEventBus();
	}

	public void addResourceItemRow() throws LocalizedException {
		ReResourceitem resourceItem = new ReResourceitem();
		REFilterGrid<ReResourceitem> activeTab = getView().getGridResourceItems();
		resourceItem.setCreatedon(LocalDateTime.now());
		resourceItem.setCreatedby(SecurityUtils.getCurrentUser(getUserService()).getReUser().getId());
		activeTab.getGridDataProvider().getListDataProvider().getItems().add(resourceItem);
		activeTab.refreshAll();
		activeTab.deselectAll();
		activeTab.scrollToEnd();
		setHasChanges(true);
	}

	public void removeResourceItemRow() {
		REFilterGrid<ReResourceitem> activeTab = getView().getGridResourceItems();
		if (activeTab.getSelectedItems().isEmpty()) {
			getView().showGridRowNotSelected();
			return;
		}
		activeTab.getSelectedItems().forEach(resourceItem -> activeTab.getGridDataProvider().removeItem(resourceItem));
		activeTab.deselectAll();
		activeTab.refreshAll();
		setHasChanges(true);
	}

	@Override
	protected Class<? extends View> getGridView() {
		return ResourceGridView.class;
	}

	@Override
	protected void getTitleForHeader() {
		String title = getView().getTitle();
		if (getItem().getResourcenr() != null) {
			title += ": " + getItem().getResourcenr();
		}
		getView().setTitle(title);
	}

}

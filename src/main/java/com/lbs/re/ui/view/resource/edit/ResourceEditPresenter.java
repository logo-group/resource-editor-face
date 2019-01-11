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
import com.lbs.re.ui.view.resourceitem.edit.ResourceItemTreeDataProvider;
import com.lbs.re.util.EnumsV2.ResourceGroupType;
import com.vaadin.data.provider.TreeDataProvider;
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
	private ResourceItemTreeDataProvider resourceItemTreeDataProvider;

	@Autowired
	public ResourceEditPresenter(ViewEventBus viewEventBus, NavigationManager navigationManager, ResourceService resourceService, REUserService userService,
			BeanFactory beanFactory, BCryptPasswordEncoder passwordEncoder, ResourceItemDataProvider resourceItemDataProvider,
			ResourceItemTreeDataProvider resourceItemTreeDataProvider) {
		super(viewEventBus, navigationManager, resourceService, ReResource.class, beanFactory, userService);
		this.resourceItemDataProvider = resourceItemDataProvider;
		this.resourceItemTreeDataProvider = resourceItemTreeDataProvider;
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
		resourceItemTreeDataProvider.provideResourceItems(resource);
		if (getItem().getResourcegroup().getResourceGroupType() == ResourceGroupType.LIST) {
			getView().getGridResourceItems().setVisible(true);
			getView().getTreeGridResourceItems().setVisible(false);
		} else if (getItem().getResourcegroup().getResourceGroupType() == ResourceGroupType.TREE) {
			getView().getGridResourceItems().setVisible(false);
			getView().getTreeGridResourceItems().setVisible(true);
		}
		getView().organizeResourceItemsGrid(resourceItemDataProvider);
		getView().organizeResourceItemsTreeGrid(resourceItemTreeDataProvider);
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

	public void prepareResourceItemWindow(ReResourceitem item, ViewMode mode) throws LocalizedException {

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

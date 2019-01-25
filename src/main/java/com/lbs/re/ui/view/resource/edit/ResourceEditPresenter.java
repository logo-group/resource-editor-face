package com.lbs.re.ui.view.resource.edit;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.vaadin.spring.events.EventBus.ViewEventBus;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;

import com.lbs.re.data.service.REUserService;
import com.lbs.re.data.service.ResourceService;
import com.lbs.re.data.service.ResourceitemService;
import com.lbs.re.data.service.StandardService;
import com.lbs.re.data.service.impl.language.LanguageServices;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReResource;
import com.lbs.re.model.ReResourceitem;
import com.lbs.re.ui.REFaceEvents.ResourceItemEvent;
import com.lbs.re.ui.components.grid.REFilterGrid;
import com.lbs.re.ui.components.grid.RETreeGrid;
import com.lbs.re.ui.navigation.NavigationManager;
import com.lbs.re.ui.util.Enums.UIParameter;
import com.lbs.re.ui.util.Enums.ViewMode;
import com.lbs.re.ui.util.RENotification;
import com.lbs.re.ui.util.RENotification.NotifyType;
import com.lbs.re.ui.util.REStatic;
import com.lbs.re.ui.view.AbstractEditPresenter;
import com.lbs.re.ui.view.resource.ResourceGridView;
import com.lbs.re.ui.view.resourceitem.edit.ResourceItemDataProvider;
import com.lbs.re.ui.view.resourceitem.edit.ResourceItemTreeDataProvider;
import com.lbs.re.util.EnumsV2.ResourceGroupType;
import com.lbs.re.util.EnumsV2.ResourceType;
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
	private ResourceitemService resourceitemService;
	private LanguageServices languageServices;
	private StandardService standardService;

	@Autowired
	public ResourceEditPresenter(ViewEventBus viewEventBus, NavigationManager navigationManager, ResourceService resourceService, REUserService userService,
			BeanFactory beanFactory, BCryptPasswordEncoder passwordEncoder, ResourceItemDataProvider resourceItemDataProvider,
			ResourceItemTreeDataProvider resourceItemTreeDataProvider, ResourceitemService resourceitemService, LanguageServices languageServices,
			StandardService standardService) {
		super(viewEventBus, navigationManager, resourceService, ReResource.class, beanFactory, userService);
		this.resourceItemDataProvider = resourceItemDataProvider;
		this.resourceItemTreeDataProvider = resourceItemTreeDataProvider;
		this.resourceitemService = resourceitemService;
		this.languageServices = languageServices;
		this.standardService = standardService;
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
			loadCreatedAndModifiedInformations(resource);
		}
		refreshView(resource, (ViewMode) parameters.get(UIParameter.MODE));
		if (getItem().getId() != 0) {
			ResourceGroupType resourceGroupType = getItem().getResourcegroup().getResourceGroupType();
			ResourceType resourceType = getItem().getResourcetype();
			if (resourceGroupType == ResourceGroupType.LIST && resourceType == ResourceType.LOCALIZABLE) {
				resourceItemDataProvider.provideResourceItems(resource);
				getView().getGridResourceItems().setVisible(true);
				getView().getTreeGridResourceItems().setVisible(false);
				getView().getGridResourceItemsStandard().setVisible(false);
				getView().getTreeGridResourceItemsStandard().setVisible(false);

				getView().organizeResourceItemsGrid(resourceItemDataProvider);
			} else if (resourceGroupType == ResourceGroupType.TREE && resourceType == ResourceType.LOCALIZABLE) {
				resourceItemTreeDataProvider.provideResourceItems(resource);
				getView().getGridResourceItems().setVisible(false);
				getView().getTreeGridResourceItems().setVisible(true);
				getView().getGridResourceItemsStandard().setVisible(false);
				getView().getTreeGridResourceItemsStandard().setVisible(false);

				getView().organizeResourceItemsTreeGrid(resourceItemTreeDataProvider);
			} else if (resourceGroupType == ResourceGroupType.LIST && resourceType == ResourceType.NONLOCALIZABLE) {
				resourceItemDataProvider.provideResourceItems(resource);
				getView().getGridResourceItems().setVisible(false);
				getView().getTreeGridResourceItems().setVisible(false);
				getView().getGridResourceItemsStandard().setVisible(true);
				getView().getTreeGridResourceItemsStandard().setVisible(false);

				getView().organizeResourceItemsStandardGrid(resourceItemDataProvider);
			} else if (resourceGroupType == ResourceGroupType.TREE && resourceType == ResourceType.NONLOCALIZABLE) {
				resourceItemTreeDataProvider.provideResourceItems(resource);
				getView().getGridResourceItems().setVisible(false);
				getView().getTreeGridResourceItems().setVisible(false);
				getView().getGridResourceItemsStandard().setVisible(false);
				getView().getTreeGridResourceItemsStandard().setVisible(true);

				getView().organizeResourceItemsStandardTreeGrid(resourceItemTreeDataProvider);
			}
			setVisibleButtons(true);
		} else {
			setVisibleButtons(false);
			getView().getGridResourceItems().setVisible(false);
			getView().getTreeGridResourceItems().setVisible(false);
			getView().getGridResourceItemsStandard().setVisible(false);
			getView().getTreeGridResourceItemsStandard().setVisible(false);
		}
		getTitleForHeader();
	}

	@PostConstruct
	public void init() {
		subscribeToEventBus();
	}

	@Override
	protected ReResource save(ReResource item) throws LocalizedException {
		if (getView().getResourceNr().getValue().isEmpty()) {
			getView().showEmptyResourceNumber();
			return null;
		}
		return super.save(item);
	}

	@SuppressWarnings("unchecked")
	public void removeResourceItemRow() throws LocalizedException {
		REFilterGrid<ReResourceitem> listGrid = getView().getGridResourceItems();
		RETreeGrid<ReResourceitem> treeGrid = getView().getTreeGridResourceItems();
		REFilterGrid<ReResourceitem> listGridStandard = getView().getGridResourceItemsStandard();
		RETreeGrid<ReResourceitem> treeGridStandard = getView().getTreeGridResourceItemsStandard();

		ResourceGroupType resourceGroupType = getItem().getResourcegroup().getResourceGroupType();
		ResourceType resourceType = getItem().getResourcetype();

		if (resourceGroupType == ResourceGroupType.TREE && resourceType == ResourceType.LOCALIZABLE) {
			if (treeGrid.getSelectedItems().isEmpty()) {
				getView().showGridRowNotSelected();
				return;
			} else if (isActiveItemExists(treeGrid.getSelectedItems())) {
				getView().showActiveRowSelected();
				return;
			}
			treeGrid.getSelectedItems().forEach(resourceItem -> {
				try {
					deleteLanguagesByItem((ReResourceitem) resourceItem);
					resourceitemService.delete((ReResourceitem) resourceItem);
				} catch (LocalizedException e) {
					e.printStackTrace();
				}
			});
			treeGrid.getSelectedItems().forEach(resourceItem -> treeGrid.getGridDataProvider().removeItem((ReResourceitem) resourceItem));
			treeGrid.deselectAll();
			treeGrid.refreshAll();
		} else if (resourceGroupType == ResourceGroupType.TREE && resourceType == ResourceType.NONLOCALIZABLE) {
			if (treeGridStandard.getSelectedItems().isEmpty()) {
				getView().showGridRowNotSelected();
				return;
			} else if (isActiveItemExists(treeGridStandard.getSelectedItems())) {
				getView().showActiveRowSelected();
				return;
			}
			treeGridStandard.getSelectedItems().forEach(resourceItem -> {
				try {
					deleteLanguagesByItem((ReResourceitem) resourceItem);
					resourceitemService.delete((ReResourceitem) resourceItem);
				} catch (LocalizedException e) {
					e.printStackTrace();
				}
			});
			treeGridStandard.getSelectedItems().forEach(resourceItem -> treeGridStandard.getGridDataProvider().removeItem((ReResourceitem) resourceItem));
			treeGridStandard.deselectAll();
			treeGridStandard.refreshAll();
		} else if (resourceGroupType == ResourceGroupType.LIST && resourceType == ResourceType.LOCALIZABLE) {
			if (listGrid.getSelectedItems().isEmpty()) {
				getView().showGridRowNotSelected();
				return;
			} else if (isActiveItemExists(listGrid.getSelectedItems())) {
				getView().showActiveRowSelected();
				return;
			}
			listGrid.getSelectedItems().forEach(resourceItem -> {
				try {
					deleteLanguagesByItem(resourceItem);
					resourceitemService.delete(resourceItem);
				} catch (LocalizedException e) {
					e.printStackTrace();
				}
			});

			listGrid.getSelectedItems().forEach(resourceItem -> listGrid.getGridDataProvider().removeItem(resourceItem));
			listGrid.deselectAll();
			listGrid.refreshAll();
		} else if (resourceGroupType == ResourceGroupType.LIST && getItem().getResourcetype() == ResourceType.NONLOCALIZABLE) {
			if (listGridStandard.getSelectedItems().isEmpty()) {
				getView().showGridRowNotSelected();
				return;
			} else if (isActiveItemExists(listGridStandard.getSelectedItems())) {
				getView().showActiveRowSelected();
				return;
			}
			listGridStandard.getSelectedItems().forEach(resourceItem -> {
				try {
					deleteLanguagesByItem(resourceItem);
					resourceitemService.delete(resourceItem);
				} catch (LocalizedException e) {
					e.printStackTrace();
				}
			});

			listGridStandard.getSelectedItems().forEach(resourceItem -> listGridStandard.getGridDataProvider().removeItem(resourceItem));
			listGridStandard.deselectAll();
			listGridStandard.refreshAll();
		}
	}

	private boolean isActiveItemExists(Set<ReResourceitem> itemSet) {
		for (ReResourceitem item : itemSet) {
			if (item.getActive() == 1) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	protected void setActiveItems(boolean isActive) {
		REFilterGrid<ReResourceitem> listGrid = getView().getGridResourceItems();
		RETreeGrid<ReResourceitem> treeGrid = getView().getTreeGridResourceItems();
		REFilterGrid<ReResourceitem> listGridStandard = getView().getGridResourceItemsStandard();
		RETreeGrid<ReResourceitem> treeGridStandard = getView().getTreeGridResourceItemsStandard();

		ResourceGroupType resourceGroupType = getItem().getResourcegroup().getResourceGroupType();
		ResourceType resourceType = getItem().getResourcetype();

		if (resourceGroupType == ResourceGroupType.TREE && resourceType == ResourceType.LOCALIZABLE) {
			if (treeGrid.getSelectedItems().isEmpty()) {
				getView().showGridRowNotSelected();
				return;
			}
			treeGrid.getSelectedItems().forEach(resourceItem -> {
				try {
					ReResourceitem item = (ReResourceitem) resourceItem;
					if (isActive) {
						item.setActive(1);
					} else {
						item.setActive(0);
					}
					resourceitemService.save(item);
				} catch (LocalizedException e) {
					e.printStackTrace();
				}
			});
			treeGrid.deselectAll();
			treeGrid.refreshAll();
		} else if (resourceGroupType == ResourceGroupType.TREE && resourceType == ResourceType.NONLOCALIZABLE) {
			if (treeGridStandard.getSelectedItems().isEmpty()) {
				getView().showGridRowNotSelected();
				return;
			}
			treeGridStandard.getSelectedItems().forEach(resourceItem -> {
				try {
					ReResourceitem item = (ReResourceitem) resourceItem;
					if (isActive) {
						item.setActive(1);
					} else {
						item.setActive(0);
					}
					resourceitemService.save(item);
				} catch (LocalizedException e) {
					e.printStackTrace();
				}
			});
			treeGridStandard.deselectAll();
			treeGridStandard.refreshAll();
		} else if (resourceGroupType == ResourceGroupType.LIST && resourceType == ResourceType.LOCALIZABLE) {
			if (listGrid.getSelectedItems().isEmpty()) {
				getView().showGridRowNotSelected();
				return;
			}
			listGrid.getSelectedItems().forEach(resourceItem -> {
				try {
					ReResourceitem item = resourceItem;
					if (isActive) {
						item.setActive(1);
					} else {
						item.setActive(0);
					}
					resourceitemService.save(item);
				} catch (LocalizedException e) {
					e.printStackTrace();
				}
			});
			listGrid.deselectAll();
			listGrid.refreshAll();
		} else if (resourceGroupType == ResourceGroupType.LIST && getItem().getResourcetype() == ResourceType.NONLOCALIZABLE) {
			if (listGridStandard.getSelectedItems().isEmpty()) {
				getView().showGridRowNotSelected();
				return;
			}
			listGridStandard.getSelectedItems().forEach(resourceItem -> {
				try {
					ReResourceitem item = resourceItem;
					if (isActive) {
						item.setActive(1);
					} else {
						item.setActive(0);
					}
					resourceitemService.save(item);
				} catch (LocalizedException e) {
					e.printStackTrace();
				}
			});
			listGridStandard.deselectAll();
			listGridStandard.refreshAll();
		}
	}

	public void prepareResourceItemWindow(ReResourceitem item, ViewMode mode, ResourceType type) throws LocalizedException {
		Map<UIParameter, Object> windowParameters = REStatic.getUIParameterMap(item.getId(), ViewMode.VIEW);
		windowParameters.put(UIParameter.RESOURCE_ID, getItem().getId());
		windowParameters.put(UIParameter.RESOURCE_TYPE, type);
		getView().openResourceItemWindow(windowParameters);
	}

	public void refreshGrid() {
		List<ReResourceitem> itemList = resourceitemService.getItemListByResource(getItem().getId());

		ResourceGroupType resourceGroupType = getItem().getResourcegroup().getResourceGroupType();
		ResourceType resourceType = getItem().getResourcetype();

		if (resourceGroupType == ResourceGroupType.TREE && resourceType == ResourceType.LOCALIZABLE) {
			getView().getTreeGridResourceItems().getGridDataProvider().refreshDataProviderByItems(itemList);
		} else if (resourceGroupType == ResourceGroupType.LIST && resourceType == ResourceType.LOCALIZABLE) {
			getView().getGridResourceItems().getGridDataProvider().refreshDataProviderByItems(itemList);
		} else if (resourceGroupType == ResourceGroupType.TREE && resourceType == ResourceType.NONLOCALIZABLE) {
			getView().getTreeGridResourceItemsStandard().getGridDataProvider().refreshDataProviderByItems(itemList);
		} else if (resourceGroupType == ResourceGroupType.LIST && resourceType == ResourceType.NONLOCALIZABLE) {
			getView().getGridResourceItemsStandard().getGridDataProvider().refreshDataProviderByItems(itemList);
		}
		resourceItemDataProvider.loadTransientData(itemList, getItem().getId());
	}

	public void deleteLanguagesByItem(ReResourceitem resourceItem) throws LocalizedException {
		languageServices.getTurkishService().deleteLanguageByResourceItemref(resourceItem.getId());
		languageServices.getAlbanianService().deleteLanguageByResourceItemref(resourceItem.getId());
		languageServices.getArabicEgService().deleteLanguageByResourceItemref(resourceItem.getId());
		languageServices.getArabicJoService().deleteLanguageByResourceItemref(resourceItem.getId());
		languageServices.getArabicSaService().deleteLanguageByResourceItemref(resourceItem.getId());
		languageServices.getAzerbaijaniazService().deleteLanguageByResourceItemref(resourceItem.getId());
		languageServices.getBulgarianService().deleteLanguageByResourceItemref(resourceItem.getId());
		languageServices.getEnglishService().deleteLanguageByResourceItemref(resourceItem.getId());
		languageServices.getFrenchService().deleteLanguageByResourceItemref(resourceItem.getId());
		languageServices.getPersianService().deleteLanguageByResourceItemref(resourceItem.getId());
		languageServices.getGeorgianService().deleteLanguageByResourceItemref(resourceItem.getId());
		languageServices.getGermanService().deleteLanguageByResourceItemref(resourceItem.getId());
		languageServices.getRomanianService().deleteLanguageByResourceItemref(resourceItem.getId());
		languageServices.getRussianruService().deleteLanguageByResourceItemref(resourceItem.getId());
		languageServices.getTurkmenService().deleteLanguageByResourceItemref(resourceItem.getId());
		standardService.deleteStandardByResourceItemref(resourceItem.getId());
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

	private void setVisibleButtons(boolean isVisible) {
		getView().getBtnAddRow().setVisible(isVisible);
		getView().getBtnRemoveRow().setVisible(isVisible);
		getView().getBtnActive().setVisible(isVisible);
		getView().getBtnDeActive().setVisible(isVisible);
	}

	public void generateResourceNumber() {
		if (getView().getViewMode() == ViewMode.NEW || getView().getViewMode() == ViewMode.EDIT) {
			Integer newNumber = getService().getMaxResourceNumber() + 1;
			getView().getResourceNr().setValue(newNumber.toString());
		} else {
			getView().showSelectEditMode();
		}
	}

	@EventBusListenerMethod
	public void resourceItemPreparedEvent(ResourceItemEvent resourceItemEvent) {
		refreshGrid();
		RENotification.showNotification(getLocaleValue("view.abstractedit.messages.SuccessfulSave"), NotifyType.SUCCESS);
	}
}

package com.lbs.re.ui.view.resource.edit;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.vaadin.spring.events.EventBus.ViewEventBus;

import com.lbs.re.data.service.REUserService;
import com.lbs.re.data.service.ResourceService;
import com.lbs.re.data.service.ResourceitemService;
import com.lbs.re.data.service.impl.language.LanguageServices;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReResource;
import com.lbs.re.model.ReResourceitem;
import com.lbs.re.model.languages.ReAlbaniankv;
import com.lbs.re.model.languages.ReArabiceg;
import com.lbs.re.model.languages.ReArabicjo;
import com.lbs.re.model.languages.ReArabicsa;
import com.lbs.re.model.languages.ReAzerbaijaniaz;
import com.lbs.re.model.languages.ReBulgarianbg;
import com.lbs.re.model.languages.ReEnglishus;
import com.lbs.re.model.languages.ReFrenchfr;
import com.lbs.re.model.languages.ReGeorgiange;
import com.lbs.re.model.languages.ReGermande;
import com.lbs.re.model.languages.RePersianir;
import com.lbs.re.model.languages.ReRomanianro;
import com.lbs.re.model.languages.ReRussianru;
import com.lbs.re.model.languages.ReTurkishtr;
import com.lbs.re.model.languages.ReTurkmentm;
import com.lbs.re.ui.components.grid.REFilterGrid;
import com.lbs.re.ui.components.grid.RETreeGrid;
import com.lbs.re.ui.navigation.NavigationManager;
import com.lbs.re.ui.util.Enums.UIParameter;
import com.lbs.re.ui.util.Enums.ViewMode;
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

	@Autowired
	public ResourceEditPresenter(ViewEventBus viewEventBus, NavigationManager navigationManager, ResourceService resourceService, REUserService userService,
			BeanFactory beanFactory, BCryptPasswordEncoder passwordEncoder, ResourceItemDataProvider resourceItemDataProvider,
			ResourceItemTreeDataProvider resourceItemTreeDataProvider, ResourceitemService resourceitemService, LanguageServices languageServices) {
		super(viewEventBus, navigationManager, resourceService, ReResource.class, beanFactory, userService);
		this.resourceItemDataProvider = resourceItemDataProvider;
		this.resourceItemTreeDataProvider = resourceItemTreeDataProvider;
		this.resourceitemService = resourceitemService;
		this.languageServices = languageServices;
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

	public void prepareResourceItemWindow(ReResourceitem item, ViewMode mode) throws LocalizedException {
		Map<UIParameter, Object> windowParameters = REStatic.getUIParameterMap(item.getId(), ViewMode.VIEW);
		windowParameters.put(UIParameter.RESOURCE_ID, getItem().getId());
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

		ReTurkishtr turkishTr = languageServices.getTurkishService().getLanguageByresourceitemref(resourceItem.getId());
		if (turkishTr != null) {
			languageServices.getTurkishService().delete(turkishTr);
		}

		ReAlbaniankv albanianKv = languageServices.getAlbanianService().getLanguageByresourceitemref(resourceItem.getId());
		if (albanianKv != null) {
			languageServices.getAlbanianService().delete(albanianKv);
		}

		ReArabiceg arabicEg = languageServices.getArabicEgService().getLanguageByresourceitemref(resourceItem.getId());
		if (arabicEg != null) {
			languageServices.getArabicEgService().delete(arabicEg);
		}

		ReArabicjo arabicJo = languageServices.getArabicJoService().getLanguageByresourceitemref(resourceItem.getId());
		if (arabicJo != null) {
			languageServices.getArabicJoService().delete(arabicJo);
		}

		ReArabicsa arabicSa = languageServices.getArabicSaService().getLanguageByresourceitemref(resourceItem.getId());
		if (arabicSa != null) {
			languageServices.getArabicSaService().delete(arabicSa);
		}

		ReAzerbaijaniaz azerbaijaniAz = languageServices.getAzerbaijaniazService().getLanguageByresourceitemref(resourceItem.getId());
		if (azerbaijaniAz != null) {
			languageServices.getAzerbaijaniazService().delete(azerbaijaniAz);
		}

		ReBulgarianbg bulgarianBg = languageServices.getBulgarianService().getLanguageByresourceitemref(resourceItem.getId());
		if (bulgarianBg != null) {
			languageServices.getBulgarianService().delete(bulgarianBg);
		}

		ReEnglishus englishUs = languageServices.getEnglishService().getLanguageByresourceitemref(resourceItem.getId());
		if (englishUs != null) {
			languageServices.getEnglishService().delete(englishUs);
		}

		ReFrenchfr frenchFr = languageServices.getFrenchService().getLanguageByresourceitemref(resourceItem.getId());
		if (frenchFr != null) {
			languageServices.getFrenchService().delete(frenchFr);
		}

		RePersianir persianIr = languageServices.getPersianService().getLanguageByresourceitemref(resourceItem.getId());
		if (persianIr != null) {
			languageServices.getPersianService().delete(persianIr);
		}

		ReGeorgiange georgianGe = languageServices.getGeorgianService().getLanguageByresourceitemref(resourceItem.getId());
		if (georgianGe != null) {
			languageServices.getGeorgianService().delete(georgianGe);
		}

		ReGermande germanDe = languageServices.getGermanService().getLanguageByresourceitemref(resourceItem.getId());
		if (germanDe != null) {
			languageServices.getGermanService().delete(germanDe);
		}

		ReRomanianro romanianRo = languageServices.getRomanianService().getLanguageByresourceitemref(resourceItem.getId());
		if (romanianRo != null) {
			languageServices.getRomanianService().delete(romanianRo);
		}

		ReRussianru russianRu = languageServices.getRussianruService().getLanguageByresourceitemref(resourceItem.getId());
		if (russianRu != null) {
			languageServices.getRussianruService().delete(russianRu);
		}

		ReTurkmentm turkmenTm = languageServices.getTurkmenService().getLanguageByresourceitemref(resourceItem.getId());
		if (turkmenTm != null) {
			languageServices.getTurkmenService().delete(turkmenTm);
		}
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
}

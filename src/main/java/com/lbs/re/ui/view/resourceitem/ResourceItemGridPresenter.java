package com.lbs.re.ui.view.resourceitem;

import java.util.Map;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus.ViewEventBus;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;

import com.lbs.re.app.security.SecurityUtils;
import com.lbs.re.data.service.REUserService;
import com.lbs.re.data.service.ResourceitemService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReResourceitem;
import com.lbs.re.ui.REFaceEvents.ResourceItemEvent;
import com.lbs.re.ui.components.grid.AdvancedSearchFilterValue;
import com.lbs.re.ui.navigation.NavigationManager;
import com.lbs.re.ui.util.Enums.UIParameter;
import com.lbs.re.ui.util.Enums.ViewMode;
import com.lbs.re.ui.util.REStatic;
import com.lbs.re.ui.view.AbstractGridPresenter;
import com.lbs.re.ui.view.advancedsearch.AdvancedSearchView;
import com.lbs.re.ui.view.resource.edit.ResourceEditView;
import com.lbs.re.ui.view.resourceitem.edit.ResourceItemDataProvider;
import com.lbs.re.util.EnumsV2.ResourceType;
import com.vaadin.navigator.ViewBeforeLeaveEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class ResourceItemGridPresenter extends AbstractGridPresenter<ReResourceitem, ResourceitemService, ResourceItemGridPresenter, ResourceItemGridView> {

	private static final long serialVersionUID = 1L;

	private NavigationManager navigationManager;

	@Autowired
	public ResourceItemGridPresenter(ResourceItemDataProvider resourceItemDataProvider, NavigationManager navigationManager, ResourceitemService service, BeanFactory beanFactory,
			ViewEventBus viewEventBus, REUserService userService) throws LocalizedException {
		super(navigationManager, service, resourceItemDataProvider, beanFactory, viewEventBus, userService);
		this.navigationManager = navigationManager;
		resourceItemDataProvider.provideLimitedResourceItems();
	}

	@Override
	protected void enterView(Map<UIParameter, Object> parameters) {
		loadAdvancedSearchValues();
		subscribeToEventBus();
	}

	@Override
	public void beforeLeavingView(ViewBeforeLeaveEvent event) {
		saveAdvancedSearchValues();
		super.beforeLeavingView(event);
	}

	private void loadAdvancedSearchValues() {
		AdvancedSearchFilterValue advancedSearchFilterValue = SecurityUtils.getAdvancedSearchFilterValue();
		AdvancedSearchView advancedSearchView = getView().getAdvancedSearchView();
		if (advancedSearchFilterValue.getResourceNrStart() != null) {
			advancedSearchView.getResourceNrStart().setValue(advancedSearchFilterValue.getResourceNrStart());
		}
		if (advancedSearchFilterValue.getResourceNrEnd() != null) {
			advancedSearchView.getResourceNrEnd().setValue(advancedSearchFilterValue.getResourceNrEnd());
		}
		if (advancedSearchFilterValue.getOrderNrStart() != null) {
			advancedSearchView.getOrderNrStart().setValue(advancedSearchFilterValue.getOrderNrStart());
		}
		if (advancedSearchFilterValue.getOrderNrEnd() != null) {
			advancedSearchView.getOrderNrEnd().setValue(advancedSearchFilterValue.getOrderNrEnd());
		}
		if (advancedSearchFilterValue.getTagNrStart() != null) {
			advancedSearchView.getTagNrStart().setValue(advancedSearchFilterValue.getTagNrStart());
		}
		if (advancedSearchFilterValue.getTagNrEnd() != null) {
			advancedSearchView.getTagNrEnd().setValue(advancedSearchFilterValue.getTagNrEnd());
		}
		if (advancedSearchFilterValue.getLevelNrStart() != null) {
			advancedSearchView.getLevelNrStart().setValue(advancedSearchFilterValue.getLevelNrStart());
		}
		if (advancedSearchFilterValue.getLevelNrEnd() != null) {
			advancedSearchView.getLevelNrEnd().setValue(advancedSearchFilterValue.getLevelNrEnd());
		}
		if (advancedSearchFilterValue.getResourceDescription() != null) {
			advancedSearchView.getResourceDescription().setValue(advancedSearchFilterValue.getResourceDescription());
		}
		if (advancedSearchFilterValue.getPrefix() != null) {
			advancedSearchView.getPrefix().setValue(advancedSearchFilterValue.getPrefix());
		}
		if (advancedSearchFilterValue.getInfo() != null) {
			advancedSearchView.getInfo().setValue(advancedSearchFilterValue.getInfo());
		}
		if (advancedSearchFilterValue.getTurkish() != null) {
			advancedSearchView.getTurkish().setValue(advancedSearchFilterValue.getTurkish());
		}
		if (advancedSearchFilterValue.getEnglish() != null) {
			advancedSearchView.getEnglish().setValue(advancedSearchFilterValue.getEnglish());
		}
		if (advancedSearchFilterValue.getStandard() != null) {
			advancedSearchView.getStandard().setValue(advancedSearchFilterValue.getStandard());
		}
		if (advancedSearchFilterValue.getModifiedDateStart() != null) {
			advancedSearchView.getModifiedDateStart().setValue(advancedSearchFilterValue.getModifiedDateStart());
		}
		if (advancedSearchFilterValue.getModifiedDateEnd() != null) {
			advancedSearchView.getModifiedDateEnd().setValue(advancedSearchFilterValue.getModifiedDateEnd());
		}
		if (advancedSearchFilterValue.getDescriptionSearchFilterComboBox() != null) {
			advancedSearchView.getDescriptionSearchFilterComboBox().setValue(advancedSearchFilterValue.getDescriptionSearchFilterComboBox());
		}
		if (advancedSearchFilterValue.getPrefixSearchFilterComboBox() != null) {
			advancedSearchView.getPrefixSearchFilterComboBox().setValue(advancedSearchFilterValue.getPrefixSearchFilterComboBox());
		}
		if (advancedSearchFilterValue.getInfoSearchFilterComboBox() != null) {
			advancedSearchView.getInfoSearchFilterComboBox().setValue(advancedSearchFilterValue.getInfoSearchFilterComboBox());
		}
		if (advancedSearchFilterValue.getTurkishSearchFilterComboBox() != null) {
			advancedSearchView.getTurkishSearchFilterComboBox().setValue(advancedSearchFilterValue.getTurkishSearchFilterComboBox());
		}
		if (advancedSearchFilterValue.getEnglishSearchFilterComboBox() != null) {
			advancedSearchView.getEnglishSearchFilterComboBox().setValue(advancedSearchFilterValue.getEnglishSearchFilterComboBox());
		}
		if (advancedSearchFilterValue.getStandardSearchFilterComboBox() != null) {
			advancedSearchView.getStandardSearchFilterComboBox().setValue(advancedSearchFilterValue.getStandardSearchFilterComboBox());
		}
		if (advancedSearchFilterValue.getResourceGroupComboBox() != null) {
			advancedSearchView.getResourceGroupComboBox().setValue(advancedSearchFilterValue.getResourceGroupComboBox());
		}
		if (advancedSearchFilterValue.getResourceTypeComboBox() != null) {
			advancedSearchView.getResourceTypeComboBox().setValue(advancedSearchFilterValue.getResourceTypeComboBox());
		}
		if (advancedSearchFilterValue.getResourceCaseComboBox() != null) {
			advancedSearchView.getResourceCaseComboBox().setValue(advancedSearchFilterValue.getResourceCaseComboBox());
		}
		if (advancedSearchFilterValue.getStateComboBox() != null) {
			advancedSearchView.getStateComboBox().setValue(advancedSearchFilterValue.getStateComboBox());
		}
		if (advancedSearchFilterValue.getCreatedUser() != null) {
			advancedSearchView.getCreatedUser().setValue(advancedSearchFilterValue.getCreatedUser());
		}
		if (advancedSearchFilterValue.getModifiedUser() != null) {
			advancedSearchView.getModifiedUser().setValue(advancedSearchFilterValue.getModifiedUser());
		}
	}

	private void saveAdvancedSearchValues() {
		AdvancedSearchFilterValue advancedSearchFilterValue = SecurityUtils.getAdvancedSearchFilterValue();
		AdvancedSearchView advancedSearchView = getView().getAdvancedSearchView();
		advancedSearchFilterValue.setResourceNrStart(advancedSearchView.getResourceNrStart().getValue());
		advancedSearchFilterValue.setResourceNrEnd(advancedSearchView.getResourceNrEnd().getValue());
		advancedSearchFilterValue.setOrderNrStart(advancedSearchView.getOrderNrStart().getValue());
		advancedSearchFilterValue.setOrderNrEnd(advancedSearchView.getOrderNrEnd().getValue());
		advancedSearchFilterValue.setTagNrStart(advancedSearchView.getTagNrStart().getValue());
		advancedSearchFilterValue.setTagNrEnd(advancedSearchView.getTagNrEnd().getValue());
		advancedSearchFilterValue.setLevelNrStart(advancedSearchView.getLevelNrStart().getValue());
		advancedSearchFilterValue.setLevelNrEnd(advancedSearchView.getLevelNrEnd().getValue());
		advancedSearchFilterValue.setResourceDescription(advancedSearchView.getResourceDescription().getValue());
		advancedSearchFilterValue.setPrefix(advancedSearchView.getPrefix().getValue());
		advancedSearchFilterValue.setInfo(advancedSearchView.getInfo().getValue());
		advancedSearchFilterValue.setTurkish(advancedSearchView.getTurkish().getValue());
		advancedSearchFilterValue.setEnglish(advancedSearchView.getEnglish().getValue());
		advancedSearchFilterValue.setStandard(advancedSearchView.getStandard().getValue());
		advancedSearchFilterValue.setModifiedDateStart(advancedSearchView.getModifiedDateStart().getValue());
		advancedSearchFilterValue.setModifiedDateEnd(advancedSearchView.getModifiedDateEnd().getValue());
		advancedSearchFilterValue.setDescriptionSearchFilterComboBox(advancedSearchView.getDescriptionSearchFilterComboBox().getValue());
		advancedSearchFilterValue.setPrefixSearchFilterComboBox(advancedSearchView.getPrefixSearchFilterComboBox().getValue());
		advancedSearchFilterValue.setInfoSearchFilterComboBox(advancedSearchView.getInfoSearchFilterComboBox().getValue());
		advancedSearchFilterValue.setTurkishSearchFilterComboBox(advancedSearchView.getTurkishSearchFilterComboBox().getValue());
		advancedSearchFilterValue.setEnglishSearchFilterComboBox(advancedSearchView.getEnglishSearchFilterComboBox().getValue());
		advancedSearchFilterValue.setStandardSearchFilterComboBox(advancedSearchView.getStandardSearchFilterComboBox().getValue());
		advancedSearchFilterValue.setResourceGroupComboBox(advancedSearchView.getResourceGroupComboBox().getValue());
		advancedSearchFilterValue.setResourceTypeComboBox(advancedSearchView.getResourceTypeComboBox().getValue());
		advancedSearchFilterValue.setResourceCaseComboBox(advancedSearchView.getResourceCaseComboBox().getValue());
		advancedSearchFilterValue.setStateComboBox(advancedSearchView.getStateComboBox().getValue());
		advancedSearchFilterValue.setCreatedUser(advancedSearchView.getCreatedUser().getValue());
		advancedSearchFilterValue.setModifiedUser(advancedSearchView.getModifiedUser().getValue());
	}

	public void prepareResourceItemWindow(ReResourceitem item) throws LocalizedException {
		Map<UIParameter, Object> windowParameters = REStatic.getUIParameterMap(item.getId(), ViewMode.VIEW);
		windowParameters.put(UIParameter.RESOURCE_ID, item.getId());
		windowParameters.put(UIParameter.RESOURCE_TYPE, ResourceType.LOCALIZABLE);
		getView().openResourceItemWindow(windowParameters);
	}

	public void navigateToItemResource(ReResourceitem item) throws LocalizedException {
		Integer resourceRef = getService().getById(item.getId()).getResourceref();
		navigationManager.navigateTo(ResourceEditView.class, resourceRef);
	}

	@EventBusListenerMethod
	public void resourceItemPreparedEvent(ResourceItemEvent resourceItemEvent) {
		getView().getGrid().getDataProvider().refreshItem(resourceItemEvent.getReResourceItem());
	}
}

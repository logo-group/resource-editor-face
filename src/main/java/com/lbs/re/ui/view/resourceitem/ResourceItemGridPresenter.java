package com.lbs.re.ui.view.resourceitem;

import java.util.Map;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus.ViewEventBus;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;

import com.lbs.re.data.service.REUserService;
import com.lbs.re.data.service.ResourceitemService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReResourceitem;
import com.lbs.re.ui.REFaceEvents.ResourceItemEvent;
import com.lbs.re.ui.navigation.NavigationManager;
import com.lbs.re.ui.util.Enums.UIParameter;
import com.lbs.re.ui.util.Enums.ViewMode;
import com.lbs.re.ui.util.REStatic;
import com.lbs.re.ui.view.AbstractGridPresenter;
import com.lbs.re.ui.view.resourceitem.edit.ResourceItemDataProvider;
import com.lbs.re.util.EnumsV2.ResourceType;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class ResourceItemGridPresenter extends AbstractGridPresenter<ReResourceitem, ResourceitemService, ResourceItemGridPresenter, ResourceItemGridView> {

	private static final long serialVersionUID = 1L;

	@Autowired
	public ResourceItemGridPresenter(ResourceItemDataProvider resourceItemDataProvider, NavigationManager navigationManager, ResourceitemService service, BeanFactory beanFactory,
			ViewEventBus viewEventBus, REUserService userService) throws LocalizedException {
		super(navigationManager, service, resourceItemDataProvider, beanFactory, viewEventBus, userService);
		resourceItemDataProvider.provideLimitedResourceItems();
	}

	@Override
	protected void enterView(Map<UIParameter, Object> parameters) {
		subscribeToEventBus();
	}

	public void prepareResourceItemWindow(ReResourceitem item) throws LocalizedException {
		Map<UIParameter, Object> windowParameters = REStatic.getUIParameterMap(item.getId(), ViewMode.VIEW);
		windowParameters.put(UIParameter.RESOURCE_ID, item.getId());
		windowParameters.put(UIParameter.RESOURCE_TYPE, ResourceType.LOCALIZABLE);
		getView().openResourceItemWindow(windowParameters);
	}

	@EventBusListenerMethod
	public void resourceItemPreparedEvent(ResourceItemEvent resourceItemEvent) {
		getView().getGrid().getDataProvider().refreshItem(resourceItemEvent.getReResourceItem());
	}
}

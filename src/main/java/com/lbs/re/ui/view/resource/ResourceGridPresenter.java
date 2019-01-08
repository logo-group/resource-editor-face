package com.lbs.re.ui.view.resource;

import java.util.Map;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus.ViewEventBus;

import com.lbs.re.data.service.REUserService;
import com.lbs.re.data.service.ResourceService;
import com.lbs.re.model.ReResource;
import com.lbs.re.ui.navigation.NavigationManager;
import com.lbs.re.ui.util.Enums.UIParameter;
import com.lbs.re.ui.view.AbstractGridPresenter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class ResourceGridPresenter extends AbstractGridPresenter<ReResource, ResourceService, ResourceGridPresenter, ResourceGridView> {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	public ResourceGridPresenter(ResourceDataProvider userDataProvider, NavigationManager navigationManager, ResourceService service, BeanFactory beanFactory,
			ViewEventBus viewEventBus, REUserService userService) {
		super(navigationManager, service, userDataProvider, beanFactory, viewEventBus, userService);
	}

	@Override
	protected void enterView(Map<UIParameter, Object> parameters) {
	}

}

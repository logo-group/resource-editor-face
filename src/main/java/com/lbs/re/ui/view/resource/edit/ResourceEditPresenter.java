package com.lbs.re.ui.view.resource.edit;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.vaadin.spring.events.EventBus.ViewEventBus;

import com.lbs.re.data.service.REUserService;
import com.lbs.re.data.service.ResourceService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReResource;
import com.lbs.re.ui.navigation.NavigationManager;
import com.lbs.re.ui.util.Enums.UIParameter;
import com.lbs.re.ui.util.Enums.ViewMode;
import com.lbs.re.ui.view.AbstractEditPresenter;
import com.lbs.re.ui.view.resource.ResourceGridView;
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

	@Autowired
	public ResourceEditPresenter(ViewEventBus viewEventBus, NavigationManager navigationManager, ResourceService resourceService, REUserService userService,
			BeanFactory beanFactory, BCryptPasswordEncoder passwordEncoder) {
		super(viewEventBus, navigationManager, resourceService, ReResource.class, beanFactory, userService);
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
		getTitleForHeader();
		organizeComponents(getView().getAccordion(), (ViewMode) parameters.get(UIParameter.MODE) == ViewMode.VIEW);
	}

	@PostConstruct
	public void init() {
		subscribeToEventBus();
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

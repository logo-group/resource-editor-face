package com.lbs.re.ui.view.resource;

import java.util.Map;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus.ViewEventBus;

import com.lbs.re.data.service.REUserService;
import com.lbs.re.data.service.ResourceService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReResource;
import com.lbs.re.model.ReResourceitem;
import com.lbs.re.ui.navigation.NavigationManager;
import com.lbs.re.ui.util.Enums.UIParameter;
import com.lbs.re.ui.view.AbstractGridPresenter;
import com.lbs.re.ui.view.resource.edit.ResourceEditPresenter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class ResourceGridPresenter extends AbstractGridPresenter<ReResource, ResourceService, ResourceGridPresenter, ResourceGridView> {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private ResourceEditPresenter resourceEditPresenter;

	@Autowired
	public ResourceGridPresenter(ResourceDataProvider userDataProvider, NavigationManager navigationManager, ResourceService service, BeanFactory beanFactory,
			ViewEventBus viewEventBus, REUserService userService, ResourceEditPresenter resourceEditPresenter) {
		super(navigationManager, service, userDataProvider, beanFactory, viewEventBus, userService);
		this.resourceEditPresenter = resourceEditPresenter;
	}

	@Override
	protected void enterView(Map<UIParameter, Object> parameters) {
	}

	@Override
	protected void delete(ReResource item) throws LocalizedException {
		for (ReResourceitem resourceItem : item.getReResourceitem()) {
			resourceEditPresenter.deleteLanguagesByItem(resourceItem);
		}
		super.delete(item);
	}

	@Override
	protected void deleteSelectedLines(Iterable<ReResource> entities) throws LocalizedException {
		super.deleteSelectedLines(entities);
	}

}

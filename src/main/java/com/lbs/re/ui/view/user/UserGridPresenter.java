package com.lbs.re.ui.view.user;

import java.util.Map;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus.ViewEventBus;

import com.lbs.re.data.service.REUserService;
import com.lbs.re.model.ReUser;
import com.lbs.re.ui.navigation.NavigationManager;
import com.lbs.re.ui.util.Enums.UIParameter;
import com.lbs.re.ui.view.AbstractGridPresenter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class UserGridPresenter extends AbstractGridPresenter<ReUser, REUserService, UserGridPresenter, UserGridView> {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	public UserGridPresenter(UserDataProvider userDataProvider, NavigationManager navigationManager, REUserService service, BeanFactory beanFactory, ViewEventBus viewEventBus,
			REUserService userService) {
		super(navigationManager, service, userDataProvider, beanFactory, viewEventBus, userService);
	}

	@Override
	protected void enterView(Map<UIParameter, Object> parameters) {
	}
}

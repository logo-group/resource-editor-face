package com.lbs.re.ui.view.message;

import java.util.Map;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus.ViewEventBus;

import com.lbs.re.data.service.MessageService;
import com.lbs.re.data.service.REUserService;
import com.lbs.re.model.ReMessage;
import com.lbs.re.ui.navigation.NavigationManager;
import com.lbs.re.ui.util.Enums.UIParameter;
import com.lbs.re.ui.view.AbstractGridPresenter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class MessageGridPresenter
		extends AbstractGridPresenter<ReMessage, MessageService, MessageGridPresenter, MessageGridView> {

	private static final long serialVersionUID = 1L;

	@Autowired
	public MessageGridPresenter(MessageDataProvider dataProvider, NavigationManager navigationManager,
			MessageService service, BeanFactory beanFactory,
			ViewEventBus viewEventBus, REUserService userService) {
		super(navigationManager, service, dataProvider, beanFactory, viewEventBus, userService);
	}

	@Override
	protected void enterView(Map<UIParameter, Object> parameters) {
	}

}

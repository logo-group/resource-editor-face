package com.lbs.re.ui.view.message.edit;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.vaadin.spring.events.EventBus.ViewEventBus;

import com.lbs.re.data.service.MessageService;
import com.lbs.re.data.service.REUserService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReMessage;
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
public class MessageEditPresenter
		extends AbstractEditPresenter<ReMessage, MessageService, MessageEditPresenter, MessageEditView> {

	private static final long serialVersionUID = 1L;

	@Autowired
	public MessageEditPresenter(ViewEventBus viewEventBus, NavigationManager navigationManager, MessageService service,
			REUserService userService, BeanFactory beanFactory, BCryptPasswordEncoder passwordEncoder) {
		super(viewEventBus, navigationManager, service, ReMessage.class, beanFactory, userService);
	}

	@Override
	protected void enterView(Map<UIParameter, Object> parameters) throws LocalizedException {
		ReMessage message;
		if ((Integer) parameters.get(UIParameter.ID) == 0) {
			message = new ReMessage();
		} else {
			message = getService().getById((Integer) parameters.get(UIParameter.ID));
			if (message == null) {
				getView().showNotFound();
				return;
			}
		}
		refreshView(message, (ViewMode) parameters.get(UIParameter.MODE));
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
		if (getItem().getConsId() != null) {
			title += ": " + getItem().getConsId();
		}
		getView().setTitle(title);
	}

}

package com.lbs.re.ui.components.window;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus.ViewEventBus;

import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReResourceitem;
import com.lbs.re.ui.components.CustomExceptions.REWindowNotAbleToOpenException;
import com.lbs.re.ui.components.basic.REWindow;
import com.lbs.re.ui.util.Enums.UIParameter;
import com.lbs.re.ui.util.Enums.WindowSize;
import com.lbs.re.ui.util.RENotification;
import com.lbs.re.ui.util.RENotification.NotifyType;
import com.lbs.re.ui.view.resource.edit.ResourceEditPresenter;
import com.lbs.re.ui.view.resourceitem.edit.ResourceItemEditPresenter;
import com.lbs.re.ui.view.resourceitem.edit.ResourceItemEditView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

@SpringComponent
@ViewScope
public class WindowResourceItem extends REWindow {

	private static final long serialVersionUID = 1L;

	private ResourceItemEditView resourceItemEditView;
	private ResourceItemEditPresenter resourceItemEditPresenter;
	private ResourceEditPresenter resourceEditPresenter;

	@Autowired
	public WindowResourceItem(ViewEventBus viewEventBus, ResourceItemEditView resourceItemEditView,
			ResourceItemEditPresenter resourceItemEditPresenter, ResourceEditPresenter resourceEditPresenter) {
		super(WindowSize.BIG, viewEventBus);
		this.resourceItemEditView = resourceItemEditView;
		this.resourceItemEditPresenter = resourceItemEditPresenter;
		this.resourceEditPresenter = resourceEditPresenter;
	}

	@Override
	public void open(Map<UIParameter, Object> parameters) throws LocalizedException, REWindowNotAbleToOpenException {
		UI.getCurrent().addWindow(this);
		center();
		setModal(true);
		focus();
		resourceItemEditPresenter.enterView(parameters);
		initWindow();
	}

	@Override
	protected Component buildContent() throws LocalizedException {
		return resourceItemEditView;
	}

	@Override
	protected String getHeader() {
		return getLocaleValue("view.resourceitem.header");
	}

	@Override
	protected void windowClose() {
		resourceItemEditPresenter.destroy();
	}

	@Override
	protected boolean readyToClose() {
		if (resourceItemEditPresenter.focusFirstErrorField()) {
			return false;
		}
		return true;
	}

	@Override
	public void publishCloseSuccessEvent() {
		try {
			ReResourceitem item = resourceItemEditPresenter.save(resourceItemEditPresenter.getResourceItem());
			resourceItemEditPresenter.checkLanguageFields(item);
			resourceEditPresenter.refreshGrid();
			RENotification.showNotification(getLocaleValue("view.abstractedit.messages.SuccessfulSave"),
					NotifyType.SUCCESS);
		} catch (LocalizedException e) {
			RENotification.showNotification(getLocaleValue("view.abstractedit.messages.FailedSave"), NotifyType.ERROR);
			e.printStackTrace();
		}
	}
}

package com.lbs.re.ui.components.window;

import java.util.Map;
import java.util.NoSuchElementException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;
import org.vaadin.spring.events.EventBus.ViewEventBus;

import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.ui.components.CustomExceptions.REWindowNotAbleToOpenException;
import com.lbs.re.ui.components.basic.RECheckBox;
import com.lbs.re.ui.components.basic.RETextField;
import com.lbs.re.ui.components.basic.REWindow;
import com.lbs.re.ui.components.combobox.ResourceGroupComboBox;
import com.lbs.re.ui.navigation.NavigationManager;
import com.lbs.re.ui.util.Enums.UIParameter;
import com.lbs.re.ui.util.Enums.WindowSize;
import com.lbs.re.ui.util.RENotification;
import com.lbs.re.ui.util.RENotification.NotifyType;
import com.lbs.re.ui.view.resource.edit.ResourceEditView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

@SpringComponent
@PrototypeScope
public class WindowResourceCopy extends REWindow {

	private static final long serialVersionUID = 1L;

	private RETextField tagStart;
	private RETextField tagEnd;
	private RECheckBox tagAll;
	private ResourceGroupComboBox resourcegroup;
	private RETextField resourceNr;

	private ResourceCopyWindowPresenter resourceCopyWindowPresenter;

	private NavigationManager navigationManager;

	@Autowired
	public WindowResourceCopy(ResourceCopyWindowPresenter resourceCopyWindowPresenter, ViewEventBus viewEventBus, ResourceGroupComboBox resourcegroup,
			NavigationManager navigationManager) {
		super(WindowSize.MEDIUM, viewEventBus);
		this.resourceCopyWindowPresenter = resourceCopyWindowPresenter;
		this.resourcegroup = resourcegroup;
		this.navigationManager = navigationManager;
	}

	@PostConstruct
	private void initView() {
		resourceCopyWindowPresenter.init(this);
	}

	private void initFields() {
		tagStart = new RETextField("view.resource.copy.tagstart", "half", true, true);
		tagEnd = new RETextField("view.resource.copy.tagend", "half", true, true);
		tagAll = new RECheckBox("view.resource.copy.tagall", null, true, true);
		resourceNr = new RETextField("view.resource.copy.resourcenumber", "half", true, true);

		tagAll.addValueChangeListener(event -> {
			if (!tagAll.getValue()) {
				tagStart.setEnabled(true);
				tagEnd.setEnabled(true);
			} else {
				tagStart.setEnabled(false);
				tagEnd.setEnabled(false);
			}
		});
	}

	@Override
	protected Component buildContent() throws LocalizedException {
		initFields();
		addSection(getLocaleValue("view.viewedit.section.values"), tagStart, tagEnd, tagAll, resourcegroup, resourceNr);
		return getMainLayout();
	}

	@Override
	protected String getHeader() {
		return getLocaleValue("view.resource.copy.header");
	}

	@Override
	public void publishCloseSuccessEvent() {
		int tagStartValue = 0;
		int tagEndValue = 0;
		try {
			int resourceNumberValue = Integer.parseInt(resourceNr.getValue());
			if (!tagAll.getValue()) {
				tagStartValue = Integer.parseInt(tagStart.getValue());
				tagEndValue = Integer.parseInt(tagEnd.getValue());
				if (tagStartValue < 0 || tagEndValue < 0) {
					RENotification.showNotification(getLocaleValue("view.resourcecopy.messages.unvalidtag"), NotifyType.WARNING);
					return;
				} else if (tagStartValue > tagEndValue) {
					RENotification.showNotification(getLocaleValue("view.resourcecopy.messages.inconsistenttag"), NotifyType.WARNING);
					return;
				}
			}
			resourceCopyWindowPresenter.copyResource(tagStartValue, tagEndValue, tagAll.getValue(), resourcegroup.getSelectedItem().get(), resourceNumberValue);
		} catch (NumberFormatException e) {
			RENotification.showNotification(getLocaleValue("view.resourcecopy.messages.numberformat"), NotifyType.WARNING);
		} catch (NoSuchElementException ne) {
			RENotification.showNotification(getLocaleValue("view.resourcecopy.messages.emptyresourcegroup"), NotifyType.WARNING);
		}
	}

	@Override
	public void open(Map<UIParameter, Object> parameters) throws REWindowNotAbleToOpenException, LocalizedException {
		UI.getCurrent().addWindow(this);
		center();
		setModal(true);
		focus();
		int resourceId = (int) parameters.get(UIParameter.ID);
		resourceCopyWindowPresenter.setResource(resourceCopyWindowPresenter.getResourceService().getById(resourceId));
		initWindow();
	}

	@Override
	protected boolean readyToClose() {
		return true;
	}

	@Override
	protected void windowClose() {
		if (resourceCopyWindowPresenter.getTempResource() != null) {
			navigationManager.navigateTo(ResourceEditView.class, resourceCopyWindowPresenter.getTempResource().getId());
		}
	}

}

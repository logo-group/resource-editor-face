package com.lbs.re.ui.view.message.edit;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.vaadin.spring.events.EventBus.ViewEventBus;

import com.lbs.re.data.service.MessageService;
import com.lbs.re.data.service.REUserService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReMessage;
import com.lbs.re.ui.components.checkbox.MessageButtonsCheckboxGroup;
import com.lbs.re.ui.components.combobox.MessageButtonsComboBox;
import com.lbs.re.ui.navigation.NavigationManager;
import com.lbs.re.ui.util.Enums.UIParameter;
import com.lbs.re.ui.util.Enums.ViewMode;
import com.lbs.re.ui.view.AbstractEditPresenter;
import com.lbs.re.ui.view.resource.ResourceGridView;
import com.lbs.re.util.EnumsV2.MessageButton;
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
		fillStandartButtons();
		fillDefaultButton();
	}

	private void fillStandartButtons() {
		MessageButtonsCheckboxGroup standartButtons = getView().getStandartButtons();
		standartButtons.deselectAll();
		int buttons = getItem().getButtons();
		switch (buttons) {
		case 1:
			standartButtons.select(MessageButton.OK);
			break;
		case 2:
			standartButtons.select(MessageButton.CANCEL);
			break;
		case 4:
			standartButtons.select(MessageButton.YES);
			break;
		case 8:
			standartButtons.select(MessageButton.NO);
			break;
		case 16:
			standartButtons.select(MessageButton.SAVE);
			break;
		case 3:
			standartButtons.select(MessageButton.OK);
			standartButtons.select(MessageButton.CANCEL);
			break;
		case 5:
			standartButtons.select(MessageButton.OK);
			standartButtons.select(MessageButton.YES);
			break;
		case 9:
			standartButtons.select(MessageButton.OK);
			standartButtons.select(MessageButton.NO);
			break;
		case 17:
			standartButtons.select(MessageButton.OK);
			standartButtons.select(MessageButton.SAVE);
			break;
		case 6:
			standartButtons.select(MessageButton.CANCEL);
			standartButtons.select(MessageButton.YES);
			break;
		case 10:
			standartButtons.select(MessageButton.CANCEL);
			standartButtons.select(MessageButton.NO);
			break;
		case 18:
			standartButtons.select(MessageButton.CANCEL);
			standartButtons.select(MessageButton.SAVE);
			break;
		case 12:
			standartButtons.select(MessageButton.YES);
			standartButtons.select(MessageButton.NO);
			break;
		case 20:
			standartButtons.select(MessageButton.YES);
			standartButtons.select(MessageButton.SAVE);
			break;
		case 7:
			standartButtons.select(MessageButton.OK);
			standartButtons.select(MessageButton.CANCEL);
			standartButtons.select(MessageButton.YES);
			break;
		case 11:
			standartButtons.select(MessageButton.OK);
			standartButtons.select(MessageButton.CANCEL);
			standartButtons.select(MessageButton.NO);
			break;
		case 19:
			standartButtons.select(MessageButton.OK);
			standartButtons.select(MessageButton.CANCEL);
			standartButtons.select(MessageButton.SAVE);
			break;
		case 14:
			standartButtons.select(MessageButton.CANCEL);
			standartButtons.select(MessageButton.YES);
			standartButtons.select(MessageButton.NO);
			break;
		case 22:
			standartButtons.select(MessageButton.CANCEL);
			standartButtons.select(MessageButton.YES);
			standartButtons.select(MessageButton.SAVE);
			break;
		case 28:
			standartButtons.select(MessageButton.YES);
			standartButtons.select(MessageButton.NO);
			standartButtons.select(MessageButton.CANCEL);
			break;
		case 15:
			standartButtons.select(MessageButton.OK);
			standartButtons.select(MessageButton.CANCEL);
			standartButtons.select(MessageButton.YES);
			standartButtons.select(MessageButton.NO);
			break;
		case 23:
			standartButtons.select(MessageButton.OK);
			standartButtons.select(MessageButton.CANCEL);
			standartButtons.select(MessageButton.YES);
			standartButtons.select(MessageButton.SAVE);
			break;
		case 30:
			standartButtons.select(MessageButton.CANCEL);
			standartButtons.select(MessageButton.YES);
			standartButtons.select(MessageButton.NO);
			standartButtons.select(MessageButton.SAVE);
			break;
		case 31:
			standartButtons.select(MessageButton.OK);
			standartButtons.select(MessageButton.CANCEL);
			standartButtons.select(MessageButton.YES);
			standartButtons.select(MessageButton.NO);
			standartButtons.select(MessageButton.SAVE);
			break;
		}
	}

	private void fillButtonsValueOfItem(ReMessage item) {
		MessageButtonsCheckboxGroup standartButtons = getView().getStandartButtons();
		Set<MessageButton> selectedItems = standartButtons.getSelectedItems();
		int buttons = 0;
		for (MessageButton messageButton : selectedItems) {
			buttons += messageButton.getButtonIntValue();
		}
		item.setButtons(buttons);
	}

	private void fillDefaultButton() {
		int defaultButtonIntValue = getItem().getDefButton();
		MessageButton button = MessageButton.findMessageButton(defaultButtonIntValue);
		if (button != null) {
			MessageButtonsComboBox defaultButton = getView().getDefaultButton();
			defaultButton.setSelectedItem(button);
		}
	}

	private void fillDefaultButtonValueOfItem(ReMessage item) {
		MessageButtonsComboBox defaultButton = getView().getDefaultButton();
		Optional<MessageButton> selectedItem = defaultButton.getSelectedItem();
		int defaultButtonIntValue = 0;
		if (selectedItem != null && selectedItem.get() != null) {
			defaultButtonIntValue = selectedItem.get().getButtonIntValue();
		}
		item.setDefButton(defaultButtonIntValue);
	}

	@Override
	protected ReMessage save(ReMessage item) throws LocalizedException {
		fillButtonsValueOfItem(item);
		fillDefaultButtonValueOfItem(item);
		return super.save(item);
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

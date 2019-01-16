package com.lbs.re.ui.view.message.edit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.lbs.re.data.service.MessageService;
import com.lbs.re.model.ReMessage;
import com.lbs.re.ui.components.basic.RETextField;
import com.lbs.re.ui.components.checkbox.MessageButtonsCheckboxGroup;
import com.lbs.re.ui.components.combobox.MessageButtonsComboBox;
import com.lbs.re.ui.components.combobox.MessageTypeComboBox;
import com.lbs.re.ui.components.combobox.ResourceGroupComboBox;
import com.lbs.re.ui.view.AbstractEditView;
import com.lbs.re.ui.view.Operation;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.spring.annotation.SpringView;

@SpringView
public class MessageEditView
		extends AbstractEditView<ReMessage, MessageService, MessageEditPresenter, MessageEditView> {

	private static final long serialVersionUID = 1L;

	private RETextField consId;
	private RETextField module;
	private RETextField listid;
	private RETextField strtag;
	private MessageTypeComboBox mtype;
	private ResourceGroupComboBox resgroup;
	private MessageButtonsComboBox defaultButton;
	private MessageButtonsCheckboxGroup standartButtons;

	@Autowired
	public MessageEditView(MessageEditPresenter presenter, MessageTypeComboBox mtype, ResourceGroupComboBox resgroup,
			MessageButtonsCheckboxGroup buttons, MessageButtonsComboBox defaultButton) {
		super(presenter);
		this.mtype = mtype;
		this.resgroup = resgroup;
		this.standartButtons = buttons;
		this.defaultButton = defaultButton;
	}

	@PostConstruct
	private void initView() {
		consId = new RETextField("view.messageedit.textfield.consId", "half", true, true);
		module = new RETextField("column.message.module", "half", true, true);
		listid = new RETextField("view.resourceedit.textfield.number", "half", true, true);
		strtag = new RETextField("view.messageedit.textfield.tag", "half", true, true);
		addSection(getLocaleValue("view.viewedit.section.general"), 0, null, mtype, consId, module, resgroup, listid,
				strtag, standartButtons, defaultButton);
		getPresenter().setView(this);
	}

	@Override
	public void bindFormFields(BeanValidationBinder<ReMessage> binder) {
		binder.forField(listid).withNullRepresentation("")
				.withConverter(new StringToIntegerConverter("must be integer"))
				.bind(ReMessage::getListid, ReMessage::setListid);
		binder.forField(strtag).withNullRepresentation("")
				.withConverter(new StringToIntegerConverter("must be integer"))
				.bind(ReMessage::getStrtag, ReMessage::setStrtag);
		super.bindFormFields(binder);
		binder.forField(mtype).asRequired().bind("mtype");
		binder.forField(consId).asRequired().bind("consId");
		binder.forField(module).asRequired().bind("module");
		binder.forField(resgroup).asRequired().bind("resgroup");
	}

	@Override
	protected void collectGrids() {
		super.collectGrids();
	}

	@Override
	public String getHeader() {
		return getLocaleValue("view.messagedit.header");
	}

	public MessageButtonsCheckboxGroup getStandartButtons() {
		return standartButtons;
	}

	public MessageButtonsComboBox getDefaultButton() {
		return defaultButton;
	}

	@Override
	public String getViewOperationName() {
		return Operation.VIEW_MESSAGE;
	}

	@Override
	public String getEditOperationName() {
		return Operation.EDIT_MESSAGE;
	}

}

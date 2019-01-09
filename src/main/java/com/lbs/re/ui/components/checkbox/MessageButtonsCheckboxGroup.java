package com.lbs.re.ui.components.checkbox;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

import com.lbs.re.localization.ResourceEditorLocalizerWrapper;
import com.lbs.re.util.EnumsV2.MessageButton;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@PrototypeScope
public class MessageButtonsCheckboxGroup extends RECheckBoxGroup<MessageButton>
		implements ResourceEditorLocalizerWrapper {

	private static final long serialVersionUID = 1L;

	@Autowired
	public MessageButtonsCheckboxGroup(MessageButtonsCheckboxGroupDataProvider dataProvider) {
		super("component.MessageButtonsCheckboxGroup", "half", true, dataProvider.getListDataProvider().getItems());
		setCaption(getLocaleValue("component.messagebuttonscheckboxgroup.caption"));
	}
}

package com.lbs.re.ui.components.combobox;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

import com.lbs.re.localization.ResourceEditorLocalizerWrapper;
import com.lbs.re.ui.components.checkbox.MessageButtonsCheckboxGroupDataProvider;
import com.lbs.re.util.EnumsV2.MessageButton;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@PrototypeScope
public class MessageButtonsComboBox extends REComboBox<MessageButton> implements ResourceEditorLocalizerWrapper {

	private static final long serialVersionUID = 1L;

	@Autowired
	public MessageButtonsComboBox(MessageButtonsCheckboxGroupDataProvider dataProvider) {
		super();
		setId("component.MessageButtonsComboBox");
		setCaption(getLocaleValue("component.messagebuttonscombobox.caption"));
		setStyleName("half");
		setEnabled(true);
		setDataProvider(dataProvider.getListDataProvider());
	}
}

package com.lbs.re.ui.components.combobox;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

import com.lbs.re.localization.ResourceEditorLocalizerWrapper;
import com.lbs.re.util.EnumsV2.MessageType;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@PrototypeScope
public class MessageTypeComboBox extends REComboBox<MessageType> implements ResourceEditorLocalizerWrapper {

	private static final long serialVersionUID = 1L;

	@Autowired
	public MessageTypeComboBox(MessageTypeComboBoxDataProvider dataProvider) {
		super();
		setId("component.MessageTypeComboBox");
		setCaption(getLocaleValue("component.messagetypecombobox.caption"));
		setStyleName("half");
		setEnabled(true);
		setDataProvider(dataProvider.getListDataProvider());
	}
}

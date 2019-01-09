package com.lbs.re.ui.components.combobox;

import java.util.Arrays;

import org.vaadin.spring.annotation.PrototypeScope;

import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.ui.view.AbstractDataProvider;
import com.lbs.re.util.EnumsV2.MessageType;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@PrototypeScope
public class MessageTypeComboBoxDataProvider extends AbstractDataProvider<MessageType> {

	private static final long serialVersionUID = 1L;

	public MessageTypeComboBoxDataProvider() throws LocalizedException {
		buildListDataProvider(Arrays.asList(MessageType.values()));
	}

}

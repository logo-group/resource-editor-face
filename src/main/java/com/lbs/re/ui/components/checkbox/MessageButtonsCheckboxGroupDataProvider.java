package com.lbs.re.ui.components.checkbox;

import java.util.Arrays;

import org.vaadin.spring.annotation.PrototypeScope;

import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.ui.view.AbstractDataProvider;
import com.lbs.re.util.EnumsV2.MessageButton;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@PrototypeScope
public class MessageButtonsCheckboxGroupDataProvider extends AbstractDataProvider<MessageButton> {

	private static final long serialVersionUID = 1L;

	public MessageButtonsCheckboxGroupDataProvider() throws LocalizedException {
		buildListDataProvider(Arrays.asList(MessageButton.values()));
	}

}
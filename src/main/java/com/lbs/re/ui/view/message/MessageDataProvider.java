package com.lbs.re.ui.view.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

import com.lbs.re.data.service.MessageService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReMessage;
import com.lbs.re.ui.view.AbstractDataProvider;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@PrototypeScope
public class MessageDataProvider extends AbstractDataProvider<ReMessage> {

	private static final long serialVersionUID = 1L;

	@Autowired
	public MessageDataProvider(MessageService service) throws LocalizedException {
		buildListDataProvider(service.getAll());
	}

}

package com.lbs.re.ui.view.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

import com.lbs.re.data.service.REUserService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReUser;
import com.lbs.re.ui.view.AbstractDataProvider;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@PrototypeScope
public class UserDataProvider extends AbstractDataProvider<ReUser> {

	private static final long serialVersionUID = 1L;

	@Autowired
	public UserDataProvider(REUserService userService) throws LocalizedException {
		buildListDataProvider(userService.getAll());
	}

}
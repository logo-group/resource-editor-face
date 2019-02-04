package com.lbs.re.ui.components.combobox;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

import com.lbs.re.data.service.REUserService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReUser;
import com.lbs.re.ui.view.AbstractDataProvider;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@PrototypeScope
public class UserComboBoxDataProvider extends AbstractDataProvider<ReUser> {

	private REUserService userService;

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	public UserComboBoxDataProvider(REUserService userService) throws LocalizedException {
		this.userService = userService;
		buildListDataProvider(userService.getAll());
	}

}

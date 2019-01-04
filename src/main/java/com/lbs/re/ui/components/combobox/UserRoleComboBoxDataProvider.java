package com.lbs.re.ui.components.combobox;

import java.util.Arrays;

import org.vaadin.spring.annotation.PrototypeScope;

import com.lbs.re.ui.view.AbstractDataProvider;
import com.lbs.re.util.EnumsV2.UserType;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@PrototypeScope
public class UserRoleComboBoxDataProvider extends AbstractDataProvider<UserType> {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public UserRoleComboBoxDataProvider() {
		buildListDataProvider(Arrays.asList(UserType.values()));
	}
}

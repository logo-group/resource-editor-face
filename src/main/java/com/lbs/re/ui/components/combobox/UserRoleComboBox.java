package com.lbs.re.ui.components.combobox;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

import com.lbs.re.localization.ResourceEditorLocalizerWrapper;
import com.lbs.re.util.EnumsV2.UserType;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@PrototypeScope
public class UserRoleComboBox extends REComboBox<UserType> implements ResourceEditorLocalizerWrapper {

	@Autowired
	public UserRoleComboBox(UserRoleComboBoxDataProvider dataProvider) {
		super();
		setId("component.ResourceGroupComboBox");
		setCaption(getLocaleValue("component.userrolcombobox.caption"));
		setStyleName("half");
		setEnabled(true);
		setDataProvider(dataProvider.getListDataProvider());
	}
}

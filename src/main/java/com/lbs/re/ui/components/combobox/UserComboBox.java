package com.lbs.re.ui.components.combobox;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

import com.lbs.re.localization.ResourceEditorLocalizerWrapper;
import com.lbs.re.model.ReUser;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@PrototypeScope
public class UserComboBox extends REComboBox<ReUser> implements ResourceEditorLocalizerWrapper {

	private static final long serialVersionUID = 1L;

	@Autowired
	public UserComboBox(UserComboBoxDataProvider dataProvider) {
		super();
		setId("component.UserComboBox");
		setCaption(getLocaleValue("component.searchfilter.caption"));
		setStyleName("full");
		setEnabled(true);
		setDataProvider(dataProvider.getListDataProvider());
	}
}

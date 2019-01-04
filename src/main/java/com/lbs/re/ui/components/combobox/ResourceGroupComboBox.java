package com.lbs.re.ui.components.combobox;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

import com.lbs.re.localization.ResourceEditorLocalizerWrapper;
import com.lbs.re.model.ReResourceGroup;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@PrototypeScope
public class ResourceGroupComboBox extends REComboBox<ReResourceGroup> implements ResourceEditorLocalizerWrapper {

	@Autowired
	public ResourceGroupComboBox(ResourceGroupComboBoxDataProvider dataProvider) {
		super();
		setId("component.ResourceGroupComboBox");
		setCaption(getLocaleValue("component.resourcegroupcombobox.caption"));
		setStyleName("half");
		setEnabled(true);
		setDataProvider(dataProvider.getListDataProvider());
	}
}

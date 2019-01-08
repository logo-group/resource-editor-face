package com.lbs.re.ui.components.combobox;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

import com.lbs.re.localization.ResourceEditorLocalizerWrapper;
import com.lbs.re.util.EnumsV2.ResourceType;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@PrototypeScope
public class ResourceTypeComboBox extends REComboBox<ResourceType> implements ResourceEditorLocalizerWrapper {

	private static final long serialVersionUID = 1L;

	@Autowired
	public ResourceTypeComboBox(ResourceTypeComboBoxDataProvider dataProvider) {
		super();
		setId("component.ResourceTypeComboBox");
		setCaption(getLocaleValue("component.resourcetypecombobox.caption"));
		setStyleName("half");
		setEnabled(true);
		setDataProvider(dataProvider.getListDataProvider());
	}
}

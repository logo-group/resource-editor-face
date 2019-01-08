package com.lbs.re.ui.components.combobox;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

import com.lbs.re.localization.ResourceEditorLocalizerWrapper;
import com.lbs.re.util.EnumsV2.ResourceCase;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@PrototypeScope
public class ResourceCaseComboBox extends REComboBox<ResourceCase> implements ResourceEditorLocalizerWrapper {

	private static final long serialVersionUID = 1L;

	@Autowired
	public ResourceCaseComboBox(ResourceCaseComboBoxDataProvider dataProvider) {
		super();
		setId("component.ResourceCaseComboBox");
		setCaption(getLocaleValue("component.resourcecasecombobox.caption"));
		setStyleName("half");
		setEnabled(true);
		setDataProvider(dataProvider.getListDataProvider());
	}
}

package com.lbs.re.ui.components.combobox;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

import com.lbs.re.localization.ResourceEditorLocalizerWrapper;
import com.lbs.re.util.EnumsV2.OwnerProduct;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@PrototypeScope
public class OwnerProductComboBox extends REComboBox<OwnerProduct> implements ResourceEditorLocalizerWrapper {

	@Autowired
	public OwnerProductComboBox(OwnerProductComboBoxDataProvider dataProvider) {
		super();
		setId("component.OwnerProductComboBox");
		setCaption(getLocaleValue("component.ownerproductcombobox.caption"));
		setStyleName("half");
		setEnabled(true);
		setDataProvider(dataProvider.getListDataProvider());
	}
}

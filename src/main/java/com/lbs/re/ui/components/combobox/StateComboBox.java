package com.lbs.re.ui.components.combobox;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

import com.lbs.re.localization.ResourceEditorLocalizerWrapper;
import com.lbs.re.util.EnumsV2.ResourceState;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@PrototypeScope
public class StateComboBox extends REComboBox<ResourceState> implements ResourceEditorLocalizerWrapper {

	private static final long serialVersionUID = 1L;

	@Autowired
	public StateComboBox(StateComboBoxDataProvider dataProvider) {
		super();
		setId("component.StateComboBox");
		setCaption(getLocaleValue("component.resourcestatecombobox.caption"));
		setStyleName("half");
		setEnabled(true);
		setDataProvider(dataProvider.getListDataProvider());
	}
}

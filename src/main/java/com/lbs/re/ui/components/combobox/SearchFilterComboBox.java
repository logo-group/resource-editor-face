package com.lbs.re.ui.components.combobox;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

import com.lbs.re.localization.ResourceEditorLocalizerWrapper;
import com.lbs.re.util.EnumsV2.SearchFilter;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@PrototypeScope
public class SearchFilterComboBox extends REComboBox<SearchFilter> implements ResourceEditorLocalizerWrapper {

	private static final long serialVersionUID = 1L;

	@Autowired
	public SearchFilterComboBox(SearchFilterComboBoxDataProvider dataProvider) {
		super();
		setId("component.SearchFilterComboBox");
		setCaption(getLocaleValue("component.searchfilter.caption"));
		setStyleName("full");
		setEnabled(true);
		setDataProvider(dataProvider.getListDataProvider());
	}

}

package com.lbs.re.ui.components.combobox;

import java.util.Arrays;

import org.vaadin.spring.annotation.PrototypeScope;

import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.ui.view.AbstractDataProvider;
import com.lbs.re.util.EnumsV2.ResourceType;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@PrototypeScope
public class ResourceTypeComboBoxDataProvider extends AbstractDataProvider<ResourceType> {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public ResourceTypeComboBoxDataProvider() throws LocalizedException {
		buildListDataProvider(Arrays.asList(ResourceType.values()));
	}

}

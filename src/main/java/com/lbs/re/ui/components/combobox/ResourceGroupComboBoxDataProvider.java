package com.lbs.re.ui.components.combobox;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

import com.lbs.re.data.service.ResourceGroupService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReResourceGroup;
import com.lbs.re.ui.view.AbstractDataProvider;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@PrototypeScope
public class ResourceGroupComboBoxDataProvider extends AbstractDataProvider<ReResourceGroup> {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	public ResourceGroupComboBoxDataProvider(ResourceGroupService resourceGroupService) throws LocalizedException {
		buildListDataProvider(resourceGroupService.getAll());
	}

}

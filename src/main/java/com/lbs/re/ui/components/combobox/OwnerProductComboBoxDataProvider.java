package com.lbs.re.ui.components.combobox;

import java.util.Arrays;

import org.vaadin.spring.annotation.PrototypeScope;

import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.ui.view.AbstractDataProvider;
import com.lbs.re.util.EnumsV2.OwnerProduct;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@PrototypeScope
public class OwnerProductComboBoxDataProvider extends AbstractDataProvider<OwnerProduct> {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public OwnerProductComboBoxDataProvider() throws LocalizedException {
		buildListDataProvider(Arrays.asList(OwnerProduct.values()));
	}

}

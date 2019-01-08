package com.lbs.re.ui.view.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

import com.lbs.re.data.service.ResourceService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReResource;
import com.lbs.re.ui.view.AbstractDataProvider;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@PrototypeScope
public class ResourceDataProvider extends AbstractDataProvider<ReResource> {

	private static final long serialVersionUID = 1L;

	@Autowired
	public ResourceDataProvider(ResourceService resourceService) throws LocalizedException {
		buildListDataProvider(resourceService.getAll());
	}

}

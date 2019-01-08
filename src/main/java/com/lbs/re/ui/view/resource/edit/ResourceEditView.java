package com.lbs.re.ui.view.resource.edit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.lbs.re.data.service.ResourceService;
import com.lbs.re.model.ReResource;
import com.lbs.re.ui.components.basic.RETextArea;
import com.lbs.re.ui.components.basic.RETextField;
import com.lbs.re.ui.components.combobox.OwnerProductComboBox;
import com.lbs.re.ui.components.combobox.ResourceCaseComboBox;
import com.lbs.re.ui.components.combobox.ResourceGroupComboBox;
import com.lbs.re.ui.components.combobox.ResourceTypeComboBox;
import com.lbs.re.ui.view.AbstractEditView;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.spring.annotation.SpringView;

@SpringView
public class ResourceEditView extends AbstractEditView<ReResource, ResourceService, ResourceEditPresenter, ResourceEditView> {

	private static final long serialVersionUID = 1L;

	private RETextField resourceNr;
	private RETextArea description;
	private ResourceGroupComboBox resourcegroup;
	private ResourceCaseComboBox resourcecase;
	private ResourceTypeComboBox resourcetype;
	private OwnerProductComboBox ownerproduct;

	@Autowired
	public ResourceEditView(ResourceEditPresenter presenter, ResourceGroupComboBox resourcegroup, ResourceCaseComboBox resourcecase, ResourceTypeComboBox resourcetype,
			OwnerProductComboBox ownerproduct) {
		super(presenter);
		this.resourcegroup = resourcegroup;
		this.resourcecase = resourcecase;
		this.resourcetype = resourcetype;
		this.ownerproduct = ownerproduct;
	}

	@PostConstruct
	private void initView() {
		resourceNr = new RETextField("view.resourceedit.textfield.number", "full", true, true);
		description = new RETextArea("view.resourceedit.textfield.description", "full", true, true);
		addSection(getLocaleValue("view.viewedit.section.general"), 0, null, resourceNr, description, resourcegroup, resourcecase, resourcetype, ownerproduct);
		getPresenter().setView(this);
	}

	@Override
	public void bindFormFields(BeanValidationBinder<ReResource> binder) {
		binder.forField(resourceNr).withNullRepresentation("").withConverter(new StringToIntegerConverter("must be integer")).bind(ReResource::getResourcenr,
				ReResource::setResourcenr);
		super.bindFormFields(binder);
	}

	@Override
	protected void collectGrids() {
		super.collectGrids();
	}

	@Override
	public String getHeader() {
		return getLocaleValue("view.resourceedit.header");
	}

	public ResourceGroupComboBox getResourcegroup() {
		return resourcegroup;
	}

	public void setResourcegroup(ResourceGroupComboBox resourcegroup) {
		this.resourcegroup = resourcegroup;
	}

}

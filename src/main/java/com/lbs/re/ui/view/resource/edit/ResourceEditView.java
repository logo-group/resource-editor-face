package com.lbs.re.ui.view.resource.edit;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.lbs.re.data.service.ResourceService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReResource;
import com.lbs.re.model.ReResourceitem;
import com.lbs.re.ui.components.basic.REButton;
import com.lbs.re.ui.components.basic.RETextArea;
import com.lbs.re.ui.components.basic.RETextField;
import com.lbs.re.ui.components.combobox.OwnerProductComboBox;
import com.lbs.re.ui.components.combobox.ResourceCaseComboBox;
import com.lbs.re.ui.components.combobox.ResourceGroupComboBox;
import com.lbs.re.ui.components.combobox.ResourceTypeComboBox;
import com.lbs.re.ui.components.grid.GridColumns;
import com.lbs.re.ui.components.grid.GridColumns.GridColumn;
import com.lbs.re.ui.components.grid.REFilterGrid;
import com.lbs.re.ui.components.grid.REGridConfig;
import com.lbs.re.ui.components.grid.RUDOperations;
import com.lbs.re.ui.util.RENotification;
import com.lbs.re.ui.util.RENotification.NotifyType;
import com.lbs.re.ui.view.AbstractDataProvider;
import com.lbs.re.ui.view.AbstractEditView;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;

@SpringView
public class ResourceEditView extends AbstractEditView<ReResource, ResourceService, ResourceEditPresenter, ResourceEditView> {

	private static final long serialVersionUID = 1L;

	private RETextField resourceNr;
	private RETextArea description;
	private ResourceGroupComboBox resourcegroup;
	private ResourceCaseComboBox resourcecase;
	private ResourceTypeComboBox resourcetype;
	private OwnerProductComboBox ownerproduct;
	private REButton btnAddRow;
	private REButton btnRemoveRow;

	private REFilterGrid<ReResourceitem> gridResourceItems;

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
		buildResourceItemsGrid();
		addSection(getLocaleValue("view.viewedit.section.general"), 0, null, resourceNr, description, resourcegroup, resourcecase, resourcetype, ownerproduct);
		addSection(getLocaleValue("view.viewedit.section.resourceitems"), 1, null, buildResourceItemsGridButtons(), gridResourceItems);
		getPresenter().setView(this);
	}

	private Component buildResourceItemsGridButtons() {
		HorizontalLayout hLayButtons = new HorizontalLayout();

		btnAddRow = new REButton("view.testcaseedit.button.addrow", VaadinIcons.PLUS_CIRCLE);
		btnRemoveRow = new REButton("view.testcaseedit.button.removerow", VaadinIcons.MINUS_CIRCLE);

		hLayButtons.addComponents(btnAddRow, btnRemoveRow);

		btnAddRow.addClickListener(e -> {
			try {
				getPresenter().addResourceItemRow();
			} catch (LocalizedException e1) {
				logError(e1);
			}
		});
		btnRemoveRow.addClickListener(e -> getPresenter().removeResourceItemRow());

		return hLayButtons;
	}

	protected void organizeResourceItemsGrid(AbstractDataProvider<ReResourceitem> dataProvider) {
		gridResourceItems.setGridDataProvider(dataProvider);
		gridResourceItems.initFilters();
	}

	private REGridConfig<ReResourceitem> buildResourceItemsGridConfig() {
		REGridConfig<ReResourceitem> resourceItemsGridConfig = new REGridConfig<ReResourceitem>() {

			@Override
			public List<GridColumn> getColumnList() {
				return GridColumns.GridColumn.RESOURCE_ITEMS_COLUMNS;
			}

			@Override
			public Class<ReResourceitem> getBeanType() {
				return ReResourceitem.class;
			}

			@Override
			public List<RUDOperations> getRUDOperations() {
				List<RUDOperations> operations = new ArrayList<RUDOperations>();
				operations.add(RUDOperations.VIEW);
				operations.add(RUDOperations.EDIT);
				return operations;
			}

		};
		return resourceItemsGridConfig;
	}

	protected void buildResourceItemsGrid() {
		gridResourceItems = new REFilterGrid<ReResourceitem>(buildResourceItemsGridConfig(), SelectionMode.MULTI) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onViewSelected(ReResourceitem resourceItem) {
				/*
				 * try { // getPresenter().prepareTestStepTypeWindow(resourceItem, ViewMode.EDIT); } catch (LocalizedException e) { logError(e); }
				 */
			}

			@Override
			public void onEditSelected(ReResourceitem resourceItem) {
				getEditor().editRow(getRowIndex(resourceItem));
			}
		};

		// gridResourceItems.getColumn(GridColumn.RESOURCE_ITEMS_COLUMNS.getColumnName()).setDescriptionGenerator(ReResourceitem::getReTurkishtr);
		gridResourceItems.setId("ResourceItemGrid");
	}

	@Override
	public void bindFormFields(BeanValidationBinder<ReResource> binder) {
		binder.forField(resourceNr).withNullRepresentation("").withConverter(new StringToIntegerConverter("must be integer")).bind(ReResource::getResourcenr,
				ReResource::setResourcenr);
		super.bindFormFields(binder);
		binder.forField(resourcegroup).bind("resourcegroup");
	}

	@Override
	protected void collectGrids() {
		super.collectGrids();
	}

	@Override
	public String getHeader() {
		return getLocaleValue("view.resourceedit.header");
	}

	public void showGridRowNotSelected() {
		RENotification.showNotification(getLocaleValue("view.testcaseedit.messages.showGridRowNotSelected"), NotifyType.ERROR);
	}

	public ResourceGroupComboBox getResourcegroup() {
		return resourcegroup;
	}

	public REFilterGrid<ReResourceitem> getGridResourceItems() {
		return gridResourceItems;
	}

}

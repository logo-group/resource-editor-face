package com.lbs.re.ui.view.resource.edit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.lbs.re.data.service.ResourceService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReResource;
import com.lbs.re.model.ReResourceitem;
import com.lbs.re.ui.components.CustomExceptions.REWindowNotAbleToOpenException;
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
import com.lbs.re.ui.components.grid.RETreeGrid;
import com.lbs.re.ui.components.grid.RUDOperations;
import com.lbs.re.ui.components.window.WindowResourceItem;
import com.lbs.re.ui.util.Enums.UIParameter;
import com.lbs.re.ui.util.Enums.ViewMode;
import com.lbs.re.ui.util.RENotification;
import com.lbs.re.ui.util.RENotification.NotifyType;
import com.lbs.re.ui.view.AbstractDataProvider;
import com.lbs.re.ui.view.AbstractEditView;
import com.lbs.re.ui.view.Operation;
import com.lbs.re.ui.view.resourceitem.edit.ResourceItemTreeDataProvider;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;

@SpringView
public class ResourceEditView
		extends AbstractEditView<ReResource, ResourceService, ResourceEditPresenter, ResourceEditView> {

	private static final long serialVersionUID = 1L;

	private RETextField resourceNr;
	private RETextArea description;
	private ResourceGroupComboBox resourcegroup;
	private ResourceCaseComboBox resourcecase;
	private ResourceTypeComboBox resourcetype;
	private OwnerProductComboBox ownerproduct;
	private REButton btnAddRow;
	private REButton btnRemoveRow;
	private REButton btnActive;
	private REButton btnDeActive;

	private REFilterGrid<ReResourceitem> gridResourceItems;
	private RETreeGrid<ReResourceitem> treeGridResourceItems;

	private WindowResourceItem windowResourceItem;

	@Autowired
	public ResourceEditView(ResourceEditPresenter presenter, ResourceGroupComboBox resourcegroup,
			ResourceCaseComboBox resourcecase, ResourceTypeComboBox resourcetype, OwnerProductComboBox ownerproduct,
			WindowResourceItem windowResourceItem) {
		super(presenter);
		this.resourcegroup = resourcegroup;
		this.resourcecase = resourcecase;
		this.resourcetype = resourcetype;
		this.ownerproduct = ownerproduct;
		this.windowResourceItem = windowResourceItem;
	}

	@PostConstruct
	protected void initView() {
		resourceNr = new RETextField("view.resourceedit.textfield.number", "full", true, true);
		description = new RETextArea("view.resourceedit.textfield.description", "full", true, true);
		buildResourceItemsGrid();
		addSection(getLocaleValue("view.viewedit.section.general"), 0, null, resourceNr, description, resourcegroup,
				resourcecase, resourcetype, ownerproduct);
		addSection(getLocaleValue("view.viewedit.section.resourceitems"), 1, null, buildResourceItemsGridButtons(),
				gridResourceItems, treeGridResourceItems);
		getPresenter().setView(this);
	}

	private Component buildResourceItemsGridButtons() {
		HorizontalLayout hLayButtons = new HorizontalLayout();

		btnAddRow = new REButton("view.testcaseedit.button.addrow", VaadinIcons.PLUS_CIRCLE);
		btnRemoveRow = new REButton("view.testcaseedit.button.removerow", VaadinIcons.MINUS_CIRCLE);
		btnActive = new REButton("view.testcaseedit.button.activerow", VaadinIcons.CHECK_SQUARE_O);
		btnDeActive = new REButton("view.testcaseedit.button.deactiverow", VaadinIcons.CLOSE_CIRCLE_O);
		hLayButtons.addComponents(btnAddRow, btnRemoveRow, btnActive, btnDeActive);

		btnAddRow.addClickListener(e -> {
			try {
				getPresenter().prepareResourceItemWindow(new ReResourceitem(), ViewMode.EDIT);
			} catch (LocalizedException e1) {
				logError(e1);
			}
		});
		btnRemoveRow.addClickListener(e -> {
			try {
				getPresenter().removeResourceItemRow();
			} catch (LocalizedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		btnActive.addClickListener(e -> {
			getPresenter().setActiveItems(true);
		});
		btnDeActive.addClickListener(e -> {
			getPresenter().setActiveItems(false);
		});
		return hLayButtons;
	}

	protected void organizeResourceItemsGrid(AbstractDataProvider<ReResourceitem> dataProvider) {
		gridResourceItems.setGridDataProvider(dataProvider);
		gridResourceItems.initFilters();
	}

	protected void organizeResourceItemsTreeGrid(ResourceItemTreeDataProvider dataProvider) {
		treeGridResourceItems.setTreeGridDataProvider(dataProvider);
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
				return operations;
			}

		};
		return resourceItemsGridConfig;
	}

	private REGridConfig<ReResourceitem> buildResourceItemsTreeGridConfig() {
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
				try {
					getPresenter().prepareResourceItemWindow(resourceItem, ViewMode.EDIT);
				} catch (LocalizedException e) {
					logError(e);
				}
			}

		};
		gridResourceItems.setId("ResourceItemGrid");

		treeGridResourceItems = new RETreeGrid<ReResourceitem>(buildResourceItemsTreeGridConfig(),
				SelectionMode.MULTI) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onViewSelected(ReResourceitem resourceItem) {
				try {
					getPresenter().prepareResourceItemWindow(resourceItem, ViewMode.EDIT);
				} catch (LocalizedException e) {
					logError(e);
				}
			}
		};
		treeGridResourceItems.setId("ResourceItemTreeGrid");
	}

	@Override
	public void bindFormFields(BeanValidationBinder<ReResource> binder) {
		binder.forField(resourceNr).withNullRepresentation("")
				.withConverter(new StringToIntegerConverter("must be integer"))
				.bind(ReResource::getResourcenr, ReResource::setResourcenr);
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

	public void openResourceItemWindow(Map<UIParameter, Object> windowParameters) throws LocalizedException {
		try {
			windowResourceItem.open(windowParameters);
		} catch (REWindowNotAbleToOpenException e) {
			windowResourceItem.close();
			RENotification.showNotification(e.getMessage(), NotifyType.ERROR);
		}
	}

	public void showGridRowNotSelected() {
		RENotification.showNotification(getLocaleValue("view.testcaseedit.messages.showGridRowNotSelected"),
				NotifyType.ERROR);
	}

	public void showActiveRowSelected() {
		RENotification.showNotification(getLocaleValue("view.testcaseedit.messages.showActiveRowSelected"),
				NotifyType.ERROR);
	}

	public ResourceGroupComboBox getResourcegroup() {
		return resourcegroup;
	}

	public REFilterGrid<ReResourceitem> getGridResourceItems() {
		return gridResourceItems;
	}

	public RETreeGrid<ReResourceitem> getTreeGridResourceItems() {
		return treeGridResourceItems;
	}

	public REButton getBtnAddRow() {
		return btnAddRow;
	}

	public REButton getBtnRemoveRow() {
		return btnRemoveRow;
	}

	public REButton getBtnActive() {
		return btnActive;
	}

	public REButton getBtnDeActive() {
		return btnDeActive;
	}

	@Override
	public String getViewOperationName() {
		return Operation.VIEW_RESOURCE;
	}

	@Override
	public String getEditOperationName() {
		return Operation.EDIT_RESOURCE;
	}

}

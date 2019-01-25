package com.lbs.re.ui.view.resourceitem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.lbs.re.data.service.ResourceitemService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReResourceitem;
import com.lbs.re.ui.components.CustomExceptions.REWindowNotAbleToOpenException;
import com.lbs.re.ui.components.basic.REButton;
import com.lbs.re.ui.components.grid.GridColumns;
import com.lbs.re.ui.components.grid.GridColumns.GridColumn;
import com.lbs.re.ui.components.grid.REGridConfig;
import com.lbs.re.ui.components.grid.RUDOperations;
import com.lbs.re.ui.components.window.WindowResourceItem;
import com.lbs.re.ui.util.Enums.UIParameter;
import com.lbs.re.ui.util.RENotification;
import com.lbs.re.ui.util.RENotification.NotifyType;
import com.lbs.re.ui.view.AbstractGridView;
import com.lbs.re.ui.view.Operation;
import com.lbs.re.ui.view.advancedsearch.AdvancedSearchView;
import com.lbs.re.ui.view.resourceitem.edit.ResourceItemEditView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid.SelectionMode;

@SpringView
public class ResourceItemGridView extends AbstractGridView<ReResourceitem, ResourceitemService, ResourceItemGridPresenter, ResourceItemGridView> {

	private static final long serialVersionUID = 1L;

	private WindowResourceItem windowResourceItem;

	private AdvancedSearchView advancedSearchView;

	private REGridConfig<ReResourceitem> config = new REGridConfig<ReResourceitem>() {

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
			operations.add(RUDOperations.ITEM);
			return operations;
		}

	};

	@Autowired
	public ResourceItemGridView(ResourceItemGridPresenter presenter, WindowResourceItem windowResourceItem, AdvancedSearchView advancedSearchView) {
		super(presenter, SelectionMode.MULTI);
		this.windowResourceItem = windowResourceItem;
		this.advancedSearchView = advancedSearchView;
	}

	@PostConstruct
	private void init() {
		getPresenter().setView(this);
		setHeader(getLocaleValue("view.resourcegrid.header"));
		buildAdvancedSearch();
	}

	@Override
	protected REGridConfig<ReResourceitem> getTedamGridConfig() {
		return config;
	}

	@Override
	protected Class<? extends View> getEditView() {
		return ResourceItemEditView.class;
	}

	@Override
	public void buildGridColumnDescription() {
		// TODO Auto-generated method stub

	}

	private void buildAdvancedSearch() {
		getGridLayout().setSecondComponent(getGrid());
		getGridLayout().setFirstComponent(advancedSearchView);
		getGridLayout().setMinSplitPosition(250, Unit.PIXELS);
		getGridLayout().setMaxSplitPosition(40, Unit.PERCENTAGE);
		getGridLayout().setSplitPosition(20, Unit.PERCENTAGE);
		getGridLayout().setLocked(false);
	}

	@Override
	public String getListOperationName() {
		return Operation.LIST_RESOURCE;
	}

	@Override
	public String getAddOperationName() {
		return Operation.ADD_RESOURCE;
	}

	@Override
	public String getDeleteOperationName() {
		return Operation.DELETE_RESOURCE;
	}

	public void openResourceItemWindow(Map<UIParameter, Object> windowParameters) throws LocalizedException {
		try {
			windowResourceItem.open(windowParameters);
		} catch (REWindowNotAbleToOpenException e) {
			windowResourceItem.close();
			RENotification.showNotification(e.getMessage(), NotifyType.ERROR);
		}
	}

	@Override
	public List<Component> buildCustomComponent(ReResourceitem item) {
		REButton itemButton = new REButton("general.button.edit", VaadinIcons.SEARCH_PLUS);
		itemButton.setId(itemButton.getId() + "." + item.getId());
		itemButton.setSizeUndefined();
		itemButton.setCaption("");

		itemButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					getPresenter().prepareResourceItemWindow(item);
				} catch (LocalizedException e) {
					e.printStackTrace();
				}
			}
		});
		List<Component> componentList = new ArrayList<>();
		componentList.add(itemButton);
		return componentList;
	}
}

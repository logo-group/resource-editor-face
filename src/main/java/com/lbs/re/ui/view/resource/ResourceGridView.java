package com.lbs.re.ui.view.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.lbs.re.data.service.ResourceService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReResource;
import com.lbs.re.ui.components.CustomExceptions.REWindowNotAbleToOpenException;
import com.lbs.re.ui.components.basic.REButton;
import com.lbs.re.ui.components.grid.GridColumns;
import com.lbs.re.ui.components.grid.GridColumns.GridColumn;
import com.lbs.re.ui.components.grid.REGridConfig;
import com.lbs.re.ui.components.grid.RUDOperations;
import com.lbs.re.ui.components.window.WindowResourceCopy;
import com.lbs.re.ui.util.Enums.UIParameter;
import com.lbs.re.ui.util.RENotification;
import com.lbs.re.ui.util.RENotification.NotifyType;
import com.lbs.re.ui.view.AbstractGridView;
import com.lbs.re.ui.view.Operation;
import com.lbs.re.ui.view.advancedsearch.AdvancedSearchView;
import com.lbs.re.ui.view.resource.edit.ResourceEditView;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid.SelectionMode;

@SpringView
public class ResourceGridView extends AbstractGridView<ReResource, ResourceService, ResourceGridPresenter, ResourceGridView> {

	private static final long serialVersionUID = 1L;

	private WindowResourceCopy windowResourceCopy;
	private AdvancedSearchView advancedSearchView;

	private REGridConfig<ReResource> config = new REGridConfig<ReResource>() {

		@Override
		public List<GridColumn> getColumnList() {
			return GridColumns.GridColumn.RESOURCE_COLUMNS;
		}

		@Override
		public Class<ReResource> getBeanType() {
			return ReResource.class;
		}

		@Override
		public List<RUDOperations> getRUDOperations() {
			List<RUDOperations> operations = new ArrayList<RUDOperations>();
			operations.add(RUDOperations.DELETE);
			operations.add(RUDOperations.VIEW);
			return operations;
		}

	};

	@Autowired
	public ResourceGridView(ResourceGridPresenter presenter, WindowResourceCopy windowResourceCopy, AdvancedSearchView advancedSearchView) {
		super(presenter, SelectionMode.MULTI);
		this.windowResourceCopy = windowResourceCopy;
		this.advancedSearchView = advancedSearchView;
	}

	@PostConstruct
	private void init() {
		getPresenter().setView(this);
		setHeader(getLocaleValue("view.resourcegrid.header"));
		getTopBarLayout().addComponents(buildCopyButton());
		buildAdvancedSearch();
	}

	private void buildAdvancedSearch() {
		getGridLayout().setSecondComponent(getGrid());
		getGridLayout().setFirstComponent(advancedSearchView);
		getGridLayout().setMinSplitPosition(250, Unit.PIXELS);
		getGridLayout().setMaxSplitPosition(40, Unit.PERCENTAGE);
		getGridLayout().setSplitPosition(20, Unit.PERCENTAGE);
		getGridLayout().setLocked(false);
	}

	private Component buildCopyButton() {
		REButton btnCopy = new REButton("general.button.copy");
		btnCopy.addStyleName("primary");
		btnCopy.setWidthUndefined();
		btnCopy.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (getGrid().getSelectedItems().size() != 1) {
					RENotification.showNotification(getLocaleValue("view.resourcecopy.messages.unselectedresource"), NotifyType.ERROR);
					return;
				}
				try {
					getPresenter().prepareCopyWindow(getGrid().getSelectedItems().iterator().next());
				} catch (LocalizedException e) {
					logError(e);
				}
				getGrid().deselectAll();
			}
		});
		return btnCopy;
	}

	public void openCopyWindow(Map<UIParameter, Object> windowParameters) throws LocalizedException {
		try {
			windowResourceCopy.open(windowParameters);
		} catch (REWindowNotAbleToOpenException e) {
			windowResourceCopy.close();
			RENotification.showNotification(e.getMessage(), NotifyType.ERROR);
		}
	}

	@Override
	protected REGridConfig<ReResource> getTedamGridConfig() {
		return config;
	}

	@Override
	protected Class<? extends View> getEditView() {
		return ResourceEditView.class;
	}

	@Override
	public void buildGridColumnDescription() {
		// TODO Auto-generated method stub

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

	@Override
	public List<Component> buildCustomComponent(ReResource item) {
		return new ArrayList<>();
	}

}

package com.lbs.re.ui.view.resource;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.lbs.re.data.service.ResourceService;
import com.lbs.re.model.ReResource;
import com.lbs.re.ui.components.grid.GridColumns;
import com.lbs.re.ui.components.grid.GridColumns.GridColumn;
import com.lbs.re.ui.components.grid.REGridConfig;
import com.lbs.re.ui.components.grid.RUDOperations;
import com.lbs.re.ui.view.AbstractGridView;
import com.lbs.re.ui.view.resource.edit.ResourceEditView;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid.SelectionMode;

@SpringView
public class ResourceGridView extends AbstractGridView<ReResource, ResourceService, ResourceGridPresenter, ResourceGridView> {

	private static final long serialVersionUID = 1L;

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
	public ResourceGridView(ResourceGridPresenter presenter) {
		super(presenter, SelectionMode.MULTI);
	}

	@PostConstruct
	private void init() {
		getPresenter().setView(this);
		setHeader(getLocaleValue("resourcesGridLabel"));
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

}

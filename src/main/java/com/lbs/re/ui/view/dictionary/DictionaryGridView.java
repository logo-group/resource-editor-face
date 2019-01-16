package com.lbs.re.ui.view.dictionary;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.lbs.re.data.service.ResourceitemService;
import com.lbs.re.model.ReResourceitem;
import com.lbs.re.ui.components.grid.GridColumns;
import com.lbs.re.ui.components.grid.GridColumns.GridColumn;
import com.lbs.re.ui.components.grid.REGridConfig;
import com.lbs.re.ui.components.grid.RUDOperations;
import com.lbs.re.ui.view.AbstractGridView;
import com.lbs.re.ui.view.Operation;
import com.lbs.re.ui.view.user.edit.UserEditView;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid.SelectionMode;

@SpringView
public class DictionaryGridView extends AbstractGridView<ReResourceitem, ResourceitemService, DictionaryGridPresenter, DictionaryGridView> {

	private static final long serialVersionUID = 1L;

	private REGridConfig<ReResourceitem> config = new REGridConfig<ReResourceitem>() {

		@Override
		public List<GridColumn> getColumnList() {
			return GridColumns.GridColumn.DICTIONARY_COLUMNS;
		}

		@Override
		public Class<ReResourceitem> getBeanType() {
			return ReResourceitem.class;
		}

		@Override
		public List<RUDOperations> getRUDOperations() {
			List<RUDOperations> operations = new ArrayList<RUDOperations>();
			return operations;
		}

	};

	@Autowired
	public DictionaryGridView(DictionaryGridPresenter presenter) {
		super(presenter, SelectionMode.SINGLE);
	}

	@PostConstruct
	private void init() {
		getAddButton().setVisible(false);
		getDeleteButton().setVisible(false);
		getClearFilterButton().setVisible(false);
		getPresenter().setView(this);
		setHeader(getLocaleValue("dictionaryGridLabel"));
	}

	@Override
	protected REGridConfig<ReResourceitem> getTedamGridConfig() {
		return config;
	}

	@Override
	protected Class<? extends View> getEditView() {
		return UserEditView.class;
	}

	@Override
	public void buildGridColumnDescription() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getListOperationName() {
		return Operation.NO_CHECK;
	}

	@Override
	public String getAddOperationName() {
		return Operation.NO_CHECK;
	}

	@Override
	public String getDeleteOperationName() {
		return Operation.NO_CHECK;
	}
}

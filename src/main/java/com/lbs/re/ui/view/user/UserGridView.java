package com.lbs.re.ui.view.user;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.lbs.re.data.service.REUserService;
import com.lbs.re.model.ReUser;
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
public class UserGridView extends AbstractGridView<ReUser, REUserService, UserGridPresenter, UserGridView> {

	private static final long serialVersionUID = 1L;

	private REGridConfig<ReUser> config = new REGridConfig<ReUser>() {

		@Override
		public List<GridColumn> getColumnList() {
			return GridColumns.GridColumn.USER_COLUMNS;
		}

		@Override
		public Class<ReUser> getBeanType() {
			return ReUser.class;
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
	public UserGridView(UserGridPresenter presenter) {
		super(presenter, SelectionMode.MULTI);
	}

	@PostConstruct
	private void init() {
		getPresenter().setView(this);
		setHeader(getLocaleValue("usersGridLabel"));
	}

	@Override
	protected REGridConfig<ReUser> getTedamGridConfig() {
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
		return Operation.LIST_USER;
	}

	@Override
	public String getAddOperationName() {
		return Operation.ADD_USER;
	}

	@Override
	public String getDeleteOperationName() {
		return Operation.DELETE_USER;
	}

}

package com.lbs.re.ui.view.message;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.lbs.re.data.service.MessageService;
import com.lbs.re.model.ReMessage;
import com.lbs.re.ui.components.grid.GridColumns;
import com.lbs.re.ui.components.grid.GridColumns.GridColumn;
import com.lbs.re.ui.components.grid.REGridConfig;
import com.lbs.re.ui.components.grid.RUDOperations;
import com.lbs.re.ui.view.AbstractGridView;
import com.lbs.re.ui.view.Operation;
import com.lbs.re.ui.view.message.edit.MessageEditView;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid.SelectionMode;

@SpringView
public class MessageGridView
		extends AbstractGridView<ReMessage, MessageService, MessageGridPresenter, MessageGridView> {

	private static final long serialVersionUID = 1L;

	private REGridConfig<ReMessage> config = new REGridConfig<ReMessage>() {

		@Override
		public List<GridColumn> getColumnList() {
			return GridColumns.GridColumn.MESSAGE_COLUMNS;
		}

		@Override
		public Class<ReMessage> getBeanType() {
			return ReMessage.class;
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
	public MessageGridView(MessageGridPresenter presenter) {
		super(presenter, SelectionMode.MULTI);
	}

	@PostConstruct
	private void init() {
		getPresenter().setView(this);
		setHeader(getLocaleValue("view.messagegrid.header"));
	}

	@Override
	protected REGridConfig<ReMessage> getTedamGridConfig() {
		return config;
	}

	@Override
	protected Class<? extends View> getEditView() {
		return MessageEditView.class;
	}

	@Override
	public void buildGridColumnDescription() {
	}

	@Override
	public String getListOperationName() {
		return Operation.LIST_MESSAGE;
	}

	@Override
	public String getAddOperationName() {
		return Operation.ADD_MESSAGE;
	}

	@Override
	public String getDeleteOperationName() {
		return Operation.DELETE_MESSAGE;
	}

}

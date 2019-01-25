package com.lbs.re.ui.components.grid;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.vaadin.gridutil.renderer.BooleanRenderer;

import com.lbs.re.localization.ResourceEditorLocalizerWrapper;
import com.lbs.re.ui.components.basic.REButton;
import com.lbs.re.ui.components.basic.RECheckBox;
import com.lbs.re.ui.components.basic.REDateField;
import com.lbs.re.ui.components.basic.REDateTimeField;
import com.lbs.re.ui.components.basic.RELabel;
import com.lbs.re.ui.components.basic.RENumberField;
import com.lbs.re.ui.components.basic.RETextArea;
import com.lbs.re.ui.components.basic.RETextField;
import com.lbs.re.ui.components.combobox.REComboBox;
import com.lbs.re.ui.components.grid.GridColumns.GridColumn;
import com.lbs.re.ui.components.layout.REHorizontalLayout;
import com.lbs.re.ui.view.AbstractDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.components.grid.HeaderCell;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.renderers.LocalDateRenderer;
import com.vaadin.ui.renderers.LocalDateTimeRenderer;

public class REGridWrapper<T> implements ResourceEditorLocalizerWrapper {

	private static final int RECORD_COUNT_LABEL_INDEX = 0;

	private REGrid<T> grid;
	private REGridConfig<T> config;
	private SelectionMode selectionMode;
	private AbstractDataProvider<T> gridDataProvider;
	private Column<T, REHorizontalLayout> RUDMenuColumn;
	private HeaderCell recordCountCell;
	private String recordCountLocaleValue = getLocaleValue("general.grid.recordCount");
	private T clickedItem;

	public REGridWrapper(REGridConfig<T> config, AbstractDataProvider<T> gridDataProvider, SelectionMode selectionMode, REGrid<T> grid) {
		this.config = config;
		this.selectionMode = selectionMode;
		this.gridDataProvider = gridDataProvider;
		this.grid = grid;
	}

	public REGridWrapper(REGridConfig<T> config, SelectionMode selectionMode, REGrid<T> grid) {
		this.config = config;
		this.selectionMode = selectionMode;
		this.gridDataProvider = null;
		this.grid = grid;
	}

	public REGridConfig<T> getConfig() {
		return config;
	}

	public void setConfig(REGridConfig<T> config) {
		this.config = config;
	}

	public SelectionMode getSelectionMode() {
		return selectionMode;
	}

	public AbstractDataProvider<T> getGridDataProvider() {
		return gridDataProvider;
	}

	public void setGridDataProvider(AbstractDataProvider<T> gridDataProvider) {
		this.gridDataProvider = gridDataProvider;
	}

	public void setRUDMenuColumn(Column<T, REHorizontalLayout> rUDMenuColumn) {
		RUDMenuColumn = rUDMenuColumn;
	}

	public Column<T, REHorizontalLayout> getRUDMenuColumn() {
		return RUDMenuColumn;
	}

	public void setRecordCountCell(HeaderCell recordCountCell) {
		this.recordCountCell = recordCountCell;
	}

	public HeaderCell getRecordCountCell() {
		return recordCountCell;
	}

	public String getRecordCountLocaleValue() {
		return recordCountLocaleValue;
	}

	public REGrid<T> getGrid() {
		return grid;
	}

	public void setClickedItem(T clickedItem) {
		this.clickedItem = clickedItem;
	}

	public T getClickedItem() {
		return clickedItem;
	}

	public void setExtraOptions() {
		getGrid().getColumns().stream().forEach(column -> column.setHidable(true));
		getGrid().getEditor().setCancelCaption(getLocaleValue("general.button.cancel"));
		getGrid().getEditor().setSaveCaption(getLocaleValue("general.button.save"));
	}

	public void setColumnTitles() {
		List<Column<T, ?>> columns = getGrid().getColumns();
		Map<String, String> columnTitles = getConfig().getColumnTitles();
		if (columns.size() != columnTitles.size())
			throw new GridBuildExcepton("Count of columns and titles are not equal!");
		for (Column<T, ?> column : columns) {
			column.setCaption(columnTitles.get(column.getId()));
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setColumnAttributes() {
		List<Column<T, ?>> columns = getGrid().getColumns();
		Map<String, GridColumn> columnMap = getConfig().getColumnMap();
		for (Column<T, ?> column : columns) {
			column.setHidden(columnMap.get(column.getId()).isHidden());
			switch (columnMap.get(column.getId()).getDataType()) {
			case DATE:
				if (columnMap.get(column.getId()).isEditable()) {
					column.setEditorComponent(new REDateField("grid_" + column.getId(), "", true, true));
				}
				((Column<T, LocalDate>) column).setRenderer(new LocalDateRenderer("dd/MM/YYYY"));
				break;
			case DATE_TIME:
				if (columnMap.get(column.getId()).isEditable()) {
					column.setEditorComponent(new REDateTimeField("grid_" + column.getId(), "", true, true));
				}
				((Column<T, LocalDateTime>) column).setRenderer(new LocalDateTimeRenderer("dd/MM/YYYY HH:mm"));
				break;
			case INTEGER:
				if (columnMap.get(column.getId()).isEditable()) {
					column.setEditorComponent(new RENumberField("grid_" + column.getId(), "", true, true));
				}
				break;
			case TEXT:
				if (columnMap.get(column.getId()).isEditable()) {
					column.setEditorComponent(new RETextField("grid_" + column.getId(), "", true, true));
				}
				break;
			case TEXT_AREA:
				if (columnMap.get(column.getId()).isEditable()) {
					column.setEditorComponent(new RETextArea("grid_" + column.getId(), "", true, true));
				}
				break;
			case BOOLEAN:
				if (columnMap.get(column.getId()).isEditable()) {
					column.setEditorComponent(new RECheckBox("grid_" + column.getId(), "", true, true));
				}
				column.setRenderer(new BooleanRenderer());
				break;
			case SELECT_ENUM:
				if (columnMap.get(column.getId()).isEditable()) {
					REComboBox<Object> combo = new REComboBox<>();
					combo.setId("grid_" + column.getId());
					combo.setItems(columnMap.get(column.getId()).getFilterBeanType().getEnumConstants());
					combo.addValueChangeListener(e -> getGrid().onComboValueChange(e));
					column.setEditorComponent(combo);
				}
				break;
			case NONE:
				break;
			default:
				break;
			}
		}
	}

	public void initRUDMenuColumn() {
		List<RUDOperations> rudOperations = getConfig().getRUDOperations();
		if (rudOperations.contains(RUDOperations.NONE)) {
			return;
		}

		Column<T, REHorizontalLayout> column = getGrid().addComponentColumn(param -> {

			REHorizontalLayout layout = new REHorizontalLayout();

			Integer idValue = getRowIndex(param) + 1;
			boolean view = rudOperations.contains(RUDOperations.VIEW);
			boolean delete = rudOperations.contains(RUDOperations.DELETE);
			boolean edit = rudOperations.contains(RUDOperations.EDIT);
			boolean item = rudOperations.contains(RUDOperations.ITEM);

			if (edit) {
				REButton editButton = new REButton("general.button.edit", VaadinIcons.EDIT);
				editButton.setId(editButton.getId() + "." + idValue);
				editButton.setSizeUndefined();
				editButton.setCaption("");

				editButton.addClickListener(new ClickListener() {

					private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(ClickEvent event) {
						getGrid().onEditSelected(param);
					}
				});
				layout.addComponent(editButton);
			}

			if (view) {
				REButton viewButton = new REButton("general.button.view", VaadinIcons.SEARCH);
				viewButton.setId(viewButton.getId() + "." + idValue);
				viewButton.setSizeUndefined();
				viewButton.setCaption("");

				viewButton.addClickListener(new ClickListener() {

					private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(ClickEvent event) {
						getGrid().onViewSelected(param);
					}
				});
				layout.addComponent(viewButton);
			}

			if (delete) {
				REButton deleteButton = new REButton("general.button.delete", VaadinIcons.TRASH);
				deleteButton.setId(deleteButton.getId() + "." + idValue);
				deleteButton.setCaption("");
				deleteButton.addClickListener(new ClickListener() {

					private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(ClickEvent event) {
						getGrid().onDeleteSelected(param);
					}

				});

				layout.addComponent(deleteButton);
			}
			for (Component customComponent : getGrid().buildCustomComponentForItem(param)) {
				if (customComponent != null) {
					layout.addComponent(customComponent);
				}
			}
			return layout;
		});
		setRUDMenuColumn(column);
		getRUDMenuColumn().setHidable(false);
		getRUDMenuColumn().setCaption(getLocaleValue("general.grid.operations"));
	}

	@SuppressWarnings("unchecked")
	public void initHeaderCell() {
		HeaderRow headerRow = getGrid().prependHeaderRow();
		Column<T, ?>[] columnArray = getGrid().getColumns().toArray(new Column[getGrid().getColumns().size()]);
		if (columnArray.length > 1) {
			setRecordCountCell(headerRow.join(columnArray));
		} else {
			setRecordCountCell(headerRow.getCell(columnArray[0]));
		}
		REHorizontalLayout layout = new REHorizontalLayout();
		RELabel label = new RELabel();
		label.setWidthUndefined();

		layout.addComponents(label);
		layout.setExpandRatio(label, 1);
		getRecordCountCell().setComponent(layout);
	}

	public boolean canCopy() {
		if (getSelectionMode() != SelectionMode.NONE && getGrid().getSelectedItems().size() >= 1 && getGrid().getEditor().isEnabled()) {
			return true;
		} else {
			return false;
		}
	}

	public void refreshAll() {
		getGrid().getDataProvider().refreshAll();
	}

	public void refreshRecordCountText(int recordCount) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getRecordCountLocaleValue()).append(" ").append(String.valueOf(recordCount));
		REHorizontalLayout layout = (REHorizontalLayout) getRecordCountCell().getComponent();
		if (layout.getComponentCount() > 0)
			((RELabel) layout.getComponent(REGridWrapper.RECORD_COUNT_LABEL_INDEX)).setCaption(buffer.toString());
	}

	public void setGridDataProviderWrapper(AbstractDataProvider<T> abstractDataProvider) {
		setGridDataProvider(abstractDataProvider);
		getGridDataProvider().addDataProviderListener(getGrid());
		getGrid().setDataProvider(abstractDataProvider.getListDataProvider());
		refreshRecordCountText(getGridDataProvider().getListDataProvider().getItems().size());
		if (getRUDMenuColumn() != null) {
			getRUDMenuColumn().setSortable(false);
		}
		getConfig().getColumnList().stream().forEach(gridColumn -> getGrid().getColumn(gridColumn.getColumnName()).setSortable(gridColumn.isSortable()));
	}

	private int getRowIndex(T item) {
		int index = 0;
		for (T currentItem : getGridDataProvider().getListDataProvider().getItems()) {
			if (item == currentItem) {
				return index;
			}
			index++;
		}
		return -1;
	}

	public void init() {
		getGrid().setStyleName("small");
		getGrid().setSizeFull();
		getConfig().getColumnList().stream().forEach(gridColumn -> getGrid().addColumn(gridColumn.getColumnName()));
		setColumnTitles();
		setColumnAttributes();
		setExtraOptions();
		initRUDMenuColumn();
		getGrid().setSelectionMode(getSelectionMode());
		initHeaderCell();
		getGrid().addItemClickListener(new ItemClickListener<T>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void itemClick(ItemClick<T> event) {
				setClickedItem(event.getItem());
			}
		});
	}

}

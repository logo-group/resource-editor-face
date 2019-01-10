package com.lbs.re.ui.components.grid;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.provider.DataChangeEvent;
import com.vaadin.data.provider.DataProviderListener;
import com.vaadin.data.provider.Query;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.components.grid.HeaderCell;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.renderers.LocalDateRenderer;
import com.vaadin.ui.renderers.LocalDateTimeRenderer;

public class REGrid<T> extends Grid<T> implements ResourceEditorLocalizerWrapper, DataProviderListener<T> {

	private static final long serialVersionUID = 1L;
	private static final int RECORD_COUNT_LABEL_INDEX = 3;

	/**
	 * Grid config instance to build grid.
	 */
	private REGridConfig<T> config;

	private T clickedItem;

	/**
	 * Menu bar column for read and delete operations.
	 */
	private Column<T, REHorizontalLayout> RUDMenuColumn;

	private SelectionMode selectionMode;

	private AbstractDataProvider<T> gridDataProvider;

	private HeaderCell recordCountCell;

	private String recordCountLocaleValue = getLocaleValue("general.grid.recordCount");

	private REButton buttonDown = new REButton("general.grid.moveDown");
	private REButton buttonUp = new REButton("general.grid.moveUp");
	private REButton buttonCopyRows = new REButton("general.grid.copyRows");

	/**
	 * One parameter constructor.
	 *
	 * @param config
	 *            Grid config instance to build grid.
	 * @param dataProvider
	 *            Data provider.
	 */
	public REGrid(REGridConfig<T> config, AbstractDataProvider<T> gridDataProvider, SelectionMode selectionMode) {
		this.config = config;
		this.selectionMode = selectionMode;
		this.gridDataProvider = gridDataProvider;
		init();
		setGridDataProvider(gridDataProvider);
	}

	public REGrid(REGridConfig<T> config, SelectionMode selectionMode) {
		this.config = config;
		this.selectionMode = selectionMode;
		this.gridDataProvider = null;
		init();
	}

	public void init() {
		setStyleName("small");
		setConfig(config);
		setSizeFull();
		setBeanType(getConfig().getBeanType());
		getConfig().getColumnList().stream().forEach(gridColumn -> addColumn(gridColumn.getColumnName()));
		setColumnTitles();
		setColumnAttributes();
		setExtraOptions();
		initRUDMenuColumn();
		setSelectionMode(selectionMode);
		initHeaderCell();
		addItemClickListener(new ItemClickListener<T>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void itemClick(ItemClick<T> event) {
				clickedItem = event.getItem();
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void initHeaderCell() {
		HeaderRow headerRow = prependHeaderRow();
		Column<T, ?>[] columnArray = getColumns().toArray(new Column[getColumns().size()]);
		if (columnArray.length > 1) {
			recordCountCell = headerRow.join(columnArray);
		} else {
			recordCountCell = headerRow.getCell(columnArray[0]);
		}
		REHorizontalLayout layout = new REHorizontalLayout();
		RELabel label = new RELabel();
		label.setWidthUndefined();

		buttonDown.setIcon(VaadinIcons.ARROW_DOWN);
		buttonDown.setWidthUndefined();
		buttonDown.setCaption("");
		buttonDown.setEnabled(false);
		buttonDown.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				onMoveDown();
			}
		});

		buttonUp.setIcon(VaadinIcons.ARROW_UP);
		buttonUp.setWidthUndefined();
		buttonUp.setCaption("");
		buttonUp.setEnabled(false);
		buttonUp.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				onMoveUp();
			}
		});

		buttonCopyRows.setIcon(VaadinIcons.COPY);
		buttonCopyRows.setWidthUndefined();
		buttonCopyRows.setCaption("");
		buttonCopyRows.setEnabled(false);
		buttonCopyRows.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				onCopyRow();
			}
		});

		layout.addComponents(buttonDown, buttonUp, buttonCopyRows, label);
		layout.setExpandRatio(label, 1);
		recordCountCell.setComponent(layout);
	}

	private void onMoveDown() {
		if (canMove()) {
			T selectedItem = getSelectedItems().iterator().next();
			List<T> items = (List<T>) getGridDataProvider().getListDataProvider().getItems();
			int selectedIndex = items.indexOf(selectedItem);
			if (selectedIndex < items.size() - 1) {
				T movableItem = items.get(selectedIndex + 1);
				items.set(selectedIndex + 1, selectedItem);
				items.set(selectedIndex, movableItem);
				refreshAll();
			}
		}
	}

	private void onMoveUp() {
		if (canMove()) {
			T selectedItem = getSelectedItems().iterator().next();
			List<T> items = (List<T>) getGridDataProvider().getListDataProvider().getItems();
			int selectedIndex = items.indexOf(selectedItem);
			if (selectedIndex > 0) {
				T movableItem = items.get(selectedIndex - 1);
				items.set(selectedIndex - 1, selectedItem);
				items.set(selectedIndex, movableItem);
				refreshAll();
			}
		}
	}

	protected void onCopyRow() {

	}

	protected boolean canCopy() {
		if (getSelectionMode() != SelectionMode.NONE && getSelectedItems().size() >= 1 && getEditor().isEnabled()) {
			return true;
		} else {
			return false;
		}
	}

	private boolean canMove() {
		if (getSelectionMode() == SelectionMode.MULTI && getSelectedItems().size() == 1 && getEditor().isEnabled()) {
			return true;
		} else {
			return false;
		}
	}

	private void refreshRecordCountText(int recordCount) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(recordCountLocaleValue).append(" ").append(String.valueOf(recordCount));
		REHorizontalLayout layout = (REHorizontalLayout) recordCountCell.getComponent();
		if (layout.getComponentCount() > 0)
			((RELabel) layout.getComponent(RECORD_COUNT_LABEL_INDEX)).setCaption(buffer.toString());
	}

	@Override
	public void onDataChange(DataChangeEvent<T> event) {
		int size = getDataProvider().size(new Query<>());
		refreshRecordCountText(size);
	}

	/**
	 * @return the clickedItem
	 */
	public T getClickedItem() {
		return clickedItem;
	}

	/**
	 * Sets extra options for grid.
	 */
	private void setExtraOptions() {
		getColumns().stream().forEach(column -> column.setHidable(true));
		getEditor().setCancelCaption(getLocaleValue("general.button.cancel"));
		getEditor().setSaveCaption(getLocaleValue("general.button.save"));
	}

	/**
	 * @return the config
	 */
	public REGridConfig<T> getConfig() {
		return config;
	}

	/**
	 * @param config
	 *            the config to set
	 */
	public void setConfig(REGridConfig<T> config) {
		this.config = config;
	}

	/**
	 * Sets column attributes.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setColumnAttributes() {
		List<Column<T, ?>> columns = getColumns();
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
					combo.addValueChangeListener(e -> onComboValueChange(e));
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

	public void onComboValueChange(ValueChangeEvent<Object> event) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Sets column titles.
	 */
	private void setColumnTitles() {
		List<Column<T, ?>> columns = getColumns();
		Map<String, String> columnTitles = getConfig().getColumnTitles();
		if (columns.size() != columnTitles.size())
			throw new GridBuildExcepton("Count of columns and titles are not equal!");
		for (Column<T, ?> column : columns) {
			column.setCaption(columnTitles.get(column.getId()));
		}
	}

	/**
	 * Inits grid view, update, delete buttons.
	 */
	private void initRUDMenuColumn() {
		List<RUDOperations> rudOperations = getConfig().getRUDOperations();
		if (rudOperations.contains(RUDOperations.NONE)) {
			return;
		}

		RUDMenuColumn = addComponentColumn(param -> {

			REHorizontalLayout layout = new REHorizontalLayout();

			Integer idValue = getRowIndex(param) + 1;
			boolean view = rudOperations.contains(RUDOperations.VIEW);
			boolean delete = rudOperations.contains(RUDOperations.DELETE);
			boolean edit = rudOperations.contains(RUDOperations.EDIT);

			if (edit) {
				REButton editButton = new REButton("general.button.edit", VaadinIcons.EDIT);
				editButton.setId(editButton.getId() + "." + idValue);
				editButton.setSizeUndefined();
				editButton.setCaption("");

				editButton.addClickListener(new ClickListener() {

					private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(ClickEvent event) {
						onEditSelected(param);
					}
				});
				layout.addComponent(editButton);
			}

			if (view) {
				REButton viewButton = new REButton("general.button.view", VaadinIcons.EDIT);
				viewButton.setId(viewButton.getId() + "." + idValue);
				viewButton.setSizeUndefined();
				viewButton.setCaption("");

				viewButton.addClickListener(new ClickListener() {

					private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(ClickEvent event) {
						onViewSelected(param);
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
						onDeleteSelected(param);
					}

				});

				layout.addComponent(deleteButton);
			}
			buildCustomComponentForItem(param).forEach(customComponent -> layout.addComponent(customComponent));
			return layout;
		});
		getRUDMenuColumn().setHidable(false);
		getRUDMenuColumn().setCaption(getLocaleValue("general.grid.operations"));
	}

	public int getRowIndex(T item) {
		int index = 0;
		for (T currentItem : getGridDataProvider().getListDataProvider().getItems()) {
			if (item == currentItem) {
				return index;
			}
			index++;
		}
		return -1;
	}

	/**
	 * To be called when view selected.
	 *
	 * @param t
	 *            Item
	 */
	public void onViewSelected(T item) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @param item
	 * @param i
	 */
	public void onEditSelected(T item) {
		throw new UnsupportedOperationException();
	}

	/**
	 * To be called when delete selected.
	 *
	 * @param t
	 *            Item
	 */
	public void onDeleteSelected(T item) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @return Read, delete included menu column.
	 */
	public Column<T, REHorizontalLayout> getRUDMenuColumn() {
		return RUDMenuColumn;
	}

	/**
	 * @return custom button list.
	 */
	public List<Component> buildCustomComponentForItem(T item) {
		return new ArrayList<>();
	}

	/**
	 * @return the selectionMode
	 */
	public SelectionMode getSelectionMode() {
		return selectionMode;
	}

	public AbstractDataProvider<T> getGridDataProvider() {
		return gridDataProvider;
	}

	public void setGridDataProvider(AbstractDataProvider<T> abstractDataProvider) {
		this.gridDataProvider = abstractDataProvider;
		gridDataProvider.addDataProviderListener(this);
		setDataProvider(abstractDataProvider.getListDataProvider());
		refreshRecordCountText(gridDataProvider.getListDataProvider().getItems().size());
		if (getRUDMenuColumn() != null) {
			getRUDMenuColumn().setSortable(false);
		}
		getConfig().getColumnList().stream().forEach(gridColumn -> getColumn(gridColumn.getColumnName()).setSortable(gridColumn.isSortable()));
	}

	public void refreshAll() {
		getDataProvider().refreshAll();
	}

	public REButton getButtonDown() {
		return buttonDown;
	}

	public REButton getButtonUp() {
		return buttonUp;
	}

	public REButton getButtonCopyRows() {
		return buttonCopyRows;
	}
}
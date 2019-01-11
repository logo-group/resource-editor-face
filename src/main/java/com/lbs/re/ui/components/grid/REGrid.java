package com.lbs.re.ui.components.grid;

import java.util.ArrayList;
import java.util.List;

import com.lbs.re.localization.ResourceEditorLocalizerWrapper;
import com.lbs.re.ui.components.layout.REHorizontalLayout;
import com.lbs.re.ui.view.AbstractDataProvider;
import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.provider.DataChangeEvent;
import com.vaadin.data.provider.DataProviderListener;
import com.vaadin.data.provider.Query;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;

public class REGrid<T> extends Grid<T> implements ResourceEditorLocalizerWrapper, DataProviderListener<T> {

	private static final long serialVersionUID = 1L;

	private REGridWrapper<T> wrapper;

	public REGrid(REGridConfig<T> config, AbstractDataProvider<T> gridDataProvider, SelectionMode selectionMode) {
		wrapper = new REGridWrapper<T>(config, gridDataProvider, selectionMode, this);
		init();
		setGridDataProvider(gridDataProvider);
	}

	public REGrid(REGridConfig<T> config, SelectionMode selectionMode) {
		wrapper = new REGridWrapper<T>(config, selectionMode, this);
		init();
	}

	public void init() {
		setBeanType(getConfig().getBeanType());
		wrapper.init();
	}

	public void onCopyRow() {
	}

	protected boolean canCopy() {
		return wrapper.canCopy();
	}

	@Override
	public void onDataChange(DataChangeEvent<T> event) {
		int size = getDataProvider().size(new Query<>());
		wrapper.refreshRecordCountText(size);
	}

	public T getClickedItem() {
		return wrapper.getClickedItem();
	}

	public REGridConfig<T> getConfig() {
		return wrapper.getConfig();
	}

	public void setConfig(REGridConfig<T> config) {
		wrapper.setConfig(config);
	}

	public void onComboValueChange(ValueChangeEvent<Object> event) {
		throw new UnsupportedOperationException();
	}

	public void onViewSelected(T item) {
		throw new UnsupportedOperationException();
	}

	public void onEditSelected(T item) {
		throw new UnsupportedOperationException();
	}

	public void onDeleteSelected(T item) {
		throw new UnsupportedOperationException();
	}

	public Column<T, REHorizontalLayout> getRUDMenuColumn() {
		return wrapper.getRUDMenuColumn();
	}

	public List<Component> buildCustomComponentForItem(T item) {
		return new ArrayList<>();
	}

	public SelectionMode getSelectionMode() {
		return wrapper.getSelectionMode();
	}

	public AbstractDataProvider<T> getGridDataProvider() {
		return wrapper.getGridDataProvider();
	}

	public void setGridDataProvider(AbstractDataProvider<T> abstractDataProvider) {
		wrapper.setGridDataProviderWrapper(abstractDataProvider);
	}

	public void refreshAll() {
		wrapper.refreshAll();
	}
}
package com.lbs.re.ui.components.grid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lbs.re.localization.ResourceEditorLocalizerWrapper;
import com.lbs.re.ui.components.grid.GridColumns.GridColumn;

public abstract class REGridConfig<T> implements ResourceEditorLocalizerWrapper {

	public abstract List<GridColumn> getColumnList();

	public abstract Class<T> getBeanType();

	public abstract List<RUDOperations> getRUDOperations();

	public Map<String, String> getColumnTitles() {
		Map<String, String> columnTitles = new HashMap<>();
		for (GridColumn gridColumn : getColumnList()) {
			columnTitles.put(gridColumn.getColumnName(), getLocaleValue(gridColumn.getResourceName()));
		}
		return columnTitles;
	}

	public Map<String, GridColumn> getColumnMap() {
		Map<String, GridColumn> columnMap = new HashMap<>();
		for (GridColumn gridColumn : getColumnList()) {
			columnMap.put(gridColumn.getColumnName(), gridColumn);
		}
		return columnMap;
	}

}

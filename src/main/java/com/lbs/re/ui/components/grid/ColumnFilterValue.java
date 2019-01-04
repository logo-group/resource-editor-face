package com.lbs.re.ui.components.grid;

import java.io.Serializable;

import com.lbs.re.ui.components.grid.GridColumns.DataType;

public class ColumnFilterValue implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Column id.
	 */
	private String columnId;

	/**
	 * Filter value.
	 */
	private Object filterValue;

	/**
	 * Data type of column.
	 */
	private DataType dataType;

	public ColumnFilterValue() {
	}

	public ColumnFilterValue(String columnId, Object filterValue, DataType dataType) {
		this.columnId = columnId;
		this.filterValue = filterValue;
		this.dataType = dataType;
	}

	/**
	 * @return the columnId
	 */
	public String getColumnId() {
		return columnId;
	}

	/**
	 * @param columnId
	 *            the columnId to set
	 */
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	/**
	 * @return the filterValue
	 */
	public Object getFilterValue() {
		return filterValue;
	}

	/**
	 * @param filterValue
	 *            the filterValue to set
	 */
	public void setFilterValue(Object filterValue) {
		this.filterValue = filterValue;
	}

	/**
	 * @return the dataType
	 */
	public DataType getDataType() {
		return dataType;
	}

	/**
	 * @param dataType
	 *            the dataType to set
	 */
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

}

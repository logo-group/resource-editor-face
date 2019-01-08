package com.lbs.re.ui.components.grid;

import java.util.Arrays;
import java.util.List;

public class GridColumns {

	private GridColumns() {

	}

	public static enum GridColumn {

		ID("id", "column.id", DataType.INTEGER, null, false, false, true),

		USER_NAME("username", "column.user.name", DataType.TEXT, null, true, false, true),
		EMAIL("email", "column.user.email", DataType.TEXT, null, true, false, true), //

		RESOURCE_DESCRIPTION("description", "column.resource.description", DataType.TEXT, null, true, false, true),
		RESOURCE_NUMBER("resourcenr", "column.resource.number", DataType.INTEGER, null, true, false, true);

		public static final List<GridColumn> USER_COLUMNS = Arrays.asList(ID, USER_NAME, EMAIL);
		public static final List<GridColumn> RESOURCE_COLUMNS = Arrays.asList(ID, RESOURCE_NUMBER, RESOURCE_DESCRIPTION);

		private final String columnName;
		private final String resourceName;
		private final DataType dataType;
		private final Class<?> filterBeanType;
		private final boolean editable;
		private final boolean hidden;
		private final boolean sortable;

		private GridColumn(String columnName, String resourceName, DataType filterType, Class<?> filterBeanType, boolean editable, boolean hidden, boolean sortable) {
			this.columnName = columnName;
			this.resourceName = resourceName;
			this.dataType = filterType;
			this.filterBeanType = filterBeanType;
			this.editable = editable;
			this.hidden = hidden;
			this.sortable = sortable;
		}

		/**
		 * @return the columnName
		 */
		public String getColumnName() {
			return columnName;
		}

		/**
		 * @return the resourceName
		 */
		public String getResourceName() {
			return resourceName;
		}

		/**
		 * @return the columnType
		 */
		public DataType getDataType() {
			return dataType;
		}

		public Class<?> getFilterBeanType() {
			return filterBeanType;
		}

		/**
		 * @return the editable
		 */
		public boolean isEditable() {
			return editable;
		}

		/**
		 * @return the hidden
		 */
		public boolean isHidden() {
			return hidden;
		}

		/**
		 * @return the sortable
		 */
		public boolean isSortable() {
			return sortable;
		}

	}

	public static enum DataType {
		NONE, TEXT, INTEGER, DATE, SELECT_ENUM, BOOLEAN, DATE_TIME, TEXT_AREA;
	}
}

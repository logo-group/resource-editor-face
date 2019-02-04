package com.lbs.re.ui.components.grid;

import java.util.Arrays;
import java.util.List;

import com.lbs.re.util.EnumsV2;

public class GridColumns {

	private GridColumns() {

	}

	public static enum GridColumn {
		USER_NAME("username", "column.user.name", DataType.TEXT, null, true, false, true),
		EMAIL("email", "column.user.email", DataType.TEXT, null, true, false, true), //

		RESOURCE_DESCRIPTION("description", "column.resource.description", DataType.TEXT, null, true, false, true),
		RESOURCE_NUMBER("resourcenr", "column.resource.number", DataType.INTEGER, null, true, false, true),
		RESOURCE_GROUP_ID("resourceGroupId", "component.resourcegroupcombobox.caption", DataType.TEXT, null, true, false, true),

		MESSAGE_TYPE("mtype", "column.message.type", DataType.SELECT_ENUM, EnumsV2.MessageType.class, true, false, true),
		MESSAGE_CONSTANT("consId", "column.message.constant", DataType.TEXT, null, true, false, true),
		MESSAGE_MODULE("module", "column.message.module", DataType.TEXT, null, true, false, true),

		// RESOURCE ITEM GRID
		ITEM_ORDER_NUMBER("ordernr", "column.resource.item.order.number", DataType.INTEGER, null, false, false, true),
		ITEM_TAG_NUMBER("tagnr", "column.resource.item.tag.number", DataType.INTEGER, null, false, false, true),
		ITEM_LEVEL_NUMBER("levelnr", "column.resource.item.level.number", DataType.INTEGER, null, false, false, true),
		ITEM_PREFIX("prefixstr", "column.resource.item.prefix", DataType.TEXT, null, false, false, true),
		ITEM_INFO("info", "column.resource.item.info", DataType.TEXT, null, false, false, true),
		ITEM_ACTIVE("activeValue", "column.resource.item.active", DataType.TEXT, null, false, true, true),
		ITEM_DICTIONARY_ID("dictionaryId", "column.resource.item.dictionary.number", DataType.INTEGER, null, false, false, true),
		ITEM_TURKISH("turkishTr", "column.resource.item.turkish", DataType.TEXT, null, false, false, true),
		ITEM_ENGLISH("englishUs", "column.resource.item.english", DataType.TEXT, null, false, false, true),
		ITEM_ALBANIANKV("albanianKv", "column.resource.item.albanian", DataType.TEXT, null, false, true, true),
		ITEM_ARABICEG("arabicEg", "column.resource.item.arabiceg", DataType.TEXT, null, false, true, true),
		ITEM_ARABICJO("arabicJo", "column.resource.item.arabicjo", DataType.TEXT, null, false, true, true),
		ITEM_ARABICSA("arabicSa", "column.resource.item.arabicsa", DataType.TEXT, null, false, true, true),
		ITEM_AZERBAIJANIAZ("azerbaijaniAz", "column.resource.item.azerbaijaniaz", DataType.TEXT, null, false, true, true),
		ITEM_BULGARIANBG("bulgarianBg", "column.resource.item.bulgarianbg", DataType.TEXT, null, false, true, true),
		ITEM_FRENCHFR("frenchFr", "column.resource.item.frenchfr", DataType.TEXT, null, false, true, true),
		ITEM_GEORGIANGE("georgianGe", "column.resource.item.georgiange", DataType.TEXT, null, false, true, true),
		ITEM_GERMANDE("germanDe", "column.resource.item.germande", DataType.TEXT, null, false, true, true),
		ITEM_PERSIANIR("persianIr", "column.resource.item.persianir", DataType.TEXT, null, false, true, true),
		ITEM_ROMANIANRO("romanianRo", "column.resource.item.romanianro", DataType.TEXT, null, false, true, true),
		ITEM_RUSSIANRU("russianRu", "column.resource.item.russianru", DataType.TEXT, null, false, true, true),
		ITEM_TURKMENTM("turkmenTm", "column.resource.item.turkmentm", DataType.TEXT, null, false, true, true),
		ITEM_STANDARD("standard", "column.resource.item.standard", DataType.TEXT, null, false, false, true),

		// ADVANCED SEARCH ALTERNATIVES
		SEARCHED_ORDER_NUMBER("ordernr", "column.resource.item.order.number", DataType.INTEGER, null, false, true, true),
		SEARCHED_TAG_NUMBER("tagnr", "column.resource.item.tag.number", DataType.INTEGER, null, false, true, true),
		SEARCHED_RESOURCENR("resourceNr", "column.resource.item.resourcenr", DataType.INTEGER, null, false, false, true),
		SEARCHED_LEVEL_NUMBER("levelnr", "column.resource.item.level.number", DataType.INTEGER, null, false, true, true),
		SEARCHED_PREFIX("prefixstr", "column.resource.item.prefix", DataType.TEXT, null, false, true, true),
		SEARCHED_INFO("info", "column.resource.item.info", DataType.TEXT, null, false, true, true),
		SEARCHED_DICTIONARY_ID("dictionaryId", "column.resource.item.dictionary.number", DataType.INTEGER, null, false, true, true);

		public static final List<GridColumn> USER_COLUMNS = Arrays.asList(USER_NAME, EMAIL);
		public static final List<GridColumn> RESOURCE_COLUMNS = Arrays.asList(RESOURCE_NUMBER, RESOURCE_DESCRIPTION, RESOURCE_GROUP_ID);
		public static final List<GridColumn> RESOURCE_ITEMS_COLUMNS = Arrays.asList(ITEM_ORDER_NUMBER, ITEM_TAG_NUMBER, ITEM_LEVEL_NUMBER, ITEM_PREFIX, ITEM_INFO, ITEM_ACTIVE,
				ITEM_DICTIONARY_ID, ITEM_TURKISH, ITEM_ENGLISH, ITEM_ALBANIANKV, ITEM_ARABICEG, ITEM_ARABICJO, ITEM_ARABICSA, ITEM_AZERBAIJANIAZ, ITEM_BULGARIANBG, ITEM_FRENCHFR,
				ITEM_GEORGIANGE, ITEM_GERMANDE, ITEM_PERSIANIR, ITEM_ROMANIANRO, ITEM_RUSSIANRU, ITEM_TURKMENTM);
		public static final List<GridColumn> RESOURCE_ITEMS_STANDARD_COLUMNS = Arrays.asList(ITEM_ORDER_NUMBER, ITEM_TAG_NUMBER, ITEM_LEVEL_NUMBER, ITEM_PREFIX, ITEM_INFO,
				ITEM_ACTIVE, ITEM_STANDARD);
		public static final List<GridColumn> MESSAGE_COLUMNS = Arrays.asList(MESSAGE_TYPE, MESSAGE_CONSTANT, MESSAGE_MODULE);
		public static final List<GridColumn> DICTIONARY_COLUMNS = Arrays.asList(ITEM_TURKISH, ITEM_ENGLISH, ITEM_ALBANIANKV, ITEM_ARABICEG, ITEM_ARABICJO, ITEM_ARABICSA,
				ITEM_AZERBAIJANIAZ, ITEM_BULGARIANBG, ITEM_FRENCHFR, ITEM_GEORGIANGE, ITEM_GERMANDE, ITEM_PERSIANIR, ITEM_ROMANIANRO, ITEM_RUSSIANRU, ITEM_TURKMENTM);
		public static final List<GridColumn> ADVANCED_SEARCH_GRIDS = Arrays.asList(SEARCHED_RESOURCENR, SEARCHED_ORDER_NUMBER, SEARCHED_TAG_NUMBER, ITEM_TURKISH, ITEM_ENGLISH,
				ITEM_STANDARD, SEARCHED_LEVEL_NUMBER, SEARCHED_PREFIX, SEARCHED_INFO, SEARCHED_DICTIONARY_ID);

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

package com.lbs.re.ui.util;

public class Enums {
	public enum ViewMode {
		EDIT, VIEW, NEW;
	}

	public enum UIParameter {
		ID, MODE, RESOURCE_ID, RESOURCE_TYPE, SELECTED_LIST, ITEMS, SELECTION_MODE, FOLDER, FOLDER_TYPE, CREATED_USER, ENVIRONMENT;
	}

	public enum WindowSize {
		SMALLEST("window-smallest"), SMALL("window-small"), MEDIUM("window-medium"), BIG("window-big");
		private final String size;

		WindowSize(String size) {
			this.size = size;
		}

		public String getSize() {
			return size;
		}
	}

}

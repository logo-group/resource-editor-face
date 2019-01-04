package com.lbs.re.ui.util;

public class Enums {
	public enum ViewMode {
		EDIT, VIEW, NEW;
	}

	public enum UIParameter {
		ID,
		MODE,
		TESTCASE_ID,
		SNAPSHOT_DEFINITION,
		TEDAM_FILE_NAME,
		TESTSTEP,
		TEST_STEP_TYPE,
		SELECTED_LIST,
		TESTSTEPS,
		TESTSET,
		ITEMS,
		SELECTION_MODE,
		TESTCASE_TESTRUN,
		JOB_PARAMETER,
		TEDAM_FOLDER,
		FOLDER,
		FOLDER_TYPE,
		CREATED_USER,
		ENVIRONMENT;
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

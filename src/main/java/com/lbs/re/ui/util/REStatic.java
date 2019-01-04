package com.lbs.re.ui.util;

import java.util.HashMap;
import java.util.Map;

import com.lbs.re.ui.util.Enums.UIParameter;
import com.lbs.re.ui.util.Enums.ViewMode;

public class REStatic {
	public static Map<UIParameter, Object> getUIParameterMap() {
		return new HashMap<UIParameter, Object>();
	}

	/**
	 * It creates a new UIParameter map and sets the frequently used id and mode values.
	 *
	 * @param id
	 * @param mode
	 * @return
	 */
	public static Map<UIParameter, Object> getUIParameterMap(Integer id, ViewMode mode) {
		HashMap<UIParameter, Object> parameters = new HashMap<UIParameter, Object>();
		parameters.put(UIParameter.ID, id);
		parameters.put(UIParameter.MODE, mode);

		return parameters;
	}

}

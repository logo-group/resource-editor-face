/*
 * Copyright 2014-2019 Logo Business Solutions
 * (a.k.a. LOGO YAZILIM SAN. VE TIC. A.S)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.lbs.re.ui.view;

import java.io.Serializable;

import com.vaadin.data.TreeData;
import com.vaadin.data.provider.DataProviderListener;
import com.vaadin.data.provider.TreeDataProvider;

/**
 * Abstract wrapper class for ListDataProvider.
 */
public class AbstractTreeDataProvider<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Data provider.
	 */
	private TreeDataProvider<T> dataProvider;

	/**
	 * Gets data provider.
	 *
	 * @return Data provider.
	 */
	public TreeDataProvider<T> getTreeDataProvider() {
		return dataProvider;
	}

	public void buildTreeDataProvider(TreeData<T> items) {
		dataProvider = new TreeDataProvider<>(items);
	}

	public void refreshDataProviderByItems(TreeData<T> items) {

		// dataProvider.getTreeData().
		//
		// dataProvider.getTreeData().clear();
		// dataProvider.getTreeData().addItems(null, items));
		// dataProvider.getTreeData().add
		// dataProvider.refreshAll();
	}

	public void removeItem(T item) {
		dataProvider.getTreeData().removeItem(item);
	}

	public void addDataProviderListener(DataProviderListener<T> listener) {
		dataProvider.addDataProviderListener(listener);
	}

}

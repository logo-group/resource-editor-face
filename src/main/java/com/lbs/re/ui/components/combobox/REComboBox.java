package com.lbs.re.ui.components.combobox;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.NativeSelect;

public class REComboBox<T> extends NativeSelect<T> {

    private static final long serialVersionUID = 1L;

    public ListDataProvider<T> listDataProvider;

	public REComboBox() {
        setResponsive(true);
        setEmptySelectionAllowed(false);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setDataProvider(DataProvider<T, ?> dataProvider) {
        this.listDataProvider = (ListDataProvider<T>) dataProvider;
        super.setDataProvider(dataProvider);
    }

    public ListDataProvider<T> getListDataProvider() {
        return listDataProvider;
    }

}

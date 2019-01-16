package com.lbs.re.ui.view.dictionary;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus.ViewEventBus;

import com.lbs.re.app.routing.DatabaseEnvironment;
import com.lbs.re.app.routing.PreferredDatabaseSession;
import com.lbs.re.data.service.REUserService;
import com.lbs.re.data.service.ResourceitemService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReResourceitem;
import com.lbs.re.ui.navigation.NavigationManager;
import com.lbs.re.ui.util.Enums.UIParameter;
import com.lbs.re.ui.view.AbstractGridPresenter;
import com.lbs.re.ui.view.resourceitem.edit.ResourceItemDataProvider;
import com.lbs.re.util.Constants;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class DictionaryGridPresenter extends AbstractGridPresenter<ReResourceitem, ResourceitemService, DictionaryGridPresenter, DictionaryGridView> {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private PreferredDatabaseSession preferredDatabaseSession;

	ResourceItemDataProvider resourceItemDataProvider;

	@Autowired
	public DictionaryGridPresenter(ResourceItemDataProvider resourceItemDataProvider, NavigationManager navigationManager, ResourceitemService service, BeanFactory beanFactory,
			ViewEventBus viewEventBus, REUserService userService, PreferredDatabaseSession preferredDatabaseSession) {
		super(navigationManager, service, resourceItemDataProvider, beanFactory, viewEventBus, userService);
		this.preferredDatabaseSession = preferredDatabaseSession;
		this.resourceItemDataProvider = resourceItemDataProvider;
	}

	@Override
	public void enterView(Map<UIParameter, Object> parameters) {
		try {
			DatabaseEnvironment prefferedDb = preferredDatabaseSession.getPreferredDb();
			DatabaseEnvironment dictionaryDb = DatabaseEnvironment.valueOf(Constants.DICTIONARY_DATABASE);
			preferredDatabaseSession.setPreferredDb(dictionaryDb);
			List<ReResourceitem> itemList = resourceItemDataProvider.loadTurkishDataForDictionary(getService().getAll());
			getDataPovider().refreshDataProviderByItems(itemList);
			preferredDatabaseSession.setPreferredDb(prefferedDb);
		} catch (LocalizedException e) {
			e.printStackTrace();
		}
	}

}

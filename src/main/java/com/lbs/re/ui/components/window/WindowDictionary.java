package com.lbs.re.ui.components.window;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus.ViewEventBus;

import com.lbs.re.data.service.impl.language.LanguageServices;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReResourceitem;
import com.lbs.re.ui.components.CustomExceptions.REWindowNotAbleToOpenException;
import com.lbs.re.ui.components.basic.REWindow;
import com.lbs.re.ui.util.Enums.UIParameter;
import com.lbs.re.ui.util.Enums.WindowSize;
import com.lbs.re.ui.view.dictionary.DictionaryGridPresenter;
import com.lbs.re.ui.view.dictionary.DictionaryGridView;
import com.lbs.re.ui.view.resourceitem.edit.ResourceItemEditPresenter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

@SpringComponent
@ViewScope
public class WindowDictionary extends REWindow {

	private static final long serialVersionUID = 1L;

	private DictionaryGridView dictionaryGridView;
	private DictionaryGridPresenter dictionaryGridPresenter;

	private ResourceItemEditPresenter resourceItemEditPresenter;
	private LanguageServices languageServices;

	@Autowired
	public WindowDictionary(ViewEventBus viewEventBus, DictionaryGridView dictionaryGridView, DictionaryGridPresenter dictionaryGridPresenter,
			ResourceItemEditPresenter resourceItemEditPresenter, LanguageServices languageServices) {
		super(WindowSize.BIG, viewEventBus);
		this.dictionaryGridView = dictionaryGridView;
		this.dictionaryGridPresenter = dictionaryGridPresenter;
		this.resourceItemEditPresenter = resourceItemEditPresenter;
		this.languageServices = languageServices;
	}

	@Override
	public void open(Map<UIParameter, Object> parameters) throws LocalizedException, REWindowNotAbleToOpenException {
		UI.getCurrent().addWindow(this);
		center();
		setModal(true);
		focus();
		dictionaryGridPresenter.enterView(parameters);
		initWindow();
	}

	@Override
	protected Component buildContent() throws LocalizedException {
		return dictionaryGridView;
	}

	@Override
	protected String getHeader() {
		return getLocaleValue("view.resourceitem.header");
	}

	@Override
	protected void windowClose() {
		dictionaryGridPresenter.destroy();
	}

	@Override
	protected boolean readyToClose() {
		return true;
	}

	@Override
	public void publishCloseSuccessEvent() {
		ReResourceitem item = dictionaryGridView.getGrid().getSelectedItems().iterator().next();
		resourceItemEditPresenter.getView().getDictionaryId().setValue(item.getId().toString());
	}

}

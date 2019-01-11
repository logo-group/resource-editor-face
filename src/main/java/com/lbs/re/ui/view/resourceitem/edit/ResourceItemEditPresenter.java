package com.lbs.re.ui.view.resourceitem.edit;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.vaadin.spring.events.EventBus.ViewEventBus;

import com.lbs.re.data.service.REUserService;
import com.lbs.re.data.service.ResourceitemService;
import com.lbs.re.data.service.impl.language.LanguageServices;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReResourceitem;
import com.lbs.re.model.languages.ReAlbaniankv;
import com.lbs.re.model.languages.ReArabiceg;
import com.lbs.re.model.languages.ReArabicjo;
import com.lbs.re.model.languages.ReArabicsa;
import com.lbs.re.model.languages.ReAzerbaijaniaz;
import com.lbs.re.model.languages.ReBulgarianbg;
import com.lbs.re.model.languages.ReEnglishus;
import com.lbs.re.model.languages.ReFrenchfr;
import com.lbs.re.model.languages.ReGeorgiange;
import com.lbs.re.model.languages.ReGermande;
import com.lbs.re.model.languages.RePersianir;
import com.lbs.re.model.languages.ReRomanianro;
import com.lbs.re.model.languages.ReRussianru;
import com.lbs.re.model.languages.ReTurkishtr;
import com.lbs.re.model.languages.ReTurkmentm;
import com.lbs.re.ui.navigation.NavigationManager;
import com.lbs.re.ui.util.Enums.UIParameter;
import com.lbs.re.ui.util.Enums.ViewMode;
import com.lbs.re.ui.view.AbstractEditPresenter;
import com.lbs.re.ui.view.resource.ResourceGridView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewBeforeLeaveEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class ResourceItemEditPresenter extends AbstractEditPresenter<ReResourceitem, ResourceitemService, ResourceItemEditPresenter, ResourceItemEditView> {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private ResourceItemDataProvider resourceItemDataProvider;

	private LanguageServices languageServices;

	private ReTurkishtr turkishTr;
	private ReAlbaniankv albanianKv;
	private ReArabiceg arabicEg;
	private ReArabicjo arabicJo;
	private ReArabicsa arabicSa;
	private ReAzerbaijaniaz azerbaijaniAz;
	private ReBulgarianbg bulgarianBg;
	private ReEnglishus englishUs;
	private ReFrenchfr frenchFr;
	private ReGeorgiange georgianGe;
	private ReGermande germanDe;
	private RePersianir persianIr;
	private ReRomanianro romanianRo;
	private ReRussianru russianRu;
	private ReTurkmentm turkmenTm;

	@Autowired
	public ResourceItemEditPresenter(ViewEventBus viewEventBus, NavigationManager navigationManager, ResourceitemService resourceItemService, REUserService userService,
			BeanFactory beanFactory, BCryptPasswordEncoder passwordEncoder, ResourceItemDataProvider resourceItemDataProvider, LanguageServices languageServices) {
		super(viewEventBus, navigationManager, resourceItemService, ReResourceitem.class, beanFactory, userService);
		this.resourceItemDataProvider = resourceItemDataProvider;
		this.languageServices = languageServices;
	}

	@Override
	protected void enterView(Map<UIParameter, Object> parameters) throws LocalizedException {
		ReResourceitem resourceItem;
		if ((Integer) parameters.get(UIParameter.ID) == 0) {
			resourceItem = new ReResourceitem();
		} else {
			resourceItem = getService().getById((Integer) parameters.get(UIParameter.ID));
			if (resourceItem == null) {
				getView().showNotFound();
				return;
			}
		}
		refreshView(resourceItem, ViewMode.EDIT);
		if (resourceItem != null) {
			getLanguageStrings(resourceItem);
		}
		getTitleForHeader();
		organizeComponents(getView().getAccordion(), (ViewMode) parameters.get(UIParameter.MODE) == ViewMode.EDIT);
	}

	@PostConstruct
	public void init() {
		subscribeToEventBus();
	}

	private void getLanguageStrings(ReResourceitem resourceItem) {
		turkishTr = languageServices.getTurkishService().getLanguageByresourceitemref(resourceItem.getId());
		if (turkishTr != null) {
			getView().getTurkishTr().setValue(turkishTr.getResourcestr());
		}

		albanianKv = languageServices.getAlbanianService().getLanguageByresourceitemref(resourceItem.getId());
		if (albanianKv != null) {
			getView().getAlbanianKv().setValue(albanianKv.getResourcestr());
		}

		arabicEg = languageServices.getArabicEgService().getLanguageByresourceitemref(resourceItem.getId());
		if (arabicEg != null) {
			getView().getArabicEg().setValue(arabicEg.getResourcestr());
		}

		arabicJo = languageServices.getArabicJoService().getLanguageByresourceitemref(resourceItem.getId());
		if (arabicJo != null) {
			getView().getArabicJo().setValue(arabicJo.getResourcestr());
		}

		arabicSa = languageServices.getArabicSaService().getLanguageByresourceitemref(resourceItem.getId());
		if (arabicSa != null) {
			getView().getArabicSa().setValue(arabicSa.getResourcestr());
		}

		azerbaijaniAz = languageServices.getAzerbaijaniazService().getLanguageByresourceitemref(resourceItem.getId());
		if (azerbaijaniAz != null) {
			getView().getAzerbaijaniAz().setValue(azerbaijaniAz.getResourcestr());
		}

		bulgarianBg = languageServices.getBulgarianService().getLanguageByresourceitemref(resourceItem.getId());
		if (bulgarianBg != null) {
			getView().getBulgarianBg().setValue(bulgarianBg.getResourcestr());
		}

		englishUs = languageServices.getEnglishService().getLanguageByresourceitemref(resourceItem.getId());
		if (englishUs != null) {
			getView().getEnglishUs().setValue(englishUs.getResourcestr());
		}

		frenchFr = languageServices.getFrenchService().getLanguageByresourceitemref(resourceItem.getId());
		if (frenchFr != null) {
			getView().getFrenchFr().setValue(frenchFr.getResourcestr());
		}

		persianIr = languageServices.getPersianService().getLanguageByresourceitemref(resourceItem.getId());
		if (persianIr != null) {
			getView().getPersianIr().setValue(persianIr.getResourcestr());
		}

		georgianGe = languageServices.getGeorgianService().getLanguageByresourceitemref(resourceItem.getId());
		if (georgianGe != null) {
			getView().getGeoargianGe().setValue(georgianGe.getResourcestr());
		}

		germanDe = languageServices.getGermanService().getLanguageByresourceitemref(resourceItem.getId());
		if (germanDe != null) {
			getView().getGermanDe().setValue(germanDe.getResourcestr());
		}

		romanianRo = languageServices.getRomanianService().getLanguageByresourceitemref(resourceItem.getId());
		if (romanianRo != null) {
			getView().getRomanianRo().setValue(romanianRo.getResourcestr());
		}

		russianRu = languageServices.getRussianruService().getLanguageByresourceitemref(resourceItem.getId());
		if (russianRu != null) {
			getView().getRussianRu().setValue(russianRu.getResourcestr());
		}

		turkmenTm = languageServices.getTurkmenService().getLanguageByresourceitemref(resourceItem.getId());
		if (turkmenTm != null) {
			getView().getTurkmenTm().setValue(turkmenTm.getResourcestr());
		}
	}


	@Override
	public void beforeLeavingView(ViewBeforeLeaveEvent event) {
		setHasChanges(false);
		super.beforeLeavingView(event);
	}

	@Override
	protected void getTitleForHeader() {
		String title = getView().getTitle();
		getView().setTitle(title);
	}

	@Override
	protected Class<? extends View> getGridView() {
		return ResourceGridView.class;
	}

}

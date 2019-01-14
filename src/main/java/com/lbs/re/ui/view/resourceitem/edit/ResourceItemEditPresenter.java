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
import com.lbs.re.model.ReLanguageTable;
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

	ReResourceitem resourceItem;

	@Autowired
	public ResourceItemEditPresenter(ViewEventBus viewEventBus, NavigationManager navigationManager, ResourceitemService resourceItemService, REUserService userService,
			BeanFactory beanFactory, BCryptPasswordEncoder passwordEncoder, LanguageServices languageServices) {
		super(viewEventBus, navigationManager, resourceItemService, ReResourceitem.class, beanFactory, userService);
		this.languageServices = languageServices;
	}

	@Override
	public void enterView(Map<UIParameter, Object> parameters) throws LocalizedException {
		subscribeToEventBus();
		if ((Integer) parameters.get(UIParameter.ID) == 0) {
			resourceItem = new ReResourceitem();
			resourceItem.setResourceref(Integer.parseInt(parameters.get(UIParameter.RESOURCE_ID).toString()));
		} else {
			resourceItem = getService().getById((Integer) parameters.get(UIParameter.ID));
			if (resourceItem == null) {
				getView().showNotFound();
				return;
			}
		}
		refreshView(resourceItem, ViewMode.EDIT);
		getLanguageFields(resourceItem);
		getTitleForHeader();
		organizeComponents(getView().getAccordion(), (ViewMode) parameters.get(UIParameter.MODE) == ViewMode.EDIT);
	}

	@PostConstruct
	public void init() {
		subscribeToEventBus();
	}

	@Override
	public ReResourceitem save(ReResourceitem item) throws LocalizedException {
		return super.save(item);
	}

	public void checkLanguageFields(ReResourceitem item) throws LocalizedException {
		if (!getView().getTurkishTr().getValue().isEmpty()) {
			turkishTr = languageServices.getTurkishService().getLanguageByresourceitemref(item.getId());
			if (turkishTr == null) {
				turkishTr = new ReTurkishtr();
			}
			persistLanguage(turkishTr, item);
		}
		if (!getView().getEnglishUs().getValue().isEmpty()) {
			englishUs = languageServices.getEnglishService().getLanguageByresourceitemref(item.getId());
			if (englishUs == null) {
				englishUs = new ReEnglishus();
			}
			persistLanguage(englishUs, item);
		}
		if (!getView().getAlbanianKv().getValue().isEmpty()) {
			albanianKv = languageServices.getAlbanianService().getLanguageByresourceitemref(item.getId());
			if (albanianKv == null) {
				albanianKv = new ReAlbaniankv();
			}
			persistLanguage(albanianKv, item);
		}
		if (!getView().getArabicEg().getValue().isEmpty()) {
			arabicEg = languageServices.getArabicEgService().getLanguageByresourceitemref(item.getId());
			if (arabicEg == null) {
				arabicEg = new ReArabiceg();
			}
			persistLanguage(arabicEg, item);
		}
		if (!getView().getArabicJo().getValue().isEmpty()) {
			arabicJo = languageServices.getArabicJoService().getLanguageByresourceitemref(item.getId());
			if (arabicJo == null) {
				arabicJo = new ReArabicjo();
			}
			persistLanguage(arabicJo, item);
		}
		if (!getView().getArabicSa().getValue().isEmpty()) {
			arabicSa = languageServices.getArabicSaService().getLanguageByresourceitemref(item.getId());
			if (arabicSa == null) {
				arabicSa = new ReArabicsa();
			}
			persistLanguage(arabicSa, item);
		}
		if (!getView().getAzerbaijaniAz().getValue().isEmpty()) {
			azerbaijaniAz = languageServices.getAzerbaijaniazService().getLanguageByresourceitemref(item.getId());
			if (azerbaijaniAz == null) {
				azerbaijaniAz = new ReAzerbaijaniaz();
			}
			persistLanguage(azerbaijaniAz, item);
		}
		if (!getView().getBulgarianBg().getValue().isEmpty()) {
			bulgarianBg = languageServices.getBulgarianService().getLanguageByresourceitemref(item.getId());
			if (bulgarianBg == null) {
				bulgarianBg = new ReBulgarianbg();
			}
			persistLanguage(bulgarianBg, item);
		}
		if (!getView().getFrenchFr().getValue().isEmpty()) {
			frenchFr = languageServices.getFrenchService().getLanguageByresourceitemref(item.getId());
			if (frenchFr == null) {
				frenchFr = new ReFrenchfr();
			}
			persistLanguage(frenchFr, item);
		}
		if (!getView().getGeorgianGe().getValue().isEmpty()) {
			georgianGe = languageServices.getGeorgianService().getLanguageByresourceitemref(item.getId());
			if (georgianGe == null) {
				georgianGe = new ReGeorgiange();
			}
			persistLanguage(georgianGe, item);
		}
		if (!getView().getGermanDe().getValue().isEmpty()) {
			germanDe = languageServices.getGermanService().getLanguageByresourceitemref(item.getId());
			if (germanDe == null) {
				germanDe = new ReGermande();
			}
			persistLanguage(germanDe, item);
		}
		if (!getView().getPersianIr().getValue().isEmpty()) {
			persianIr = languageServices.getPersianService().getLanguageByresourceitemref(item.getId());
			if (persianIr == null) {
				persianIr = new RePersianir();
			}
			persistLanguage(persianIr, item);
		}
		if (!getView().getRomanianRo().getValue().isEmpty()) {
			romanianRo = languageServices.getRomanianService().getLanguageByresourceitemref(item.getId());
			if (romanianRo == null) {
				romanianRo = new ReRomanianro();
			}
			persistLanguage(romanianRo, item);
		}
		if (!getView().getRussianRu().getValue().isEmpty()) {
			russianRu = languageServices.getRussianruService().getLanguageByresourceitemref(item.getId());
			if (russianRu == null) {
				russianRu = new ReRussianru();
			}
			persistLanguage(russianRu, item);
		}
		if (!getView().getTurkmenTm().getValue().isEmpty()) {
			turkmenTm = languageServices.getTurkmenService().getLanguageByresourceitemref(item.getId());
			if (turkmenTm == null) {
				turkmenTm = new ReTurkmentm();
			}
			persistLanguage(turkmenTm, item);
		}
	}

	private <T extends ReLanguageTable> void persistLanguage(T language, ReResourceitem item) throws LocalizedException {
		if (item.getResourceref() != null) {
			language.setResourceref(item.getResourceref());
			language.setReResourceitem(item);
			language.setResourceitemref(item.getId());

			if (language instanceof ReTurkishtr) {
				String trValue = getView().getTurkishTr().getValue();
				if (language.getResourcestr() != null) {
					if (!language.getResourcestr().equals(trValue)) {
						language.setResourcestr(trValue);
						languageServices.getTurkishService().save((ReTurkishtr) language);
					}
				} else {
					language.setResourcestr(trValue);
					languageServices.getTurkishService().save((ReTurkishtr) language);
				}

			}

			if (language instanceof ReEnglishus) {
				String enValue = getView().getEnglishUs().getValue();
				if (language.getResourcestr() != null) {
					if (!language.getResourcestr().equals(enValue)) {
						language.setResourcestr(enValue);
						languageServices.getEnglishService().save((ReEnglishus) language);
					}
				} else {
					language.setResourcestr(enValue);
					languageServices.getEnglishService().save((ReEnglishus) language);
				}
			}

			if (language instanceof ReAlbaniankv) {
				String kvValue = getView().getAlbanianKv().getValue();
				if (language.getResourcestr() != null) {
					if (!language.getResourcestr().equals(kvValue)) {
						language.setResourcestr(kvValue);
						languageServices.getAlbanianService().save((ReAlbaniankv) language);
					}
				} else {
					language.setResourcestr(kvValue);
					languageServices.getAlbanianService().save((ReAlbaniankv) language);
				}
			}

			if (language instanceof ReArabiceg) {
				String egValue = getView().getArabicEg().getValue();
				if (language.getResourcestr() != null) {
					if (!language.getResourcestr().equals(egValue)) {
						language.setResourcestr(egValue);
						languageServices.getArabicEgService().save((ReArabiceg) language);
					}
				} else {
					language.setResourcestr(egValue);
					languageServices.getArabicEgService().save((ReArabiceg) language);
				}
			}

			if (language instanceof ReArabicjo) {
				String joValue = getView().getArabicJo().getValue();
				if (language.getResourcestr() != null) {
					if (!language.getResourcestr().equals(joValue)) {
						language.setResourcestr(joValue);
						languageServices.getArabicJoService().save((ReArabicjo) language);
					}
				} else {
					language.setResourcestr(joValue);
					languageServices.getArabicJoService().save((ReArabicjo) language);
				}
			}

			if (language instanceof ReArabicsa) {
				String saValue = getView().getArabicSa().getValue();
				if (language.getResourcestr() != null) {
					if (!language.getResourcestr().equals(saValue)) {
						language.setResourcestr(saValue);
						languageServices.getArabicSaService().save((ReArabicsa) language);
					}
				} else {
					language.setResourcestr(saValue);
					languageServices.getArabicSaService().save((ReArabicsa) language);
				}
			}

			if (language instanceof ReAzerbaijaniaz) {
				String azValue = getView().getAzerbaijaniAz().getValue();
				if (language.getResourcestr() != null) {
					if (!language.getResourcestr().equals(azValue)) {
						language.setResourcestr(azValue);
						languageServices.getAzerbaijaniazService().save((ReAzerbaijaniaz) language);
					}
				} else {
					language.setResourcestr(azValue);
					languageServices.getAzerbaijaniazService().save((ReAzerbaijaniaz) language);
				}
			}

			if (language instanceof ReBulgarianbg) {
				String bgValue = getView().getBulgarianBg().getValue();
				if (language.getResourcestr() != null) {
					if (!language.getResourcestr().equals(bgValue)) {
						language.setResourcestr(bgValue);
						languageServices.getBulgarianService().save((ReBulgarianbg) language);
					}
				} else {
					language.setResourcestr(bgValue);
					languageServices.getBulgarianService().save((ReBulgarianbg) language);
				}
			}

			if (language instanceof ReFrenchfr) {
				String frValue = getView().getFrenchFr().getValue();
				if (language.getResourcestr() != null) {
					if (!language.getResourcestr().equals(frValue)) {
						language.setResourcestr(frValue);
						languageServices.getFrenchService().save((ReFrenchfr) language);
					}
				} else {
					language.setResourcestr(frValue);
					languageServices.getFrenchService().save((ReFrenchfr) language);
				}

			}

			if (language instanceof ReGeorgiange) {
				String geValue = getView().getGeorgianGe().getValue();
				if (language.getResourcestr() != null) {
					if (!language.getResourcestr().equals(geValue)) {
						language.setResourcestr(geValue);
						languageServices.getGeorgianService().save((ReGeorgiange) language);
					}
				} else {
					language.setResourcestr(geValue);
					languageServices.getGeorgianService().save((ReGeorgiange) language);
				}
			}

			if (language instanceof ReGermande) {
				String deValue = getView().getGermanDe().getValue();
				if (language.getResourcestr() != null) {
					if (!language.getResourcestr().equals(deValue)) {
						language.setResourcestr(deValue);
						languageServices.getGermanService().save((ReGermande) language);
					}
				} else {
					language.setResourcestr(deValue);
					languageServices.getGermanService().save((ReGermande) language);
				}
			}

			if (language instanceof RePersianir) {
				String irValue = getView().getPersianIr().getValue();
				if (language.getResourcestr() != null) {
					if (!language.getResourcestr().equals(irValue)) {
						language.setResourcestr(irValue);
						languageServices.getPersianService().save((RePersianir) language);
					}
				} else {
					language.setResourcestr(irValue);
					languageServices.getPersianService().save((RePersianir) language);
				}
			}

			if (language instanceof ReRomanianro) {
				String roValue = getView().getRomanianRo().getValue();
				if (language.getResourcestr() != null) {
					if (!language.getResourcestr().equals(roValue)) {
						language.setResourcestr(roValue);
						languageServices.getRomanianService().save((ReRomanianro) language);
					}
				} else {
					language.setResourcestr(roValue);
					languageServices.getRomanianService().save((ReRomanianro) language);
				}
			}

			if (language instanceof ReRussianru) {
				String ruValue = getView().getRussianRu().getValue();
				if (language.getResourcestr() != null) {
					if (!language.getResourcestr().equals(ruValue)) {
						language.setResourcestr(ruValue);
						languageServices.getRussianruService().save((ReRussianru) language);
					}
				} else {
					language.setResourcestr(ruValue);
					languageServices.getRussianruService().save((ReRussianru) language);
				}
			}

			if (language instanceof ReTurkmentm) {
				String tmValue = getView().getTurkmenTm().getValue();
				if (language.getResourcestr() != null) {
					if (!language.getResourcestr().equals(tmValue)) {
						language.setResourcestr(tmValue);
						languageServices.getTurkmenService().save((ReTurkmentm) language);
					}
				} else {
					language.setResourcestr(tmValue);
					languageServices.getTurkmenService().save((ReTurkmentm) language);
				}
			}

		}
	}

	private void getLanguageFields(ReResourceitem resourceItem) {
		if (resourceItem.getId() != 0) {
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
				getView().getGeorgianGe().setValue(georgianGe.getResourcestr());
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
	}

	@Override
	public void beforeLeavingView(ViewBeforeLeaveEvent event) {
		getLanguageFields(resourceItem);
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

	public ReResourceitem getResourceItem() {
		return resourceItem;
	}

}

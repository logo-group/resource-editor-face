package com.lbs.re.ui.view.resourceitem.edit;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.vaadin.spring.events.EventBus.ViewEventBus;

import com.lbs.re.app.security.SecurityUtils;
import com.lbs.re.data.service.REUserService;
import com.lbs.re.data.service.ResourceService;
import com.lbs.re.data.service.ResourceitemService;
import com.lbs.re.data.service.StandardService;
import com.lbs.re.data.service.impl.language.LanguageServices;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReLanguageTable;
import com.lbs.re.model.ReResource;
import com.lbs.re.model.ReResourceitem;
import com.lbs.re.model.ReStandard;
import com.lbs.re.model.ReUser;
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
import com.lbs.re.ui.AppUI;
import com.lbs.re.ui.components.basic.RETextArea;
import com.lbs.re.ui.dialog.ConfirmationListener;
import com.lbs.re.ui.dialog.REDialog;
import com.lbs.re.ui.navigation.NavigationManager;
import com.lbs.re.ui.util.Enums.UIParameter;
import com.lbs.re.ui.util.Enums.ViewMode;
import com.lbs.re.ui.util.RENotification;
import com.lbs.re.ui.util.RENotification.NotifyType;
import com.lbs.re.ui.util.REStatic;
import com.lbs.re.ui.view.AbstractEditPresenter;
import com.lbs.re.ui.view.resource.ResourceGridView;
import com.lbs.re.util.EnumsV2.Languages;
import com.lbs.re.util.EnumsV2.ResourceType;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewBeforeLeaveEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Component;

@SpringComponent
@ViewScope
public class ResourceItemEditPresenter extends AbstractEditPresenter<ReResourceitem, ResourceitemService, ResourceItemEditPresenter, ResourceItemEditView> {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private LanguageServices languageServices;

	private StandardService standardService;

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
	private ReStandard standard;

	private ReResourceitem resourceItem;
	private ResourceService resourceService;

	private ReUser user = null;

	@Autowired
	public ResourceItemEditPresenter(ViewEventBus viewEventBus, NavigationManager navigationManager, ResourceitemService resourceItemService, REUserService userService,
			BeanFactory beanFactory, BCryptPasswordEncoder passwordEncoder, LanguageServices languageServices, ResourceService resourceService, StandardService standardService) {
		super(viewEventBus, navigationManager, resourceItemService, ReResourceitem.class, beanFactory, userService);
		this.languageServices = languageServices;
		this.resourceService = resourceService;
		this.standardService = standardService;
		try {
			this.user = SecurityUtils.getCurrentUser(userService).getReUser();
		} catch (LocalizedException e) {
			RENotification.showNotification(e.getMessage(), NotifyType.ERROR);
		}
	}

	@Override
	public void enterView(Map<UIParameter, Object> parameters) throws LocalizedException {
		subscribeToEventBus();
		if ((Integer) parameters.get(UIParameter.ID) == 0) {
			resourceItem = new ReResourceitem();
			resourceItem.setResourceref(Integer.parseInt(parameters.get(UIParameter.RESOURCE_ID).toString()));
		} else {
			resourceItem = getService().getById((Integer) parameters.get(UIParameter.ID));
			ResourceType resourceType = (ResourceType) parameters.get(UIParameter.RESOURCE_TYPE);
			if (resourceItem == null) {
				getView().showNotFound();
				return;
			}
			organizeAccordionsByResourceType(resourceType);
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
		super.save(item);
		ReResource reResource = resourceService.getById(item.getResourceref());
		reResource.orderResourceItems();
		resourceService.save(reResource);
		return item;
	}

	public void checkLanguageFields(ReResourceitem item) throws LocalizedException {
		persistLanguage(turkishTr, item);
		persistLanguage(englishUs, item);
		persistLanguage(albanianKv, item);
		persistLanguage(arabicEg, item);
		persistLanguage(arabicJo, item);
		persistLanguage(arabicSa, item);
		persistLanguage(azerbaijaniAz, item);
		persistLanguage(bulgarianBg, item);
		persistLanguage(frenchFr, item);
		persistLanguage(georgianGe, item);
		persistLanguage(germanDe, item);
		persistLanguage(persianIr, item);
		persistLanguage(romanianRo, item);
		persistLanguage(russianRu, item);
		persistLanguage(turkmenTm, item);
		persistStandard(standard, item);
	}

	private void persistStandard(ReStandard standard, ReResourceitem item) throws LocalizedException {
		if (item.getResourceref() != null && standard != null) {
			standard.setResourceItem(item);
			if (standard.getId() == 0) {
				standard.setCreatedon(LocalDateTime.now());
				standard.setCreatedby(SecurityUtils.getCurrentUser(getUserService()).getReUser().getId());
			} else {
				standard.setModifiedon(LocalDateTime.now());
				standard.setModifiedby(SecurityUtils.getCurrentUser(getUserService()).getReUser().getId());
			}
			String standardValue = getView().getStandard().getValue();
			if (standard.getResourceStr() != null) {
				if (!standard.getResourceStr().equals(standardValue)) {
					standard.setResourceStr(standardValue);
					standardService.save(standard);
				}
			} else {
				standard.setResourceStr(standardValue);
				standardService.save(standard);
			}
		}
	}

	private <T extends ReLanguageTable> void persistLanguage(T language, ReResourceitem item) throws LocalizedException {
		if (item.getResourceref() != null && language != null) {
			language.setResourceref(item.getResourceref());
			language.setReResourceitem(item);
			language.setResourceitemref(item.getId());
			if (language.getId() == 0) {
				language.setCreatedon(LocalDateTime.now());
				language.setCreatedby(SecurityUtils.getCurrentUser(getUserService()).getReUser().getId());
			} else {
				language.setModifiedon(LocalDateTime.now());
				language.setModifiedby(SecurityUtils.getCurrentUser(getUserService()).getReUser().getId());
			}

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

			else if (language instanceof ReEnglishus) {
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

			else if (language instanceof ReAlbaniankv) {
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

			else if (language instanceof ReArabiceg) {
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

			else if (language instanceof ReArabicjo) {
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

			else if (language instanceof ReArabicsa) {
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

			else if (language instanceof ReAzerbaijaniaz) {
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

			else if (language instanceof ReBulgarianbg) {
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

			else if (language instanceof ReFrenchfr) {
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

			else if (language instanceof ReGeorgiange) {
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

			else if (language instanceof ReGermande) {
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

			else if (language instanceof RePersianir) {
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

			else if (language instanceof ReRomanianro) {
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

			else if (language instanceof ReRussianru) {
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

			else if (language instanceof ReTurkmentm) {
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

			standard = standardService.getStandardByResourceItemref(resourceItem.getId());
			if (standard != null) {
				getView().getStandard().setValue(standard.getResourceStr());
			}

		}
	}

	public void prepareDictionaryWindow(ReResourceitem item, ViewMode mode) throws LocalizedException {
		Map<UIParameter, Object> windowParameters = REStatic.getUIParameterMap(item.getId(), ViewMode.VIEW);
		windowParameters.put(UIParameter.RESOURCE_ID, getItem().getId());
		getView().openDictionaryWindow(windowParameters);
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

	public ReUser getUser() {
		return user;
	}

	public void confirmDelete(RETextArea area, Languages language) {
		REDialog.confirm(AppUI.getCurrent(), new ConfirmationListener() {

			@Override
			public void onConfirm() {
				area.clear();
				try {
					switch (language) {
					case ALBANIAN:
						languageServices.getAlbanianService().delete(albanianKv);
						break;
					case ARABICEG:
						languageServices.getArabicEgService().delete(arabicEg);
						break;
					case ARABISJO:
						languageServices.getArabicJoService().delete(arabicJo);
						break;
					case ARABICSA:
						languageServices.getArabicSaService().delete(arabicSa);
						break;
					case AZARBAIJAN:
						languageServices.getAzerbaijaniazService().delete(azerbaijaniAz);
						break;
					case BULGARIAN:
						languageServices.getBulgarianService().delete(bulgarianBg);
						break;
					case ENGLISH:
						languageServices.getEnglishService().delete(englishUs);
						break;
					case FRENCH:
						languageServices.getFrenchService().delete(frenchFr);
						break;
					case GEORGIAN:
						languageServices.getGeorgianService().delete(georgianGe);
						break;
					case GERMAN:
						languageServices.getGermanService().delete(germanDe);
						break;
					case PERSIAN:
						languageServices.getPersianService().delete(persianIr);
						break;
					case ROMANIAN:
						languageServices.getRomanianService().delete(romanianRo);
						break;
					case RUSSIAN:
						languageServices.getRussianruService().delete(russianRu);
						break;
					case TURKISH:
						languageServices.getTurkishService().delete(turkishTr);
						break;
					case TURKMEN:
						languageServices.getTurkmenService().delete(turkmenTm);
						break;
					case STANDARD:
						standardService.delete(standard);
					}
				} catch (LocalizedException e) {
					getView().logError(e);
				}
			}

			@Override
			public void onCancel() {
			}

		}, getLocaleValue("confirm.message.delete"), getLocaleValue("general.button.ok"), getLocaleValue("general.button.cancel"));
	}

	private void organizeAccordionsByResourceType(ResourceType resourceType) {
		Iterator<Component> iterator = getView().getAccordion().iterator();
		int componentCount = getView().getAccordion().getComponentCount();
		int count = 0;
		while (iterator.hasNext()) {
			Component component = iterator.next();
			if (resourceType.equals(ResourceType.NONLOCALIZABLE) && componentCount == 4 && count == 1) {
				getView().getAccordion().removeComponent(component);
				break;
			} else if (resourceType.equals(ResourceType.LOCALIZABLE) && componentCount == 4 && count == 2) {
				getView().getAccordion().removeComponent(component);
				break;
			}
			count++;
		}
	}
}

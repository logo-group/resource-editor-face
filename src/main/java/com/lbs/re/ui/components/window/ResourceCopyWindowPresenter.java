package com.lbs.re.ui.components.window;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lbs.re.app.security.SecurityUtils;
import com.lbs.re.data.service.REUserService;
import com.lbs.re.data.service.ResourceService;
import com.lbs.re.data.service.StandardService;
import com.lbs.re.data.service.impl.language.LanguageServices;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReLanguageTable;
import com.lbs.re.model.ReResource;
import com.lbs.re.model.ReResourceGroup;
import com.lbs.re.model.ReResourceitem;
import com.lbs.re.model.ReStandard;
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
import com.lbs.re.ui.components.CustomExceptions.REWindowNotAbleToOpenException;
import com.lbs.re.ui.view.AbstractWindowPresenter;
import com.lbs.re.ui.view.resource.ResourceGridPresenter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class ResourceCopyWindowPresenter extends AbstractWindowPresenter<WindowResourceCopy> {

	private static final long serialVersionUID = 1L;

	private ResourceService resourceService;
	private REUserService userService;
	private StandardService standardService;
	private LanguageServices languageServices;
	private ResourceGridPresenter resourceGridPresenter;
	private ReResource resource;
	private ReResource tempResource;

	@Autowired
	public ResourceCopyWindowPresenter(ResourceService resourceService, REUserService userService, LanguageServices languageServices, StandardService standardService,
			ResourceGridPresenter resourceGridPresenter) {
		this.resourceService = resourceService;
		this.userService = userService;
		this.languageServices = languageServices;
		this.standardService = standardService;
		this.resourceGridPresenter = resourceGridPresenter;
	}

	@Override
	public void fillComponentsWithValues() throws REWindowNotAbleToOpenException, LocalizedException {
	}

	public void copyResource(int tagStart, int tagEnd, Boolean tagAll, ReResourceGroup reResourceGroup, int resourceNumber) {
		try {
			ReResource copiedResource = resource.copyResource();
			List<ReResourceitem> itemList = resource.getReResourceitem();
			copiedResource.setResourcenr(resourceNumber);
			copiedResource.setResourcegroup(reResourceGroup);
			copiedResource.setCreatedby(SecurityUtils.getCurrentUser(userService).getReUser().getId());
			copiedResource.setCreatedon(LocalDateTime.now());

			// create empty arrays for copied field
			List<ReTurkishtr> copiedTrList = new ArrayList<ReTurkishtr>();
			List<ReAlbaniankv> copiedkvList = new ArrayList<ReAlbaniankv>();
			List<ReArabiceg> copiedegList = new ArrayList<ReArabiceg>();
			List<ReArabicjo> copiedjoList = new ArrayList<ReArabicjo>();
			List<ReArabicsa> copiedsaList = new ArrayList<ReArabicsa>();
			List<ReAzerbaijaniaz> copiedazList = new ArrayList<ReAzerbaijaniaz>();
			List<ReBulgarianbg> copiedbgList = new ArrayList<ReBulgarianbg>();
			List<ReEnglishus> copiedusList = new ArrayList<ReEnglishus>();
			List<ReFrenchfr> copiedfrList = new ArrayList<ReFrenchfr>();
			List<ReGeorgiange> copiedgeList = new ArrayList<ReGeorgiange>();
			List<ReGermande> copieddeList = new ArrayList<ReGermande>();
			List<RePersianir> copiedirList = new ArrayList<RePersianir>();
			List<ReRomanianro> copiedroList = new ArrayList<ReRomanianro>();
			List<ReRussianru> copiedruList = new ArrayList<ReRussianru>();
			List<ReTurkmentm> copiedtmList = new ArrayList<ReTurkmentm>();
			List<ReStandard> copiedstandardList = new ArrayList<ReStandard>();

			// get languages list of orginal resource
			List<ReTurkishtr> turkishList = languageServices.getTurkishService().getLanguageListByresourceref(resource.getId());
			List<ReAlbaniankv> albanianList = languageServices.getAlbanianService().getLanguageListByresourceref(resource.getId());
			List<ReArabiceg> arabicEgList = languageServices.getArabicEgService().getLanguageListByresourceref(resource.getId());
			List<ReArabicsa> arabicSaList = languageServices.getArabicSaService().getLanguageListByresourceref(resource.getId());
			List<ReArabicjo> arabicJoList = languageServices.getArabicJoService().getLanguageListByresourceref(resource.getId());
			List<ReAzerbaijaniaz> azList = languageServices.getAzerbaijaniazService().getLanguageListByresourceref(resource.getId());
			List<ReBulgarianbg> bgList = languageServices.getBulgarianService().getLanguageListByresourceref(resource.getId());
			List<ReEnglishus> usList = languageServices.getEnglishService().getLanguageListByresourceref(resource.getId());
			List<ReFrenchfr> frList = languageServices.getFrenchService().getLanguageListByresourceref(resource.getId());
			List<ReGeorgiange> geList = languageServices.getGeorgianService().getLanguageListByresourceref(resource.getId());
			List<ReGermande> deList = languageServices.getGermanService().getLanguageListByresourceref(resource.getId());
			List<RePersianir> irList = languageServices.getPersianService().getLanguageListByresourceref(resource.getId());
			List<ReRomanianro> roList = languageServices.getRomanianService().getLanguageListByresourceref(resource.getId());
			List<ReRussianru> ruList = languageServices.getRussianruService().getLanguageListByresourceref(resource.getId());
			List<ReTurkmentm> tmList = languageServices.getTurkmenService().getLanguageListByresourceref(resource.getId());
			List<ReStandard> stList = standardService.getStandardListByResourceref(resource.getId());

			// copy resource items
			for (ReResourceitem item : resource.getReResourceitem()) {
				ReResourceitem copiedResourceItem = item.copyResourceItem(resource);
				if (!tagAll) {
					if (copiedResourceItem.getTagnr() >= tagStart && copiedResourceItem.getTagnr() <= tagEnd) {
						setCreatedItemInformations(copiedResourceItem);
						copiedResource.getReResourceitem().add(copiedResourceItem);
					}
				} else {
					setCreatedItemInformations(copiedResourceItem);
					copiedResource.getReResourceitem().add(copiedResourceItem);
				}
			}
			tempResource = resourceService.save(copiedResource);

			// copy resourceitem languages
			for (ReResourceitem copiedResourceItem : tempResource.getReResourceitem()) {
				if (turkishList != null) {
					copyTurkish(turkishList, itemList, copiedTrList, copiedResourceItem);
				}
				if (albanianList != null) {
					copyAlbeninan(albanianList, itemList, copiedkvList, copiedResourceItem);
				}
				if (arabicEgList != null) {
					copyArabicEg(arabicEgList, itemList, copiedegList, copiedResourceItem);
				}
				if (arabicSaList != null) {
					copyArabicSa(arabicSaList, itemList, copiedsaList, copiedResourceItem);
				}
				if (arabicJoList != null) {
					copyArabicJo(arabicJoList, itemList, copiedjoList, copiedResourceItem);
				}
				if (azList != null) {
					copyAzerbaijaniAz(azList, itemList, copiedazList, copiedResourceItem);
				}
				if (bgList != null) {
					copyBulgarianBg(bgList, itemList, copiedbgList, copiedResourceItem);
				}
				if (usList != null) {
					copyEnglishUs(usList, itemList, copiedusList, copiedResourceItem);
				}
				if (frList != null) {
					copyFrenchFr(frList, itemList, copiedfrList, copiedResourceItem);
				}
				if (geList != null) {
					copyGeorgianGe(geList, itemList, copiedgeList, copiedResourceItem);
				}
				if (deList != null) {
					copyGermanDe(deList, itemList, copieddeList, copiedResourceItem);
				}
				if (irList != null) {
					copyPersianIr(irList, itemList, copiedirList, copiedResourceItem);
				}
				if (roList != null) {
					copyRomanianRo(roList, itemList, copiedroList, copiedResourceItem);
				}
				if (ruList != null) {
					copyRussianRo(ruList, itemList, copiedruList, copiedResourceItem);
				}
				if (tmList != null) {
					copyTurkmenTm(tmList, itemList, copiedtmList, copiedResourceItem);
				}
				if (stList != null) {
					copyStandard(stList, itemList, copiedstandardList, copiedResourceItem);
				}
			}

			// save languages and standard
			if (!copiedTrList.isEmpty()) {
				languageServices.getTurkishService().save(copiedTrList);
			}
			if (!copiedusList.isEmpty()) {
				languageServices.getEnglishService().save(copiedusList);
			}
			if (!copiedkvList.isEmpty()) {
				languageServices.getAlbanianService().save(copiedkvList);
			}
			if (!copiedegList.isEmpty()) {
				languageServices.getArabicEgService().save(copiedegList);
			}
			if (!copiedjoList.isEmpty()) {
				languageServices.getArabicJoService().save(copiedjoList);
			}
			if (!copiedsaList.isEmpty()) {
				languageServices.getArabicSaService().save(copiedsaList);
			}
			if (!copiedazList.isEmpty()) {
				languageServices.getAzerbaijaniazService().save(copiedazList);
			}
			if (!copiedbgList.isEmpty()) {
				languageServices.getBulgarianService().save(copiedbgList);
			}
			if (!copiedfrList.isEmpty()) {
				languageServices.getFrenchService().save(copiedfrList);
			}
			if (!copiedgeList.isEmpty()) {
				languageServices.getGeorgianService().save(copiedgeList);
			}
			if (!copieddeList.isEmpty()) {
				languageServices.getGermanService().save(copieddeList);
			}
			if (!copiedirList.isEmpty()) {
				languageServices.getPersianService().save(copiedirList);
			}
			if (!copiedroList.isEmpty()) {
				languageServices.getRomanianService().save(copiedroList);
			}
			if (!copiedruList.isEmpty()) {
				languageServices.getRussianruService().save(copiedruList);
			}
			if (!copiedtmList.isEmpty()) {
				languageServices.getTurkmenService().save(copiedtmList);
			}
			if (!copiedstandardList.isEmpty()) {
				standardService.save(copiedstandardList);
			}
		} catch (LocalizedException e) {
			e.printStackTrace();
		}
	}

	private <T extends ReLanguageTable> void setLanguageCreatedInformations(T language) {
		try {
			language.setCreatedby(SecurityUtils.getCurrentUser(userService).getReUser().getId());
			language.setCreatedon(LocalDateTime.now());
		} catch (LocalizedException e) {
			e.printStackTrace();
		}
	}

	private void setCreatedItemInformations(ReResourceitem item) {
		try {
			item.setCreatedby(SecurityUtils.getCurrentUser(userService).getReUser().getId());
			item.setCreatedon(LocalDateTime.now());
		} catch (LocalizedException e) {
			e.printStackTrace();
		}
	}

	private void copyTurkish(List<ReTurkishtr> turkishList, List<ReResourceitem> itemList, List<ReTurkishtr> copiedTurkishList, ReResourceitem copiedResourceItem) {
		for (ReResourceitem item : itemList) {
			for (ReTurkishtr tr : turkishList) {
				if (tr.getResourceitemref().equals(item.getId()) && item.getTagnr().equals(copiedResourceItem.getTagnr())) {
					ReTurkishtr copiedTurkish = tr.cloneTurkish(copiedResourceItem);
					setLanguageCreatedInformations(copiedTurkish);
					copiedTurkishList.add(copiedTurkish);
					break;
				}
			}
		}
	}

	private void copyAlbeninan(List<ReAlbaniankv> albanianList, List<ReResourceitem> itemList, List<ReAlbaniankv> copiedAlbeninanList, ReResourceitem copiedResourceItem) {
		for (ReResourceitem item : itemList) {
			for (ReAlbaniankv kv : albanianList) {
				if (kv.getResourceitemref().equals(item.getId()) && item.getTagnr().equals(copiedResourceItem.getTagnr())) {
					ReAlbaniankv copiedAlbanian = kv.cloneAlbaniankv(copiedResourceItem);
					setLanguageCreatedInformations(copiedAlbanian);
					copiedAlbeninanList.add(copiedAlbanian);
					break;
				}
			}
		}
	}

	private void copyArabicEg(List<ReArabiceg> arabicEgList, List<ReResourceitem> itemList, List<ReArabiceg> copiedArabicEgList, ReResourceitem copiedResourceItem) {
		for (ReResourceitem item : itemList) {
			for (ReArabiceg eg : arabicEgList) {
				if (eg.getResourceitemref().equals(item.getId()) && item.getTagnr().equals(copiedResourceItem.getTagnr())) {
					ReArabiceg copiedArabicEg = eg.cloneArabiceg(copiedResourceItem);
					setLanguageCreatedInformations(copiedArabicEg);
					copiedArabicEgList.add(copiedArabicEg);
					break;
				}
			}
		}
	}

	private void copyArabicSa(List<ReArabicsa> arabicSaList, List<ReResourceitem> itemList, List<ReArabicsa> copiedArabicSaList, ReResourceitem copiedResourceItem) {
		for (ReResourceitem item : itemList) {
			for (ReArabicsa sa : arabicSaList) {
				if (sa.getResourceitemref().equals(item.getId()) && item.getTagnr().equals(copiedResourceItem.getTagnr())) {
					ReArabicsa copiedArabicSa = sa.cloneArabicsa(copiedResourceItem);
					setLanguageCreatedInformations(copiedArabicSa);
					copiedArabicSaList.add(copiedArabicSa);
					break;
				}
			}
		}
	}

	private void copyArabicJo(List<ReArabicjo> arabicJoList, List<ReResourceitem> itemList, List<ReArabicjo> copiedArabicJoList, ReResourceitem copiedResourceItem) {
		for (ReResourceitem item : itemList) {
			for (ReArabicjo jo : arabicJoList) {
				if (jo.getResourceitemref().equals(item.getId()) && item.getTagnr().equals(copiedResourceItem.getTagnr())) {
					ReArabicjo copiedArabicJo = jo.cloneArabicjo(copiedResourceItem);
					setLanguageCreatedInformations(copiedArabicJo);
					copiedArabicJoList.add(copiedArabicJo);
					break;
				}
			}
		}
	}

	private void copyAzerbaijaniAz(List<ReAzerbaijaniaz> azList, List<ReResourceitem> itemList, List<ReAzerbaijaniaz> copiedAzerbaijaniazList, ReResourceitem copiedResourceItem) {
		for (ReResourceitem item : itemList) {
			for (ReAzerbaijaniaz az : azList) {
				if (az.getResourceitemref().equals(item.getId()) && item.getTagnr().equals(copiedResourceItem.getTagnr())) {
					ReAzerbaijaniaz copiedAz = az.cloneAzerbaijaniaz(copiedResourceItem);
					setLanguageCreatedInformations(copiedAz);
					copiedAzerbaijaniazList.add(copiedAz);
					break;
				}
			}
		}
	}

	private void copyBulgarianBg(List<ReBulgarianbg> bgList, List<ReResourceitem> itemList, List<ReBulgarianbg> copiedBulgarianList, ReResourceitem copiedResourceItem) {
		for (ReResourceitem item : itemList) {
			for (ReBulgarianbg bg : bgList) {
				if (bg.getResourceitemref().equals(item.getId()) && item.getTagnr().equals(copiedResourceItem.getTagnr())) {
					ReBulgarianbg copiedBg = bg.cloneBulgarianbg(copiedResourceItem);
					setLanguageCreatedInformations(copiedBg);
					copiedBulgarianList.add(copiedBg);
					break;
				}
			}
		}
	}

	private void copyEnglishUs(List<ReEnglishus> usList, List<ReResourceitem> itemList, List<ReEnglishus> copiedEnglishList, ReResourceitem copiedResourceItem) {
		for (ReResourceitem item : itemList) {
			for (ReEnglishus us : usList) {
				if (us.getResourceitemref().equals(item.getId()) && item.getTagnr().equals(copiedResourceItem.getTagnr())) {
					ReEnglishus copiedUs = us.cloneEnglishus(copiedResourceItem);
					setLanguageCreatedInformations(copiedUs);
					copiedEnglishList.add(copiedUs);
					break;
				}
			}
		}
	}

	private void copyFrenchFr(List<ReFrenchfr> frList, List<ReResourceitem> itemList, List<ReFrenchfr> copiedFrenchList, ReResourceitem copiedResourceItem) {
		for (ReResourceitem item : itemList) {
			for (ReFrenchfr fr : frList) {
				if (fr.getResourceitemref().equals(item.getId()) && item.getTagnr().equals(copiedResourceItem.getTagnr())) {
					ReFrenchfr copiedFr = fr.cloneFrench(copiedResourceItem);
					setLanguageCreatedInformations(copiedFr);
					copiedFrenchList.add(copiedFr);
					break;
				}
			}
		}
	}

	private void copyGeorgianGe(List<ReGeorgiange> geList, List<ReResourceitem> itemList, List<ReGeorgiange> copiedGeorgianList, ReResourceitem copiedResourceItem) {
		for (ReResourceitem item : itemList) {
			for (ReGeorgiange ge : geList) {
				if (ge.getResourceitemref().equals(item.getId()) && item.getTagnr().equals(copiedResourceItem.getTagnr())) {
					ReGeorgiange copiedGe = ge.cloneGeorgiange(copiedResourceItem);
					setLanguageCreatedInformations(copiedGe);
					copiedGeorgianList.add(copiedGe);
					break;
				}
			}
		}
	}

	private void copyGermanDe(List<ReGermande> deList, List<ReResourceitem> itemList, List<ReGermande> copiedGermanList, ReResourceitem copiedResourceItem) {
		for (ReResourceitem item : itemList) {
			for (ReGermande ge : deList) {
				if (ge.getResourceitemref().equals(item.getId()) && item.getTagnr().equals(copiedResourceItem.getTagnr())) {
					ReGermande copiedDe = ge.cloneGermande(copiedResourceItem);
					setLanguageCreatedInformations(copiedDe);
					copiedGermanList.add(copiedDe);
					break;
				}
			}
		}
	}

	private void copyPersianIr(List<RePersianir> irList, List<ReResourceitem> itemList, List<RePersianir> copiedPersianList, ReResourceitem copiedResourceItem) {
		for (ReResourceitem item : itemList) {
			for (RePersianir ir : irList) {
				if (ir.getResourceitemref().equals(item.getId()) && item.getTagnr().equals(copiedResourceItem.getTagnr())) {
					RePersianir copiedIr = ir.clonePersianir(copiedResourceItem);
					setLanguageCreatedInformations(copiedIr);
					copiedPersianList.add(copiedIr);
					break;
				}
			}
		}
	}

	private void copyRomanianRo(List<ReRomanianro> roList, List<ReResourceitem> itemList, List<ReRomanianro> copiedRomanianList, ReResourceitem copiedResourceItem) {
		for (ReResourceitem item : itemList) {
			for (ReRomanianro ro : roList) {
				if (ro.getResourceitemref().equals(item.getId()) && item.getTagnr().equals(copiedResourceItem.getTagnr())) {
					ReRomanianro copiedRo = ro.cloneRomanianro(copiedResourceItem);
					setLanguageCreatedInformations(copiedRo);
					copiedRomanianList.add(copiedRo);
					break;
				}
			}
		}
	}

	private void copyRussianRo(List<ReRussianru> ruList, List<ReResourceitem> itemList, List<ReRussianru> copiedRussianList, ReResourceitem copiedResourceItem) {
		for (ReResourceitem item : itemList) {
			for (ReRussianru ru : ruList) {
				if (ru.getResourceitemref().equals(item.getId()) && item.getTagnr().equals(copiedResourceItem.getTagnr())) {
					ReRussianru copiedRu = ru.cloneRussianru(copiedResourceItem);
					setLanguageCreatedInformations(copiedRu);
					copiedRussianList.add(copiedRu);
					break;
				}
			}
		}
	}

	private void copyTurkmenTm(List<ReTurkmentm> tmList, List<ReResourceitem> itemList, List<ReTurkmentm> copiedTurkmenList, ReResourceitem copiedResourceItem) {
		for (ReResourceitem item : itemList) {
			for (ReTurkmentm tm : tmList) {
				if (tm.getResourceitemref().equals(item.getId()) && item.getTagnr().equals(copiedResourceItem.getTagnr())) {
					ReTurkmentm copiedTm = tm.cloneTurkmentm(copiedResourceItem);
					setLanguageCreatedInformations(copiedTm);
					copiedTurkmenList.add(copiedTm);
					break;
				}
			}
		}
	}

	private void copyStandard(List<ReStandard> stList, List<ReResourceitem> itemList, List<ReStandard> copiedStandardList, ReResourceitem copiedResourceItem) {
		for (ReResourceitem item : itemList) {
			for (ReStandard st : stList) {
				if (st.getResourceitemref().equals(item.getId()) && item.getTagnr().equals(copiedResourceItem.getTagnr())) {
					ReStandard copiedSt = st.cloneStandard(copiedResourceItem);
					try {
						copiedSt.setCreatedby(SecurityUtils.getCurrentUser(userService).getReUser().getId());
						copiedSt.setCreatedon(LocalDateTime.now());
					} catch (LocalizedException e) {
						e.printStackTrace();
					}
					copiedStandardList.add(copiedSt);
					break;
				}
			}
		}
	}

	public void generateResourceNumber() {
		Integer newNumber = resourceService.getMaxResourceNumber() + 1;
		getWindow().getResourceNr().setValue(newNumber.toString());
	}

	public ReResource getResource() {
		return resource;
	}

	public void setResource(ReResource resource) {
		this.resource = resource;
	}

	public ResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public ResourceGridPresenter getResourceGridPresenter() {
		return resourceGridPresenter;
	}

	public void setResourceGridPresenter(ResourceGridPresenter resourceGridPresenter) {
		this.resourceGridPresenter = resourceGridPresenter;
	}

	public ReResource getTempResource() {
		return tempResource;
	}

	public void setTempResource(ReResource tempResource) {
		this.tempResource = tempResource;
	}
}

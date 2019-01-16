package com.lbs.re.ui.view.resourceitem.edit;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

import com.lbs.re.data.service.ResourceitemService;
import com.lbs.re.data.service.impl.language.LanguageServices;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReResource;
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
import com.lbs.re.ui.view.AbstractDataProvider;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@PrototypeScope
public class ResourceItemDataProvider extends AbstractDataProvider<ReResourceitem> {

	private static final long serialVersionUID = 1L;
	private BeanFactory beanFactory;

	private LanguageServices languageServices;

	@Autowired
	public ResourceItemDataProvider(BeanFactory beanFactory, LanguageServices languageServices, ResourceitemService resourceItemService) throws LocalizedException {
		this.beanFactory = beanFactory;
		this.languageServices = languageServices;
		buildListDataProvider(new ArrayList<>());
	}

	public void provideResourceItems(ReResource resource) throws LocalizedException {
		List<ReResourceitem> resourceItemList = resource.getReResourceitem();
		buildListDataProvider(loadTransientData(resourceItemList, resource.getId()));
	}

	public List<ReResourceitem> loadTransientData(List<ReResourceitem> resourceItemList, Integer resourceId) {
		for (ReResourceitem item : resourceItemList) {
			loadTurkishData(item, resourceId);
			loadAlbanianData(item, resourceId);
			loadArabicEgData(item, resourceId);
			loadArabicJoData(item, resourceId);
			loadArabicSaData(item, resourceId);
			loadAzerbaijaniAzData(item, resourceId);
			loadBulgarianData(item, resourceId);
			loadEnglishUsData(item, resourceId);
			loadFrenchFrData(item, resourceId);
			loadGeorgianData(item, resourceId);
			loadGermanData(item, resourceId);
			loadPersianData(item, resourceId);
			loadRomanianData(item, resourceId);
			loadRussianData(item, resourceId);
			loadTurkmenData(item, resourceId);
		}

		return resourceItemList;
	}

	public List<ReResourceitem> loadTurkishDataForDictionary(List<ReResourceitem> resourceItemList) throws LocalizedException {
		List<ReTurkishtr> turkishList = languageServices.getTurkishService().getAll();
		for (ReTurkishtr tr : turkishList) {
			for (ReResourceitem item : resourceItemList) {
				if (item.getId().equals(tr.getResourceitemref())) {
					item.setTurkishTr(tr.getResourcestr());
					break;
				}
			}
		}
		return resourceItemList;
	}

	private void loadTurkishData(ReResourceitem item, Integer resourceId) {
		List<ReTurkishtr> trList = languageServices.getTurkishService().getLanguageListByresourceref(resourceId);
		if (trList != null && !trList.isEmpty()) {
			for (ReTurkishtr turkish : trList) {
				if (turkish.getResourceitemref().equals(item.getId())) {
					item.setTurkishTr(turkish.getResourcestr());
					break;
				}
			}
		}
	}

	private void loadAlbanianData(ReResourceitem item, Integer resourceId) {
		List<ReAlbaniankv> albenianList = languageServices.getAlbanianService().getLanguageListByresourceref(resourceId);
		if (albenianList != null && !albenianList.isEmpty()) {
			for (ReAlbaniankv albanian : albenianList) {
				if (albanian.getResourceitemref().equals(item.getId())) {
					item.setAlbanianKv(albanian.getResourcestr());
					break;
				}
			}
		}
	}

	private void loadArabicEgData(ReResourceitem item, Integer resourceId) {
		List<ReArabiceg> arabicEgList = languageServices.getArabicEgService().getLanguageListByresourceref(resourceId);
		if (arabicEgList != null && !arabicEgList.isEmpty()) {
			for (ReArabiceg arabicEg : arabicEgList) {
				if (arabicEg.getResourceitemref().equals(item.getId())) {
					item.setArabicEg(arabicEg.getResourcestr());
					break;
				}
			}
		}
	}

	private void loadArabicJoData(ReResourceitem item, Integer resourceId) {
		List<ReArabicjo> arabicJoList = languageServices.getArabicJoService().getLanguageListByresourceref(resourceId);
		if (arabicJoList != null && !arabicJoList.isEmpty()) {
			for (ReArabicjo arabicJo : arabicJoList) {
				if (arabicJo.getResourceitemref().equals(item.getId())) {
					item.setArabicJo(arabicJo.getResourcestr());
					break;
				}
			}
		}
	}

	private void loadArabicSaData(ReResourceitem item, Integer resourceId) {
		List<ReArabicsa> arabicSaList = languageServices.getArabicSaService().getLanguageListByresourceref(resourceId);
		if (arabicSaList != null && !arabicSaList.isEmpty()) {
			for (ReArabicsa arabicSa : arabicSaList) {
				if (arabicSa.getResourceitemref().equals(item.getId())) {
					item.setArabicSa(arabicSa.getResourcestr());
					break;
				}
			}
		}
	}

	private void loadBulgarianData(ReResourceitem item, Integer resourceId) {
		List<ReBulgarianbg> bulgarianBgList = languageServices.getBulgarianService().getLanguageListByresourceref(resourceId);
		if (bulgarianBgList != null && !bulgarianBgList.isEmpty()) {
			for (ReBulgarianbg bulgarian : bulgarianBgList) {
				if (bulgarian.getResourceitemref().equals(item.getId())) {
					item.setBulgarianBg(bulgarian.getResourcestr());
					break;
				}
			}
		}
	}

	private void loadAzerbaijaniAzData(ReResourceitem item, Integer resourceId) {
		List<ReAzerbaijaniaz> azerbaijaniAzList = languageServices.getAzerbaijaniazService().getLanguageListByresourceref(resourceId);
		if (azerbaijaniAzList != null && !azerbaijaniAzList.isEmpty()) {
			for (ReAzerbaijaniaz azerbaijaniAz : azerbaijaniAzList) {
				if (azerbaijaniAz.getResourceitemref().equals(item.getId())) {
					item.setAzerbaijaniAz(azerbaijaniAz.getResourcestr());
					break;
				}
			}
		}
	}

	private void loadEnglishUsData(ReResourceitem item, Integer resourceId) {
		List<ReEnglishus> englishUsList = languageServices.getEnglishService().getLanguageListByresourceref(resourceId);
		if (englishUsList != null && !englishUsList.isEmpty()) {
			for (ReEnglishus englishUs : englishUsList) {
				if (englishUs.getResourceitemref().equals(item.getId())) {
					item.setEnglishUs(englishUs.getResourcestr());
					break;
				}
			}
		}
	}

	private void loadFrenchFrData(ReResourceitem item, Integer resourceId) {
		List<ReFrenchfr> frenchFrList = languageServices.getFrenchService().getLanguageListByresourceref(resourceId);
		if (frenchFrList != null && !frenchFrList.isEmpty()) {
			for (ReFrenchfr frenchFr : frenchFrList) {
				if (frenchFr.getResourceitemref().equals(item.getId())) {
					item.setFrenchFr(frenchFr.getResourcestr());
					break;
				}
			}
		}
	}

	private void loadGeorgianData(ReResourceitem item, Integer resourceId) {
		List<ReGeorgiange> georgianList = languageServices.getGeorgianService().getLanguageListByresourceref(resourceId);
		if (georgianList != null && !georgianList.isEmpty()) {
			for (ReGeorgiange georgian : georgianList) {
				if (georgian.getResourceitemref().equals(item.getId())) {
					item.setGeorgianGe(georgian.getResourcestr());
					break;
				}
			}
		}
	}

	private void loadGermanData(ReResourceitem item, Integer resourceId) {
		List<ReGermande> germanList = languageServices.getGermanService().getLanguageListByresourceref(resourceId);
		if (germanList != null && !germanList.isEmpty()) {
			for (ReGermande german : germanList) {
				if (german.getResourceitemref().equals(item.getId())) {
					item.setGermanDe(german.getResourcestr());
					break;
				}
			}
		}
	}

	private void loadPersianData(ReResourceitem item, Integer resourceId) {
		List<RePersianir> persianList = languageServices.getPersianService().getLanguageListByresourceref(resourceId);
		if (persianList != null && !persianList.isEmpty()) {
			for (RePersianir persian : persianList) {
				if (persian.getResourceitemref().equals(item.getId())) {
					item.setPersianIr(persian.getResourcestr());
					break;
				}
			}
		}
	}

	private void loadRomanianData(ReResourceitem item, Integer resourceId) {
		List<ReRomanianro> romanianList = languageServices.getRomanianService().getLanguageListByresourceref(resourceId);
		if (romanianList != null && !romanianList.isEmpty()) {
			for (ReRomanianro romanian : romanianList) {
				if (romanian.getResourceitemref().equals(item.getId())) {
					item.setRomanianRo(romanian.getResourcestr());
					break;
				}
			}
		}
	}

	private void loadRussianData(ReResourceitem item, Integer resourceId) {
		List<ReRussianru> russianList = languageServices.getRussianruService().getLanguageListByresourceref(resourceId);
		if (russianList != null && !russianList.isEmpty()) {
			for (ReRussianru russian : russianList) {
				if (russian.getResourceitemref().equals(item.getId())) {
					item.setRussianRu(russian.getResourcestr());
					break;
				}
			}
		}
	}

	private void loadTurkmenData(ReResourceitem item, Integer resourceId) {
		List<ReTurkmentm> turkmenList = languageServices.getTurkmenService().getLanguageListByresourceref(resourceId);
		if (turkmenList != null && !turkmenList.isEmpty()) {
			for (ReTurkmentm turkmen : turkmenList) {
				if (turkmen.getResourceitemref().equals(item.getId())) {
					item.setTurkmenTm(turkmen.getResourcestr());
					break;
				}
			}
		}
	}
}

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
		ReTurkishtr tr = languageServices.getTurkishService().getLanguageByresourceitemref(item.getId());
		if (tr != null) {
			item.setTurkishTr(tr.getResourcestr());
		}
	}

	private void loadAlbanianData(ReResourceitem item, Integer resourceId) {
		ReAlbaniankv kv = languageServices.getAlbanianService().getLanguageByresourceitemref(item.getId());
		if (kv != null) {
			item.setAlbanianKv(kv.getResourcestr());
		}
	}

	private void loadArabicEgData(ReResourceitem item, Integer resourceId) {
		ReArabiceg eg = languageServices.getArabicEgService().getLanguageByresourceitemref(item.getId());
		if (eg != null) {
			item.setArabicEg(eg.getResourcestr());
		}
	}

	private void loadArabicJoData(ReResourceitem item, Integer resourceId) {
		ReArabicjo jo = languageServices.getArabicJoService().getLanguageByresourceitemref(item.getId());
		if (jo != null) {
			item.setArabicJo(jo.getResourcestr());
		}
	}

	private void loadArabicSaData(ReResourceitem item, Integer resourceId) {
		ReArabicsa sa = languageServices.getArabicSaService().getLanguageByresourceitemref(item.getId());
		if (sa != null) {
			item.setArabicSa(sa.getResourcestr());
		}
	}

	private void loadBulgarianData(ReResourceitem item, Integer resourceId) {
		ReBulgarianbg bg = languageServices.getBulgarianService().getLanguageByresourceitemref(item.getId());
		if (bg != null) {
			item.setBulgarianBg(bg.getResourcestr());
		}
	}

	private void loadAzerbaijaniAzData(ReResourceitem item, Integer resourceId) {
		ReAzerbaijaniaz az = languageServices.getAzerbaijaniazService().getLanguageByresourceitemref(item.getId());
		if (az != null) {
			item.setAzerbaijaniAz(az.getResourcestr());
		}
	}

	private void loadEnglishUsData(ReResourceitem item, Integer resourceId) {
		ReEnglishus us = languageServices.getEnglishService().getLanguageByresourceitemref(item.getId());
		if (us != null) {
			item.setEnglishUs(us.getResourcestr());
		}
	}

	private void loadFrenchFrData(ReResourceitem item, Integer resourceId) {
		ReFrenchfr fr = languageServices.getFrenchService().getLanguageByresourceitemref(item.getId());
		if (fr != null) {
			item.setFrenchFr(fr.getResourcestr());
		}
	}

	private void loadGeorgianData(ReResourceitem item, Integer resourceId) {
		ReGeorgiange ge = languageServices.getGeorgianService().getLanguageByresourceitemref(item.getId());
		if (ge != null) {
			item.setGeorgianGe(ge.getResourcestr());
		}
	}

	private void loadGermanData(ReResourceitem item, Integer resourceId) {
		ReGermande de = languageServices.getGermanService().getLanguageByresourceitemref(item.getId());
		if (de != null) {
			item.setGermanDe(de.getResourcestr());
		}
	}

	private void loadPersianData(ReResourceitem item, Integer resourceId) {
		RePersianir ir = languageServices.getPersianService().getLanguageByresourceitemref(item.getId());
		if (ir != null) {
			item.setPersianIr(ir.getResourcestr());
		}
	}

	private void loadRomanianData(ReResourceitem item, Integer resourceId) {
		ReRomanianro ro = languageServices.getRomanianService().getLanguageByresourceitemref(item.getId());
		if (ro != null) {
			item.setRomanianRo(ro.getResourcestr());
		}
	}

	private void loadRussianData(ReResourceitem item, Integer resourceId) {
		ReRussianru ru = languageServices.getRussianruService().getLanguageByresourceitemref(item.getId());
		if (ru != null) {
			item.setRussianRu(ru.getResourcestr());
		}
	}

	private void loadTurkmenData(ReResourceitem item, Integer resourceId) {
		ReTurkmentm tm = languageServices.getTurkmenService().getLanguageByresourceitemref(item.getId());
		if (tm != null) {
			item.setTurkmenTm(tm.getResourcestr());
		}
	}
}

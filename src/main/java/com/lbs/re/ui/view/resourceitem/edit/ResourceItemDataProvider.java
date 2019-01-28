package com.lbs.re.ui.view.resourceitem.edit;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

import com.lbs.re.data.service.ResourceitemService;
import com.lbs.re.data.service.StandardService;
import com.lbs.re.data.service.impl.language.LanguageServices;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReResource;
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
import com.lbs.re.ui.view.AbstractDataProvider;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@PrototypeScope
public class ResourceItemDataProvider extends AbstractDataProvider<ReResourceitem> {

	private static final long serialVersionUID = 1L;
	private BeanFactory beanFactory;

	private LanguageServices languageServices;

	private StandardService standardService;

	private ResourceitemService resourceItemService;

	@Autowired
	public ResourceItemDataProvider(BeanFactory beanFactory, LanguageServices languageServices, ResourceitemService resourceItemService, StandardService standardService)
			throws LocalizedException {
		this.beanFactory = beanFactory;
		this.languageServices = languageServices;
		this.standardService = standardService;
		this.resourceItemService = resourceItemService;
		buildListDataProvider(new ArrayList<>());
	}

	public void provideResourceItems(ReResource resource) throws LocalizedException {
		List<ReResourceitem> resourceItemList = resource.getReResourceitem();
		buildListDataProvider(loadTransientData(resourceItemList, resource.getId()));
	}

	public void provideLimitedResourceItems() throws LocalizedException {
		buildListDataProvider(loadItemGridTransientData(resourceItemService.getLimitedItemList()));
	}

	public List<ReResourceitem> provideSearchedResourceItems(List<Criterion> resourceItemCriterias, List<Criterion> turkishCriterias,
			List<Criterion> englishCriterias, List<Criterion> standardCriterias) throws LocalizedException {
		return loadItemGridTransientData(
				resourceItemService.getAdvancedSearchedItemList(resourceItemCriterias, turkishCriterias, englishCriterias, standardCriterias));
	}

	public List<ReResourceitem> loadItemGridTransientData(List<ReResourceitem> resourceItemList) {
		List<Integer> resourceItemIdList = new ArrayList<>();
		for (ReResourceitem item : resourceItemList) {
			if (resourceItemIdList.size() == 2000) {
				break;
			}
			resourceItemIdList.add(item.getId());
		}
		List<ReTurkishtr> trList = languageServices.getTurkishService().getAllByResourceitemrefIn(resourceItemIdList);
		List<ReEnglishus> usList = languageServices.getEnglishService().getAllByResourceitemrefIn(resourceItemIdList);
		List<ReStandard> stList = standardService.getAllByResourceitemrefIn(resourceItemIdList);
		for (ReResourceitem item : resourceItemList) {
			for (ReTurkishtr tr : trList) {
				if (item.getId().equals(tr.getResourceitemref())) {
					item.setTurkishTr(tr.getResourcestr());
					break;
				}
			}
			for (ReEnglishus us : usList) {
				if (item.getId().equals(us.getResourceitemref())) {
					item.setEnglishUs(us.getResourcestr());
					break;
				}
			}
			for (ReStandard st : stList) {
				if (item.getId().equals(st.getResourceitemref())) {
					item.setStandard(st.getResourceStr());
					break;
				}
			}
		}
		return resourceItemList;
	}

	public List<ReResourceitem> loadTransientData(List<ReResourceitem> resourceItemList, Integer resourceId) {
		List<ReTurkishtr> trList = languageServices.getTurkishService().getLanguageListByresourceref(resourceId);
		List<ReAlbaniankv> kvList = languageServices.getAlbanianService().getLanguageListByresourceref(resourceId);
		List<ReArabiceg> egList = languageServices.getArabicEgService().getLanguageListByresourceref(resourceId);
		List<ReArabicjo> joList = languageServices.getArabicJoService().getLanguageListByresourceref(resourceId);
		List<ReArabicsa> saList = languageServices.getArabicSaService().getLanguageListByresourceref(resourceId);
		List<ReAzerbaijaniaz> azList = languageServices.getAzerbaijaniazService().getLanguageListByresourceref(resourceId);
		List<ReBulgarianbg> bgList = languageServices.getBulgarianService().getLanguageListByresourceref(resourceId);
		List<ReEnglishus> usList = languageServices.getEnglishService().getLanguageListByresourceref(resourceId);
		List<ReFrenchfr> frList = languageServices.getFrenchService().getLanguageListByresourceref(resourceId);
		List<ReGeorgiange> geList = languageServices.getGeorgianService().getLanguageListByresourceref(resourceId);
		List<ReGermande> deList = languageServices.getGermanService().getLanguageListByresourceref(resourceId);
		List<RePersianir> irList = languageServices.getPersianService().getLanguageListByresourceref(resourceId);
		List<ReRomanianro> roList = languageServices.getRomanianService().getLanguageListByresourceref(resourceId);
		List<ReRussianru> ruList = languageServices.getRussianruService().getLanguageListByresourceref(resourceId);
		List<ReTurkmentm> tmList = languageServices.getTurkmenService().getLanguageListByresourceref(resourceId);
		List<ReStandard> stList = standardService.getStandardListByResourceref(resourceId);
		for (ReResourceitem item : resourceItemList) {
			loadTurkishData(item, trList);
			loadAlbanianData(item, kvList);
			loadArabicEgData(item, egList);
			loadArabicJoData(item, joList);
			loadArabicSaData(item, saList);
			loadAzerbaijaniAzData(item, azList);
			loadBulgarianData(item, bgList);
			loadEnglishUsData(item, usList);
			loadFrenchFrData(item, frList);
			loadGeorgianData(item, geList);
			loadGermanData(item, deList);
			loadPersianData(item, irList);
			loadRomanianData(item, roList);
			loadRussianData(item, ruList);
			loadTurkmenData(item, tmList);
			loadStandardData(item, stList);
		}

		return resourceItemList;
	}

	private void loadTurkishData(ReResourceitem item, List<ReTurkishtr> trList) {
		for (ReTurkishtr tr : trList) {
			if (tr.getResourceitemref().equals(item.getId())) {
				item.setTurkishTr(tr.getResourcestr());
				break;
			}
		}
	}

	private void loadAlbanianData(ReResourceitem item, List<ReAlbaniankv> kvList) {
		for (ReAlbaniankv kv : kvList) {
			if (kv.getResourceitemref().equals(item.getId())) {
				item.setAlbanianKv(kv.getResourcestr());
				break;
			}
		}
	}

	private void loadArabicEgData(ReResourceitem item, List<ReArabiceg> egList) {
		for (ReArabiceg eg : egList) {
			if (eg.getResourceitemref().equals(item.getId())) {
				item.setArabicEg(eg.getResourcestr());
				break;
			}
		}
	}

	private void loadArabicJoData(ReResourceitem item, List<ReArabicjo> joList) {
		for (ReArabicjo jo : joList) {
			if (jo.getResourceitemref().equals(item.getId())) {
				item.setArabicJo(jo.getResourcestr());
				break;
			}
		}
	}

	private void loadArabicSaData(ReResourceitem item, List<ReArabicsa> saList) {
		for (ReArabicsa sa : saList) {
			if (sa.getResourceitemref().equals(item.getId())) {
				item.setArabicSa(sa.getResourcestr());
				break;
			}
		}
	}

	private void loadBulgarianData(ReResourceitem item, List<ReBulgarianbg> bgList) {
		for (ReBulgarianbg bg : bgList) {
			if (bg.getResourceitemref().equals(item.getId())) {
				item.setBulgarianBg(bg.getResourcestr());
				break;
			}
		}
	}

	private void loadAzerbaijaniAzData(ReResourceitem item, List<ReAzerbaijaniaz> azList) {
		for (ReAzerbaijaniaz az : azList) {
			if (az.getResourceitemref().equals(item.getId())) {
				item.setAzerbaijaniAz(az.getResourcestr());
				break;
			}
		}
	}

	private void loadEnglishUsData(ReResourceitem item, List<ReEnglishus> usList) {
		for (ReEnglishus us : usList) {
			if (us.getResourceitemref().equals(item.getId())) {
				item.setEnglishUs(us.getResourcestr());
				break;
			}
		}
	}

	private void loadFrenchFrData(ReResourceitem item, List<ReFrenchfr> frList) {
		for (ReFrenchfr fr : frList) {
			if (fr.getResourceitemref().equals(item.getId())) {
				item.setFrenchFr(fr.getResourcestr());
				break;
			}
		}
	}

	private void loadGeorgianData(ReResourceitem item, List<ReGeorgiange> geList) {
		for (ReGeorgiange ge : geList) {
			if (ge.getResourceitemref().equals(item.getId())) {
				item.setGeorgianGe(ge.getResourcestr());
				break;
			}
		}
	}

	private void loadGermanData(ReResourceitem item, List<ReGermande> deList) {
		for (ReGermande de : deList) {
			if (de.getResourceitemref().equals(item.getId())) {
				item.setGermanDe(de.getResourcestr());
				break;
			}
		}
	}

	private void loadPersianData(ReResourceitem item, List<RePersianir> irList) {
		for (RePersianir ir : irList) {
			if (ir.getResourceitemref().equals(item.getId())) {
				item.setPersianIr(ir.getResourcestr());
				break;
			}
		}

	}

	private void loadRomanianData(ReResourceitem item, List<ReRomanianro> roList) {
		for (ReRomanianro ro : roList) {
			if (ro.getResourceitemref().equals(item.getId())) {
				item.setRomanianRo(ro.getResourcestr());
				break;
			}
		}
	}

	private void loadRussianData(ReResourceitem item, List<ReRussianru> ruList) {
		for (ReRussianru ru : ruList) {
			if (ru.getResourceitemref().equals(item.getId())) {
				item.setRussianRu(ru.getResourcestr());
				break;
			}
		}
	}

	private void loadTurkmenData(ReResourceitem item, List<ReTurkmentm> tmList) {
		for (ReTurkmentm tm : tmList) {
			if (tm.getResourceitemref().equals(item.getId())) {
				item.setTurkmenTm(tm.getResourcestr());
				break;
			}
		}
	}

	private void loadStandardData(ReResourceitem item, List<ReStandard> stList) {
		for (ReStandard st : stList) {
			if (st.getResourceitemref().equals(item.getId())) {
				item.setStandard(st.getResourceStr());
				break;
			}
		}
	}

	public List<ReResourceitem> loadTurkishDataForDictionary(List<ReResourceitem> resourceItemList) throws LocalizedException {
		List<ReTurkishtr> turkishList = languageServices.getTurkishService().getAll();
		List<ReEnglishus> englishList = languageServices.getEnglishService().getAll();
		for (ReResourceitem item : resourceItemList) {
			for (ReTurkishtr tr : turkishList) {
				if (item.getId().equals(tr.getResourceitemref())) {
					item.setTurkishTr(tr.getResourcestr());
					break;
				}
			}
			for (ReEnglishus us : englishList) {
				if (item.getId().equals(us.getResourceitemref())) {
					item.setEnglishUs(us.getResourcestr());
					break;
				}
			}
		}
		return resourceItemList;
	}

}

package com.lbs.re.ui.view.resourceitem.edit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.lbs.re.data.service.ResourceitemService;
import com.lbs.re.model.ReResourceitem;
import com.lbs.re.ui.components.basic.RETextArea;
import com.lbs.re.ui.components.basic.RETextField;
import com.lbs.re.ui.view.AbstractEditView;
import com.lbs.re.ui.view.Operation;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.spring.annotation.SpringView;

@SpringView
public class ResourceItemEditView
		extends AbstractEditView<ReResourceitem, ResourceitemService, ResourceItemEditPresenter, ResourceItemEditView> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private RETextField ordernr;
	private RETextField tagnr;
	private RETextField levelnr;
	private RETextField prefixstr;
	private RETextArea info;
	private RETextArea albanianKv;
	private RETextArea arabicEg;
	private RETextArea arabicJo;
	private RETextArea arabicSa;
	private RETextArea azerbaijaniAz;
	private RETextArea bulgarianBg;
	private RETextArea englishUs;
	private RETextArea frenchFr;
	private RETextArea georgianGe;
	private RETextArea germanDe;
	private RETextArea persianIr;
	private RETextArea romanianRo;
	private RETextArea russianRu;
	private RETextArea turkishTr;
	private RETextArea turkmenTm;

	@Autowired
	public ResourceItemEditView(ResourceItemEditPresenter presenter) {
		super(presenter);
	}

	@PostConstruct
	private void initView() {
		ordernr = new RETextField("column.resource.item.order.number", "full", true, true);
		tagnr = new RETextField("column.resource.item.tag.number", "full", true, true);
		levelnr = new RETextField("column.resource.item.level.number", "full", true, true);
		prefixstr = new RETextField("column.resource.item.prefix", "full", true, true);
		info = new RETextArea("column.resource.item.info", "full", true, true);
		albanianKv = new RETextArea("column.resource.item.albanian", "full", true, true);
		arabicEg = new RETextArea("column.resource.item.arabiceg", "full", true, true);
		arabicJo = new RETextArea("column.resource.item.arabicjo", "full", true, true);
		arabicSa = new RETextArea("column.resource.item.arabicsa", "full", true, true);
		azerbaijaniAz = new RETextArea("column.resource.item.azerbaijaniaz", "full", true, true);
		bulgarianBg = new RETextArea("column.resource.item.bulgarianbg", "full", true, true);
		englishUs = new RETextArea("column.resource.item.english", "full", true, true);
		frenchFr = new RETextArea("column.resource.item.frenchfr", "full", true, true);
		georgianGe = new RETextArea("column.resource.item.georgianGe", "full", true, true);
		germanDe = new RETextArea("column.resource.item.germande", "full", true, true);
		persianIr = new RETextArea("column.resource.item.persianir", "full", true, true);
		romanianRo = new RETextArea("column.resource.item.romanianro", "full", true, true);
		russianRu = new RETextArea("column.resource.item.russianru", "full", true, true);
		turkishTr = new RETextArea("column.resource.item.turkish", "full", true, true);
		turkmenTm = new RETextArea("column.resource.item.turkmentm", "full", true, true);

		addSection(getLocaleValue("view.viewedit.section.general"), 0, null, ordernr, tagnr, levelnr, prefixstr, info);
		addSection(getLocaleValue("view.viewedit.section.languages"), 1, null, turkishTr, englishUs, albanianKv,
				arabicEg, arabicJo, arabicSa, azerbaijaniAz, bulgarianBg, frenchFr, georgianGe, germanDe, persianIr,
				romanianRo, russianRu, turkmenTm);

		getCancel().setVisible(false);
		getSave().setVisible(false);

		getPresenter().setView(this);
	}

	@Override
	public void bindFormFields(BeanValidationBinder<ReResourceitem> binder) {
		binder.forField(ordernr).withNullRepresentation("")
				.withConverter(new StringToIntegerConverter("must be integer"))
				.bind(ReResourceitem::getOrdernr, ReResourceitem::setOrdernr);
		binder.forField(tagnr).withNullRepresentation("").withConverter(new StringToIntegerConverter("must be integer"))
				.bind(ReResourceitem::getTagnr, ReResourceitem::setTagnr);
		binder.forField(levelnr).withNullRepresentation("")
				.withConverter(new StringToIntegerConverter("must be integer"))
				.bind(ReResourceitem::getLevelnr, ReResourceitem::setLevelnr);
		super.bindFormFields(binder);
	}

	@Override
	public String getHeader() {
		return getLocaleValue("view.resourceitem.header");
	}

	public RETextField getOrdernr() {
		return ordernr;
	}

	public RETextField getTagnr() {
		return tagnr;
	}

	public RETextField getLevelnr() {
		return levelnr;
	}

	public RETextField getPrefixstr() {
		return prefixstr;
	}

	public RETextArea getInfo() {
		return info;
	}

	public RETextArea getAlbanianKv() {
		return albanianKv;
	}

	public RETextArea getArabicEg() {
		return arabicEg;
	}

	public RETextArea getArabicJo() {
		return arabicJo;
	}

	public RETextArea getArabicSa() {
		return arabicSa;
	}

	public RETextArea getAzerbaijaniAz() {
		return azerbaijaniAz;
	}

	public RETextArea getBulgarianBg() {
		return bulgarianBg;
	}

	public RETextArea getEnglishUs() {
		return englishUs;
	}

	public RETextArea getFrenchFr() {
		return frenchFr;
	}

	public RETextArea getGeorgianGe() {
		return georgianGe;
	}

	public RETextArea getGermanDe() {
		return germanDe;
	}

	public RETextArea getPersianIr() {
		return persianIr;
	}

	public RETextArea getRomanianRo() {
		return romanianRo;
	}

	public RETextArea getRussianRu() {
		return russianRu;
	}

	public RETextArea getTurkishTr() {
		return turkishTr;
	}

	public RETextArea getTurkmenTm() {
		return turkmenTm;
	}

	@Override
	public String getViewOperationName() {
		return Operation.VIEW_RESOURCE_ITEM;
	}

	@Override
	public String getEditOperationName() {
		return Operation.EDIT_RESOURCE_ITEM;
	}

}

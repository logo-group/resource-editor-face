package com.lbs.re.ui.view.resourceitem.edit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.lbs.re.data.service.ResourceitemService;
import com.lbs.re.model.ReResourceitem;
import com.lbs.re.ui.components.basic.REButton;
import com.lbs.re.ui.components.basic.RETextArea;
import com.lbs.re.ui.components.basic.RETextField;
import com.lbs.re.ui.components.layout.REHorizontalLayout;
import com.lbs.re.ui.view.AbstractEditView;
import com.lbs.re.ui.view.Operation;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;

@SpringView
public class ResourceItemEditView
		extends AbstractEditView<ReResourceitem, ResourceitemService, ResourceItemEditPresenter, ResourceItemEditView> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private REHorizontalLayout albanianKvLayout;
	private REHorizontalLayout arabicEgLayout;
	private REHorizontalLayout arabicJoLayout;
	private REHorizontalLayout arabicSaLayout;
	private REHorizontalLayout azerbaijaniAzLayout;
	private REHorizontalLayout bulgarianBgLayout;
	private REHorizontalLayout englishUsLayout;
	private REHorizontalLayout frenchFrLayout;
	private REHorizontalLayout georgianGeLayout;
	private REHorizontalLayout germanDeLayout;
	private REHorizontalLayout persianIrLayout;
	private REHorizontalLayout romanianRoLayout;
	private REHorizontalLayout russianRuLayout;
	private REHorizontalLayout turkishTrLayout;
	private REHorizontalLayout turkmenTmLayout;

	private REButton albanianKvDelete;
	private REButton arabicEgDelete;
	private REButton arabicJoDelete;
	private REButton arabicSaDelete;
	private REButton azerbaijaniAzDelete;
	private REButton bulgarianBgDelete;
	private REButton englishUsDelete;
	private REButton frenchFrDelete;
	private REButton georgianGeDelete;
	private REButton germanDeDelete;
	private REButton persianIrDelete;
	private REButton romanianRoDelete;
	private REButton russianRuDelete;
	private REButton turkishTrDelete;
	private REButton turkmenTmDelete;

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
		initTextFields();
		initLangTextAreas();
		initDeleteButtons();
		initREHorizontalLayouts();
		initSections();

		getCancel().setVisible(false);
		getSave().setVisible(false);

		getPresenter().setView(this);
	}

	private void initSections() {
		addSection(getLocaleValue("view.viewedit.section.general"), 0, null, ordernr, tagnr, levelnr, prefixstr, info);
		addSection(getLocaleValue("view.viewedit.section.languages"), 1, null, turkishTrLayout, englishUsLayout,
				albanianKvLayout, arabicEgLayout, arabicJoLayout, arabicSaLayout, azerbaijaniAzLayout,
				bulgarianBgLayout, frenchFrLayout, georgianGeLayout, germanDeLayout, persianIrLayout, romanianRoLayout,
				russianRuLayout, turkmenTmLayout);
	}

	private void initTextFields() {
		ordernr = new RETextField("column.resource.item.order.number", "full", true, true);
		tagnr = new RETextField("column.resource.item.tag.number", "full", true, true);
		levelnr = new RETextField("column.resource.item.level.number", "full", true, true);
		prefixstr = new RETextField("column.resource.item.prefix", "full", true, true);
		info = new RETextArea("column.resource.item.info", "full", true, true);
	}

	private void initREHorizontalLayouts() {
		albanianKvLayout = initREHorizontalLayout("albanianKvLayout", albanianKvDelete, albanianKv);
		arabicEgLayout = initREHorizontalLayout("arabicEgLayout", arabicEgDelete, arabicEg);
		arabicJoLayout = initREHorizontalLayout("arabicJoLayout", arabicJoDelete, arabicJo);
		arabicSaLayout = initREHorizontalLayout("arabicSaLayout", arabicSaDelete, arabicSa);
		azerbaijaniAzLayout = initREHorizontalLayout("azerbaijaniAzLayout", azerbaijaniAzDelete, azerbaijaniAz);
		bulgarianBgLayout = initREHorizontalLayout("bulgarianBgLayout", bulgarianBgDelete, bulgarianBg);
		englishUsLayout = initREHorizontalLayout("englishUsLayout", englishUsDelete, englishUs);
		frenchFrLayout = initREHorizontalLayout("frenchFrLayout", frenchFrDelete, frenchFr);
		georgianGeLayout = initREHorizontalLayout("georgianGeLayout", georgianGeDelete, georgianGe);
		germanDeLayout = initREHorizontalLayout("germanDeLayout", germanDeDelete, germanDe);
		persianIrLayout = initREHorizontalLayout("persianIrLayout", persianIrDelete, persianIr);
		romanianRoLayout = initREHorizontalLayout("romanianRoLayout", romanianRoDelete, romanianRo);
		russianRuLayout = initREHorizontalLayout("russianRuLayout", russianRuDelete, russianRu);
		turkishTrLayout = initREHorizontalLayout("turkishTrLayout", turkishTrDelete, turkishTr);
		turkmenTmLayout = initREHorizontalLayout("turkmenTmLayout", turkmenTmDelete, turkmenTm);
	}

	private void initLangTextAreas() {
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
	}

	private void initDeleteButtons() {
		albanianKvDelete = new REButton("albanianKvDelete", VaadinIcons.TRASH);
		arabicEgDelete = new REButton("albanianKvDelete", VaadinIcons.TRASH);
		arabicJoDelete = new REButton("albanianKvDelete", VaadinIcons.TRASH);
		arabicSaDelete = new REButton("albanianKvDelete", VaadinIcons.TRASH);
		azerbaijaniAzDelete = new REButton("albanianKvDelete", VaadinIcons.TRASH);
		bulgarianBgDelete = new REButton("albanianKvDelete", VaadinIcons.TRASH);
		englishUsDelete = new REButton("albanianKvDelete", VaadinIcons.TRASH);
		frenchFrDelete = new REButton("albanianKvDelete", VaadinIcons.TRASH);
		georgianGeDelete = new REButton("albanianKvDelete", VaadinIcons.TRASH);
		germanDeDelete = new REButton("albanianKvDelete", VaadinIcons.TRASH);
		persianIrDelete = new REButton("albanianKvDelete", VaadinIcons.TRASH);
		romanianRoDelete = new REButton("albanianKvDelete", VaadinIcons.TRASH);
		russianRuDelete = new REButton("albanianKvDelete", VaadinIcons.TRASH);
		turkishTrDelete = new REButton("albanianKvDelete", VaadinIcons.TRASH);
		turkmenTmDelete = new REButton("albanianKvDelete", VaadinIcons.TRASH);
	}

	private REHorizontalLayout initREHorizontalLayout(String id, Component... components) {
		REHorizontalLayout layout = new REHorizontalLayout();
		layout.setStyleName("top-bar");
		layout.setSpacing(false);
		layout.setWidth("100%");
		layout.setId(id);
		layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		layout.addComponents(components);
//		layout.setExpandRatio(innerLayout, 1);
		layout.setSpacing(true);
		return layout;
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

package com.lbs.re.ui.view.resourceitem.edit;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.lbs.re.data.service.ResourceitemService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReResourceitem;
import com.lbs.re.model.ReUser;
import com.lbs.re.ui.components.CustomExceptions.REWindowNotAbleToOpenException;
import com.lbs.re.ui.components.basic.REButton;
import com.lbs.re.ui.components.basic.RETextArea;
import com.lbs.re.ui.components.basic.RETextField;
import com.lbs.re.ui.components.layout.REHorizontalLayout;
import com.lbs.re.ui.components.window.WindowDictionary;
import com.lbs.re.ui.util.Enums.UIParameter;
import com.lbs.re.ui.util.Enums.ViewMode;
import com.lbs.re.ui.util.RENotification;
import com.lbs.re.ui.util.RENotification.NotifyType;
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

	private static final int READ = 1;
	private static final int WRITE = 2;
	private static final int DELETE = 4;

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

	private REHorizontalLayout albanianKvButtonLayout;
	private REHorizontalLayout arabicEgButtonLayout;
	private REHorizontalLayout arabicJoButtonLayout;
	private REHorizontalLayout arabicSaButtonLayout;
	private REHorizontalLayout azerbaijaniAzButtonLayout;
	private REHorizontalLayout bulgarianBgButtonLayout;
	private REHorizontalLayout englishUsButtonLayout;
	private REHorizontalLayout frenchFrButtonLayout;
	private REHorizontalLayout georgianGeButtonLayout;
	private REHorizontalLayout germanDeButtonLayout;
	private REHorizontalLayout persianIrButtonLayout;
	private REHorizontalLayout romanianRoButtonLayout;
	private REHorizontalLayout russianRuButtonLayout;
	private REHorizontalLayout turkishTrButtonLayout;
	private REHorizontalLayout turkmenTmButtonLayout;

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
	private RETextField dictionaryId;
	private REButton btnDictionary;

	private WindowDictionary windowDictionary;

	private ReUser sessionUser;

	@Autowired
	public ResourceItemEditView(ResourceItemEditPresenter presenter, WindowDictionary windowDictionary) {
		super(presenter);
		this.windowDictionary = windowDictionary;
		sessionUser = presenter.getUser();
	}

	@PostConstruct
	private void initView() {
		initButtons();
		initTextFields();
		initLangTextAreas();
		initDeleteButtons();
		initButtonHorizontalLayouts();
		initHorizontalLayouts();
		initSections();

		getCancel().setVisible(false);
		getSave().setVisible(false);

		getPresenter().setView(this);
	}

	private void initSections() {
		addSection(getLocaleValue("view.viewedit.section.general"), 0, null, ordernr, tagnr, levelnr, prefixstr, info,
				btnDictionary, dictionaryId);
		addSection(getLocaleValue("view.viewedit.section.languages"), 1, null, turkishTrLayout, englishUsLayout,
				albanianKvLayout, arabicEgLayout, arabicJoLayout, arabicSaLayout, azerbaijaniAzLayout,
				bulgarianBgLayout, frenchFrLayout, georgianGeLayout, germanDeLayout, persianIrLayout, romanianRoLayout,
				russianRuLayout, turkmenTmLayout);
	}

	private void initButtons() {
		btnDictionary = new REButton("view.testcaseedit.button.dictionary", VaadinIcons.GLOBE);
		btnDictionary.addClickListener(e -> {
			try {
				getPresenter().prepareDictionaryWindow(getPresenter().getItem(), ViewMode.EDIT);
			} catch (LocalizedException e1) {
				logError(e1);
			}
		});
	}

	private void initTextFields() {
		ordernr = new RETextField("column.resource.item.order.number", "full", true, true);
		tagnr = new RETextField("column.resource.item.tag.number", "full", true, true);
		levelnr = new RETextField("column.resource.item.level.number", "full", true, true);
		prefixstr = new RETextField("column.resource.item.prefix", "full", true, true);
		info = new RETextArea("column.resource.item.info", "full", true, true);
		dictionaryId = new RETextField("column.resource.item.dictionary", "full", true, false);
	}

	private void initButtonHorizontalLayouts() {
		albanianKvButtonLayout = initREHorizontalLayout("albanianKvButtonLayout", albanianKvDelete);
		arabicEgButtonLayout = initREHorizontalLayout("arabicEgButtonLayout", arabicEgDelete);
		arabicJoButtonLayout = initREHorizontalLayout("arabicJoButtonLayout", arabicJoDelete);
		arabicSaButtonLayout = initREHorizontalLayout("arabicSaButtonLayout", arabicSaDelete);
		azerbaijaniAzButtonLayout = initREHorizontalLayout("azerbaijaniAzButtonLayout", azerbaijaniAzDelete);
		bulgarianBgButtonLayout = initREHorizontalLayout("bulgarianBgButtonLayout", bulgarianBgDelete);
		englishUsButtonLayout = initREHorizontalLayout("englishUsButtonLayout", englishUsDelete);
		frenchFrButtonLayout = initREHorizontalLayout("frenchFrButtonLayout", frenchFrDelete);
		georgianGeButtonLayout = initREHorizontalLayout("georgianGeButtonLayout", georgianGeDelete);
		germanDeButtonLayout = initREHorizontalLayout("germanDeButtonLayout", germanDeDelete);
		persianIrButtonLayout = initREHorizontalLayout("persianIrButtonLayout", persianIrDelete);
		romanianRoButtonLayout = initREHorizontalLayout("romanianRoButtonLayout", romanianRoDelete);
		russianRuButtonLayout = initREHorizontalLayout("russianRuButtonLayout", russianRuDelete);
		turkishTrButtonLayout = initREHorizontalLayout("turkishTrButtonLayout", turkishTrDelete);
		turkmenTmButtonLayout = initREHorizontalLayout("turkmenTmButtonLayout", turkmenTmDelete);
	}

	private void initHorizontalLayouts() {
		albanianKvLayout = initREHorizontalLayout("albanianKvLayout", albanianKvButtonLayout, albanianKv);
		arabicEgLayout = initREHorizontalLayout("arabicEgLayout", arabicEgButtonLayout, arabicEg);
		arabicJoLayout = initREHorizontalLayout("arabicJoLayout", arabicJoButtonLayout, arabicJo);
		arabicSaLayout = initREHorizontalLayout("arabicSaLayout", arabicSaButtonLayout, arabicSa);
		azerbaijaniAzLayout = initREHorizontalLayout("azerbaijaniAzLayout", azerbaijaniAzButtonLayout, azerbaijaniAz);
		bulgarianBgLayout = initREHorizontalLayout("bulgarianBgLayout", bulgarianBgButtonLayout, bulgarianBg);
		englishUsLayout = initREHorizontalLayout("englishUsLayout", englishUsButtonLayout, englishUs);
		frenchFrLayout = initREHorizontalLayout("frenchFrLayout", frenchFrButtonLayout, frenchFr);
		georgianGeLayout = initREHorizontalLayout("georgianGeLayout", georgianGeButtonLayout, georgianGe);
		germanDeLayout = initREHorizontalLayout("germanDeLayout", germanDeButtonLayout, germanDe);
		persianIrLayout = initREHorizontalLayout("persianIrLayout", persianIrButtonLayout, persianIr);
		romanianRoLayout = initREHorizontalLayout("romanianRoLayout", romanianRoButtonLayout, romanianRo);
		russianRuLayout = initREHorizontalLayout("russianRuLayout", russianRuButtonLayout, russianRu);
		turkishTrLayout = initREHorizontalLayout("turkishTrLayout", turkishTrButtonLayout, turkishTr);
		turkmenTmLayout = initREHorizontalLayout("turkmenTmLayout", turkmenTmButtonLayout, turkmenTm);

		checkForUserRight(sessionUser.getSqkvaccessrights(), albanianKvLayout);
		checkForUserRight(sessionUser.getAregaccessrights(), arabicEgLayout);
		checkForUserRight(sessionUser.getArjoaccessrights(), arabicJoLayout);
		checkForUserRight(sessionUser.getArsaaccessrights(), arabicSaLayout);
		checkForUserRight(sessionUser.getAzazaccessrights(), azerbaijaniAzLayout);
		checkForUserRight(sessionUser.getBgbgaccessrights(), bulgarianBgLayout);
		checkForUserRight(sessionUser.getEnusaccessrights(), englishUsLayout);
		checkForUserRight(sessionUser.getFrfraccessrights(), frenchFrLayout);
		checkForUserRight(sessionUser.getKageaccessrights(), georgianGeLayout);
		checkForUserRight(sessionUser.getDedeaccessrights(), germanDeLayout);
		checkForUserRight(sessionUser.getFairaccessrights(), persianIrLayout);
		checkForUserRight(sessionUser.getRoroaccessrights(), romanianRoLayout);
		checkForUserRight(sessionUser.getRuruaccessrights(), russianRuLayout);
		checkForUserRight(sessionUser.getTrtraccessrights(), turkishTrLayout);
		checkForUserRight(sessionUser.getTktmaccessrights(), turkmenTmLayout);
	}

	private void checkForUserRight(int userValue, REHorizontalLayout layout) {
		if ((userValue == 0) || (userValue % 2 != READ)) { // no read right
			layout.removeAllComponents();
		} else {
			switch (userValue) {
			case READ:
				layout.setEnabled(false);
				break;
			case READ + WRITE:
				layout.getComponent(0).setEnabled(false); // button
				break;
			case READ + DELETE:
				layout.getComponent(1).setEnabled(false); // textarea
				break;
			case READ + WRITE + DELETE:
				// user has all rights. Enable all.
				break;
			default:
				// for sonar and other tools.
				break;
			}
		}
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
		albanianKvDelete.addClickListener(e -> {
			albanianKv.clear();
		});

		arabicEgDelete = new REButton("arabicEgDelete", VaadinIcons.TRASH);
		arabicEgDelete.addClickListener(e -> {
			arabicEg.clear();
		});

		arabicJoDelete = new REButton("arabicJoDelete", VaadinIcons.TRASH);
		arabicJoDelete.addClickListener(e -> {
			arabicJo.clear();
		});

		arabicSaDelete = new REButton("arabicSaDelete", VaadinIcons.TRASH);
		arabicSaDelete.addClickListener(e -> {
			arabicSa.clear();
		});

		azerbaijaniAzDelete = new REButton("azerbaijaniAzDelete", VaadinIcons.TRASH);
		azerbaijaniAzDelete.addClickListener(e -> {
			azerbaijaniAz.clear();
		});

		bulgarianBgDelete = new REButton("bulgarianBgDelete", VaadinIcons.TRASH);
		bulgarianBgDelete.addClickListener(e -> {
			bulgarianBg.clear();
		});

		englishUsDelete = new REButton("englishUsDelete", VaadinIcons.TRASH);
		englishUsDelete.addClickListener(e -> {
			englishUs.clear();
		});

		frenchFrDelete = new REButton("frenchFrDelete", VaadinIcons.TRASH);
		frenchFrDelete.addClickListener(e -> {
			frenchFr.clear();
		});

		georgianGeDelete = new REButton("georgianGeDelete", VaadinIcons.TRASH);
		georgianGeDelete.addClickListener(e -> {
			georgianGe.clear();
		});

		germanDeDelete = new REButton("germanDeDelete", VaadinIcons.TRASH);
		germanDeDelete.addClickListener(e -> {
			germanDe.clear();
		});

		persianIrDelete = new REButton("persianIrDelete", VaadinIcons.TRASH);
		persianIrDelete.addClickListener(e -> {
			persianIr.clear();
		});

		romanianRoDelete = new REButton("romanianRoDelete", VaadinIcons.TRASH);
		romanianRoDelete.addClickListener(e -> {
			romanianRo.clear();
		});

		russianRuDelete = new REButton("russianRuDelete", VaadinIcons.TRASH);
		russianRuDelete.addClickListener(e -> {
			russianRu.clear();
		});

		turkishTrDelete = new REButton("turkishTrDelete", VaadinIcons.TRASH);
		turkishTrDelete.addClickListener(e -> {
			turkishTr.clear();
		});

		turkmenTmDelete = new REButton("turkmenTmDelete", VaadinIcons.TRASH);
		turkmenTmDelete.addClickListener(e -> {
			turkmenTm.clear();
		});

	}

	private REHorizontalLayout initREHorizontalLayout(String id, Component... components) {
		REHorizontalLayout layout = new REHorizontalLayout();
		layout.setStyleName("top-bar");
		layout.setSpacing(false);
		layout.setWidth("100%");
		layout.setId(id);
		layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		layout.addComponents(components);
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
		binder.forField(dictionaryId).withNullRepresentation("")
				.withConverter(new StringToIntegerConverter("must be integer"))
				.bind(ReResourceitem::getDictionaryId, ReResourceitem::setDictionaryId);
		super.bindFormFields(binder);
	}

	public void openDictionaryWindow(Map<UIParameter, Object> windowParameters) throws LocalizedException {
		try {
			windowDictionary.open(windowParameters);
		} catch (REWindowNotAbleToOpenException e) {
			windowDictionary.close();
			RENotification.showNotification(e.getMessage(), NotifyType.ERROR);
		}
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

	public RETextField getDictionaryId() {
		return dictionaryId;
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

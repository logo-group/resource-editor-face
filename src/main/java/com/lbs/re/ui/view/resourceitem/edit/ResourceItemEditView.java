package com.lbs.re.ui.view.resourceitem.edit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.lbs.re.data.service.ResourceitemService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReResourceitem;
import com.lbs.re.model.ReUser;
import com.lbs.re.ui.components.CustomExceptions.REWindowNotAbleToOpenException;
import com.lbs.re.ui.components.basic.REButton;
import com.lbs.re.ui.components.basic.RENumberField;
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
import com.lbs.re.util.EnumsV2.Languages;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;

@SpringView
public class ResourceItemEditView extends AbstractEditView<ReResourceitem, ResourceitemService, ResourceItemEditPresenter, ResourceItemEditView> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int READ = 1;
	private static final int WRITE = 2;
	private static final int DELETE = 4;

	private REHorizontalLayoutWithState albanianKvLayout;
	private REHorizontalLayoutWithState arabicEgLayout;
	private REHorizontalLayoutWithState arabicJoLayout;
	private REHorizontalLayoutWithState arabicSaLayout;
	private REHorizontalLayoutWithState azerbaijaniAzLayout;
	private REHorizontalLayoutWithState bulgarianBgLayout;
	private REHorizontalLayoutWithState englishUsLayout;
	private REHorizontalLayoutWithState frenchFrLayout;
	private REHorizontalLayoutWithState georgianGeLayout;
	private REHorizontalLayoutWithState germanDeLayout;
	private REHorizontalLayoutWithState persianIrLayout;
	private REHorizontalLayoutWithState romanianRoLayout;
	private REHorizontalLayoutWithState russianRuLayout;
	private REHorizontalLayoutWithState turkishTrLayout;
	private REHorizontalLayoutWithState turkmenTmLayout;
	private REHorizontalLayoutWithState standardLayout;

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
	private REHorizontalLayout standardButtonLayout;

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
	private REButton standardDelete;

	private RETextField ordernr;
	private RETextField tagnr;
	private RETextField levelnr;
	private RETextField prefixstr;
	private RETextArea info;
	private RENumberField count;
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
	private RETextArea standard;
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

		getCancel().setVisible(false);
		getSave().setVisible(false);

		getPresenter().setView(this);
		initSections();
	}

	private void initSections() {
		List<REHorizontalLayoutWithState> list = new ArrayList<REHorizontalLayoutWithState>();
		List<REHorizontalLayoutWithState> standardList = new ArrayList<REHorizontalLayoutWithState>();
		if (turkishTrLayout.isRemove() == false) {
			list.add(turkishTrLayout);
		}
		if (englishUsLayout.isRemove() == false) {
			list.add(englishUsLayout);
		}
		if (albanianKvLayout.isRemove() == false) {
			list.add(albanianKvLayout);
		}
		if (arabicEgLayout.isRemove() == false) {
			list.add(arabicEgLayout);
		}
		if (arabicJoLayout.isRemove() == false) {
			list.add(arabicJoLayout);
		}
		if (arabicSaLayout.isRemove() == false) {
			list.add(arabicSaLayout);
		}
		if (azerbaijaniAzLayout.isRemove() == false) {
			list.add(azerbaijaniAzLayout);
		}
		if (bulgarianBgLayout.isRemove() == false) {
			list.add(bulgarianBgLayout);
		}
		if (frenchFrLayout.isRemove() == false) {
			list.add(frenchFrLayout);
		}
		if (georgianGeLayout.isRemove() == false) {
			list.add(georgianGeLayout);
		}
		if (germanDeLayout.isRemove() == false) {
			list.add(germanDeLayout);
		}
		if (persianIrLayout.isRemove() == false) {
			list.add(persianIrLayout);
		}
		if (romanianRoLayout.isRemove() == false) {
			list.add(romanianRoLayout);
		}
		if (russianRuLayout.isRemove() == false) {
			list.add(russianRuLayout);
		}
		if (turkmenTmLayout.isRemove() == false) {
			list.add(turkmenTmLayout);
		}
		if (standardLayout.isRemove() == false) {
			standardList.add(standardLayout);
		}
		REHorizontalLayoutWithState[] array = new REHorizontalLayoutWithState[list.size()];
		REHorizontalLayoutWithState[] standardArray = new REHorizontalLayoutWithState[standardList.size()];
		list.toArray(array);
		standardList.toArray(standardArray);
		addSection(getLocaleValue("view.viewedit.section.general"), 0, null, ordernr, tagnr, levelnr, prefixstr, count, info, btnDictionary, dictionaryId);
		addSection("languages", getLocaleValue("view.viewedit.section.languages"), 1, null, array);
		addSection("standards", getLocaleValue("view.viewedit.section.standard"), 2, null, standardArray);
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
		ordernr = new RETextField("column.resource.item.order.number", "half", true, true, 10);
		tagnr = new RETextField("column.resource.item.tag.number", "half", true, true, 10);
		levelnr = new RETextField("column.resource.item.level.number", "half", true, true, 10);
		prefixstr = new RETextField("column.resource.item.prefix", "half", true, true);
		info = new RETextArea("column.resource.item.info", "full", true, true);
		dictionaryId = new RETextField("column.resource.item.dictionary", "full", true, false);
		count = new RENumberField("column.resource.item.count", "full", true, true);
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
		standardButtonLayout = initREHorizontalLayout("standardButtonLayout", standardDelete);
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
		standardLayout = initREHorizontalLayout("standardLayout", standardButtonLayout, standard);

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
		checkForUserRight(sessionUser.getStdaccessrights(), standardLayout);
	}

	private void checkForUserRight(int userValue, REHorizontalLayoutWithState layout) {
		if ((userValue == 0) || (userValue % 2 != READ)) { // no read right
			layout.setRemove(true);
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
		standard = new RETextArea("column.resource.item.standard", "full", true, true);
	}

	private void initDeleteButtons() {
		albanianKvDelete = new REButton("albanianKvDelete", VaadinIcons.TRASH);
		albanianKvDelete.addClickListener(e -> {
			getPresenter().confirmDelete(albanianKv, Languages.ALBANIAN);
		});

		arabicEgDelete = new REButton("arabicEgDelete", VaadinIcons.TRASH);
		arabicEgDelete.addClickListener(e -> {
			getPresenter().confirmDelete(arabicEg, Languages.ARABICEG);
		});

		arabicJoDelete = new REButton("arabicJoDelete", VaadinIcons.TRASH);
		arabicJoDelete.addClickListener(e -> {
			getPresenter().confirmDelete(arabicJo, Languages.ARABISJO);
		});

		arabicSaDelete = new REButton("arabicSaDelete", VaadinIcons.TRASH);
		arabicSaDelete.addClickListener(e -> {
			getPresenter().confirmDelete(arabicSa, Languages.ARABICSA);
		});

		azerbaijaniAzDelete = new REButton("azerbaijaniAzDelete", VaadinIcons.TRASH);
		azerbaijaniAzDelete.addClickListener(e -> {
			getPresenter().confirmDelete(azerbaijaniAz, Languages.AZARBAIJAN);
		});

		bulgarianBgDelete = new REButton("bulgarianBgDelete", VaadinIcons.TRASH);
		bulgarianBgDelete.addClickListener(e -> {
			getPresenter().confirmDelete(bulgarianBg, Languages.BULGARIAN);
		});

		englishUsDelete = new REButton("englishUsDelete", VaadinIcons.TRASH);
		englishUsDelete.addClickListener(e -> {
			getPresenter().confirmDelete(englishUs, Languages.ENGLISH);
		});

		frenchFrDelete = new REButton("frenchFrDelete", VaadinIcons.TRASH);
		frenchFrDelete.addClickListener(e -> {
			getPresenter().confirmDelete(frenchFr, Languages.FRENCH);
		});

		georgianGeDelete = new REButton("georgianGeDelete", VaadinIcons.TRASH);
		georgianGeDelete.addClickListener(e -> {
			getPresenter().confirmDelete(georgianGe, Languages.GEORGIAN);
		});

		germanDeDelete = new REButton("germanDeDelete", VaadinIcons.TRASH);
		germanDeDelete.addClickListener(e -> {
			getPresenter().confirmDelete(germanDe, Languages.GERMAN);
		});

		persianIrDelete = new REButton("persianIrDelete", VaadinIcons.TRASH);
		persianIrDelete.addClickListener(e -> {
			getPresenter().confirmDelete(persianIr, Languages.PERSIAN);
		});

		romanianRoDelete = new REButton("romanianRoDelete", VaadinIcons.TRASH);
		romanianRoDelete.addClickListener(e -> {
			getPresenter().confirmDelete(romanianRo, Languages.ROMANIAN);
		});

		russianRuDelete = new REButton("russianRuDelete", VaadinIcons.TRASH);
		russianRuDelete.addClickListener(e -> {
			getPresenter().confirmDelete(russianRu, Languages.RUSSIAN);
		});

		turkishTrDelete = new REButton("turkishTrDelete", VaadinIcons.TRASH);
		turkishTrDelete.addClickListener(e -> {
			getPresenter().confirmDelete(turkishTr, Languages.TURKISH);
		});

		turkmenTmDelete = new REButton("turkmenTmDelete", VaadinIcons.TRASH);
		turkmenTmDelete.addClickListener(e -> {
			getPresenter().confirmDelete(turkmenTm, Languages.TURKMEN);
		});

		standardDelete = new REButton("standardDelete", VaadinIcons.TRASH);
		standardDelete.addClickListener(e -> {
			getPresenter().confirmDelete(standard, Languages.TURKMEN);
		});

	}

	private REHorizontalLayoutWithState initREHorizontalLayout(String id, Component... components) {
		REHorizontalLayoutWithState layout = new REHorizontalLayoutWithState();
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
		binder.forField(ordernr).asRequired().withNullRepresentation("").withConverter(new StringToIntegerConverter(getLocaleValue("message.enterIntegerValue")))
				.bind(ReResourceitem::getOrdernr, ReResourceitem::setOrdernr);
		binder.forField(tagnr).asRequired().withNullRepresentation("").withConverter(new StringToIntegerConverter(getLocaleValue("message.enterIntegerValue")))
				.bind(ReResourceitem::getTagnr, ReResourceitem::setTagnr);
		binder.forField(levelnr).asRequired().withNullRepresentation("").withConverter(new StringToIntegerConverter(getLocaleValue("message.enterIntegerValue")))
				.bind(ReResourceitem::getLevelnr, ReResourceitem::setLevelnr);
		binder.forField(dictionaryId).withNullRepresentation("").withConverter(new StringToIntegerConverter(getLocaleValue("message.enterIntegerValue")))
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

	public RETextArea getStandard() {
		return standard;
	}

	public RENumberField getCount() {
		return count;
	}

	@Override
	public String getViewOperationName() {
		return Operation.VIEW_RESOURCE_ITEM;
	}

	@Override
	public String getEditOperationName() {
		return Operation.EDIT_RESOURCE_ITEM;
	}

	@Override
	public String getAddOperationName() {
		return Operation.NO_CHECK;
	}

	@Override
	protected void logError(LocalizedException e) {
		getLogger().error(e.getLocalizedMessage(), e);
		RENotification.showNotification(e.getLocalizedMessage(), NotifyType.ERROR);
	}

	public void showTagUniqueException() {
		RENotification.showNotification(getLocaleValue("view.abstractedit.messages.UniqueTagException"), NotifyType.ERROR);
	}

}

class REHorizontalLayoutWithState extends REHorizontalLayout {

	private static final long serialVersionUID = 1L;

	private boolean remove = false;

	public boolean isRemove() {
		return remove;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}
}
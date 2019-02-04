package com.lbs.re.ui.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.lbs.re.data.service.BaseService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.exception.localized.OperationNotAuthedException;
import com.lbs.re.localization.ResourceEditorLocalizerWrapper;
import com.lbs.re.model.AbstractBaseEntity;
import com.lbs.re.ui.components.basic.REButton;
import com.lbs.re.ui.components.basic.REDateTimeField;
import com.lbs.re.ui.components.basic.RELabel;
import com.lbs.re.ui.components.basic.RETextField;
import com.lbs.re.ui.components.grid.REGrid;
import com.lbs.re.ui.components.layout.RECssLayout;
import com.lbs.re.ui.components.layout.REHorizontalLayout;
import com.lbs.re.ui.components.layout.REVerticalLayout;
import com.lbs.re.ui.util.Enums.UIParameter;
import com.lbs.re.ui.util.Enums.ViewMode;
import com.lbs.re.ui.util.RENotification;
import com.lbs.re.ui.util.RENotification.NotifyType;
import com.lbs.re.ui.util.REStatic;
import com.lbs.re.util.Constants;
import com.lbs.re.util.HasLogger;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewBeforeLeaveEvent;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public abstract class AbstractEditView<T extends AbstractBaseEntity, S extends BaseService<T, Integer>, P extends AbstractEditPresenter<T, S, P, V>, V extends AbstractEditView<T, S, P, V>>
		extends VerticalLayout implements Serializable, View, HasLogger, ResourceEditorLocalizerWrapper {

	private static final long serialVersionUID = 1L;

	private transient P presenter;

	private ViewMode viewMode;

	private REButton btnCancel;
	private REButton btnSave;

	private RETextField createdUser;
	private REDateTimeField dateCreated;
	private RETextField updatedUser;
	private REDateTimeField dateUpdated;
	private RETextField id;

	private Accordion accordion;

	private RELabel lblHeader;

	private RELabel lblFolder;

	private List<REGrid<?>> gridList;

	public AbstractEditView(P presenter) {
		this.presenter = presenter;
		initComponents();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		collectGrids();
		getPresenter().laodGridPreference(gridList);
		String parameter = event.getParameters();
		try {
			if (parameter.contains("new")) {
				getPresenter().checkForAddOperation();
				Map<UIParameter, Object> uiParameterMap = buildUIParameterMap(parameter);
				getPresenter().enterView(uiParameterMap);
			} else {
				getPresenter().checkForViewOperation();
				getPresenter().enterView(REStatic.getUIParameterMap(Integer.valueOf(parameter), ViewMode.VIEW));
			}
		} catch (OperationNotAuthedException e) {
			removeAllComponents();
			logError(e);
		} catch (LocalizedException e) {
			logError(e);
		}
	}

	private Map<UIParameter, Object> buildUIParameterMap(String parameter) {
		Map<UIParameter, Object> uiParameterMap = REStatic.getUIParameterMap(0, ViewMode.NEW);
		if (parameter.contains("?")) {
			String splittedParam = parameter.split("\\?")[1];
			String[] uiParameters = splittedParam.split(Constants.EQUAL);
			UIParameter uiParameter = UIParameter.valueOf(uiParameters[0].toUpperCase());
			switch (uiParameter) {
			case FOLDER:
				uiParameterMap.put(UIParameter.FOLDER, Integer.valueOf(uiParameters[1]));
				break;
			case ENVIRONMENT:
				uiParameterMap.put(UIParameter.ENVIRONMENT, Integer.valueOf(uiParameters[1]));
				break;
			default:
				break;
			}
		}
		return uiParameterMap;
	}

	public abstract String getHeader();

	private void initComponents() {
		// setStyleName("crud-template");
		setResponsive(true);
		setSpacing(false);
		// setSizeFull();
		setMargin(new MarginInfo(false, true, true, true));

		accordion = new Accordion();
		accordion.setResponsive(true);
		accordion.setSizeFull();

		addComponents(initHeader(), initFolder(), accordion);
		setExpandRatio(accordion, 1);
		initBasicFormLayout();

		getCancel().addClickListener(e -> getPresenter().backPressed());
		getSave().addClickListener(e -> {
			try {
				if (viewMode == ViewMode.VIEW) {
					getPresenter().checkForEditOperation();
				}
				getPresenter().okPressed();
			} catch (LocalizedException e1) {
				logError(e1);
			}
		});
	}

	/**
	 * Get presenter instance.
	 *
	 * @return Presenter instance.
	 */
	protected P getPresenter() {
		return presenter;
	}

	private HorizontalLayout initHeader() {
		REHorizontalLayout hLayHeader = new REHorizontalLayout();
		hLayHeader.setSizeFull();
		hLayHeader.setId("reportHeader");

		btnCancel = new REButton("general.button.cancel", VaadinIcons.CLOSE_SMALL);
		btnCancel.setWidthUndefined();
		btnCancel.addStyleName("cancel");
		btnSave = new REButton("general.button.save", VaadinIcons.THUMBS_UP);
		btnSave.setWidthUndefined();
		btnSave.addStyleName("primary icon-align-right");
		lblHeader = new RELabel(getHeader());
		lblHeader.setStyleName("h2 colored");
		hLayHeader.addComponents(lblHeader, btnCancel, btnSave);
		hLayHeader.setComponentAlignment(btnCancel, Alignment.MIDDLE_LEFT);
		hLayHeader.setComponentAlignment(btnSave, Alignment.MIDDLE_RIGHT);
		hLayHeader.setExpandRatio(lblHeader, 1);
		return hLayHeader;
	}

	private REVerticalLayout initFolder() {
		REVerticalLayout folderHeader = new REVerticalLayout();
		folderHeader.setSizeFull();
		folderHeader.setId("folderHeader");

		lblFolder = new RELabel();
		lblFolder.setStyleName("h3 colored");
		folderHeader.addComponent(lblFolder);
		return folderHeader;
	}

	private void initBasicFormLayout() {
		id = new RETextField("textfield.id", "full", true, false);
		createdUser = new RETextField("textfield.createduser", "half", true, false);
		dateCreated = new REDateTimeField("textfield.createddate", "half", true, false);
		updatedUser = new RETextField("textfield.updateduser", "half", true, false);
		dateUpdated = new REDateTimeField("textfield.updateddate", "half", true, false);
		addSection(getLocaleValue("section.userproperties"), 0, null, id, createdUser, dateCreated, updatedUser, dateUpdated);
	}

	protected void addSection(String title, int position, Resource icon, Component... c) {
		RECssLayout cssLayout = new RECssLayout();
		cssLayout.setStyleName("responsive");
		cssLayout.setResponsive(true);
		cssLayout.setSizeFull();
		for (int i = 0; i < c.length; i++) {
			cssLayout.addComponent(wrapWithHorizontalLayout(c[i]));
		}
		addSectionToAccordion(title, position, icon, cssLayout);
	}

	protected void addSection(String id, String title, int position, Resource icon, Component... c) {
		RECssLayout cssLayout = new RECssLayout();
		cssLayout.setStyleName("responsive");
		cssLayout.setResponsive(true);
		cssLayout.setSizeFull();
		for (int i = 0; i < c.length; i++) {
			cssLayout.addComponent(wrapWithHorizontalLayout(c[i]));
		}
		addSectionToAccordion(title, position, icon, cssLayout);
	}

	private void addSectionToAccordion(String title, int position, Resource icon, RECssLayout cssLayout) {
		VerticalLayout vlayTemp = new VerticalLayout();
		vlayTemp.setMargin(true);
		vlayTemp.setSpacing(true);
		vlayTemp.addComponent(cssLayout);
		accordion.addTab(vlayTemp, title, icon, position).setStyleName(com.lbs.re.ui.util.Constants.TEDAM_ACCORDION_TAB_CSS);
	}

	private Component wrapWithHorizontalLayout(Component c) {
		HorizontalLayout vlayTemp = new HorizontalLayout();
		vlayTemp.setSizeFull();
		vlayTemp.setStyleName(c.getStyleName());
		c.removeStyleName(c.getStyleName());
		c.setSizeFull();
		vlayTemp.setMargin(false);
		vlayTemp.setSpacing(false);
		vlayTemp.addComponent(c);
		return vlayTemp;
	}

	@Override
	public void beforeLeave(ViewBeforeLeaveEvent event) {
		getPresenter().beforeLeavingView(event);
		getPresenter().saveGridPreference(gridList);
	}

	public REButton getCancel() {
		return btnCancel;
	}

	public REButton getSave() {
		return btnSave;
	}

	public ViewMode getViewMode() {
		return viewMode;
	}

	protected void setViewMode(ViewMode viewMode) {

		// Allow to style different modes separately
		if (getViewMode() != null) {
			removeStyleName(this.getViewMode().name().toLowerCase());
		}
		addStyleName(viewMode.name().toLowerCase());
		this.viewMode = viewMode;
		getBinder().setReadOnly(viewMode == ViewMode.VIEW);
		if (viewMode == ViewMode.VIEW) {
			getCancel().setCaption(getLocaleValue("general.button.back"));
			getCancel().setIcon(VaadinIcons.ANGLE_LEFT);
			getSave().setCaption(getLocaleValue("general.button.edit"));
			getSave().setIcon(VaadinIcons.EDIT);
		} else if (viewMode == ViewMode.NEW) {
			getCancel().setCaption(getLocaleValue("general.button.cancel"));
			getCancel().setIcon(VaadinIcons.ANGLE_LEFT);
			getSave().setCaption(getLocaleValue("general.button.save"));
		} else if (viewMode == ViewMode.EDIT) {
			getCancel().setCaption(getLocaleValue("general.button.cancel"));
			getCancel().setIcon(VaadinIcons.CLOSE);
			getSave().setCaption(getLocaleValue("general.button.update"));
		} else {
			throw new IllegalArgumentException("Unknown mode " + viewMode);
		}

	}

	public Accordion getAccordion() {
		return accordion;
	}

	public BeanValidationBinder<T> getBinder() {
		return getPresenter().getBinder();
	}

	public Stream<Object> validate() {
		Stream<Object> errorFields = getBinder().validate().getFieldValidationErrors().stream().map(BindingValidationStatus::getField);
		return errorFields;
	}

	public void bindFormFields(BeanValidationBinder<T> binder) {
		binder.forField(id).withNullRepresentation("").withConverter(new StringToIntegerConverter(Integer.valueOf(0), "")).bind(T::getId, T::setId);
		getBinder().bindInstanceFields(this);
	}

	public void showNotFound() {
		removeAllComponents();
		addComponent(new Label("Item not found"));
	}

	public void showNotAuthorized() {
		removeAllComponents();
		addComponent(new Label(getLocaleValue("view.usersettings.notauthorized")));
	}

	public void showDataIntegrityException() {
		RENotification.showNotification(getLocaleValue("view.abstractedit.messages.DataIntegrityViolationException"), NotifyType.ERROR);
	}

	protected void logError(LocalizedException e) {
		getLogger().error(e.getLocalizedMessage(), e);
		RENotification.showNotification(e.getLocalizedMessage(), NotifyType.ERROR);
	}

	protected RELabel getLblHeader() {
		return lblHeader;
	}

	public String getTitle() {
		return getHeader();
	}

	public void setTitle(String title) {
		getLblHeader().setValue(title);
	}

	public void showSuccessfulSave() {
		RENotification.showNotification(getLocaleValue("view.abstractedit.messages.SuccessfulSave"), NotifyType.SUCCESS);
	}

	public void showSuccessfulUpdate() {
		RENotification.showNotification(getLocaleValue("view.abstractedit.messages.SuccessfulUpdate"), NotifyType.SUCCESS);
	}

	public List<REGrid<?>> getGridList() {
		return gridList;
	}

	public void setGridList(List<REGrid<?>> gridList) {
		this.gridList = gridList;
	}

	protected void collectGrids() {
		gridList = new ArrayList<REGrid<?>>();
	}

	public RELabel getLblFolder() {
		return lblFolder;
	}

	public void setLblFolder(RELabel lblFolder) {
		this.lblFolder = lblFolder;
	}

	public RETextField getCreatedUser() {
		return createdUser;
	}

	public REDateTimeField getDateCreated() {
		return dateCreated;
	}

	public RETextField getUpdatedUser() {
		return updatedUser;
	}

	public REDateTimeField getDateUpdated() {
		return dateUpdated;
	}

	public abstract String getViewOperationName();

	public abstract String getEditOperationName();

	public abstract String getAddOperationName();
}

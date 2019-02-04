package com.lbs.re.ui.view;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.lbs.re.app.security.SecurityUtils;
import com.lbs.re.data.service.BaseService;
import com.lbs.re.data.service.GridPreferenceService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.localization.ResourceEditorLocalizerWrapper;
import com.lbs.re.model.AbstractBaseEntity;
import com.lbs.re.model.GridPreference;
import com.lbs.re.ui.AppUI;
import com.lbs.re.ui.components.basic.REButton;
import com.lbs.re.ui.components.basic.RELabel;
import com.lbs.re.ui.components.grid.REFilterGrid;
import com.lbs.re.ui.components.grid.REGridConfig;
import com.lbs.re.ui.components.layout.REHorizontalLayout;
import com.lbs.re.ui.dialog.ConfirmationListener;
import com.lbs.re.ui.dialog.REDialog;
import com.lbs.re.ui.util.RENotification;
import com.lbs.re.ui.util.RENotification.NotifyType;
import com.lbs.re.ui.util.REStatic;
import com.lbs.re.util.HasLogger;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewBeforeLeaveEvent;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.themes.ValoTheme;

public abstract class AbstractGridView<T extends AbstractBaseEntity, S extends BaseService<T, Integer>, P extends AbstractGridPresenter<T, S, P, V>, V extends AbstractGridView<T, S, P, V>>
		extends VerticalLayout implements Serializable, View, HasLogger, ResourceEditorLocalizerWrapper {

	private static final long serialVersionUID = 1L;

	/**
	 * Id for topBarLayout.
	 */
	private static final String ID_TOPBAR_LAYOUT = "topBarLayout";

	/**
	 * Id for innerLayout.
	 */
	private static final String ID_INNER_LAYOUT = "innerLayout";

	/**
	 * Id for gridLabel.
	 */
	private static final String ID_GRID_LABEL = "gridLabel";

	/**
	 * Id for grid.
	 */
	private static final String ID_GRID = "grid";

	/**
	 * Id for gridLayout.
	 */
	private static final String ID_GRID_LAYOUT = "gridLayout";

	/**
	 * Label of grid.
	 */
	private RELabel gridLabel;

	/**
	 * Add new button.
	 */
	private REButton addButton;

	/**
	 * Add new button.
	 */
	private REButton deleteButton;

	/**
	 * Clear filter button.
	 */
	private REButton clearFilterButton;

	/**
	 * Layout that grid is put into.
	 */
	private HorizontalSplitPanel gridLayout;

	/**
	 * Grid instance for grid view.
	 */
	private REFilterGrid<T> grid;

	/**
	 * Presenter instance for presenter.
	 */
	private transient P presenter;

	/**
	 * Top bar layout that contains label, add new button.
	 */
	private REHorizontalLayout topBarLayout;

	/**
	 * Layout that contains label.
	 */
	private REHorizontalLayout innerLayout;

	/**
	 * selectionMode for grid
	 */
	private SelectionMode selectionMode;

	@Autowired
	private GridPreferenceService gridPreferenceService;

	public AbstractGridView(P presenter, SelectionMode selectionMode) {
		this.presenter = presenter;
		this.selectionMode = selectionMode;
	}

	/**
	 * Get presenter instance.
	 *
	 * @return Presenter instance.
	 */
	protected P getPresenter() {
		return presenter;
	}

	/**
	 * Gets grid config.
	 *
	 * @return TedamGridConfig instance.
	 */
	protected abstract REGridConfig<T> getTedamGridConfig();

	/**
	 * Gets edit view class to navigate from grid.
	 *
	 * @return Edit view to navigate.
	 */
	protected abstract Class<? extends View> getEditView();

	/**
	 * Inits base vertical layout.
	 */
	private void initVerticalLayout() {
		setStyleName("crud-template");
		setResponsive(true);
		setSpacing(false);
		setSizeFull();
		setMargin(false);
	}

	/**
	 * Inits top bar layout.
	 */
	private void initTopBarLayout() {
		topBarLayout = new REHorizontalLayout();
		topBarLayout.setStyleName("top-bar");
		topBarLayout.setSpacing(false);
		topBarLayout.setWidth("100%");
		topBarLayout.setId(ID_TOPBAR_LAYOUT);
	}

	/**
	 * Inits inner layout.
	 */
	private void initInnerLayout() {
		innerLayout = new REHorizontalLayout();
		innerLayout.setSpacing(false);
		innerLayout.setWidth("100%");
		innerLayout.setId(ID_INNER_LAYOUT);
	}

	/**
	 * Inits clear filter button.
	 */
	private void initClearFilterButton() {
		clearFilterButton = new REButton("general.button.clearFilter", VaadinIcons.ERASER);
		clearFilterButton.setCaption("");
		clearFilterButton.setWidthUndefined();
		clearFilterButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				grid.getFilter().clearAllFilters();
			}
		});
	}

	/**
	 * Inits add new button.
	 */
	private void initAddButton() {
		addButton = new REButton("general.button.add", VaadinIcons.PLUS);
		addButton.addStyleName("friendly");
		addButton.setCaption("");
		addButton.setWidthUndefined();
		addButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					addButtonClickEvent();
				} catch (LocalizedException e) {
					logError(e);
				}
			}
		});
	}

	public void addButtonClickEvent() throws LocalizedException {
		if (getEditView() != null) {
			getPresenter().checkForAddOperation();
			getPresenter().getNavigationManager().navigateTo(getEditView(), "new");
		}
	}

	/**
	 * Inits add new button.
	 */
	private void initRemoveButtonButton() {
		deleteButton = new REButton("general.button.deleteAll", VaadinIcons.MINUS);
		deleteButton.addStyleName("danger");
		deleteButton.setCaption("");
		deleteButton.setWidthUndefined();
		deleteButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Set<T> selectedItems = getGrid().getSelectedItems();
				if (selectedItems.size() > 0) {
					REDialog.confirm(AppUI.getCurrent(), new ConfirmationListener() {

						@Override
						public void onConfirm() {
							try {
								getPresenter().checkForDeleteOperation();
								getPresenter().deleteSelectedLines(selectedItems);
								getGrid().deselectAll();
								getGrid().getDataProvider().refreshAll();
							} catch (LocalizedException e) {
								logError(e);
							}
						}

						@Override
						public void onCancel() {
						}
					}, getLocaleValue("confirm.message.delete"), getLocaleValue("general.button.ok"), getLocaleValue("general.button.cancel"));
				}

			}
		});
	}

	/**
	 * Inits grid label.
	 */
	private void initGridLabel() {
		gridLabel = new RELabel("");
		gridLabel.setStyleName(ValoTheme.LABEL_H3 + " bold");
		gridLabel.setId(ID_GRID_LABEL);
	}

	/**
	 * Inits grid layout.
	 */
	private void initGridLayout() {
		gridLayout = new HorizontalSplitPanel();
		gridLayout.setId("listParent");
		gridLayout.setSizeFull();
		gridLayout.setId(ID_GRID_LAYOUT);
	}

	/**
	 * Inits grid.
	 */
	protected void initGrid() {
		AbstractDataProvider<T> dataPovider = getPresenter().getDataPovider();
		grid = new REFilterGrid<T>(getTedamGridConfig(), dataPovider, selectionMode) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onViewSelected(T item) {
				onGridViewSelected(item);
			}

			@Override
			public void onDeleteSelected(T item) {
				confirmDelete(item);
			}

			@Override
			public List<Component> buildCustomComponentForItem(T item) {
				// TODO Auto-generated method stub
				return buildCustomComponent(item);
			}

		};

		grid.setSizeFull();
		grid.setId(ID_GRID);

		grid.addItemClickListener(new ItemClickListener<T>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void itemClick(ItemClick<T> event) {
				if (event.getMouseEventDetails().isDoubleClick()) {
					onGridViewSelected(event.getItem());
				}
			}
		});
	}

	public abstract List<Component> buildCustomComponent(T item);

	/**
	 * Inits all components.
	 */
	private void initComponents() {
		initVerticalLayout();
		initTopBarLayout();
		initInnerLayout();
		initAddButton();
		initClearFilterButton();
		initRemoveButtonButton();
		initGridLabel();
		initGridLayout();
		initGrid();
		buildGridColumnDescription();
		innerLayout.addComponent(gridLabel);
		topBarLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		topBarLayout.addComponents(innerLayout, addButton, deleteButton, clearFilterButton);
		topBarLayout.setExpandRatio(innerLayout, 1);
		topBarLayout.setSpacing(true);
		gridLayout.setFirstComponent(grid);
		gridLayout.setSplitPosition(100, Unit.PERCENTAGE);
		gridLayout.setLocked(true);
		addComponents(topBarLayout, gridLayout);
		setExpandRatio(gridLayout, 1);
	}

	protected abstract void buildGridColumnDescription();

	@Override
	public void beforeLeave(ViewBeforeLeaveEvent event) {
		saveGridPreference();
		getPresenter().beforeLeavingView(event);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		laodGridPreference();
		View.super.enter(event);
		try {
			getPresenter().checkForListOperation();
			getPresenter().enterView(REStatic.getUIParameterMap());
		} catch (LocalizedException e) {
			removeAllComponents();
			logError(e);
		}
	}

	/**
	 * Calling after instance created.
	 */
	@PostConstruct
	private void init() {
		initComponents();
	}

	/**
	 * To be called when view selected.
	 *
	 * @param item
	 *            Item
	 */
	public void onGridViewSelected(T item) {
		if (getEditView() != null) {
			getPresenter().getNavigationManager().navigateTo(getEditView(), item.getId());
		}
	}

	/**
	 * To be called when delete selected.
	 *
	 * @param item
	 *            Item
	 * @throws LocalizedException
	 */
	public void onGridDeleteSelected(T item) throws LocalizedException {
		getPresenter().checkForDeleteOperation();
		getPresenter().delete(item);
		getGrid().getDataProvider().refreshAll();
	}

	/**
	 * Executes confirmation for delete operations.
	 *
	 * @param item
	 *            Item
	 */
	private void confirmDelete(T item) {
		REDialog.confirm(AppUI.getCurrent(), new ConfirmationListener() {

			@Override
			public void onConfirm() {
				try {
					onGridDeleteSelected(item);
				} catch (LocalizedException e) {
					logError(e);
				}
			}

			@Override
			public void onCancel() {
			}
		}, getLocaleValue("confirm.message.delete"), getLocaleValue("general.button.ok"), getLocaleValue("general.button.cancel"));
	}

	private void saveGridPreference() {
		try {
			GridPreference gridPreference = findGridPreference();
			if (gridPreference == null) {
				gridPreference = grid.saveGridPreference();
				gridPreference.setUserId(SecurityUtils.getUser().getId());
				gridPreference.setViewId(this.getClass().getName());
			} else {
				gridPreference = grid.saveGridPreference(gridPreference);
			}
			gridPreferenceService.save(gridPreference);
		} catch (LocalizedException e) {
			logError(e);
		}
	}

	private void laodGridPreference() {
		try {
			GridPreference gridPreference = findGridPreference();
			grid.loadGridPreference(gridPreference);
		} catch (LocalizedException e) {
			logError(e);
		}
	}

	private GridPreference findGridPreference() throws LocalizedException {
		Integer userId = SecurityUtils.getUser().getId();
		String viewId = this.getClass().getName();
		String gridId = grid.getId();
		GridPreference gridPreference = gridPreferenceService.findByUserIdAndViewIdAndGridId(userId, viewId, gridId);
		return gridPreference;
	}

	/**
	 * @return the gridLabel
	 */
	protected void setHeader(String header) {
		gridLabel.setValue(header);
	}

	/**
	 * @return the addButton
	 */
	protected REButton getAddButton() {
		return addButton;
	}

	/**
	 * @return the gridLayout
	 */
	public HorizontalSplitPanel getGridLayout() {
		return gridLayout;
	}

	/**
	 * @return the grid
	 */
	public REFilterGrid<T> getGrid() {
		return grid;
	}

	/**
	 * @return the topBarLayout
	 */
	protected REHorizontalLayout getTopBarLayout() {
		return topBarLayout;
	}

	/**
	 * @return the innerLayout
	 */
	protected REHorizontalLayout getInnerLayout() {
		return innerLayout;
	}

	public REButton getDeleteButton() {
		return deleteButton;
	}

	public REButton getClearFilterButton() {
		return clearFilterButton;
	}

	protected void logError(LocalizedException e) {
		getLogger().error(e.getLocalizedMessage(), e);
		RENotification.showNotification(e.getLocalizedMessage(), NotifyType.ERROR);
	}

	public abstract String getListOperationName();

	public abstract String getAddOperationName();

	public abstract String getDeleteOperationName();
}

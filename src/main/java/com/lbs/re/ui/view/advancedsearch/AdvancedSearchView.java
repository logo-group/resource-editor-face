package com.lbs.re.ui.view.advancedsearch;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.lbs.re.localization.ResourceEditorLocalizerWrapper;
import com.lbs.re.ui.components.basic.REButton;
import com.lbs.re.ui.components.basic.RECheckBox;
import com.lbs.re.ui.components.basic.REDateTimeField;
import com.lbs.re.ui.components.basic.RETextField;
import com.lbs.re.ui.components.combobox.ResourceCaseComboBox;
import com.lbs.re.ui.components.combobox.ResourceGroupComboBox;
import com.lbs.re.ui.components.combobox.ResourceTypeComboBox;
import com.lbs.re.ui.components.combobox.SearchFilterComboBox;
import com.lbs.re.ui.components.combobox.StateComboBox;
import com.lbs.re.ui.components.combobox.UserComboBox;
import com.lbs.re.ui.components.layout.REHorizontalLayout;
import com.lbs.re.ui.components.layout.REVerticalLayout;
import com.lbs.re.util.HasLogger;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

@SpringView
public class AdvancedSearchView extends CssLayout implements Serializable, View, HasLogger, ResourceEditorLocalizerWrapper {

	private static final long serialVersionUID = 1L;

	private final AdvancedSearchPresenter advancedSearchPresenter;
	private REVerticalLayout mainLayout;
	private REButton btnSearch;

	private RETextField resourceNrStart;
	private RETextField resourceNrEnd;
	private RETextField orderNrEnd;
	private RETextField orderNrStart;
	private RETextField tagNrStart;
	private RETextField tagNrEnd;
	private RETextField levelNrStart;
	private RETextField levelNrEnd;
	private SearchFilterComboBox descriptionSearchFilterComboBox;
	private SearchFilterComboBox prefixSearchFilterComboBox;
	private SearchFilterComboBox infoSearchFilterComboBox;
	private SearchFilterComboBox turkishSearchFilterComboBox;
	private SearchFilterComboBox englishSearchFilterComboBox;
	private SearchFilterComboBox standardSearchFilterComboBox;
	private RETextField resourceDescription;
	private RETextField prefix;
	private RETextField info;
	private RETextField turkish;
	private RETextField english;
	private RETextField standard;
	private ResourceGroupComboBox resourceGroupComboBox;
	private ResourceTypeComboBox resourceTypeComboBox;
	private ResourceCaseComboBox resourceCaseComboBox;
	private StateComboBox stateComboBox;
	private RECheckBox matchCase;
	private RECheckBox modifiedByMe;
	private UserComboBox modifiedUser;
	private UserComboBox createdUser;
	private REDateTimeField modifiedDateStart;
	private REDateTimeField modifiedDateEnd;

	@Autowired
	public AdvancedSearchView(AdvancedSearchPresenter advancedSearchPresenter, SearchFilterComboBox descriptionSearchFilterComboBox, ResourceGroupComboBox resourceGroupComboBox,
			ResourceTypeComboBox resourceTypeComboBox, ResourceCaseComboBox resourceCaseComboBox, StateComboBox stateComboBox, SearchFilterComboBox prefixSearchFilterComboBox,
			SearchFilterComboBox infoSearchFilterComboBox, SearchFilterComboBox turkishSearchFilterComboBox, SearchFilterComboBox englishSearchFilterComboBox,
			SearchFilterComboBox standardSearchFilterComboBox, UserComboBox modifiedUser, UserComboBox createdUser) {
		this.advancedSearchPresenter = advancedSearchPresenter;
		this.descriptionSearchFilterComboBox = descriptionSearchFilterComboBox;
		this.prefixSearchFilterComboBox = prefixSearchFilterComboBox;
		this.infoSearchFilterComboBox = infoSearchFilterComboBox;
		this.turkishSearchFilterComboBox = turkishSearchFilterComboBox;
		this.englishSearchFilterComboBox = englishSearchFilterComboBox;
		this.standardSearchFilterComboBox = standardSearchFilterComboBox;
		this.resourceGroupComboBox = resourceGroupComboBox;
		this.resourceTypeComboBox = resourceTypeComboBox;
		this.resourceCaseComboBox = resourceCaseComboBox;
		this.stateComboBox = stateComboBox;
		this.modifiedUser = modifiedUser;
		this.createdUser = createdUser;
	}

	@PostConstruct
	public void init() {
		advancedSearchPresenter.setAdvancedSearchView(this);
		initView();
	}

	private void initView() {
		setResponsive(true);
		setWidth("100%");
		addComponents(buildMainLayout());
	}

	private REVerticalLayout buildMainLayout() {
		mainLayout = new REVerticalLayout();
		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);
		mainLayout.setSizeFull();

		Label seperator = new Label("<hr/>", ContentMode.HTML);
		seperator.setWidth(100f, Sizeable.Unit.PERCENTAGE);

		REHorizontalLayout resourceNumberLayout = new REHorizontalLayout();
		resourceNrStart = new RETextField("view.advancedsearch.resourcenumber", "full", true, true);
		resourceNrEnd = new RETextField("view.advancedsearch.empty", "full", true, true);
		resourceNumberLayout.addComponents(resourceNrStart, resourceNrEnd);

		REHorizontalLayout orderNumberLayout = new REHorizontalLayout();
		orderNrStart = new RETextField("view.advancedsearch.ordernumber", "full", true, true);
		orderNrEnd = new RETextField("view.advancedsearch.empty", "full", true, true);
		orderNumberLayout.addComponents(orderNrStart, orderNrEnd);

		REHorizontalLayout tagNumberLayout = new REHorizontalLayout();
		tagNrStart = new RETextField("view.advancedsearch.tagnumber", "full", true, true);
		tagNrEnd = new RETextField("view.advancedsearch.empty", "full", true, true);
		tagNumberLayout.addComponents(tagNrStart, tagNrEnd);

		REHorizontalLayout levelNumberLayout = new REHorizontalLayout();
		levelNrStart = new RETextField("view.advancedsearch.levelnumber", "full", true, true);
		levelNrEnd = new RETextField("view.advancedsearch.empty", "full", true, true);
		levelNumberLayout.addComponents(levelNrStart, levelNrEnd);

		REHorizontalLayout descriptionLayout = new REHorizontalLayout();
		resourceDescription = new RETextField("view.advancedsearch.empty", "full", true, true);
		descriptionSearchFilterComboBox.setCaption(getLocaleValue("view.advancedsearch.description"));
		descriptionLayout.addComponents(descriptionSearchFilterComboBox, resourceDescription);

		REHorizontalLayout groupAndTypeLayout = new REHorizontalLayout();
		groupAndTypeLayout.addComponents(resourceGroupComboBox, resourceTypeComboBox);

		REHorizontalLayout caseAndStateLayout = new REHorizontalLayout();
		caseAndStateLayout.addComponents(resourceCaseComboBox, stateComboBox);

		REHorizontalLayout prefixLayout = new REHorizontalLayout();
		prefix = new RETextField("view.advancedsearch.empty", "full", true, true);
		prefixSearchFilterComboBox.setCaption(getLocaleValue("view.advancedsearch.prefix"));
		prefixLayout.addComponents(prefixSearchFilterComboBox, prefix);

		REHorizontalLayout infoLayout = new REHorizontalLayout();
		info = new RETextField("view.advancedsearch.empty", "full", true, true);
		infoSearchFilterComboBox.setCaption(getLocaleValue("view.advancedsearch.info"));
		infoLayout.addComponents(infoSearchFilterComboBox, info);

		REHorizontalLayout turkishLayout = new REHorizontalLayout();
		turkish = new RETextField("view.advancedsearch.empty", "full", true, true);
		turkishSearchFilterComboBox.setCaption(getLocaleValue("view.advancedsearch.turkish"));
		turkishLayout.addComponents(turkishSearchFilterComboBox, turkish);

		REHorizontalLayout englishLayout = new REHorizontalLayout();
		english = new RETextField("view.advancedsearch.empty", "full", true, true);
		englishSearchFilterComboBox.setCaption(getLocaleValue("view.advancedsearch.english"));
		englishLayout.addComponents(englishSearchFilterComboBox, english);

		REHorizontalLayout standardLayout = new REHorizontalLayout();
		standard = new RETextField("view.advancedsearch.empty", "full", true, true);
		standardSearchFilterComboBox.setCaption(getLocaleValue("view.advancedsearch.standard"));
		standardLayout.addComponents(standardSearchFilterComboBox, standard);

		REHorizontalLayout dateLayout = new REHorizontalLayout();
		modifiedDateStart = new REDateTimeField("view.advancedsearch.modifieddate", "half", false, true);
		modifiedDateEnd = new REDateTimeField("view.advancedsearch.empty", "half", false, true);
		dateLayout.setSpacing(true);
		dateLayout.addComponents(modifiedDateStart, modifiedDateEnd);

		matchCase = new RECheckBox("view.advancedsearch.matchcase", null, true, true);

		modifiedByMe = new RECheckBox("view.advancedsearch.modifiedbyme", null, true, true);

		btnSearch = new REButton("general.button.search", VaadinIcons.SEARCH);
		btnSearch.addClickListener(e -> {
			advancedSearchPresenter.search();
		});
		btnSearch.setClickShortcut(KeyCode.ENTER);

		descriptionSearchFilterComboBox.setWidth("100%");
		prefixSearchFilterComboBox.setWidth("100%");
		infoSearchFilterComboBox.setWidth("100%");
		turkishSearchFilterComboBox.setWidth("100%");
		englishSearchFilterComboBox.setWidth("100%");
		standardSearchFilterComboBox.setWidth("100%");
		resourceGroupComboBox.setWidth("100%");
		resourceTypeComboBox.setWidth("100%");
		resourceCaseComboBox.setWidth("100%");
		stateComboBox.setWidth("100%");

		modifiedUser.setCaption(getLocaleValue("view.advancedsearch.modifieduser"));
		createdUser.setCaption(getLocaleValue("view.advancedsearch.createduser"));

		mainLayout.addComponents(btnSearch, resourceNumberLayout, descriptionLayout, groupAndTypeLayout, caseAndStateLayout, seperator, orderNumberLayout, tagNumberLayout,
				levelNumberLayout, prefixLayout, infoLayout, turkishLayout, englishLayout, standardLayout, createdUser, modifiedUser, dateLayout, matchCase, modifiedByMe);
		return mainLayout;
	}

	public REVerticalLayout getMainLayout() {
		return mainLayout;
	}

	public REButton getBtnSearch() {
		return btnSearch;
	}

	public RETextField getResourceNrStart() {
		return resourceNrStart;
	}

	public RETextField getResourceNrEnd() {
		return resourceNrEnd;
	}

	public RETextField getOrderNrEnd() {
		return orderNrEnd;
	}

	public RETextField getOrderNrStart() {
		return orderNrStart;
	}

	public RETextField getTagNrStart() {
		return tagNrStart;
	}

	public RETextField getTagNrEnd() {
		return tagNrEnd;
	}

	public RETextField getLevelNrStart() {
		return levelNrStart;
	}

	public RETextField getLevelNrEnd() {
		return levelNrEnd;
	}

	public SearchFilterComboBox getDescriptionSearchFilterComboBox() {
		return descriptionSearchFilterComboBox;
	}

	public SearchFilterComboBox getPrefixSearchFilterComboBox() {
		return prefixSearchFilterComboBox;
	}

	public SearchFilterComboBox getInfoSearchFilterComboBox() {
		return infoSearchFilterComboBox;
	}

	public SearchFilterComboBox getTurkishSearchFilterComboBox() {
		return turkishSearchFilterComboBox;
	}

	public SearchFilterComboBox getEnglishSearchFilterComboBox() {
		return englishSearchFilterComboBox;
	}

	public SearchFilterComboBox getStandardSearchFilterComboBox() {
		return standardSearchFilterComboBox;
	}

	public RETextField getResourceDescription() {
		return resourceDescription;
	}

	public RETextField getPrefix() {
		return prefix;
	}

	public RETextField getInfo() {
		return info;
	}

	public RETextField getTurkish() {
		return turkish;
	}

	public RETextField getEnglish() {
		return english;
	}

	public RETextField getStandard() {
		return standard;
	}

	public ResourceGroupComboBox getResourceGroupComboBox() {
		return resourceGroupComboBox;
	}

	public ResourceTypeComboBox getResourceTypeComboBox() {
		return resourceTypeComboBox;
	}

	public ResourceCaseComboBox getResourceCaseComboBox() {
		return resourceCaseComboBox;
	}

	public StateComboBox getStateComboBox() {
		return stateComboBox;
	}

	public RECheckBox getMatchCase() {
		return matchCase;
	}

	public RECheckBox getModifiedByMe() {
		return modifiedByMe;
	}

	public AdvancedSearchPresenter getAdvancedSearchPresenter() {
		return advancedSearchPresenter;
	}

	public UserComboBox getModifiedUser() {
		return modifiedUser;
	}

	public UserComboBox getCreatedUser() {
		return createdUser;
	}

	public REDateTimeField getModifiedDateStart() {
		return modifiedDateStart;
	}

	public REDateTimeField getModifiedDateEnd() {
		return modifiedDateEnd;
	}

}

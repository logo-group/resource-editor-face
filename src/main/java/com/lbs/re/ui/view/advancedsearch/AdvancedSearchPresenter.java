package com.lbs.re.ui.view.advancedsearch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.lbs.re.app.security.SecurityUtils;
import com.lbs.re.data.service.REUserService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReResourceGroup;
import com.lbs.re.ui.view.resourceitem.ResourceItemGridPresenter;
import com.lbs.re.ui.view.resourceitem.edit.ResourceItemDataProvider;
import com.lbs.re.util.EnumsV2.ResourceState;
import com.lbs.re.util.EnumsV2.ResourceType;
import com.lbs.re.util.EnumsV2.SearchFilter;
import com.lbs.re.util.HasLogger;
import com.lbs.re.util.LogoResConstants;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class AdvancedSearchPresenter implements HasLogger, Serializable {

	private static final long serialVersionUID = 1L;

	private AdvancedSearchView advancedSearchView;
	private ResourceItemDataProvider resourceItemDataProvider;
	private ResourceItemGridPresenter resourceItemGridPresenter;
	private REUserService userService;

	@Autowired
	public AdvancedSearchPresenter(ResourceItemDataProvider resourceItemDataProvider, ResourceItemGridPresenter resourceItemGridPresenter, REUserService userService) {
		this.resourceItemDataProvider = resourceItemDataProvider;
		this.resourceItemGridPresenter = resourceItemGridPresenter;
		this.userService = userService;
	}

	public void setAdvancedSearchView(AdvancedSearchView advancedSearchView) {
		this.advancedSearchView = advancedSearchView;
	}

	protected void search() {
		List<Criterion> resourceItemCriterias = generateResourceItemCriterias();
		List<Criterion> turkishCriterias = generateTurkishCriterias();
		List<Criterion> englishCriterias = generateEnglishCriterias();
		List<Criterion> standardCriterias = generateStandardCriterias();
		try {
			resourceItemGridPresenter.getDataPovider().refreshDataProviderByItems(
					resourceItemDataProvider.provideSearchedResourceItems(resourceItemCriterias, turkishCriterias, englishCriterias, standardCriterias));
		} catch (LocalizedException e) {
			e.printStackTrace();
		}
	}

	private boolean isMatchCase() {
		return advancedSearchView.getMatchCase().getValue();
	}

	private boolean isModifiedByMeSelected() {
		return advancedSearchView.getModifiedByMe().getValue();
	}

	private List<Criterion> generateResourceItemCriterias() {
		List<Criterion> criterions = new ArrayList<>();
		try {
			if (!advancedSearchView.getResourceNrStart().getValue().isEmpty()) {
				int resourceNumberStart = Integer.parseInt(advancedSearchView.getResourceNrStart().getValue());
				criterions.add(Restrictions.ge("resource.resourceNr", resourceNumberStart));
			}
			if (!advancedSearchView.getResourceNrEnd().getValue().isEmpty()) {
				int resourceNumberEnd = Integer.parseInt(advancedSearchView.getResourceNrEnd().getValue());
				criterions.add(Restrictions.le("resource.resourceNr", resourceNumberEnd));
			}
			if (!advancedSearchView.getResourceDescription().getValue().isEmpty() || advancedSearchView.getDescriptionSearchFilterComboBox().getValue().equals(SearchFilter.ISEMPTY)
					|| advancedSearchView.getDescriptionSearchFilterComboBox().getValue().equals(SearchFilter.ISNOTEMPTY)) {
				String resourceDescription = advancedSearchView.getResourceDescription().getValue();
				criterions.add(generateCriterion(advancedSearchView.getDescriptionSearchFilterComboBox().getValue(), "resource.description", resourceDescription, isMatchCase()));
			}
			if (advancedSearchView.getResourceGroupComboBox().getValue() != null) {
				ReResourceGroup resourceGroup = advancedSearchView.getResourceGroupComboBox().getValue();
				criterions.add(Restrictions.eq("resource.resourcegroup", resourceGroup));
			}
			ResourceType resourceType = advancedSearchView.getResourceTypeComboBox().getValue();
			criterions.add(Restrictions.eq("resource.resourcetype", resourceType));
			if (advancedSearchView.getStateComboBox().getValue() != null) {
				ResourceState state = advancedSearchView.getStateComboBox().getValue();
				criterions.add(Restrictions.eq("resource.active", state));
			}
			if (!advancedSearchView.getOrderNrStart().getValue().isEmpty()) {
				int orderNumberStart = Integer.parseInt(advancedSearchView.getOrderNrStart().getValue());
				criterions.add(Restrictions.ge("ordernr", orderNumberStart));
			}
			if (!advancedSearchView.getOrderNrEnd().getValue().isEmpty()) {
				int orderNumberEnd = Integer.parseInt(advancedSearchView.getOrderNrEnd().getValue());
				criterions.add(Restrictions.le("ordernr", orderNumberEnd));
			}
			if (!advancedSearchView.getTagNrStart().getValue().isEmpty()) {
				int tagNumberStart = Integer.parseInt(advancedSearchView.getTagNrStart().getValue());
				criterions.add(Restrictions.ge("tagnr", tagNumberStart));
			}
			if (!advancedSearchView.getTagNrEnd().getValue().isEmpty()) {
				int tagNumberEnd = Integer.parseInt(advancedSearchView.getTagNrEnd().getValue());
				criterions.add(Restrictions.le("tagnr", tagNumberEnd));
			}
			if (!advancedSearchView.getLevelNrStart().getValue().isEmpty()) {
				int levelNumberStart = Integer.parseInt(advancedSearchView.getLevelNrStart().getValue());
				criterions.add(Restrictions.ge("levelnr", levelNumberStart));
			}
			if (!advancedSearchView.getLevelNrEnd().getValue().isEmpty()) {
				int levelNumberEnd = Integer.parseInt(advancedSearchView.getLevelNrEnd().getValue());
				criterions.add(Restrictions.le("levelnr", levelNumberEnd));
			}
			if (!advancedSearchView.getPrefix().getValue().isEmpty() || advancedSearchView.getPrefixSearchFilterComboBox().getValue().equals(SearchFilter.ISEMPTY)
					|| advancedSearchView.getPrefixSearchFilterComboBox().getValue().equals(SearchFilter.ISNOTEMPTY)) {
				String prefix = advancedSearchView.getPrefix().getValue();
				criterions.add(generateCriterion(advancedSearchView.getPrefixSearchFilterComboBox().getValue(), "prefixstr", prefix, isMatchCase()));
			}
			if (!advancedSearchView.getInfo().getValue().isEmpty() || advancedSearchView.getInfoSearchFilterComboBox().getValue().equals(SearchFilter.ISEMPTY)
					|| advancedSearchView.getInfoSearchFilterComboBox().getValue().equals(SearchFilter.ISNOTEMPTY)) {
				String info = advancedSearchView.getInfo().getValue();
				criterions.add(generateCriterion(advancedSearchView.getInfoSearchFilterComboBox().getValue(), "info", info, isMatchCase()));
			}
			if (isModifiedByMeSelected()) {
				criterions.add(Restrictions.eq("modifiedby", SecurityUtils.getCurrentUser(userService).getReUser().getId()));
			}
		} catch (NumberFormatException e) {
			// TODO: handle exception
		} catch (LocalizedException e) {
			// TODO: handle exception
		}
		return criterions;
	}

	private List<Criterion> generateTurkishCriterias() {
		List<Criterion> criterions = new ArrayList<>();

		if (advancedSearchView.getTurkish().getValue().isEmpty()) {
			if (advancedSearchView.getTurkishSearchFilterComboBox().getValue().equals(SearchFilter.ISEMPTY)) {
				criterions.add(Restrictions.eq("resourcestr", LogoResConstants.ISEMPTY_CONTROL));
			} else if (advancedSearchView.getTurkishSearchFilterComboBox().getValue().equals(SearchFilter.ISNOTEMPTY)) {
				criterions.add(generateCriterion(SearchFilter.CONTAINS, "resourcestr", "", isMatchCase()));
			}
		} else {
			String turkish = advancedSearchView.getTurkish().getValue();
			criterions.add(generateCriterion(advancedSearchView.getTurkishSearchFilterComboBox().getValue(), "resourcestr", turkish, isMatchCase()));
		}
		return criterions;
	}

	private List<Criterion> generateEnglishCriterias() {
		List<Criterion> criterions = new ArrayList<>();

		if (advancedSearchView.getEnglish().getValue().isEmpty()) {
			if (advancedSearchView.getEnglishSearchFilterComboBox().getValue().equals(SearchFilter.ISEMPTY)) {
				criterions.add(Restrictions.eq("resourcestr", LogoResConstants.ISEMPTY_CONTROL));
			} else if (advancedSearchView.getEnglishSearchFilterComboBox().getValue().equals(SearchFilter.ISNOTEMPTY)) {
				criterions.add(generateCriterion(SearchFilter.CONTAINS, "resourcestr", "", isMatchCase()));
			}
		} else {
			String english = advancedSearchView.getEnglish().getValue();
			criterions.add(generateCriterion(advancedSearchView.getEnglishSearchFilterComboBox().getValue(), "resourcestr", english, isMatchCase()));
		}
		return criterions;
	}

	private List<Criterion> generateStandardCriterias() {
		List<Criterion> criterions = new ArrayList<>();

		if (advancedSearchView.getStandard().getValue().isEmpty()) {
			if (advancedSearchView.getStandardSearchFilterComboBox().getValue().equals(SearchFilter.ISEMPTY)) {
				criterions.add(Restrictions.eq("resourceStr", LogoResConstants.ISEMPTY_CONTROL));
			} else if (advancedSearchView.getStandardSearchFilterComboBox().getValue().equals(SearchFilter.ISNOTEMPTY)) {
				criterions.add(generateCriterion(SearchFilter.CONTAINS, "resourceStr", "", isMatchCase()));
			}
		} else {
			String standard = advancedSearchView.getStandard().getValue();
			criterions.add(generateCriterion(advancedSearchView.getStandardSearchFilterComboBox().getValue(), "resourceStr", standard, isMatchCase()));
		}

		return criterions;
	}

	public Criterion generateCriterion(SearchFilter searchFilter, String propertyName, String parameter, boolean matchCase) {
		switch (searchFilter) {
		case BEGINSWITH:
			return matchCase ? Restrictions.ilike(propertyName, parameter, MatchMode.START) : Restrictions.like(propertyName, parameter, MatchMode.START);
		case CONTAINS:
			return matchCase ? Restrictions.ilike(propertyName, parameter, MatchMode.ANYWHERE) : Restrictions.like(propertyName, parameter, MatchMode.ANYWHERE);
		case ENDSWITH:
			return matchCase ? Restrictions.like(propertyName, parameter, MatchMode.END) : Restrictions.like(propertyName, parameter, MatchMode.END);
		case ISEMPTY:
			return Restrictions.eq(propertyName, "");
		case ISEQUALTO:
			return matchCase ? Restrictions.ilike(propertyName, parameter, MatchMode.EXACT) : Restrictions.like(propertyName, parameter, MatchMode.EXACT);
		case ISNOTEMPTY:
			return Restrictions.not(Restrictions.eq(propertyName, ""));
		case ISNOTEQUALTO:
			return matchCase ? Restrictions.not(Restrictions.ilike(propertyName, parameter, MatchMode.EXACT))
					: Restrictions.not(Restrictions.like(propertyName, parameter, MatchMode.EXACT));
		case NOTBEGINSWITH:
			return matchCase ? Restrictions.not(Restrictions.ilike(propertyName, parameter, MatchMode.START))
					: Restrictions.not(Restrictions.like(propertyName, parameter, MatchMode.START));
		case NOTCONTAIN:
			return matchCase ? Restrictions.not(Restrictions.ilike(propertyName, parameter, MatchMode.ANYWHERE))
					: Restrictions.not(Restrictions.like(propertyName, parameter, MatchMode.ANYWHERE));
		case NOTENDSWITH:
			return matchCase ? Restrictions.not(Restrictions.ilike(propertyName, parameter, MatchMode.END))
					: Restrictions.not(Restrictions.like(propertyName, parameter, MatchMode.END));
		}
		return null;
	}

}

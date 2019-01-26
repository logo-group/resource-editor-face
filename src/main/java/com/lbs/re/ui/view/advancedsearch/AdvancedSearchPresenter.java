package com.lbs.re.ui.view.advancedsearch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.lbs.re.data.service.ResourceitemService;
import com.lbs.re.exception.localized.LocalizedException;
import com.lbs.re.model.ReResourceGroup;
import com.lbs.re.ui.view.resourceitem.ResourceItemGridPresenter;
import com.lbs.re.ui.view.resourceitem.edit.ResourceItemDataProvider;
import com.lbs.re.util.EnumsV2.ResourceState;
import com.lbs.re.util.EnumsV2.ResourceType;
import com.lbs.re.util.EnumsV2.SearchFilter;
import com.lbs.re.util.HasLogger;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class AdvancedSearchPresenter implements HasLogger, Serializable {

	private static final long serialVersionUID = 1L;

	private AdvancedSearchView advancedSearchView;
	private ResourceItemDataProvider resourceItemDataProvider;
	private ResourceitemService resourceItemService;
	private ResourceItemGridPresenter resourceItemGridPresenter;

	@Autowired
	public AdvancedSearchPresenter(ResourceItemDataProvider resourceItemDataProvider, ResourceitemService resourceItemService,
			ResourceItemGridPresenter resourceItemGridPresenter) {
		this.resourceItemDataProvider = resourceItemDataProvider;
		this.resourceItemService = resourceItemService;
		this.resourceItemGridPresenter = resourceItemGridPresenter;
	}

	public void setAdvancedSearchView(AdvancedSearchView advancedSearchView) {
		this.advancedSearchView = advancedSearchView;
	}

	protected void search() {
		List<Criterion> resourceCriterias = generateResourceCriterias();
		List<Criterion> resourceItemCriterias = generateResourceItemCriterias();
		try {
			resourceItemGridPresenter.getDataPovider().refreshDataProviderByItems(resourceItemDataProvider.provideSearchedResourceItems(resourceItemCriterias, resourceCriterias));
		} catch (LocalizedException e) {
			e.printStackTrace();
		}
	}

	private boolean isMatchCase() {
		return advancedSearchView.getMatchCase().getValue();
	}

	private List<Criterion> generateResourceItemCriterias() {
		List<Criterion> criterions = new ArrayList<>();
		try {
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
		} catch (NumberFormatException e) {
			// TODO: handle exception
		}
		return criterions;
	}

	private List<Criterion> generateResourceCriterias() {
		List<Criterion> criterions = new ArrayList<>();
		try {
			if (!advancedSearchView.getResourceNrStart().getValue().isEmpty()) {
				int resourceNumberStart = Integer.parseInt(advancedSearchView.getResourceNrStart().getValue());
				criterions.add(Restrictions.ge("resourceNr", resourceNumberStart));
			}
			if (!advancedSearchView.getResourceNrEnd().getValue().isEmpty()) {
				int resourceNumberEnd = Integer.parseInt(advancedSearchView.getResourceNrEnd().getValue());
				criterions.add(Restrictions.le("resourceNr", resourceNumberEnd));
			}
			if (!advancedSearchView.getResourceDescription().getValue().isEmpty() || advancedSearchView.getDescriptionSearchFilterComboBox().getValue().equals(SearchFilter.ISEMPTY)
					|| advancedSearchView.getDescriptionSearchFilterComboBox().getValue().equals(SearchFilter.ISNOTEMPTY)) {
				String resourceDescription = advancedSearchView.getResourceDescription().getValue();
				criterions.add(generateCriterion(advancedSearchView.getDescriptionSearchFilterComboBox().getValue(), "description", resourceDescription, isMatchCase()));
			}
			if (advancedSearchView.getResourceGroupComboBox().getValue() != null) {
				ReResourceGroup resourceGroup = advancedSearchView.getResourceGroupComboBox().getValue();
				criterions.add(Restrictions.eq("resourcegroup", resourceGroup));
			}
			ResourceType resourceType = advancedSearchView.getResourceTypeComboBox().getValue();
			criterions.add(Restrictions.eq("resourcetype", resourceType));
			if (advancedSearchView.getStateComboBox().getValue() != null) {
				ResourceState state = advancedSearchView.getStateComboBox().getValue();
				criterions.add(Restrictions.eq("active", state));
			}
		} catch (NumberFormatException e) {
			// TODO: handle exception
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

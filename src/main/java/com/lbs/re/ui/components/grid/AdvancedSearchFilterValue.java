package com.lbs.re.ui.components.grid;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.lbs.re.model.ReResourceGroup;
import com.lbs.re.model.ReUser;
import com.lbs.re.util.EnumsV2.ResourceCase;
import com.lbs.re.util.EnumsV2.ResourceState;
import com.lbs.re.util.EnumsV2.ResourceType;
import com.lbs.re.util.EnumsV2.SearchFilter;

public class AdvancedSearchFilterValue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String resourceNrStart;
	private String resourceNrEnd;
	private String orderNrEnd;
	private String orderNrStart;
	private String tagNrStart;
	private String tagNrEnd;
	private String levelNrStart;
	private String levelNrEnd;
	private SearchFilter descriptionSearchFilterComboBox;
	private SearchFilter prefixSearchFilterComboBox;
	private SearchFilter infoSearchFilterComboBox;
	private SearchFilter turkishSearchFilterComboBox;
	private SearchFilter englishSearchFilterComboBox;
	private SearchFilter standardSearchFilterComboBox;
	private String resourceDescription;
	private String prefix;
	private String info;
	private String turkish;
	private String english;
	private String standard;
	private ReResourceGroup resourceGroupComboBox;
	private ResourceType resourceTypeComboBox;
	private ResourceCase resourceCaseComboBox;
	private ResourceState stateComboBox;
	private ReUser modifiedUser;
	private ReUser createdUser;
	private LocalDateTime modifiedDateStart;
	private LocalDateTime modifiedDateEnd;

	public String getResourceNrStart() {
		return resourceNrStart;
	}

	public void setResourceNrStart(String resourceNrStart) {
		this.resourceNrStart = resourceNrStart;
	}

	public String getResourceNrEnd() {
		return resourceNrEnd;
	}

	public void setResourceNrEnd(String resourceNrEnd) {
		this.resourceNrEnd = resourceNrEnd;
	}

	public String getOrderNrEnd() {
		return orderNrEnd;
	}

	public void setOrderNrEnd(String orderNrEnd) {
		this.orderNrEnd = orderNrEnd;
	}

	public String getOrderNrStart() {
		return orderNrStart;
	}

	public void setOrderNrStart(String orderNrStart) {
		this.orderNrStart = orderNrStart;
	}

	public String getTagNrStart() {
		return tagNrStart;
	}

	public void setTagNrStart(String tagNrStart) {
		this.tagNrStart = tagNrStart;
	}

	public String getTagNrEnd() {
		return tagNrEnd;
	}

	public void setTagNrEnd(String tagNrEnd) {
		this.tagNrEnd = tagNrEnd;
	}

	public String getLevelNrStart() {
		return levelNrStart;
	}

	public void setLevelNrStart(String levelNrStart) {
		this.levelNrStart = levelNrStart;
	}

	public String getLevelNrEnd() {
		return levelNrEnd;
	}

	public void setLevelNrEnd(String levelNrEnd) {
		this.levelNrEnd = levelNrEnd;
	}

	public SearchFilter getDescriptionSearchFilterComboBox() {
		return descriptionSearchFilterComboBox;
	}

	public void setDescriptionSearchFilterComboBox(SearchFilter descriptionSearchFilterComboBox) {
		this.descriptionSearchFilterComboBox = descriptionSearchFilterComboBox;
	}

	public SearchFilter getPrefixSearchFilterComboBox() {
		return prefixSearchFilterComboBox;
	}

	public void setPrefixSearchFilterComboBox(SearchFilter prefixSearchFilterComboBox) {
		this.prefixSearchFilterComboBox = prefixSearchFilterComboBox;
	}

	public SearchFilter getInfoSearchFilterComboBox() {
		return infoSearchFilterComboBox;
	}

	public void setInfoSearchFilterComboBox(SearchFilter infoSearchFilterComboBox) {
		this.infoSearchFilterComboBox = infoSearchFilterComboBox;
	}

	public SearchFilter getTurkishSearchFilterComboBox() {
		return turkishSearchFilterComboBox;
	}

	public void setTurkishSearchFilterComboBox(SearchFilter turkishSearchFilterComboBox) {
		this.turkishSearchFilterComboBox = turkishSearchFilterComboBox;
	}

	public SearchFilter getEnglishSearchFilterComboBox() {
		return englishSearchFilterComboBox;
	}

	public void setEnglishSearchFilterComboBox(SearchFilter englishSearchFilterComboBox) {
		this.englishSearchFilterComboBox = englishSearchFilterComboBox;
	}

	public SearchFilter getStandardSearchFilterComboBox() {
		return standardSearchFilterComboBox;
	}

	public void setStandardSearchFilterComboBox(SearchFilter standardSearchFilterComboBox) {
		this.standardSearchFilterComboBox = standardSearchFilterComboBox;
	}

	public String getResourceDescription() {
		return resourceDescription;
	}

	public void setResourceDescription(String resourceDescription) {
		this.resourceDescription = resourceDescription;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getTurkish() {
		return turkish;
	}

	public void setTurkish(String turkish) {
		this.turkish = turkish;
	}

	public String getEnglish() {
		return english;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public ReResourceGroup getResourceGroupComboBox() {
		return resourceGroupComboBox;
	}

	public void setResourceGroupComboBox(ReResourceGroup resourceGroupComboBox) {
		this.resourceGroupComboBox = resourceGroupComboBox;
	}

	public ResourceType getResourceTypeComboBox() {
		return resourceTypeComboBox;
	}

	public void setResourceTypeComboBox(ResourceType resourceTypeComboBox) {
		this.resourceTypeComboBox = resourceTypeComboBox;
	}

	public ResourceCase getResourceCaseComboBox() {
		return resourceCaseComboBox;
	}

	public void setResourceCaseComboBox(ResourceCase resourceCaseComboBox) {
		this.resourceCaseComboBox = resourceCaseComboBox;
	}

	public ResourceState getStateComboBox() {
		return stateComboBox;
	}

	public void setStateComboBox(ResourceState stateComboBox) {
		this.stateComboBox = stateComboBox;
	}

	public ReUser getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(ReUser modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public ReUser getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(ReUser createdUser) {
		this.createdUser = createdUser;
	}

	public LocalDateTime getModifiedDateStart() {
		return modifiedDateStart;
	}

	public void setModifiedDateStart(LocalDateTime modifiedDateStart) {
		this.modifiedDateStart = modifiedDateStart;
	}

	public LocalDateTime getModifiedDateEnd() {
		return modifiedDateEnd;
	}

	public void setModifiedDateEnd(LocalDateTime modifiedDateEnd) {
		this.modifiedDateEnd = modifiedDateEnd;
	}

}

/*
 * Copyright 2014-2019 Logo Business Solutions
 * (a.k.a. LOGO YAZILIM SAN. VE TIC. A.S)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.lbs.re.ui.view.user.edit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.lbs.re.data.service.REUserService;
import com.lbs.re.model.ReUser;
import com.lbs.re.ui.components.basic.RECheckBox;
import com.lbs.re.ui.components.basic.RELabel;
import com.lbs.re.ui.components.basic.REPasswordField;
import com.lbs.re.ui.components.basic.RETextField;
import com.lbs.re.ui.components.combobox.ResourceGroupComboBox;
import com.lbs.re.ui.components.combobox.UserRoleComboBox;
import com.lbs.re.ui.components.layout.REHorizontalLayout;
import com.lbs.re.ui.components.layout.REVerticalLayout;
import com.lbs.re.ui.view.AbstractEditView;
import com.lbs.re.ui.view.Operation;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;

@SpringView
public class UserEditView extends AbstractEditView<ReUser, REUserService, UserEditPresenter, UserEditView> {

	private static final long serialVersionUID = 1L;

	private RETextField userName;
	private RETextField name;
	private RETextField surname;
	private RETextField email;
	private RETextField altemail;
	private RETextField department;
	private REPasswordField newPass;
	private ResourceGroupComboBox defaultresourcegroup;
	private UserRoleComboBox generalaccessrights;

	private RECheckBox turkishRead;
	private RECheckBox turkishWrite;
	private RECheckBox turkishDelete;
	private RECheckBox englishRead;
	private RECheckBox englishWrite;
	private RECheckBox englishDelete;

	@Autowired
	public UserEditView(UserEditPresenter presenter, ResourceGroupComboBox defaultresourcegroup,
			UserRoleComboBox generalaccessrights) {
		super(presenter);
		this.defaultresourcegroup = defaultresourcegroup;
		this.generalaccessrights = generalaccessrights;
	}

	@Override
	public String getHeader() {
		return getLocaleValue("view.useredit.header");
	}

	@PostConstruct
	private void initView() {
		userName = new RETextField("view.useredit.textfield.username", "half", true, true);
		name = new RETextField("view.useredit.textfield.name", "half", true, true);
		surname = new RETextField("view.useredit.textfield.surname", "half", true, true);
		email = new RETextField("view.useredit.textfield.email", "half", true, true);
		altemail = new RETextField("view.useredit.textfield.altemail", "half", true, true);
		department = new RETextField("view.useredit.textfield.department", "half", true, true);
		newPass = new REPasswordField("view.useredit.passwordfield.password", "half", true, true);
		newPass.addValueChangeListener(e -> getPresenter().setNewPassword(newPass.getValue()));
		newPass.addValueChangeListener(e -> getPresenter().setNewPassword(newPass.getValue()));

		addSection(getLocaleValue("view.viewedit.section.general"), 0, null, userName, newPass, name, surname, email,
				altemail, department, defaultresourcegroup, generalaccessrights);
		addSection(getLocaleValue("view.viewedit.section.languageauthorities"), 1, null, listLanguageAuthorities());
		getPresenter().setView(this);
	}

	private Component listLanguageAuthorities() {
		REVerticalLayout layout = new REVerticalLayout();

		REHorizontalLayout turkishLayout = new REHorizontalLayout();
		RELabel turkish = new RELabel(getLocaleValue("view.useredit.languageauthorities.turkish"));
		turkishRead = new RECheckBox("view.useredit.languageauthorities.read", null, true, true);
		turkishWrite = new RECheckBox("view.useredit.languageauthorities.write", null, true, true);
		turkishDelete = new RECheckBox("view.useredit.languageauthorities.delete", null, true, true);
		turkishLayout.addComponents(turkish, turkishRead, turkishWrite, turkishDelete);

		REHorizontalLayout englishLayout = new REHorizontalLayout();
		RELabel english = new RELabel(getLocaleValue("view.useredit.languageauthorities.english"));
		englishRead = new RECheckBox("view.useredit.languageauthorities.read", null, true, true);
		englishWrite = new RECheckBox("view.useredit.languageauthorities.write", null, true, true);
		englishDelete = new RECheckBox("view.useredit.languageauthorities.delete", null, true, true);
		englishLayout.addComponents(english, englishRead, englishWrite, englishDelete);

		layout.setSpacing(true);
		layout.addComponents(turkishLayout, englishLayout);
		return layout;
	}

	@Override
	public void bindFormFields(BeanValidationBinder<ReUser> binder) {
		super.bindFormFields(binder);
	}

	@Override
	protected void collectGrids() {
		super.collectGrids();
	}

	public RECheckBox getTurkishRead() {
		return turkishRead;
	}

	public RECheckBox getTurkishWrite() {
		return turkishWrite;
	}

	public RECheckBox getTurkishDelete() {
		return turkishDelete;
	}

	public RECheckBox getEnglishRead() {
		return englishRead;
	}

	public RECheckBox getEnglishWrite() {
		return englishWrite;
	}

	public RECheckBox getEnglishDelete() {
		return englishDelete;
	}

	public ResourceGroupComboBox getDefaultresourcegroup() {
		return defaultresourcegroup;
	}

	public void setDefaultresourcegroup(ResourceGroupComboBox defaultresourcegroup) {
		this.defaultresourcegroup = defaultresourcegroup;
	}

	@Override
	public String getViewOperationName() {
		return Operation.VIEW_USER;
	}

	@Override
	public String getEditOperationName() {
		return Operation.EDIT_USER;
	}

	@Override
	public String getAddOperationName() {
		return Operation.ADD_USER;
	}

}

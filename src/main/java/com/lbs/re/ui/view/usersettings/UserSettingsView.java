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

package com.lbs.re.ui.view.usersettings;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.lbs.re.data.service.REUserService;
import com.lbs.re.model.ReUser;
import com.lbs.re.ui.components.basic.REPasswordField;
import com.lbs.re.ui.components.basic.RETextField;
import com.lbs.re.ui.components.combobox.ResourceGroupComboBox;
import com.lbs.re.ui.view.AbstractEditView;
import com.vaadin.spring.annotation.SpringView;

@SpringView
public class UserSettingsView extends AbstractEditView<ReUser, REUserService, UserSettingsPresenter, UserSettingsView> {

	private static final long serialVersionUID = 1L;
	private RETextField userName;
	private RETextField name;
	private RETextField surname;
	private RETextField email;
	private RETextField altemail;
	private RETextField department;
	private REPasswordField newPass;

	@Autowired
	public UserSettingsView(UserSettingsPresenter presenter, ResourceGroupComboBox defaultresourcegroup) {
		super(presenter);
	}

	@PostConstruct
	private void initView() {
		userName = new RETextField("view.useredit.textfield.username", "half", true, false);
		name = new RETextField("view.useredit.textfield.name", "half", true, true);
		surname = new RETextField("view.useredit.textfield.surname", "half", true, true);
		email = new RETextField("view.useredit.textfield.email", "half", true, true);
		altemail = new RETextField("view.useredit.textfield.altemail", "half", true, true);
		department = new RETextField("view.useredit.textfield.department", "half", true, true);
		newPass = new REPasswordField("view.useredit.passwordfield.password", "half", true, true);
		newPass.addValueChangeListener(e -> getPresenter().setNewPassword(newPass.getValue()));

		addSection(getLocaleValue("view.viewedit.section.general"), 0, null, userName, newPass, name, surname, email, altemail, department);

		getPresenter().setView(this);
	}

	@Override
	public String getHeader() {
		return getLocaleValue("view.useredit.header");
	}

}

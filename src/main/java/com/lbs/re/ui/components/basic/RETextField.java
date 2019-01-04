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

package com.lbs.re.ui.components.basic;

import com.lbs.re.localization.ResourceEditorLocalizerWrapper;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Extension of native Vaadin Label.
 */
public class RETextField extends TextField implements ResourceEditorLocalizerWrapper {

    private static final long serialVersionUID = 1L;

    public RETextField(String id, String style, boolean isSizeFull, boolean isEnabled) {
        super();
        setStyleName(style);
        if (isSizeFull) {
            setSizeFull();
        }
        setEnabled(isEnabled);
        init(id);
    }

    private void init(String id) {
        addStyleName(ValoTheme.TEXTFIELD_SMALL);
        setWidth("100%");
        setResponsive(true);
        if (!id.isEmpty()) {
            setId(id);
            setCaption(getLocaleValue(id));
        }
    }
}

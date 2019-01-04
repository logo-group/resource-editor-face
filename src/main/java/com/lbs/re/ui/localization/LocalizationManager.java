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

package com.lbs.re.ui.localization;

import org.springframework.stereotype.Component;

import com.lbs.re.localization.LocaleConstants;
import com.lbs.re.localization.Localizer;
import com.lbs.re.localization.LocalizerManager;

@Component
public class LocalizationManager {

    public LocalizationManager() {
        initLocalization();
    }

    private void initLocalization() {
		Localizer localizer = Localizer.instance("resourceeditor", "localization.resourceeditor");
		LocalizerManager.putLocalizer("resourceeditor", localizer);

        Localizer exceptionLocalizer = Localizer.instance("localizedException", "localization.localizedException");
        LocalizerManager.putLocalizer("localizedException", exceptionLocalizer);

        // TODO language to be learned from the outside
        LocalizerManager.loadLocaleForAll(LocaleConstants.LOCALE_TRTR);
    }

}

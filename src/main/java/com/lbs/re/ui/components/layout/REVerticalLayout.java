package com.lbs.re.ui.components.layout;

import com.vaadin.ui.VerticalLayout;

public class REVerticalLayout extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	public REVerticalLayout() {
		setStyleName("responsive");
		setSpacing(false);
		setMargin(false);
		setResponsive(true);
		setSizeFull();
	}
}

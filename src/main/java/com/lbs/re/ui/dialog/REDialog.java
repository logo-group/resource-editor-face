package com.lbs.re.ui.dialog;

import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.ui.UI;

public class REDialog {
	public static void confirm(UI ui, ConfirmationListener listener, String message, String confirmCaption, String cancelCaption) {
		ConfirmDialog.show(ui, "", message, confirmCaption, cancelCaption, new ConfirmDialog.Listener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClose(ConfirmDialog dialog) {
				if (dialog.isConfirmed()) {
					listener.onConfirm();
				} else {
					listener.onCancel();
				}
			}
		}).removeAllCloseShortcuts();
	}

}

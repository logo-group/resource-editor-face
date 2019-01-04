package com.lbs.re.ui.util;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;

public class RENotification {
	public static void showNotification(String message, NotifyType notify) {
		Notification notification = new Notification(message);
		notification.setIcon(VaadinIcons.EXCLAMATION);
		// notification.setDelayMsec(Helper.NOTIFICATION_DELAYMSEC);
		switch (notify) {
		case SUCCESS:
			notification.setStyleName("success");
			notification.setDelayMsec(2000);
			break;
		case ERROR:
			notification.setStyleName("error");
			// notification.setDelayMsec(-1);
			break;
		case WARNING:
			notification.setStyleName("warning");
			// notification.setDelayMsec(-1);
			break;
		}
		notification.setPosition(Position.TOP_RIGHT);
		notification.show(Page.getCurrent());
	}

	public static void showTrayNotification(String message, NotifyType notify) {
		Notification notification = new Notification(message, Notification.Type.TRAY_NOTIFICATION);
		switch (notify) {
		case SUCCESS:
			notification.setStyleName("success");
			notification.setDelayMsec(2000);
			break;
		case ERROR:
			notification.setStyleName("error");
			// notification.setDelayMsec(-1);
			break;
		case WARNING:
			notification.setStyleName("warning");
			// notification.setDelayMsec(-1);
			break;
		}
		notification.show(Page.getCurrent());
	}

	public enum NotifyType {
		SUCCESS, ERROR, WARNING
	}
}

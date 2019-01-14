package com.lbs.re.ui;

import com.lbs.re.model.ReResourceitem;

public class REFaceEvents {

	private REFaceEvents() {

	}

	public static final class ResourceItemEvent {
		private final ReResourceitem resourceItem;

		public ResourceItemEvent(ReResourceitem resourceItem) {
			this.resourceItem = resourceItem;
		}

		public ReResourceitem getReResourceItem() {
			return resourceItem;
		}
	}
}

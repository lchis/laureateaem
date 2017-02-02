package com.mirum.laureate.content;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;

public final class PojoHelpers {
	private static final int DEPTH_OF_ROOT_PAGE = 4;
	
	public static Page getRootPage(WCMUsePojo requestObject){
		Page currentPage = requestObject.getCurrentPage();
		Page rootPage = currentPage.getAbsoluteParent(DEPTH_OF_ROOT_PAGE);
		
		return rootPage != null ? rootPage : currentPage;
	}
}

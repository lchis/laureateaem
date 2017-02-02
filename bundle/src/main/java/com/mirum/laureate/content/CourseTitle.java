package com.mirum.laureate.content;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;

public class CourseTitle extends WCMUsePojo {
	private String title;
	
	@Override
	public void activate() throws Exception {
		Page rootPage = PojoHelpers.getRootPage(this);
		this.title = rootPage.getTitle();
	}
	
	public String getTitle(){
		return title;
	}
}

package com.mirum.laureate.content;

import com.adobe.cq.sightly.WCMUsePojo;

public class CourseTitle extends WCMUsePojo {
	private static final int DEPTH_OF_ROOT_PAGE = 3;
	private String title;
	
	@Override
	public void activate() throws Exception {
		title = getCurrentPage().getAbsoluteParent(DEPTH_OF_ROOT_PAGE).getTitle();
	}
	
	public String getTitle(){
		return title;
	}
}

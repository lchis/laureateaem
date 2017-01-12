package com.mirum.laureate.content;

import com.adobe.cq.sightly.WCMUsePojo;

public class CourseSectionLabel extends WCMUsePojo {
	private CourseSectionBean courseSectionBean;
	
	@Override
	public void activate() throws Exception {
		courseSectionBean = new CourseSectionBean(getResource());
	}
	
	public CourseSectionBean getCourseSectionBean(){
		return courseSectionBean;
	}

}

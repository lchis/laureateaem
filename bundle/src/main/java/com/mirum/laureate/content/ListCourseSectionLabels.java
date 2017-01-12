package com.mirum.laureate.content;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.PageManager;

public class ListCourseSectionLabels extends WCMUsePojo{
	private final static Logger LOGGER = LoggerFactory.getLogger(ListCourseSectionLabels.class);
	
	private final static String COURSE_SECTIONS_PAR 			= "par";
	private final static String COURSE_SECTION_RESOURCE_TYPE 	= "laureate/components/course-section";

	private ArrayList<CourseSectionBean> courseSectionLabels;
	
	@Override
	public void activate() throws Exception {
		ResourceResolver resourceResolver = getResourceResolver();
		
		Resource pageContentResource = resourceResolver.adaptTo(PageManager.class).getContainingPage(getResource()).getContentResource();
		Resource courseSectionsParent = pageContentResource.getChild(COURSE_SECTIONS_PAR);
		Iterator<Resource> courseSections = courseSectionsParent.listChildren();
		
		courseSectionLabels = new ArrayList<CourseSectionBean>();
		while(courseSections.hasNext()){
			Resource courseSection = courseSections.next();
			
			// Ignore anything that's not a course section resource
			if(!resourceResolver.isResourceType(courseSection, COURSE_SECTION_RESOURCE_TYPE)){
				LOGGER.warn("Found non {} resource under {}, ignoring", COURSE_SECTION_RESOURCE_TYPE, courseSectionsParent.getPath());
				continue;
			}
			
			CourseSectionBean courseSectionBean = new CourseSectionBean(courseSection);
			
			if(courseSectionBean.getLabel() == null || courseSectionBean.getLabel().isEmpty()){
				LOGGER.warn("Got empty section label for {} resource, ignoring", courseSection.getPath());
				continue;
			}
			
			courseSectionLabels.add(courseSectionBean);
		}
	}

	public ArrayList<CourseSectionBean> getCourseSectionLabels(){
		return courseSectionLabels;
	}
}

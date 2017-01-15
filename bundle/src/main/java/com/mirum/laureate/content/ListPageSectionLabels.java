package com.mirum.laureate.content;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.PageManager;

public class ListPageSectionLabels extends WCMUsePojo{
	private final static Logger LOGGER = LoggerFactory.getLogger(ListPageSectionLabels.class);
	
	private final static String PAGE_SECTIONS_PAR 			= "par";
	private final static String PAGE_SECTION_RESOURCE_TYPE 	= "laureate/components/content/page-section";

	private ArrayList<PageSectionBean> pageSectionLabels;
	
	@Override
	public void activate() throws Exception {
		ResourceResolver resourceResolver = getResourceResolver();
		
		Resource pageContentResource = resourceResolver.adaptTo(PageManager.class).getContainingPage(getResource()).getContentResource();
		Resource pageSectionsParent = pageContentResource.getChild(PAGE_SECTIONS_PAR);
		Iterator<Resource> pageSections = pageSectionsParent.listChildren();
		
		pageSectionLabels = new ArrayList<PageSectionBean>();
		while(pageSections.hasNext()){
			Resource pageSection = pageSections.next();
			
			// Ignore anything that's not a page section resource
			if(!pageSection.isResourceType(PAGE_SECTION_RESOURCE_TYPE)){
				LOGGER.warn("Found non {} resource at {}, ignoring", PAGE_SECTION_RESOURCE_TYPE, pageSection.getPath());
				continue;
			}
			
			PageSectionBean pageSectionBean = new PageSectionBean(pageSection);
			if(pageSectionBean.getLabel() == null || pageSectionBean.getLabel().isEmpty()){
				LOGGER.warn("Got empty section label for {} resource, ignoring", pageSection.getPath());
				continue;
			}
			
			pageSectionLabels.add(pageSectionBean);
		}
	}

	public ArrayList<PageSectionBean> getPageSectionLabels(){
		return pageSectionLabels;
	}
}

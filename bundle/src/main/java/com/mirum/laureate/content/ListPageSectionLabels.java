package com.mirum.laureate.content;

import java.util.ArrayList;
import java.util.Iterator;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

public class ListPageSectionLabels extends WCMUsePojo{
	private final static Logger LOGGER = LoggerFactory.getLogger(ListPageSectionLabels.class);
	
	private final static String PAGE_SECTIONS_PAR 			= "par";
	private final static String PAGE_SECTION_RESOURCE_TYPE 	= "laureate/components/content/page-section";
	private static final String TEMPLATE_PROP = "cq:template";
	private static final String JCR_CONTENT_NODE = "jcr:content";
	
	private PageSectionBean firstPageSectionBean;
	private ArrayList<PageSectionBean> pageSectionLabels;
	private String template;
	private String assumedSiteLanguage="en";
	
	@Override
	public void activate() throws Exception {
		ResourceResolver resourceResolver = getResourceResolver();
		
		Resource pageContentResource = resourceResolver.adaptTo(PageManager.class).getContainingPage(getResource()).getContentResource();
		Resource pageSectionsParent = pageContentResource.getChild(PAGE_SECTIONS_PAR);
		
		template=findTemplate(getResource());
		if(pageSectionsParent == null){
			return;
		}
		
		Iterator<Resource> pageSections = pageSectionsParent.listChildren();
		
		if(!pageSections.hasNext()){
			return;
		}
		
		firstPageSectionBean = new PageSectionBean(pageSections.next());
		
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
	
	public PageSectionBean getFirstPageSectionLabel(){
		return firstPageSectionBean;
	}
	
	
	private String findTemplate(Resource resource) throws RepositoryException{
		ResourceResolver resourceResolver = resource.getResourceResolver();
		PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
		Page containingPage = pageManager.getContainingPage(resource);
		Node pageNode = containingPage.adaptTo(Node.class);
		
		Node pageConentNode;
		if(pageNode.hasNode(JCR_CONTENT_NODE)){
			pageConentNode = pageNode.getNode(JCR_CONTENT_NODE);
			
			if(pageConentNode.hasProperty(TEMPLATE_PROP)){
				String templatePath = pageConentNode.getProperty(TEMPLATE_PROP).getString();
				int templateNameIndex = templatePath.lastIndexOf("/");
				String templateName = templatePath.substring(templateNameIndex+1);
				
				return templateName;
			} else{
				return "No Template";
			}
		}else{
			return "No Template";
		}
	}
	
	public String getAssumedSiteLanguage(){
		if (template.contains("unitec") || template.contains("uvm"))
		{
			LOGGER.info("*%*%*%*%LANGUAGE -es");
			assumedSiteLanguage="es";
		}
		return assumedSiteLanguage;
	}
}

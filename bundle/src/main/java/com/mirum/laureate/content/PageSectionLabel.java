package com.mirum.laureate.content;

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


public class PageSectionLabel extends WCMUsePojo {
	private static final Logger LOGGER = LoggerFactory.getLogger(PageSectionLabel.class);
	private static final String TEMPLATE_PROP = "cq:template";
	private static final String JCR_CONTENT_NODE = "jcr:content";
	
	private final static String[] WALDEN_COLOURS = {"", "purple", "navy", "gold"}; 
	private final static String[] UNITEC_COLOURS={"", "blue", "blue", "blue"};
	private final static String[] UVM_COLOURS={"", "blue", "blue", "red"};
	private final static String DEFAULT_COLOUR = "";
	
	private PageSectionBean pageSectionBean;
	private int sectionIndex;
	private String template;
	
	@Override
	public void activate() throws Exception {
		pageSectionBean = new PageSectionBean(getResource());
		sectionIndex = findSectionIndex();
		template = findTemplate(getResource());
	}
	
	private int findSectionIndex(){
		Iterator<Resource> siblings = getResource().getParent().listChildren();
		
		try{
			String sectionId = getResource().adaptTo(Node.class).getIdentifier();
			
			for(sectionIndex = 0; siblings.hasNext(); sectionIndex++){
				if(siblings.next().adaptTo(Node.class).getIdentifier().equals(sectionId)){
					break;
				}
			}
		} catch(RepositoryException e){
			LOGGER.error("Failed getting resource or page section node id", e);
			sectionIndex = -1;
		}
		
		return sectionIndex;
	}
	
	public PageSectionBean getPageSectionBean(){
		return pageSectionBean;
	}
	
	public boolean getIsFirst(){
		return sectionIndex == 0;
	}
	
	public String getColour(){
		if(sectionIndex >= 0){
			int colourIndex = sectionIndex % WALDEN_COLOURS.length;
			
			if(template.contains("walden") )
			{
				return WALDEN_COLOURS[colourIndex];
			}
			else if (template.contains("unitec") )
			{
				return UNITEC_COLOURS[colourIndex];
			}
			else if (template.contains("uvm"))
			{
				return UVM_COLOURS[colourIndex];
			}
			else
				return DEFAULT_COLOUR;
		} else{
			LOGGER.warn("Received invalid section node id, returning blank colour");
			return DEFAULT_COLOUR;
		}
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
	
}
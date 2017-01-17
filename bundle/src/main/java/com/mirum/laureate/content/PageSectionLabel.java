package com.mirum.laureate.content;

import java.util.Iterator;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;

public class PageSectionLabel extends WCMUsePojo {
	private static final Logger LOGGER = LoggerFactory.getLogger(PageSectionLabel.class);
	
	private final static String[] WALDEN_COLOURS = {"", "purple", "navy", "gold"}; 
	private final static String[] UNITEC_COLOURS={"", "blue", "blue", "blue"};
	private final static String[] UVM_COLOURS={"", "blue", "blue", "red"};
	private final static String DEFAULT_COLOUR = "";
	
	private PageSectionBean pageSectionBean;
	private int sectionIndex;
	
	@Override
	public void activate() throws Exception {
		pageSectionBean = new PageSectionBean(getResource());
		sectionIndex = findSectionIndex();
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
			return WALDEN_COLOURS[colourIndex];
		} else{
			LOGGER.warn("Received invalid section node id, returning blank colour");
			return DEFAULT_COLOUR;
		}
	}
}

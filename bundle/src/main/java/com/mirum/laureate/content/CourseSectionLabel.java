package com.mirum.laureate.content;

import java.util.Iterator;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;

public class CourseSectionLabel extends WCMUsePojo {
	private static final Logger LOGGER = LoggerFactory.getLogger(CourseSectionLabel.class);
	
	private final static String[] COLOURS = {"purple", "navy", "gold"}; 
	private final static String DEFAULT_COLOUR = "";
	
	private CourseSectionBean courseSectionBean;
	private int sectionIndex;
	
	@Override
	public void activate() throws Exception {
		courseSectionBean = new CourseSectionBean(getResource());
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
			LOGGER.error("Failed getting resource or course section node id", e);
			sectionIndex = -1;
		}
		
		return sectionIndex;
	}
	
	public CourseSectionBean getCourseSectionBean(){
		return courseSectionBean;
	}
	
	public String getColour(){
		if(sectionIndex >= 0){
			int colourIndex = sectionIndex % COLOURS.length;
			return COLOURS[colourIndex];
		} else{
			LOGGER.warn("Received invalid section node id, returning blank colour");
			return DEFAULT_COLOUR;
		}
	}
}

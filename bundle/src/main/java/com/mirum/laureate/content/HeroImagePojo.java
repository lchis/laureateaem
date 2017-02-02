package com.mirum.laureate.content;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;

public class HeroImagePojo extends WCMUsePojo {
	private static final Logger LOGGER = LoggerFactory.getLogger(HeroImagePojo.class);
	
	private static final String DEFAULT_HERO_PATH		=  "";
	private static final String HERO_IMAGE_NODE_NAME 	= "hero"; 
	private static final String HERO_IMAGE_PATH_PROP 	= "./" + HERO_IMAGE_NODE_NAME + "/fileReference";
	
	private String heroImagePath;
	
	@Override
	public void activate() throws Exception {
		heroImagePath = setHeroImagePath(getResource());
	}
	
	// Traverse parent pages until one is found with a hero image
	private String setHeroImagePath(Resource resource){
		Node pageNode = resource.adaptTo(Node.class);
		try {
			if(pageNode.hasProperty(HERO_IMAGE_PATH_PROP)){
				LOGGER.info("Setting hero image for {} from {}", getResource().getPath(), resource.getPath());
				
				return pageNode.getNode(HERO_IMAGE_NODE_NAME).getPath();
			} else{
				Page parentPage = getPageManager().getContainingPage(resource).getParent();
				
				if(parentPage != null){
					return setHeroImagePath(parentPage.getContentResource());
				} else{
					return DEFAULT_HERO_PATH;
				}
			}
		} catch (RepositoryException e) {
			LOGGER.error("Error while getting hero image for {}", resource, e);
			return DEFAULT_HERO_PATH;
		}
	}
	
	public String getHeroImagePath(){
		return heroImagePath;
	}
}

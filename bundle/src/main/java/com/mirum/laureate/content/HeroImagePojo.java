package com.mirum.laureate.content;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;

import org.apache.sling.api.resource.Resource;

import com.adobe.cq.sightly.WCMUsePojo;

public class HeroImagePojo extends WCMUsePojo {
	private static final String HERO_IMAGE_PROP = "./hero/fileReference";
	
	private String heroImagePath;
	
	@Override
	public void activate() throws Exception {
		setHeroImagePath(getResource());
	}
	
	private void setHeroImagePath(Resource resource){
		Node pageNode = resource.adaptTo(Node.class);
		try {
			if(pageNode.hasProperty(HERO_IMAGE_PROP)){
				heroImagePath = pageNode.getProperty(HERO_IMAGE_PROP).getString();
				return;
			} else{
				
			}
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

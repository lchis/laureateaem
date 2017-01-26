package com.mirum.laureate.content;

import java.util.Iterator;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;

import com.adobe.cq.sightly.WCMUsePojo;

public class VideoPojo extends WCMUsePojo {
	private static final String VIDEO_RESOURCE_TYPE = "laureate/components/content/video";
	private static final String ENTRY_ID_PROP = "entryId";
	private static final String PARTNER_ID_PROP = "partnerId";
	private static final String VIDEO_OBJ_ID_BASE = "kaltura-video-obj";
	
	private String entryId;
	private String partnerId;
	private String videoObjId;
	
	@Override
	public void activate() throws Exception {
		this.entryId = getResource().getValueMap().get(ENTRY_ID_PROP, String.class);
		this.partnerId = getCurrentStyle().get(PARTNER_ID_PROP, String.class);
		
		try {
			this.videoObjId = VIDEO_OBJ_ID_BASE + getVideoIndex(getResource());
		} catch (RepositoryException e) {
			this.videoObjId = VIDEO_OBJ_ID_BASE;
		}
	}

	private int getVideoIndex(Resource videoResource) throws RepositoryException{
		Iterator<Resource> siblings = videoResource.getParent().listChildren();
		
		int videoIndex = 0;
		while(siblings.hasNext()){
			Resource nextSibling = siblings.next();
			
			if(nextSibling.isResourceType(VIDEO_RESOURCE_TYPE)){
				videoIndex++;
			}
			
			if(videoResource.adaptTo(Node.class).getIdentifier().equals(nextSibling.adaptTo(Node.class).getIdentifier())){
				break;
			}
		}
		
		return videoIndex;
	}
	
	public String getEntryId(){
		return entryId;
	}
	
	public String getPartnerId(){
		return partnerId;
	}
	
	public String getVideoObjId(){
		return videoObjId;
	}
}
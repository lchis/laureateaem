package com.mirum.laureate.content;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;

public class Link extends WCMUsePojo {
	private static final String LINK_URL_PROP 		= "linkURL";
	private static final String DEFAULT_LINK_URL 	= "";
	
	private static final String LINK_TITLE_PROP		= "linkTitle";
	private static final String DEFAULT_LINK_TITLE 	= "";
	
	private String linkUrl;
	private String linkTitle;
	private Page linkedPage;
	
	@Override
	public void activate() throws Exception {
		setLinkUrl();
		setLinkTitle();
	}

	private void setLinkUrl(){
		linkUrl = getResource().getValueMap().get(LINK_URL_PROP, DEFAULT_LINK_URL);
		
		linkedPage = getPageManager().getPage(linkUrl);
		
		linkUrl = linkedPage != null ? linkedPage.getPath() + ".html" : linkUrl;
	}
	
	private void setLinkTitle() {
		linkTitle = getResource().getValueMap().get(LINK_TITLE_PROP, DEFAULT_LINK_TITLE);
		
		if(!linkTitle.equals(DEFAULT_LINK_TITLE)){
			return;
		}
		
		linkTitle = linkedPage != null ? linkedPage.getTitle() : linkTitle;
	}
	
	public String getLinkUrl(){
		return linkUrl;
	}
	
	public String getLinkTitle(){
		return linkTitle;
	}
}

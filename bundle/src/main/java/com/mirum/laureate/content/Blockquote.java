package com.mirum.laureate.content;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;

public class Blockquote extends WCMUsePojo {
	private static final String LINK_URL_PROP 		= "linkURL";
	private static final String LINK_TITLE_PROP		= "linkTitle";
	private static final String LINK_ICON_PROP		= "icon";
	private static final String LINK_FORMAT_PROP	= "format";
	
	private static final String DEFAULT_LINK_URL 	= "";
	private static final String DEFAULT_LINK_TITLE 	= "";
	private static final String DEFAULT_LINK_ICON 	= "clickable";
	private static final String DEFAULT_LINK_FORMAT = "cta";
	
	private static final String LINK_TAG			= "a";
	private static final String SPAN_TAG			= "span";
	private static final String NO_LINK_TAG			= SPAN_TAG;
	private static final String NOTE_FORMAT_ICON	= "note";
	
	private String linkUrl;
	private String linkTitle;
	private String linkIcon;
	private String linkFormat;
	private Page linkedPage;
	
	@Override
	public void activate() throws Exception {
		setLinkUrl();
		setLinkTitle();
	}

	private void setLinkUrl(){
		linkUrl = getResource().getValueMap().get(LINK_URL_PROP, DEFAULT_LINK_URL);
		linkFormat = getResource().getValueMap().get(LINK_FORMAT_PROP, DEFAULT_LINK_FORMAT);
		linkIcon = linkFormat.equals(DEFAULT_LINK_FORMAT) ? getResource().getValueMap().get(LINK_ICON_PROP, DEFAULT_LINK_ICON) : NOTE_FORMAT_ICON;
		
		
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
	
	public boolean getHasLink(){
		return linkUrl != null && !linkUrl.isEmpty();
	}
	
	public String getLinkElement(){
		return getHasLink() ? LINK_TAG : NO_LINK_TAG;
	}
	
	public String getLinkUrl(){
		return linkUrl;
	}
	
	public String getLinkTitle(){
		return linkTitle;
	}
	
	public String getLinkFormat(){
		return linkFormat;
	}
	
	public String getLinkIcon(){
		return linkIcon;
	}
}

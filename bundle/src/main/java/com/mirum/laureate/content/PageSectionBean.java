package com.mirum.laureate.content;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

public class PageSectionBean {
	private static final String PAGE_LABEL_PROP = "pageSectionLabel";
	
	private String label;
	private String id;
	
	public PageSectionBean(Resource pageSectionResource){
		this.label = pageSectionResource.adaptTo(ValueMap.class).get(PAGE_LABEL_PROP, "");
		this.id = generateId(label);
	}
	
	public String getLabel(){
		return label;
	}
	
	public String getId(){
		return id;
	}
	
	public static String generateId(String label){
		return label.replace(' ', '-').toLowerCase();
	}
}

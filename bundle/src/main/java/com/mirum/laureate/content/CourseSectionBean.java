package com.mirum.laureate.content;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

public class CourseSectionBean {
	private static final String COURSE_LABEL_PROP = "sectionId";
	
	private String label;
	private String id;
	
	public CourseSectionBean(Resource courseSectionResource){
		this.label = courseSectionResource.adaptTo(ValueMap.class).get(COURSE_LABEL_PROP, "");
		this.id = generateId(label);
	}
	
	public static String generateId(String label){
		return label.replace(' ', '-').toLowerCase();
	}
	
	public String getLabel(){
		return label;
	}
	
	public String getId(){
		return id;
	}
}

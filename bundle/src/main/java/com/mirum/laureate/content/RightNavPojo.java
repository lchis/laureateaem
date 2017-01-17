package com.mirum.laureate.content;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;

public class RightNavPojo extends WCMUsePojo{
	private static final Logger LOGGER = LoggerFactory.getLogger(RightNavPojo.class);
	
	private static final String LINK_TO_BLACKBOARD_PROP 		= "blackboardLink";
	private static final String LINK_TO_BLACKBOARD_DEFAULT 		= "";
	private static final String PATH_TO_SYLLABUS_PAGE			= "syllabus";
	/*private static final String PATH_TO_COURSE_OVERVIEW_PAGE	= "course-overview";
	private static final String PATH_TO_COURSE_INFORMATION_PAGE	= "course-information";
	private static final String PATH_TO_RESOURCE_LIST_PAGE		= "resource-list";*/
	private static final String[] CHILD_PAGES_TO_IGNORE			= {PATH_TO_SYLLABUS_PAGE};
	
	private ArrayList<Page> childPages;
	private String linkToBlackboard;
	private String syllabusUrl;
	private boolean showSyllabus;
	
	@Override
	public void activate() throws Exception {
		setChildPages();
		//setLinkToBlackboard();
		setSyllabus();
		LOGGER.info("Page {} a syllabus", showSyllabus ? "has" : "doesn't have");
	}
	
	private void setSyllabus() {
		showSyllabus = getCurrentPage().hasChild(PATH_TO_SYLLABUS_PAGE);
		
		LOGGER.info("Page {} a syllabus", showSyllabus ? "has" : "doesn't have");
		
		if(showSyllabus){
			syllabusUrl = getCurrentPage().adaptTo(Resource.class).getChild(PATH_TO_SYLLABUS_PAGE).getPath();
		}
	}
	
	public boolean getShowSyllabus(){
		return showSyllabus;
	}
	
	public String getSyllabusUrl(){
		return syllabusUrl;
	}

	private void setLinkToBlackboard(){
		linkToBlackboard = getResource().adaptTo(ValueMap.class).get(LINK_TO_BLACKBOARD_PROP, LINK_TO_BLACKBOARD_DEFAULT);
	}
	
	public String getLinkToBlackboard() {
		return linkToBlackboard;
	}
	
	private void setChildPages(){
		Iterator<Page> children = getCurrentPage().listChildren();
		childPages = new ArrayList<Page>();
		
		while(children.hasNext()){
			Page currChild = children.next();
			if(Arrays.asList(CHILD_PAGES_TO_IGNORE).contains(currChild.getName())){
				LOGGER.info("Ignoring page " + currChild.getName());
				continue;
			}
			childPages.add(currChild);
		}
	}
	
	public ArrayList<Page> getChildPages(){
		return childPages;
	}
	
	public String getTitle(){
		return getCurrentPage().getPageTitle();
	}
}

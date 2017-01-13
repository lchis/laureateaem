package com.mirum.laureate.content;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.dam.api.Asset;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;

public class DownloadLink extends WCMUsePojo {
	private static final String FILE_REFERENCE_PROP = "fileReference";
	
	private static final Map<String, String> MIME_TYPES;
	static{
		MIME_TYPES = new HashMap<String, String>();
		MIME_TYPES.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "Word");
		MIME_TYPES.put("application/pdf", "PDF");
	}
	
	private String assetPath;
	private String title;
	private String format;
	
	@Override
	public void activate() throws Exception {
		if(!getResource().adaptTo(Node.class).hasProperty(FILE_REFERENCE_PROP)){
			assetPath = "";
			title = "";
			
			return;
		}
		
		assetPath = getResource().adaptTo(Node.class).getProperty(FILE_REFERENCE_PROP).getString();
		Asset asset = getResourceResolver().getResource(assetPath).adaptTo(Asset.class);
		
		title = asset.getName();
		
		String mimeType = asset.getMimeType();
		format = MIME_TYPES.get(mimeType);
	}
	
	public String getAssetPath(){
		return assetPath;
	}
	
	public String getTitle(){
		return title;
	}

	public String getFormat(){
		return format;
	}
}

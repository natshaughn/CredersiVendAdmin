package com.credersi_vend.backend.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.credersi_vend.routes.CredersiRoute;
import com.credersi_vend.routes.base.CredersiNode;
import com.credersi_vend.routes.base.CredersiPair;

public class BaseController {
	protected static final String PREFIX = "/api/v1/";
	
	@Value("${route.db.id}")
	private String dbID;
	
	@Value("${route.db.pw}")
	private String dbPassword;
	
	public void init() {
		CredersiRoute.INIT(dbPassword, "neo4j+s://"+dbID+".databases.neo4j.io", "neo4j");
	}
	
	public String toJsonList(List<CredersiNode> nodes) {
		String json = null;
        for (CredersiNode node : nodes) {
        	json = (json == null) ? node.toJson() : json+","+node.toJson();
        }
        
        return (json == null) ? "[]" : "["+json+"]";
	}
	
	public String toJsonPairs(List<CredersiPair> pairs) {
		String json = null;
        for (CredersiPair pair : pairs) {
        	json = (json == null) ? pair.toJson() : json+","+pair.toJson();
        }
        
        return "["+json+"]";
	}
}

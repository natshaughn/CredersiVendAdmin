package com.credersi_vend.routes.rels;

import java.util.List;

import com.credersi_vend.routes.base.CredersiRel;

public class RouteRel extends CredersiRel {
    protected static final String LABEL = "ROUTE";

    protected String directions;

    public String getDirections() {
        return this.directions;
    }

    public String getLabel() {
        return LABEL;
    }

    public RouteRel directions(String directions) {
        this.directions = directions;
        return this;
    }

    @Override
    public boolean setProp(String key, String value) {
        if ((key == null) || (key.length() < 1)) {
            return false;
        }

        if (super.setProp(key, value)) {
            return true;
        }
        
        if (key.equals("directions")) {
            this.directions = value;
            return true;
        }
        
        return false;
    }

    public String toJson() {
        Object[] props = {"directions", this.directions};
        return this.asJson(props);
    }

    public String toProps(String alias, List<String> params) {
        Object[] props = {"directions", this.directions};
        return this.asProps(alias, props, params);
    }
}
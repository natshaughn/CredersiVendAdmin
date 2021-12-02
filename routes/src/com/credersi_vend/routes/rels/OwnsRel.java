package com.credersi_vend.routes.rels;

import java.util.List;

import com.credersi_vend.routes.base.CredersiRel;

public class OwnsRel extends CredersiRel {
    protected static final String LABEL = "OWNS";

    public String getLabel() {
        return LABEL;
    }

    public String toJson() {
        return "";
    }
    
    public String toProps(String alias, List<String> params) {
        return "";
    }
}
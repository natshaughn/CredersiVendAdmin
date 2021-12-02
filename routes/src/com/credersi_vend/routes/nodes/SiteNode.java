package com.credersi_vend.routes.nodes;

import java.util.List;
import java.util.UUID;

import com.credersi_vend.routes.base.CredersiNode;

public class SiteNode extends CredersiNode {
    public static final String LABEL = "Site";

    protected String address;
    protected boolean origin = false;

    public SiteNode() {
        super();
    }

    public SiteNode(UUID uuid) {
        super(uuid);
    }

    public CredersiNode another() {
        return new SiteNode();
    }

    public String getAddress() {
        return this.address;
    }

    public String getLabel() {
        return LABEL;
    }

    public boolean getOrigin() {
        return this.origin;
    }

    public SiteNode address(String address) {
        this.address = address;
        return this;
    }

    public SiteNode name(String name) {
        this.name = name;
        return this;
    }

    public SiteNode origin() {
        this.origin = true;
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
        
        if (key.equals("address")) {
            this.address = value;
            return true;
        }

        return false;
    }

    @Override
    public String toJson() {
        Object[] props = {"address", this.address, "name", this.name, "uuid", this.uuid};
        return this.asJson(props);
    }
    
    public String toProps(String alias, List<String> params) {
        Object[] props = {"address", this.address, "name", this.name, "uuid", this.uuid};
        return this.asProps(alias, props, params);
    }
}
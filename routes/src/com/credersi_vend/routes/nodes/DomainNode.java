package com.credersi_vend.routes.nodes;

import java.util.List;
import java.util.UUID;

import com.credersi_vend.routes.base.CredersiNode;

public class DomainNode extends CredersiNode {
    public static final String LABEL = "Domain";
    protected static final String NAME = "Credersi-vend";

    protected String environment;

    public DomainNode() {
        super();
        this.name = NAME;
    }

    public CredersiNode another() {
        return new DomainNode();
    }

    public DomainNode(UUID uuid) {
        super(uuid);
    }

    public String getEnvironment() {
        return this.environment;
    }

    public String getLabel() {
        return LABEL;
    }

    public DomainNode environment(String environment) {
        this.environment = environment;
        return this;
    }

    public DomainNode name(String name) {
        this.name = name;
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
        
        if (key.equals("environment")) {
            this.environment = value;
            return true;
        }

        return false;
    }

    @Override
    public String toJson() {
        Object[] props = {"environment", this.environment, "name", this.name, "uuid", this.uuid};
        return this.asJson(props);
    }
    
    public String toProps(String alias, List<String> params) {
        Object[] props = {"environment", this.environment, "name", this.name, "uuid", this.uuid};
        return this.asProps(alias, props, params);
    }
}
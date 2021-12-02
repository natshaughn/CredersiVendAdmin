package com.credersi_vend.routes.nodes;

import java.util.List;
import java.util.UUID;

import com.credersi_vend.routes.base.CredersiNode;

public class MachineNode extends CredersiNode {
    public static final String LABEL = "Machine";

    protected String location;
    protected UUID parentUuid;

    public MachineNode() {
        super();
    }

    public MachineNode(UUID uuid) {
        super(uuid);
    }

    public CredersiNode another() {
        return new MachineNode();
    }

    public String getLabel() {
        return LABEL;
    }

    public String getLocation() {
        return this.location;
    }

    public MachineNode location(String location) {
        this.location = location;
        return this;
    }

    public MachineNode name(String name) {
        this.name = name;
        return this;
    }

    public void setParentUuid(UUID parent) {
        this.parentUuid = parent;
    }

    @Override
    public boolean setProp(String key, String value) {
        if ((key == null) || (key.length() < 1)) {
            return false;
        }

        if (super.setProp(key, value)) {
            return true;
        }
        
        if (key.equals("location")) {
            this.location = value;
            return true;
        }

        return false;
    }

    @Override
    public String toJson() {
        Object[] props = {"location", this.location, "name", this.name, "parentUuid", this.parentUuid, "uuid", this.uuid};
        return this.asJson(props);
    }
    
    public String toProps(String alias, List<String> params) {
        Object[] props = {"location", this.location, "name", this.name, "uuid", this.uuid};
        return this.asProps(alias, props, params);
    }
}
package com.credersi_vend.routes.base;

import java.util.List;
import java.util.UUID;

abstract public class CredersiNode extends CredersiLabel {
    abstract public CredersiNode another();
    
    public static CredersiNode ANOTHER() {
        class AnotherCredersiNode extends CredersiNode {
            public CredersiNode another() {
                return new AnotherCredersiNode();
            }

            public String getLabel() {
                return null;
            }
        }

        return new AnotherCredersiNode();
    }

    protected String name = "";
    protected UUID uuid = null;

    protected CredersiNode() {}
    protected CredersiNode(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return this.name;
    }
    
    public UUID getUuid() {
        return this.uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toProps(String alias, List<String> params) {
        Object[] props = {"name", this.name, "uuid", this.uuid};
        return this.asProps(alias, props, params);
    }
    
    @Override
    public boolean setProp(String key, String value) {
        if ((key == null) || (key.length() < 1)) {
            return false;
        }

        if (key.equals("name")) {
            this.name = value;
            return true;
        }

        if (key.equals("uuid")) {
            this.uuid = UUID.fromString(value);
            return true;
        }

        return false;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String toJson() {
        Object[] props = {"name", this.name, "uuid", this.uuid};
        return this.asJson(props);
    }
}
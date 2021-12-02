package com.credersi_vend.routes.base;

public class CredersiPair extends CredersiBase {
    public final CredersiNode node;
    public final CredersiRel rel;

    public CredersiPair(CredersiNode node, CredersiRel rel) {
        this.node = node;
        this.rel = rel;
    }

    public String toJson() {
        return String.format("{\"node\": %s, \"rel\": %s}",
            this.node.toJson(), this.rel.toJson());
    }
}
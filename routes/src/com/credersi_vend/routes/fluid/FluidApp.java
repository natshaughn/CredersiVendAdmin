package com.credersi_vend.routes.fluid;

import java.util.List;
import java.util.UUID;

import com.credersi_vend.routes.base.*;
import com.credersi_vend.routes.nodes.DomainNode;

abstract public class FluidApp {
    abstract public List<CredersiNode> executeList();
    abstract public CredersiNode executeNode();
    abstract public CredersiNode executeOrigin(CredersiNode origin);
    abstract public List<CredersiPair> executeRoute();
    abstract public UUID executeUuid();
    abstract public void executeVoid();
    abstract public DomainNode getDomain();
    abstract public CredersiNode getPrimary();
    abstract public CredersiRel getRelationship();
    abstract public CredersiNode getSecondary();
    abstract public void setPrimary(CredersiNode primary);
    abstract public void setRelationship(CredersiRel relationship);
    abstract public void setSecondary(CredersiNode secondary);
    abstract public void statement();
}

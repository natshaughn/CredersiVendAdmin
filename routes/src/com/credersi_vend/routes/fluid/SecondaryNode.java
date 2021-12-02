package com.credersi_vend.routes.fluid;

import java.util.UUID;

import com.credersi_vend.routes.base.CredersiNode;

public class SecondaryNode {
    protected final FluidApp app;
    protected final CredersiNode secondary;

    protected SecondaryNode(FluidApp app, CredersiNode secondary) {
        this.app = app;
        this.secondary = secondary;
    }

    public SecondaryNode name(String name) {
        this.secondary.setName(name);
        return this;
    }

    public SecondaryNode uuid(UUID uuid) {
        this.secondary.setUuid(uuid);
        return this;
    }

    public UUID EXECUTE() {
        return this.app.executeUuid();
    }
    
    public void STATEMENT() {
        this.app.statement();
    }    
}

package com.credersi_vend.routes.fluid;

import java.util.UUID;

import com.credersi_vend.routes.rels.SuppliesRel;

public class FluidDomain {
    protected final FluidApp app;
    
    protected FluidDomain(FluidApp app) {
        this.app = app;
    }

    public SecondaryCreate Supplies() {
        this.app.setRelationship(new SuppliesRel());
        return new SecondaryCreate(app);
    }

    public UUID EXECUTE() {
        return this.app.executeUuid();
    }
    
    public void STATEMENT() {
        this.app.statement();
    }
}

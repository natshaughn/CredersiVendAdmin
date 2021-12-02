package com.credersi_vend.routes.fluid;

import com.credersi_vend.routes.rels.OwnsRel;

public class FluidCustomer {
    protected final FluidApp app;
    
    protected FluidCustomer(FluidApp app) {
        this.app = app;
    }

    public SecondaryCreate Owns() {
        this.app.setRelationship(new OwnsRel());
        return new SecondaryCreate(app);
    }
}

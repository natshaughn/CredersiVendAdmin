package com.credersi_vend.routes.fluid;

import com.credersi_vend.routes.rels.*;

public class RelList {
    protected final FluidApp app;

    protected RelList(FluidApp app) {
        this.app = app;
    }

    public SecondaryList Owns() {
        this.app.setRelationship(new OwnsRel());
        return new SecondaryList(app);
    }

    public SecondaryList Route() {
        this.app.setRelationship(new RouteRel());
        return new SecondaryList(app);
    }

    public SecondaryList Supplies() {
        this.app.setRelationship(new SuppliesRel());
        return new SecondaryList(app);
    }
}

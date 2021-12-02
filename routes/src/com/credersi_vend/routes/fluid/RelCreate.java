package com.credersi_vend.routes.fluid;

import com.credersi_vend.routes.rels.RouteRel;

public class RelCreate {
    protected final FluidApp app;

    protected RelCreate(FluidApp app) {
        this.app = app;
    }

    public RouteCreate Route() {
        this.app.setRelationship(new RouteRel());
        return new RouteCreate(app, (RouteRel)this.app.getRelationship());
    }
}

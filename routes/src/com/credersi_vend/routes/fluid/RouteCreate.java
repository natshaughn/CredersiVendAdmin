package com.credersi_vend.routes.fluid;

import com.credersi_vend.routes.rels.RouteRel;

public class RouteCreate extends SecondaryCreate {
    protected final RouteRel relationship;

    protected RouteCreate(FluidApp app, RouteRel relationship) {
        super(app);
        this.relationship = relationship;
    }

    public SecondaryCreate directions(String directions) {
        this.relationship.directions(directions);
        return new SecondaryCreate(this.app);
    }
}

package com.credersi_vend.routes.fluid;

import java.util.UUID;

import com.credersi_vend.routes.nodes.*;

public class FluidQuery {
    protected final FluidApp app;

    public FluidQuery(FluidApp app) {
        this.app = app;
    }

    public ReturnNode Customer(UUID uuid) {
        this.app.setPrimary(new CustomerNode(uuid));
        return new ReturnNode(app);
    }

    public ReturnNode Domain() {
        this.app.setPrimary(this.app.getDomain());
        return new ReturnNode(app);
    }

    public ReturnNode Machine(UUID uuid) {
        this.app.setPrimary(new MachineNode(uuid));
        return new ReturnNode(app);
    }

    public ReturnOrigin Site(UUID uuid) {
        this.app.setPrimary(new SiteNode(uuid));
        return new ReturnOrigin(app);
    }
}

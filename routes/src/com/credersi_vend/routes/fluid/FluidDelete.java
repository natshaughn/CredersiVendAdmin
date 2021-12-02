package com.credersi_vend.routes.fluid;

import java.util.UUID;

import com.credersi_vend.routes.nodes.*;

public class FluidDelete {
    protected final FluidApp app;

    public FluidDelete(FluidApp app) {
        this.app = app;
    }

    public ReturnVoid Customer(UUID uuid) {
        this.app.setPrimary(new CustomerNode(uuid));
        return new ReturnVoid(app);
    }

    public ReturnVoid Domain() {
        this.app.setPrimary(this.app.getDomain());
        return new ReturnVoid(app);
    }

    public ReturnVoid Machine(UUID uuid) {
        this.app.setPrimary(new MachineNode(uuid));
        return new ReturnVoid(app);
    }

    public ReturnVoid Site(UUID uuid) {
        this.app.setPrimary(new SiteNode(uuid));
        return new ReturnVoid(app);
    }
}

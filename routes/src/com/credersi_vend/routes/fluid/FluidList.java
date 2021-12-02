package com.credersi_vend.routes.fluid;

import java.util.UUID;

import com.credersi_vend.routes.nodes.*;

public class FluidList {
    protected final FluidApp app;

    public FluidList(FluidApp app) {
        this.app = app;
    }

    public RelList Customer(UUID uuid) {
        this.app.setPrimary(new CustomerNode(uuid));
        return new RelList(app);
    }

    public RelList Domain() {
        this.app.setPrimary(this.app.getDomain());
        return new RelList(app);
    }

    public RelList Machine(UUID uuid) {
        this.app.setPrimary(new MachineNode(uuid));
        return new RelList(app);
    }

    public RelList Site(UUID uuid) {
        this.app.setPrimary(new SiteNode(uuid));
        return new RelList(app);
    }
}

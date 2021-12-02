package com.credersi_vend.routes.fluid;

import java.util.UUID;

import com.credersi_vend.routes.nodes.*;

public class FluidCreate {
    protected final FluidApp app;
    
    public FluidCreate(FluidApp app) {
        this.app = app;
    }

    public FluidCustomer Customer(UUID uuid) {
        this.app.setPrimary(new CustomerNode(uuid));
        return new FluidCustomer(this.app);
    }

    public FluidDomain Domain() {
        this.app.setPrimary(this.app.getDomain());
        return new FluidDomain(this.app);
    }

    public RelCreate Machine(UUID uuid) {
        this.app.setPrimary(new MachineNode(uuid));
        return new RelCreate(this.app);
    }

    public RelCreate Site(UUID uuid) {
        this.app.setPrimary(new SiteNode(uuid));
        return new RelCreate(this.app);
    }
}

package com.credersi_vend.routes.fluid;

import java.util.UUID;

import com.credersi_vend.routes.nodes.*;

public class SecondaryCreate {
    protected final FluidApp app;

    protected SecondaryCreate(FluidApp app) {
        this.app = app;
    }

    public SecondaryNode Customer(UUID uuid) {
        this.app.setSecondary(new CustomerNode(uuid));
        return new SecondaryNode(app, this.app.getSecondary());
    }

    public SecondaryMachine Machine() {
        this.app.setSecondary(new MachineNode());
        return new SecondaryMachine(app, (MachineNode)this.app.getSecondary());
    }

    public SecondarySite Site() {
        this.app.setSecondary(new SiteNode());
        return new SecondarySite(app, (SiteNode)this.app.getSecondary());
    }
}

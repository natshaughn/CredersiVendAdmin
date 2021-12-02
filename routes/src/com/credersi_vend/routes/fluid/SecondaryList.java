package com.credersi_vend.routes.fluid;

import com.credersi_vend.routes.nodes.*;

public class SecondaryList {
    protected final FluidApp app;

    protected SecondaryList(FluidApp app) {
        this.app = app;
    }

    public ReturnList Customer() {
        this.app.setSecondary(new CustomerNode());
        return new ReturnList(app);
    }

    public ReturnRoute Machine() {
        this.app.setSecondary(new MachineNode());
        return new ReturnRoute(app);
    }

    public ReturnList Site() {
        this.app.setSecondary(new SiteNode());
        return new ReturnList(app);
    }
}

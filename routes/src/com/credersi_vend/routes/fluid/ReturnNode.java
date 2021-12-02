package com.credersi_vend.routes.fluid;

import com.credersi_vend.routes.base.CredersiNode;

public class ReturnNode {
    protected final FluidApp app;

    protected ReturnNode(FluidApp app) {
        this.app = app;
    }

    public CredersiNode EXECUTE() {
        return this.app.executeNode();
    }
    
    public void STATEMENT() {
        this.app.statement();
    }
}

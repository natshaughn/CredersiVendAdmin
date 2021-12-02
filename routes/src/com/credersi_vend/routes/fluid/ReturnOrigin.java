package com.credersi_vend.routes.fluid;

import com.credersi_vend.routes.base.CredersiNode;
import com.credersi_vend.routes.nodes.SiteNode;

public class ReturnOrigin {
    protected final FluidApp app;

    protected ReturnOrigin(FluidApp app) {
        this.app = app;
    }

    public ExecuteOrigin origin() {
        ((SiteNode)this.app.getPrimary()).origin();
        return new ExecuteOrigin(this.app);
    }
    
    public CredersiNode EXECUTE() {
        return this.app.executeNode();
    }
    
    public void STATEMENT() {
        this.app.statement();
    }
}

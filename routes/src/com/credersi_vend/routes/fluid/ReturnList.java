package com.credersi_vend.routes.fluid;

import java.util.List;

import com.credersi_vend.routes.base.CredersiNode;

public class ReturnList {
    protected final FluidApp app;

    protected ReturnList(FluidApp app) {
        this.app = app;
    }

    public List<CredersiNode> EXECUTE() {
        return this.app.executeList();
    }
    
    public void STATEMENT() {
        this.app.statement();
    } 
}

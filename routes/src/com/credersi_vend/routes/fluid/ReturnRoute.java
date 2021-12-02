package com.credersi_vend.routes.fluid;

import java.util.List;

import com.credersi_vend.routes.base.CredersiPair;

public class ReturnRoute {
    protected final FluidApp app;

    protected ReturnRoute(FluidApp app) {
        this.app = app;
    }

    public List<CredersiPair> EXECUTE() {
        return this.app.executeRoute();
    }
    
    public void STATEMENT() {
        this.app.statement();
    }   
}

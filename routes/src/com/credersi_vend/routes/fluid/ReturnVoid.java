package com.credersi_vend.routes.fluid;

public class ReturnVoid {
    protected final FluidApp app;

    protected ReturnVoid(FluidApp app) {
        this.app = app;
    }

    public void EXECUTE() {
        this.app.executeVoid();
    }
    
    public void STATEMENT() {
        this.app.statement();
    }
}

package com.credersi_vend.routes.fluid;

import com.credersi_vend.routes.base.CredersiNode;
import com.credersi_vend.routes.nodes.MachineNode;

public class ExecuteOrigin {
    protected final FluidApp app;

    protected ExecuteOrigin(FluidApp app) {
        this.app = app;
    }

    public CredersiNode EXECUTE() {
        return this.app.executeOrigin(new MachineNode());
    }
    
    public void STATEMENT() {
        this.app.statement();
    }
}

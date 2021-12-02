package com.credersi_vend.routes.fluid;

import com.credersi_vend.routes.nodes.MachineNode;

public class SecondaryMachine extends SecondaryNode {
    protected SecondaryMachine(FluidApp app, MachineNode secondary) {
        super(app, secondary);
    }
    
    public SecondaryNode location(String location) {
        ((MachineNode)this.secondary).location(location);
        return this;
    }
}

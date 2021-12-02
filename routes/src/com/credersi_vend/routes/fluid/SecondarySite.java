package com.credersi_vend.routes.fluid;

import com.credersi_vend.routes.nodes.SiteNode;

public class SecondarySite extends SecondaryNode {
    protected SecondarySite(FluidApp app, SiteNode secondary) {
        super(app, secondary);
    }

    public SecondaryNode address(String address) {
        ((SiteNode)this.secondary).address(address);
        return this;
    }
}

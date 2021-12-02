package com.credersi_vend.routes.nodes;

import java.util.UUID;

import com.credersi_vend.routes.base.CredersiNode;

/**
 * Representation of a database Customer node
 */
public class CustomerNode extends CredersiNode {
    public static final String LABEL = "Customer";
    
    /**
     * Constructor to use when querying customer lists
     */
    public CustomerNode() {
        super();
    }

    /**
     * Constructor to use when creating or querying by UUID
     * @param uuid Specified UUID for the customer to create or query
     */
    public CustomerNode(UUID uuid) {
        super(uuid);
    }

    /**
     * Factory method to return a new blank customer node
     * @return A new blank customer node
     */
    public CredersiNode another() {
        return new CustomerNode();
    }

    /**
     * To return the database label applied to customer nodes 
     * @return Database label applied to customer nodes
     */
    public String getLabel() {
        return LABEL;
    }

    /**
     * Fluent API to specifies name of the customer node
     * @param name Customer name
     * @return This customer node as a fluent API
     */
    public CustomerNode name(String name) {
        this.name = name;
        return this;
    }
}
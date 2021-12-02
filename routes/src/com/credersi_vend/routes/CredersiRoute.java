/*
 ******************************************************************************
 * Credersi-vend Route Management Database Application
 * ============================================================================
 * 
 * Part of the software testing bootcamp project suite.  Designed to simulate
 * real-life scenarios, with a middleware Java fluent API for CRUD of vending
 * machine routes to a Neo4j graph database.  Deliberately written with minimal
 * validation for testing purposes.  Refer to the README file for more detail.
 * 
 ******************************************************************************
*/
package com.credersi_vend.routes;

import org.neo4j.driver.AuthToken;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;
import org.neo4j.driver.Value;
import org.neo4j.driver.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.neo4j.driver.Values.parameters;

import com.credersi_vend.routes.base.*;
import com.credersi_vend.routes.fluid.*;
import com.credersi_vend.routes.nodes.*;
import com.credersi_vend.routes.rels.*;

enum RouteCommand {
    CREATE,
    DELETE,
    LIST,
    QUERY
}

/**
 * Fluent API for the Credersi-vend Route Management Application
 */
public class CredersiRoute extends FluidApp {
    private static String DB_PASSWORD = null;
    private static String DB_SERVER = null;
    private static String DB_USERNAME = null;
    private static boolean TRACE = false;
    
    protected final RouteCommand command;
    protected final DomainNode domain;
    protected CredersiNode primary;
    protected CredersiRel relationship;
    protected CredersiNode secondary;
    
    abstract class ProcessResult {
        abstract public void process(Result result);
    }
        
    private CredersiRoute(RouteCommand command, DomainNode domain) {
        this.command = command;
        this.domain = domain;
    }

    private void execute(ProcessResult processor) {
        final String ENV_PASSWORD = "CREDERSI_VEND_ROUTE_PASSWORD";
        final String ENV_SERVER = "CREDERSI_VEND_ROUTE_SERVER";
        final String ENV_USERNAME = "CREDERSI_VEND_ROUTE_USERNAME";

        String server = CredersiRoute.DB_SERVER;
        String username = CredersiRoute.DB_USERNAME;
        String password = CredersiRoute.DB_PASSWORD;

        if (server == null) {
            server = System.getenv(ENV_SERVER);
            if ((server == null) || (server.length() < 1)) {
                throw new RuntimeException("Environment variable missing for Neo4j server!");
            }
        }
        
        if (username == null) {
            username = System.getenv(ENV_USERNAME);
            if ((username == null) || (username.length() < 1)) {
                throw new RuntimeException("Environment variable missing for Neo4j username!");
            }
        }

        if (password == null) {
            password = System.getenv(ENV_PASSWORD);
            if ((password == null) || (password.length() < 1)) {
                throw new RuntimeException("Environment variable missing for Neo4j password!");
            }
        }
        
        ArrayList<String> params = new ArrayList<String>();
        AuthToken token = AuthTokens.basic(username, password);
        Driver driver = GraphDatabase.driver(server, token);
        
        try {
            String statement = this.toCypher(params);
            Session session = driver.session();
            
            TransactionWork<Void> work = new TransactionWork<Void>() {
                @Override
                public Void execute(Transaction tx) {
                    // List to varargs using toArray(T[] arr)
                    processor.process(tx.run(statement, parameters(params.toArray(new Object[0]))));
                    return null;
                }
            };

            if (TRACE) {
                System.out.println("");
                System.out.println("Statement:");
                System.out.println(statement);
                System.out.println(String.format("Parameters: %s", params));
                System.out.println("");
                System.out.println("Executing...");
            }

            session.writeTransaction(work);
            
            if (TRACE) {
                System.out.println(" - Committed!");
                System.out.println("");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            if (driver != null) {
                driver.close();
            }
        }
    }

    private String toCypher(List<String> params) {
        if (this.command == RouteCommand.CREATE) {
            if ((this.secondary != null) && (this.secondary.getUuid() == null)) {
                this.secondary.setUuid(UUID.randomUUID());
            }

            if ((this.primary instanceof DomainNode) && (this.secondary == null)) {
                if (this.primary.getUuid() == null) {
                    this.primary.setUuid(UUID.randomUUID());
                }
                
                return String.format(
                    "CREATE (domain:%s%s) RETURN domain.uuid",
                    this.domain.getLabel(),
                    this.domain.toProps("domain", params));
            } else if (this.primary instanceof DomainNode) {
                return String.format(
                    "MATCH (domain:%s%s) CREATE (domain)-[:%s]->(secondary:%s%s) RETURN secondary.uuid",
                    this.domain.getLabel(),
                    this.domain.toProps("domain", params),
                    this.relationship.getLabel(),
                    this.secondary.getLabel(),
                    this.secondary.toProps("secondary", params));
            } else {
                return String.format(
                    "MATCH (domain:%s%s)-[*]->(primary:%s%s) CREATE (primary)-[:%s]->(secondary:%s%s) RETURN secondary.uuid",
                    this.domain.getLabel(),
                    this.domain.toProps("domain", params),
                    this.primary.getLabel(),
                    this.primary.toProps("primary", params),
                    this.relationship.getLabel(),
                    this.secondary.getLabel(),
                    this.secondary.toProps("secondary", params));
            }
        } else if (this.command == RouteCommand.DELETE) {
            if (this.primary instanceof DomainNode) {
                return String.format(
                    "MATCH (domain:%s%s) OPTIONAL MATCH (domain)-[*]->(descendant) DETACH DELETE domain, descendant",
                    this.domain.getLabel(),
                    this.domain.toProps("domain", params));
            } else {
                return String.format(
                    "MATCH (domain:%s%s)-[*]->(primary:%s%s) OPTIONAL MATCH (primary)-[*]->(descendant) DETACH DELETE primary, descendant",
                    this.domain.getLabel(),
                    this.domain.toProps("domain", params),
                    this.primary.getLabel(),
                    this.primary.toProps("primary", params));
            }
        } else if (this.command == RouteCommand.LIST) {
            if (this.primary instanceof DomainNode) {
                return String.format(
                    "MATCH (domain:%s%s)-[:%s]->(secondary:%s%s) RETURN secondary",
                    this.domain.getLabel(),
                    this.domain.toProps("domain", params),
                    this.relationship.getLabel(),
                    this.secondary.getLabel(),
                    this.secondary.toProps("secondary", params));
            } else if ((this.primary instanceof SiteNode) || (this.primary instanceof MachineNode)) {
                return String.format(
                    "MATCH (domain:%s%s)-[*]->(primaryNode:%s%s)-[secondaryRel:%s]->(secondaryNode:%s%s) OPTIONAL MATCH (secondaryNode)-[*]->(descendantNode:%s) OPTIONAL MATCH (descendantNode)<-[descendantRel]-(parentNode) RETURN primaryNode, secondaryRel, secondaryNode, descendantRel, descendantNode, parentNode",
                    this.domain.getLabel(),
                    this.domain.toProps("domain", params),
                    this.primary.getLabel(),
                    this.primary.toProps("primary", params),
                    this.relationship.getLabel(),
                    this.secondary.getLabel(),
                    this.secondary.toProps("secondary", params),
                    this.secondary.getLabel());
            } else {
                return String.format(
                    "MATCH (domain:%s%s)-[*]->(primary:%s%s)-[:%s]->(secondary:%s%s) RETURN secondary",
                    this.domain.getLabel(),
                    this.domain.toProps("domain", params),
                    this.primary.getLabel(),
                    this.primary.toProps("primary", params),
                    this.relationship.getLabel(),
                    this.secondary.getLabel(),
                    this.secondary.toProps("secondary", params));
            }
        } else if (this.command == RouteCommand.QUERY) {
            if (this.primary instanceof DomainNode) {
                return String.format(
                    "MATCH (domain:%s%s) RETURN domain",
                    this.domain.getLabel(),
                    this.domain.toProps("domain", params));
            } else if ((this.primary instanceof SiteNode) && (((SiteNode)this.primary).getOrigin())) {
                return String.format(
                    "MATCH (domain:%s%s)-[*]->(primary:%s%s)-[:ROUTE]->(secondary:Machine) RETURN secondary",
                    this.domain.getLabel(),
                    this.domain.toProps("domain", params),
                    this.primary.getLabel(),
                    this.primary.toProps("primary", params));
            } else {
                return String.format(
                    "MATCH (domain:%s%s)-[*]->(primary:%s%s) RETURN primary",
                    this.domain.getLabel(),
                    this.domain.toProps("domain", params),
                    this.primary.getLabel(),
                    this.primary.toProps("primary", params));
            }
        } else {
            throw new RuntimeException("Unsupported command!");
        }
    }

    public List<CredersiNode> executeList() {
        class ProcessList extends ProcessResult {
            private final CredersiNode secondary;
            public final List<CredersiNode> queried = new ArrayList<CredersiNode>();

            ProcessList(CredersiNode secondary) {
                this.secondary = secondary;
            }

            public void process(Result result) {
                for (Record record : result.list()) {
                    CredersiNode node = this.secondary.another();
                    for (Pair<String, Value> pair : record.fields()) {
                        for (Map.Entry<String, Object> prop : pair.value().asMap().entrySet()) {
                            node.setProp(prop.getKey(), prop.getValue().toString());
                        }
                    }

                    this.queried.add(node);
                }
            }
        }

        ProcessList process = new ProcessList(this.secondary);
        this.execute(process);
        return process.queried;       
    }

    public CredersiNode executeNode() {
        class ProcessNode extends ProcessResult {
            private final CredersiNode primary;

            ProcessNode(CredersiNode primary) {
                this.primary = primary;
            }

            public void process(Result result) {
                for (Pair<String, Value> pair : result.single().fields()) {
                    for (Map.Entry<String, Object> prop : pair.value().asMap().entrySet()) {
                        this.primary.setProp(prop.getKey(), prop.getValue().toString());
                    }
                }
            }
        }

        this.execute(new ProcessNode(this.primary));
        return this.primary;
    }

    public CredersiNode executeOrigin(CredersiNode origin) {
        class ProcessNode extends ProcessResult {
            private final CredersiNode origin;

            ProcessNode(CredersiNode origin) {
                this.origin = origin;
            }

            public void process(Result result) {
                if (!result.hasNext()) {
                    return;
                }
                
                for (Pair<String, Value> pair : result.single().fields()) {
                    for (Map.Entry<String, Object> prop : pair.value().asMap().entrySet()) {
                        this.origin.setProp(prop.getKey(), prop.getValue().toString());
                    }
                }
            }
        }
        
        this.execute(new ProcessNode(origin));
        return origin;
    }

    public List<CredersiPair> executeRoute() {
        class ProcessList extends ProcessResult {
            public final List<CredersiPair> route = new ArrayList<CredersiPair>();

            public void process(Result result) {
                Set<String> secondaryUuids = new HashSet<String>();

                for (Record record : result.list()) {
                    CredersiNode primaryNode = null;
                    RouteRel secondaryRel = null;
                    MachineNode secondaryNode = null;
                    RouteRel descendantRel = null;
                    MachineNode descendantNode = null;
                    MachineNode parentNode = null;
                    
                    for (Pair<String, Value> pair : record.fields()) {
                        if (pair.value().isNull()) {
                            continue;
                        }

                        CredersiLabel current = null;
                        if (pair.key().equals("primaryNode")) {
                            primaryNode = CredersiNode.ANOTHER();
                            current = primaryNode;
                        } else if (pair.key().equals("secondaryRel")) {
                            secondaryRel = new RouteRel();
                            current = secondaryRel;
                        } else if (pair.key().equals("secondaryNode")) {
                            secondaryNode = new MachineNode();
                            current = secondaryNode;
                        } else if (pair.key().equals("descendantRel")) {
                            descendantRel = new RouteRel();
                            current = descendantRel;
                        } else if (pair.key().equals("descendantNode")) {
                            descendantNode = new MachineNode();
                            current = descendantNode;
                        } else if (pair.key().equals("parentNode")) {
                            parentNode = new MachineNode();
                            current = parentNode;
                        } else {
                            throw new RuntimeException("Invalid field for route record!");
                        }
                        
                        for (Map.Entry<String, Object> prop : pair.value().asMap().entrySet()) {
                            current.setProp(prop.getKey(), prop.getValue().toString());
                        }
                    }

                    if ((primaryNode != null) && (secondaryRel != null) && (secondaryNode != null)) {
                        String secondaryUuid = secondaryNode.getUuid().toString();
                        if (!secondaryUuids.contains(secondaryUuid)) {
                            secondaryUuids.add(secondaryUuid);
                            secondaryNode.setParentUuid(primaryNode.getUuid());
                            route.add(new CredersiPair(secondaryNode, secondaryRel));
                        }
                    }

                    if ((parentNode != null) && (descendantRel != null) && (descendantNode != null)) {
                        descendantNode.setParentUuid(parentNode.getUuid());
                        route.add(new CredersiPair(descendantNode, descendantRel));
                    }
                }
            }
        }

        ProcessList process = new ProcessList();
        this.execute(process);
        return process.route;       
    }

    public UUID executeUuid() {
        class PrcessUuid extends ProcessResult {
            public UUID uuid;
            public void process(Result result) {
                this.uuid = UUID.fromString(result.single().get(0).asString());
            }
        }

        PrcessUuid process = new PrcessUuid();
        this.execute(process);
        return process.uuid;
    }

    public void executeVoid() {
        this.execute(new ProcessResult() {
            public void process(Result result) {} 
        });
    }

    public DomainNode getDomain() {
        return this.domain;
    }

    public CredersiNode getPrimary() {
        return this.primary;
    }

    public CredersiRel getRelationship() {
        return this.relationship;
    }

    public CredersiNode getSecondary() {
        return this.secondary;
    }

    public void setPrimary(CredersiNode primary) {
        this.primary = primary;
    }

    public void setRelationship(CredersiRel relationship) {
        this.relationship = relationship;
    }

    public void setSecondary(CredersiNode secondary) {
        this.secondary = secondary;
    }
    
    public void statement() {
        ArrayList<String> params = new ArrayList<String>();
        if (TRACE) {
            System.out.println(this.toCypher(params));
            System.out.println(String.format("Parameters: %s", params));
            System.out.println("");
        }
    }
    
    public static FluidCreate CREATE() {
        return new FluidCreate(new CredersiRoute(
            RouteCommand.CREATE,
            new DomainNode()));
    }

    public static FluidCreate CREATE(String name) {
        return new FluidCreate(new CredersiRoute(
            RouteCommand.CREATE,
            new DomainNode().name(name)));
    }

    public static FluidCreate CREATE(String name, String environment) {
        return new FluidCreate(new CredersiRoute(
            RouteCommand.CREATE,
            new DomainNode().name(name).environment(environment)));
    }

    public static FluidCreate CREATE(UUID uuid) {
        return new FluidCreate(new CredersiRoute(
            RouteCommand.CREATE,
            new DomainNode(uuid)));
    }

    public static FluidDelete DELETE() {
        return new FluidDelete(new CredersiRoute(
            RouteCommand.DELETE,
            new DomainNode()));
    }

    public static FluidDelete DELETE(String name) {
        return new FluidDelete(new CredersiRoute(
            RouteCommand.DELETE,
            new DomainNode().name(name)));
    }

    public static FluidDelete DELETE(String name, String environment) {
        return new FluidDelete(new CredersiRoute(
            RouteCommand.DELETE,
            new DomainNode().name(name).environment(environment)));
    }

    public static FluidDelete DELETE(UUID uuid) {
        return new FluidDelete(new CredersiRoute(
            RouteCommand.DELETE,
            new DomainNode(uuid)));
    }

    public static void INIT(String password, String server, String username) {
        CredersiRoute.DB_PASSWORD = password;
        CredersiRoute.DB_SERVER = server;
        CredersiRoute.DB_USERNAME = username;
    }
    
    public static FluidList LIST() {
        return new FluidList(new CredersiRoute(
            RouteCommand.LIST,
            new DomainNode()));
    }

    public static FluidList LIST(String name) {
        return new FluidList(new CredersiRoute(
            RouteCommand.LIST,
            new DomainNode().name(name)));
    }

    public static FluidList LIST(String name, String environment) {
        return new FluidList(new CredersiRoute(
            RouteCommand.LIST,
            new DomainNode().name(name).environment(environment)));
    }

    public static FluidList LIST(UUID uuid) {
        return new FluidList(new CredersiRoute(
            RouteCommand.LIST,
            new DomainNode(uuid)));
    }

    public static FluidQuery QUERY() {
        return new FluidQuery(new CredersiRoute(
            RouteCommand.QUERY,
            new DomainNode()));
    }

    public static FluidQuery QUERY(String name) {
        return new FluidQuery(new CredersiRoute(
            RouteCommand.QUERY,
            new DomainNode().name(name)));
    }

    public static FluidQuery QUERY(String name, String environment) {
        return new FluidQuery(new CredersiRoute(
            RouteCommand.QUERY,
            new DomainNode().name(name).environment(environment)));
    }

    public static FluidQuery QUERY(UUID uuid) {
        return new FluidQuery(new CredersiRoute(
            RouteCommand.QUERY,
            new DomainNode(uuid)));
    }

    public static void main(String[] args) {
        CredersiRoute.TRACE = true;
        System.out.println("Credersi Route");

        String customerName = "Acme Ltd";
        String domainEnv = "dev";
        String domainName = "Team 1";
        String machineDirections = "End of the hallway";
        String machineLocation = "Hallway End";
        String machineName = "Hallway";
        String originDirections = "End of the stairwell";
        String originLocation = "Stairwell Top";
        String originName = "Stairwell";
        String siteAddress = "10 Harvey Road, Hello-on-sea, Worldshire, HW10 2WH";
        String siteName = "Harveyland";

        UUID charlieUuid = UUID.fromString("27828019-4e08-42a1-a536-d773ecf83d4d"); // Customer
        UUID customerUuid = UUID.fromString("fa8c7721-52eb-4af8-91c0-69af612201b3");
        UUID funfairUuid = UUID.fromString("26827dee-ebda-4449-983e-49a907d6ae8c"); // Machine
        UUID machineUuid = UUID.fromString("dcf942cb-5b24-492e-bcae-5bbf6e0f715c");
        UUID originUuid = UUID.fromString("218e3a00-7985-4439-9b22-b8c69a614b7f");
        UUID parkUuid = UUID.fromString("ae22dffa-6939-4da2-bf43-ad64a324f088"); // Site
        UUID siteUuid = UUID.fromString("f63a4a98-e136-4ad1-be86-a5e84bf2d95d");

        List<String> queried = new ArrayList<String>();

        System.out.println("");
        System.out.println("Create Domain:");
        UUID domainUuid = CredersiRoute.CREATE(domainName, domainEnv).Domain().EXECUTE();
        System.out.println(" - UUID: "+domainUuid);
                
        System.out.println("");
        System.out.println("Create Customer:");
        CredersiRoute.CREATE(domainUuid).Domain().Supplies().Customer(customerUuid).name(customerName).EXECUTE();

        System.out.println("");
        System.out.println("Create Site:");
        CredersiRoute.CREATE(domainUuid).Customer(customerUuid).Owns().Site().address(siteAddress).name(siteName).uuid(siteUuid).EXECUTE();

        System.out.println("");
        System.out.println("Create Origin:");
        CredersiRoute.CREATE(domainUuid).Site(siteUuid).Route().directions(originDirections).Machine().location(originLocation).name(originName).uuid(originUuid).EXECUTE();

        System.out.println("");
        System.out.println("Append Route:");
        CredersiRoute.CREATE(domainUuid).Machine(originUuid).Route().directions(machineDirections).Machine().location(machineLocation).name(machineName).uuid(machineUuid).EXECUTE();

        System.out.println("");
        System.out.println("Query domain:");
        queried.add(CredersiRoute.QUERY(domainUuid).Domain().EXECUTE().toJson());
        System.out.println(" - Queried!");

        System.out.println("");
        System.out.println("Query customer:");
        queried.add(CredersiRoute.QUERY(domainUuid).Customer(customerUuid).EXECUTE().toJson());
        System.out.println(" - Queried!");

        System.out.println("");
        System.out.println("Query site:");
        queried.add(CredersiRoute.QUERY(domainUuid).Site(siteUuid).EXECUTE().toJson());
        System.out.println(" - Queried!");

        System.out.println("");
        System.out.println("Query machine:");
        queried.add(CredersiRoute.QUERY(domainUuid).Machine(machineUuid).EXECUTE().toJson());
        System.out.println(" - Queried!");

        System.out.println("");
        System.out.println("Delete route (recursive):");
        CredersiRoute.DELETE(domainUuid).Machine(originUuid).EXECUTE();

        System.out.println("");
        System.out.println("Delete site (recursive):");
        CredersiRoute.DELETE(domainUuid).Site(siteUuid).EXECUTE();

        System.out.println("");
        System.out.println("Delete customer (recursive):");
        CredersiRoute.DELETE(domainUuid).Customer(customerUuid).EXECUTE();
        
        System.out.println("");
        System.out.println("Delete domain (recursive):");
        CredersiRoute.DELETE(domainUuid).Domain().EXECUTE();

        System.out.println("List customers:");
        List<CredersiNode> customers = CredersiRoute.LIST().Domain().Supplies().Customer().EXECUTE();
        System.out.println(" - Queried!");

        System.out.println("List sites:");
        List<CredersiNode> sites = CredersiRoute.LIST().Customer(charlieUuid).Owns().Site().EXECUTE();

        System.out.println("List entire route:");
        List<CredersiPair> routes = CredersiRoute.LIST().Site(parkUuid).Route().Machine().EXECUTE();

        System.out.println("List partial route:");
        List<CredersiPair> partials = CredersiRoute.LIST().Machine(funfairUuid).Route().Machine().EXECUTE();

        System.out.println("");
        System.out.println("Queried:");
        for (String json : queried) {
            System.out.println(json);
        }
        
        System.out.println("");
        System.out.println("Customers:");
        for (CredersiNode customer : customers) {
            System.out.println(" - "+customer.toJson());
        }

        System.out.println("");
        System.out.println("Sites:");
        for (CredersiNode site : sites) {
            System.out.println(" - "+site.toJson());
        }

        System.out.println("");
        System.out.println("Routes:");
        for (CredersiPair route : routes) {
            System.out.println(" - "+route.toJson());
        }

        System.out.println("");
        System.out.println("Partials:");
        for (CredersiPair partial : partials) {
            System.out.println(" - "+partial.toJson());
        }

        System.out.println("");
    }
}
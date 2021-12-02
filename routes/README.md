# Credersi-vend Routes
Part of the software testing bootcamp project suite designed to simulate real-life scenarios.\
This is a Java component for CRUD of customer route graph data to a Neo4j AuraDB Server.\
It was delivered with minimal documentation & testing.

## Database
* Create a [Neo4j AuraDB Server](https://console.neo4j.io/) database instance
* The username should be `neo4j` (default)
* The connection URL will be `neo4j+s://DATABASEID.databases.neo4j.io`
* The ID & password are auto-generared and required by Credersi-vend Routes to connect

## Compiling & running smoke tests
* Ensure appropriate Java SDK is available on the path 
* Open the `routes` folder within a Git Bash Shell
* Compile using command: `./routes-compile-jar.sh`
  * Generates: `dist/VendRoutes.jar`
* To compile documentation: `./routes-generate-docs.sh`
  * Generates: `doc/index.html`
  * Minimal and requires more [javadoc](https://www.oracle.com/uk/technical-resources/articles/java/javadoc-tool.html) comments
  * Looks like only only `nodes/CustomerNode.java` has javadoc comments
  * Seems the developer started but soon abandoned documentation
* Execute using command: `./routes-smoke-test.sh`
  * Prompts for database ID & password
  * Runs minimal commands to read and write route data
  * Outputs to screen for manual inspection

## Dependant libraries
* `neo4j-java-driver-4.3.4.jar` for database access
* `reactive-streams-1.0.3.jar` required by Neo4j

## Graph database schema
Like other NoSQL databases, Neo4j is schema-less (albeit with indexes & constraints).\
It's a graph that supports dynamic creation of nodes, attributes, and relationships.\
As such, no formalized structure need be pre-determined, so documenting here instead.

### Graph node types

#### *Domain*
Root node type as a starting point for CYHPER queries

Attribute | Type | Description
--------- | ---- | -----------
environment | string | Optional, and when ommitted, `production` is assumed.<br/>For testing, this will be levels, like `cit` or `sit`.
name | string | For production, this would be `Credersi-vend`.<br/>For testing, this may be bootcamp team name.
uuid | string | Every node will have a UUID, even the root!

#### *Customer*
Minimal attributes as normalized with SQL Server

Attribute | Type | Description
--------- | ---- | -----------
name | string | SQL Server duplicate for Neo4j browsing
uuid | string | Synchronized with that from SQL Server

#### *Site*
Representing a phycial customer site location

Attribute | Type | Description
--------- | ---- | -----------
address | string | Optional and defaults to customer address
name | string | Company name when customer has only one site
uuid | string | Every node will have a UUID, even each site!

#### *Machine*
Representing a physical site-located vending machine

Attribute | Type | Description
--------- | ---- | -----------
location | string | Descriptive location for machine on the site estate
name | string | Each machine has a name (much like delivery lockers)
uuid | string | Every node will have a UUID, even each machine!

### Graph relationship types

#### *OWNS*
Representing that customers own site locations

#### *ROUTE*
Representing a vending machine site route

Attribute | Type | Description
--------- | ---- | -----------
directions | string | Optional text to aid navigation between machines

#### *SUPPLIES*
Representing the Credersi-vend / customer relationship

### Graph structured relationships

#### `(:Domain)-[:SUPPLIES]->(:Customer)`
One to many relationship between Credersi-vend as a supplier, and its customers.

#### `(:Customer)-[:OWNS]->(:Site)`
One to many relationship, where each customer must have at least one site to locate a vendine machine route 

#### `(:Site)-[:ROUTE]->(:Machine)`
One to one relationship, as each site is the origin to the first vending machine in the route.

#### `(:Machine)-[:ROUTE]->(:Machine)`
One to zero, one, or many relationships, given that each machine:
* Is a step in the vendine machine site route
* Could be the first machine on the route
* Could be the last and route end-point
* Could be a step between the previous machine and the next
* Could be the point in which the route branches to other paths

## Fluent API design
The following is the only design documentation provided, and ostensibly a guide to the Credersi-vend Route fluent API design.
```
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
CredersiRoute.CREATE()  Domain()        Supplies()                          Customer(uuid)  name(name)                                                  STATEMENT();
                                        ---
                                        FluidDomain

CredersiRoute.CREATE()  Customer(uuid)  Owns()                              Site()          name(name)      address(address)                            STATEMENT();
                                        ---
                                        FluidCustomer

CredersiRoute.CREATE()  Site(uuid)      Route()     directions(directions)  Machine()       name(name)                          location(location)      STATEMENT();
CredersiRoute.CREATE()  Machine(uuid)   Route()     directions(directions)  Machine()       name(name)                          location(location)      STATEMENT();
                        ---             ---         ---                     ---             ---             ---                 ---                     ---
                        FluidCreate     RelCreate   RouteCreate         <-  SecondaryCreate SecondaryNode   SecondarySite       SecondaryMachine
----------------------------------------------------------------------------------------------------------------------------------------------------------------------

----------------------------------------------------------------------------------------------------------------------------------------------------------------------
CredersiRoute.LIST()    Domain()        Supplies()                          Customer()                                                                  STATEMENT();
CredersiRoute.LIST()    Customer(uuid)  Owns()                              Site()                                                                      STATEMENT();
CredersiRoute.LIST()    Site(uuid)      Route()                             Machine()                                                                   STATEMENT();
CredersiRoute.LIST()    Machine(uuid)   Route()                             Machine()                                                                   STATEMENT();
                        ---             ---                                 ---                                                                         ---
                        FluidList       RelList                             SecondaryList                                                               ReturnList
----------------------------------------------------------------------------------------------------------------------------------------------------------------------

----------------------------------------------------------------------------------------------------------------------------------------------------------------------
CredersiRoute.QUERY()                                                       Domain()                                                                    STATEMENT();
CredersiRoute.QUERY()                                                       Customer(uuid)                                                              STATEMENT();
CredersiRoute.QUERY()                                                       Site(uuid)                                                                  STATEMENT();
CredersiRoute.QUERY()                                                       Machine(uuid)                                                               STATEMENT();
                                                                            ---                                                                         ---
                                                                            FluidQuery                                                                  ReturnNode
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
```
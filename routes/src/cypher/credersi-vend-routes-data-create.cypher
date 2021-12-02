// ----------------------------------------------------------------------------
// Credersi-vend Domain
// ----------------------------------------------------------------------------

CREATE (domain:Domain {
    name: "Credersi-vend",
    uuid: "7bd996ad-79f9-4197-ac33-8ef5181899af"});

// ----------------------------------------------------------------------------
// Alfa Ltd Customer
// ----------------------------------------------------------------------------

MATCH (domain:Domain {uuid: "7bd996ad-79f9-4197-ac33-8ef5181899af"})
CREATE (domain)-[:SUPPLIES]->(:Customer {
    name: "Alfa Ltd",
    uuid: "a7082b2f-12eb-49d2-a0ea-03885d2f3691"});

//
// Alfa Ltd Sites
//

MATCH (customer:Customer {uuid: "a7082b2f-12eb-49d2-a0ea-03885d2f3691"})
CREATE (customer)-[:OWNS]->(:Site {
    name: "Alpha",
    uuid: "d7139eab-ef77-4acc-9775-c3fd5f024e7b"});

MATCH (customer:Customer {uuid: "a7082b2f-12eb-49d2-a0ea-03885d2f3691"})
CREATE (customer)-[:OWNS]->(:Site {
    address: "24 Beta Lane, Alfatown, Alfashire, AL01 1BL",
    name: "Beta",
    uuid: "dc5e7463-c914-4507-b29f-87d9e2a59d88"});

//
// Alfa Ltd Alpha Route
//

MATCH (site:Site {uuid: "d7139eab-ef77-4acc-9775-c3fd5f024e7b"})
CREATE (site)-[:ROUTE]->(:Machine {
    location: "Reception",
    name: "Alpha",
    uuid: "f76dbb25-a7fd-451e-8c27-47bebcc9a104"});

//
// Alfa Ltd Beta Main Route
//

MATCH (site:Site {uuid: "dc5e7463-c914-4507-b29f-87d9e2a59d88"})
CREATE (site)-[:ROUTE]->(:Machine {
    location: "Reception",
    name: "Ground Floor",
    uuid: "c61bd599-969c-4c95-9018-5b67286ca93b"});

MATCH (machine:Machine {uuid: "c61bd599-969c-4c95-9018-5b67286ca93b"})
CREATE (machine)-[:ROUTE {
    directions: "Take the lift to the first floor"}]->(:Machine {
    location: "First floor by the lifts",
    name: "First Floor",
    uuid: "44677775-9613-4a27-afed-adf8a98471b9"});

MATCH (machine:Machine {uuid: "44677775-9613-4a27-afed-adf8a98471b9"})
CREATE (machine)-[:ROUTE {
    directions: "Take the lift to the second floor, then take a left to the stairwell"}]->(:Machine {
    location: "Second floor by the stairs",
    name: "Second Floor",
    uuid: "dbf0b439-42cc-45d1-a1e6-35bfd7c82671"});

MATCH (machine:Machine {uuid: "dbf0b439-42cc-45d1-a1e6-35bfd7c82671"})
CREATE (machine)-[:ROUTE {
    directions: "Take the lift to the third floor, then follow signs to emergency exit"}]->(:Machine {
    location: "Third floor by the emergency exit",
    name: "Third Floor",
    uuid: "e98b43fb-f4d0-47c2-9745-7a498b1376ff"});

//
// Alfa Ltd Beta Branch Route
//

MATCH (machine:Machine {uuid: "c61bd599-969c-4c95-9018-5b67286ca93b"})
CREATE (machine)-[:ROUTE {
    directions: "Take the stairs to the basement"}]->(:Machine {
    location: "Basement by stairs",
    name: "Staff Machine",
    uuid: "f706602e-edae-4639-aee9-df40d787627c"});

// ----------------------------------------------------------------------------
// Bravo Ltd Customer
// ----------------------------------------------------------------------------

MATCH (domain:Domain {uuid: "7bd996ad-79f9-4197-ac33-8ef5181899af"})
CREATE (domain)-[:SUPPLIES]->(:Customer {
    name: "Bravo Ltd",
    uuid: "4b6b9e30-b420-45e1-8fb9-0e93ab33caca"});

//
// Bravo Ltd Sites
//

MATCH (customer:Customer {uuid: "4b6b9e30-b420-45e1-8fb9-0e93ab33caca"})
CREATE (customer)-[:OWNS]->(:Site {
    name: "Bravo Ltd",
    uuid: "5bf7b3d2-d2da-4c64-a9f7-059af714501c"});

//
// Bravo Ltd Route
//

MATCH (site:Site {uuid: "5bf7b3d2-d2da-4c64-a9f7-059af714501c"})
CREATE (site)-[:ROUTE]->(:Machine {
    location: "Reception",
    name: "Fellowship",
    uuid: "3b207542-9872-4e97-a6db-608f76d641b5"});

MATCH (machine:Machine {uuid: "3b207542-9872-4e97-a6db-608f76d641b5"})
CREATE (machine)-[:ROUTE]->(:Machine {
    location: "Waiting room",
    name: "Towers",
    uuid: "7a595c67-b3e2-4aa9-880c-bc126cb2461a"});

MATCH (machine:Machine {uuid: "7a595c67-b3e2-4aa9-880c-bc126cb2461a"})
CREATE (machine)-[:ROUTE]->(:Machine {
    location: "Workshop",
    name: "King",
    uuid: "38e65470-0980-45dc-9c13-f4f398d0125c"});

// ----------------------------------------------------------------------------
// Charlie Ltd Customer
// ----------------------------------------------------------------------------

MATCH (domain:Domain {uuid: "7bd996ad-79f9-4197-ac33-8ef5181899af"})
CREATE (domain)-[:SUPPLIES]->(:Customer {
    name: "Charlie Ltd",
    uuid: "27828019-4e08-42a1-a536-d773ecf83d4d"});

//
// Charlie Ltd Sites
//

MATCH (customer:Customer {uuid: "27828019-4e08-42a1-a536-d773ecf83d4d"})
CREATE (customer)-[:OWNS]->(:Site {
    name: "Head-office",
    uuid: "8441d76f-1801-4f28-a323-7bb70d1d245d"});

MATCH (customer:Customer {uuid: "27828019-4e08-42a1-a536-d773ecf83d4d"})
CREATE (customer)-[:OWNS]->(:Site {
    address: "Charlie's Wild-life Park, Charlietown, Charlieshire, CH20 3CH",
    name: "Wild-life Park",
    uuid: "ae22dffa-6939-4da2-bf43-ad64a324f088"});

MATCH (customer:Customer {uuid: "27828019-4e08-42a1-a536-d773ecf83d4d"})
CREATE (customer)-[:OWNS]->(:Site {
    address: "Charlie's Wild-life Sanctuary, Charlie-on-sea, Charlieshire, CH24 3OS",
    name: "Wild-life Sanctuary",
    uuid: "b8a32ff1-b3ce-491c-8ed7-0b3b90ca806b"});

//
// Charlie Ltd Head-office Route
//

MATCH (site:Site {uuid: "8441d76f-1801-4f28-a323-7bb70d1d245d"})
CREATE (site)-[:ROUTE]->(:Machine {
    location: "Reception",
    name: "Reception",
    uuid: "fd723110-b806-4ab0-a08c-2c8482327bd3"});

//
// Charlie Ltd Wild-life Park Main Route
//

MATCH (site:Site {uuid: "ae22dffa-6939-4da2-bf43-ad64a324f088"})
CREATE (site)-[:ROUTE]->(:Machine {
    location: "Entrance outside the cafe",
    name: "Entrance",
    uuid: "77967e93-9b5c-486a-80cd-a40c6ed5e29b"});

MATCH (machine:Machine {uuid: "77967e93-9b5c-486a-80cd-a40c6ed5e29b"})
CREATE (machine)-[:ROUTE {
    directions: "Follow main path from the cafe to the crossroads"}]->(:Machine {
    location: "By the main crossroads sign",
    name: "Crossroads",
    uuid: "fff634bf-e6f7-4a6a-b900-90578a8566f1"});

MATCH (machine:Machine {uuid: "fff634bf-e6f7-4a6a-b900-90578a8566f1"})
CREATE (machine)-[:ROUTE {
    directions: "Follow signs to the lion enclosure"}]->(:Machine {
    location: "Near the lion enclosure",
    name: "Lions",
    uuid: "f8d55440-a0e8-477d-b11e-7010fed071ae"});

//
// Charlie Ltd Wild-life Park Aquarium Route
//

MATCH (machine:Machine {uuid: "fff634bf-e6f7-4a6a-b900-90578a8566f1"})
CREATE (machine)-[:ROUTE {
    directions: "Follow signs to the aquarium"}]->(:Machine {
    location: "Just outside the aquarium entrance",
    name: "Aquarium",
    uuid: "af9f08f9-f3b5-4a5d-8180-2f19e919e3e7"});

MATCH (machine:Machine {uuid: "af9f08f9-f3b5-4a5d-8180-2f19e919e3e7"})
CREATE (machine)-[:ROUTE {
    directions: "Follow aquarium through to the exit"}]->(:Machine {
    location: "Near the aquarium exit by the penguin pool",
    name: "Penguin",
    uuid: "998f7c60-68cc-4a88-8bc9-fa6f3953c25b"});

//
// Charlie Ltd Wild-life Park Funfair Route
//

MATCH (machine:Machine {uuid: "fff634bf-e6f7-4a6a-b900-90578a8566f1"})
CREATE (machine)-[:ROUTE {
    directions: "Follow signs to the funfair"}]->(:Machine {
    location: "At the entrance to the funfair",
    name: "Funfair",
    uuid: "26827dee-ebda-4449-983e-49a907d6ae8c"});

MATCH (machine:Machine {uuid: "26827dee-ebda-4449-983e-49a907d6ae8c"})
CREATE (machine)-[:ROUTE]->(:Machine {
    location: "By the bumper cars ride",
    name: "Bumper Cars",
    uuid: "2c8ce74c-0b77-4fbe-8f51-43a8100c29e8"});

MATCH (machine:Machine {uuid: "26827dee-ebda-4449-983e-49a907d6ae8c"})
CREATE (machine)-[:ROUTE]->(:Machine {
    location: "By the food-court",
    name: "Food-court",
    uuid: "64bc6adf-5737-4659-8019-ce3ea275dce5"});

MATCH (machine:Machine {uuid: "26827dee-ebda-4449-983e-49a907d6ae8c"})
CREATE (machine)-[:ROUTE]->(:Machine {
    location: "By the arcade",
    name: "Arcade",
    uuid: "aeb1fef3-3242-4139-a8f5-912662935bcd"});

//
// Charlie Ltd Wild-life Sanctuary Route
//

MATCH (site:Site {uuid: "b8a32ff1-b3ce-491c-8ed7-0b3b90ca806b"})
CREATE (site)-[:ROUTE]->(:Machine {
    location: "By the entrance",
    name: "Entrace",
    uuid: "a9f8f817-a0bb-4c8c-9a0e-70a0a77e3ed2"});

MATCH (machine:Machine {uuid: "a9f8f817-a0bb-4c8c-9a0e-70a0a77e3ed2"})
CREATE (machine)-[:ROUTE]->(:Machine {
    location: "By the cafe",
    name: "Cafe",
    uuid: "291a277c-d920-4c7b-a5f7-6111298a1f8b"});
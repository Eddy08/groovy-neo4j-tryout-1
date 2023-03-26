### Neo4j Schema and Corresponding Data

![neo4j](./neo4j-parent-child.png)

### Groovy Example for Github Issue

1. Groovy Tryout for issue : https://github.com/neo4j-graphql/neo4j-graphql-java/issues/277
2. To run the code try out the gitpod button

### Commands to run Neo4j container

$/home/others/groovy$ docker run --publish=7474:7474 --publish=7687:7687 neo4j:5.6.0-community

### Neo4j Script:

CREATE(p1:Parent {parent_id:'p1',parent_name:'P1'}),
(p2:Parent {parent_id:'p2',parent_name:'P2'}),

        (c1:Child {child_id:'c1'}),
        (c2:Child {child_id:'c2'}),

        (p1)-[:Parent_Child_Mapping{edgeDetails:'edge_1'}]->(c1),
        (p1)-[:Parent_Child_Mapping{edgeDetails:'edge_2'}]->(c1),
        (p1)-[:Parent_Child_Mapping{edgeDetails:'edge_3'}]->(c1),
        (p2)-[:Parent_Child_Mapping{edgeDetails:'edge_1'}]->(c2),
        (p2)-[:Parent_Child_Mapping{edgeDetails:'edge_2'}]->(c2),
        (p2)-[:Parent_Child_Mapping{edgeDetails:'edge_3'}]->(c2)

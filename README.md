### Neo4j Schema and Corresponding Data

Schema:

```
type Parent{
     parent_id: String
     parent_name: String
    Parent_Child_Mapping:[Parent_Child_Mapping]
}
type Parent_Child_Mapping @relation (name: "Parent_Child_Mapping", from :"Parent", to: "Child"){
     Parent: [Parent]
    Child: [Child]
    edgeDetails: String
}
type Child{
     child_id: String
     Parent_Child_Mapping: [Parent_Child_Mapping]
}
```

![neo4j](./neo4j-parent-child.png)

Query ( I Was Trying TO RUN ):

```
           parent(first:2) {
                    parent_id

                    Parent_Child_Mapping(first:2){
                        edgeDetails
                        Child(first:2){
                                    child_id
                                    }
                            }
                    }
```

One Relationship should have only one child

That is one edge will show only one child node,not multiple.
So no need of filter on Child Node !!!

![neo4j-property-not-possible](./neo4j-not-possible-prop.png)

### Groovy Example for Github Issue

1. Groovy Tryout for issue : https://github.com/neo4j-graphql/neo4j-graphql-java/issues/277
2. To run the code try out the gitpod button
3.

- If something wrong is happening then only do
  - ─➤ groovy -Dgroovy.grape.report.downloads=true -Divy.message.logger.level=4 neo4j-query.groovy
- Else
  - ─➤ just go to the Section where Curl post commands are mentioned
  - ─➤ Try to run them in the gitpod console

### Running the Groovy Script and graphql queries

1.

```
curl -XPOST http://localhost:4567/graphql -d'{"query":"{Parent {parent_id}}"}'
```

output:

```

 [{"Parent":{"parent_id":"p1"}},{"Parent":{"parent_id":"p2"}}]
```

2.

```
curl -XPOST http://localhost:4567/graphql -d'{"query":"{Parent(first:1) {parent_id}}"}'
```

output:

```
 [{"Parent":{"parent_id":"p1"}}]
```

3.

```
curl -XPOST http://localhost:4567/graphql -d'{"query":"{Parent(first:1) {parent_id Parent_Child_Mapping{edgeDetails}}}"}'
```

output:

```
 [{"Parent":{"Parent_Child_Mapping":[{"edgeDetails":"edge_3"},{"edgeDetails":"edge_2"},{"edgeDetails":"edge_1"}],"parent_id":"p1"}}]
```

4.

```
curl -XPOST http://localhost:4567/graphql -d'{"query":"{Parent(first:1) {parent_id Parent_Child_Mapping{edgeDetails Child{child_id}}}}"}'
```

output:

```
 [{"Parent":{"Parent_Child_Mapping":[{"Child":{"child_id":"c1"},"edgeDetails":"edge_3"},{"Child":{"child_id":"c1"},"edgeDetails":"edge_2"},{"Child":{"child_id":"c1"},"edgeDetails":"edge_1"}],"parent_id":"p1"}}]
```

5.

```
curl -XPOST http://localhost:4567/graphql -d'{"query":"{Parent(first:1) {parent_id Parent_Child_Mapping(first:2){edgeDetails Child{child_id}}}}"}'
```

output:

```
 [{"Parent":{"Parent_Child_Mapping":[{"Child":{"child_id":"c1"},"edgeDetails":"edge_3"},{"Child":{"child_id":"c1"},"edgeDetails":"edge_2"}],"parent_id":"p1"}}]
```

### Commands to run Neo4j container ( \*\*\* For Mannual running in docker )

$/home/others/groovy$ docker run --publish=7474:7474 --publish=7687:7687 neo4j:5.6.0-community

### Neo4j Script ( \*\*\* For Mannual running in docker )

```
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
```

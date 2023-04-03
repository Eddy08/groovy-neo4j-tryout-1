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
  or
  - ─➤ groovy -Dhttp.proxyHost=localhost -Dhttp.proxyPort=8080 neo4j-query.groovy  

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

## Insert Neo4j data through rest api

authorization

```
 echo -n 'neo4j:Harsh@123' | base64
```

```
fcurl --location --request POST 'http://localhost:7474/db/neo4j/tx/commit' \
--header 'Authorization: Basic bmVvNGo6SGFyc2hAMTIz' \
--header 'Content-Type: application/json' \
--data-raw '{
    "statements": [
        {
            "statement": "CREATE(p1:Parent {parent_id:'\''p1'\'',parent_name:'\''P1'\''}),(p2:Parent {parent_id:'\''p2'\'',parent_name:'\''P2'\''}),(c1:Child {child_id:'\''c1'\''}),(c2:Child {child_id:'\''c2'\''}),(p1)-[:Parent_Child_Mapping{edgeDetails:'\''edge_1'\''}]->(c1),(p1)-[:Parent_Child_Mapping{edgeDetails:'\''edge_2'\''}]->(c1),(p1)-[:Parent_Child_Mapping{edgeDetails:'\''edge_3'\''}]->(c1),(p2)-[:Parent_Child_Mapping{edgeDetails:'\''edge_1'\''}]->(c2),(p2)-[:Parent_Child_Mapping{edgeDetails:'\''edge_2'\''}]->(c2),(p2)-[:Parent_Child_Mapping{edgeDetails:'\''edge_3'\''}]->(c2)"
        }
    ]
}'
```

### Bing AI Chat helped

Harsh Bhardwaj <bhardwajharsh08@gmail.com>
10:51 PM (0 minutes ago)
to me

---------- Forwarded message ---------
From: Harsh Bhardwaj <bhardwajharsh08@gmail.com>
Date: Wed, 29 Mar, 2023, 12:48 pm
Subject: Gitpod.yml
To: Harsh Bhardwaj <bhardwajharsh08@gmail.com>

Sure, I can try to generate a gitpod.yml file that will contain two containers. Based on the search results, I found that you can use the **additionalRepositories** and **mainConfiguration** keys in the .gitpod.yml file to clone multiple repositories and configure services that need to be aware of each other¹. Here is an example of a possible gitpod.yml file:

```yaml
# This is a gitpod.yml file that contains two containers
image: gitpod/workspace-full # Use a public Docker image as the base image

tasks:
  - name: Container 1 # Name of the first container
    before: # Commands to run before cloning the repository
      - echo "This is container 1"
    init: # Commands to run after cloning the repository
      - git clone https://github.com/example/repo1.git # Clone an additional repository
    command: # Commands to run after init
      - cd repo1
      - npm install # Install dependencies
      - npm start # Start the application
    additionalRepositories: # Additional repositories to clone
      - https://github.com/example/repo2.git

  - name: Container 2 # Name of the second container
    before: # Commands to run before cloning the repository
      - echo "This is container 2"
    init: # Commands to run after cloning the repository
      - git clone https://github.com/example/repo3.git # Clone another additional repository
    command: # Commands to run after init
      - cd repo3
      - python app.py # Run a Python script
    mainConfiguration: .gitpod.Dockerfile # Use a custom Dockerfile for this container

ports:
  - port: 3000 # Expose port 3000 for the first container
    onOpen: open-preview # Open a preview when the port is ready
  - port: 5000 # Expose port 5000 for the second container
    onOpen: open-browser # Open a browser when the port is ready
```

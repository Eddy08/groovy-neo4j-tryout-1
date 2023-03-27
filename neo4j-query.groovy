// Simplistic GraphQL Server using SparkJava
@Grapes([
  @Grab('com.sparkjava:spark-core:2.7.2'),
  @Grab('org.neo4j.driver:neo4j-java-driver:5.6.0'),
  @Grab('com.google.code.gson:gson:2.8.5'),
  @Grab('org.neo4j:neo4j-graphql-java:1.7.0')
])

import spark.*
import static spark.Spark.*
import com.google.gson.Gson
import org.neo4j.graphql.*
import org.neo4j.driver.*

schema = """
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
type Query {
    parent : [Parent]
    child :  [Child] 
    }
"""
print "hello"


gson = new Gson()
render = (ResponseTransformer)gson.&toJson
def query(value) { gson.fromJson(value,Map.class)["query"] }

graphql = new Translator(SchemaBuilder.buildSchema(schema))
def translate(query) {
     println query
      graphql.translate(query)
       }

driver = GraphDatabase.driver("bolt://localhost",AuthTokens.basic("neo4j","Harsh@123"))
def run(cypher) { driver.session().withCloseable {
     it.run(cypher.query, Values.value(cypher.params)).list{ it.asMap() }}}

try{
post("/graphql","application/json", { req, res ->  run(translate(query(req.body())).first()) }, render);
}
catch (Exception e){
     print e
}
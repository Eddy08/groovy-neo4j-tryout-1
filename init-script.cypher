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
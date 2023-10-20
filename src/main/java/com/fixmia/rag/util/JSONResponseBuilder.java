package com.fixmia.rag.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;

public class JSONResponseBuilder {
    private ObjectMapper mapper = new ObjectMapper();
    private ArrayNode arrayNode = mapper.createArrayNode();
    private ObjectNode objectNode = mapper.createObjectNode();
    private List<String> keys = new ArrayList<>();
    private List<Object> values = new ArrayList();
    private List<ObjectNode> responses ;
   public JSONResponseBuilder(){
       mapper = new ObjectMapper();
       responses = new ArrayList<>();
   }
//   public void addItems(String key,Object value){
//
//       ObjectNode objectNode1 = mapper.createObjectNode();
//       objectNode1.put(key,mapper.valueToTree(value));
//       responses.add(objectNode1);
//
//   }
//   public ArrayNode getJSON(){
//       ArrayNode arrayNode1 = mapper.createArrayNode();
//       arrayNode1.addAll(responses);
//       return arrayNode1;

//   }
    public void addItems(String key, Object value) {
        keys.add(key);
        values.add(value);
    }

    public ArrayNode getJSON() {
        for (int i = 0; i < keys.size(); i++) {
            if (values.get(i) instanceof String) {
                String value = (String) values.get(i);
                objectNode.put(keys.get(i), value);
            } else {
                ObjectNode value = (ObjectNode) values.get(i);
                objectNode.put(keys.get(i), value);
            }
        }
        arrayNode.add(objectNode);
        return arrayNode;
    }

    public ObjectNode getObjectNode() {
        getJSON();
        return objectNode;
    }
    public JSONResponseBuilder createBuilder(){
       return new JSONResponseBuilder();
    }

//    public static void main(String[] args) {
//
//        JSONResponseBuilder response = new JSONResponseBuilder();
//        JSONResponseBuilder response2 = new JSONResponseBuilder();
//        response2.addItems("book-name","harry potter");
//        response2.addItems("auther","JK Rowlings");
//        response.addItems("name", "ragjn");
//        response.addItems("age", "23");
//        response.addItems("book", response2.getObjectNode());
//        ArrayNode arrayNode1 = response.getJSON();
//        for (int i = 0; i < arrayNode1.size(); i++) {
//            System.out.println(arrayNode1.get(i));
//        }
//    }
}

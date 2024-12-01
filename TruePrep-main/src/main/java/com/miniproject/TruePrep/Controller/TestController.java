package com.miniproject.TruePrep.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.bson.Document;

@RestController
public class TestController {
    @Autowired
    MongoTemplate mongoTemplate;

    @GetMapping("/test-insert")
    public String insertTest() {
        Document doc = new Document("key", "value");
        mongoTemplate.getCollection("testCollection").insertOne(doc);
        return "Document inserted!";
    }
}

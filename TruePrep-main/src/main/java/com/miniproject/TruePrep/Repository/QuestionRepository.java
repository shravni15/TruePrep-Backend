package com.miniproject.TruePrep.Repository;

import com.miniproject.TruePrep.Model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface QuestionRepository extends MongoRepository<Question, String> {
    List<Question> findBySection(String section);
}

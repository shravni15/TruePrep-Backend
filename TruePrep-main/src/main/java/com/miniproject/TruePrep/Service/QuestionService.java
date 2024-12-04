package com.miniproject.TruePrep.Service;

import com.miniproject.TruePrep.Model.Question;
import com.miniproject.TruePrep.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    public List<Question> saveQuestions(List<Question> questions) {
        return questionRepository.saveAll(questions);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public List<Question> getQuestionsBySection(String section) {
        return questionRepository.findBySection(section);
    }
}

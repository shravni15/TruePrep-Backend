package com.miniproject.TruePrep.Controller;

import com.miniproject.TruePrep.Model.Question;
import com.miniproject.TruePrep.Repository.QuestionRepository;
import com.miniproject.TruePrep.Service.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/questions") // Base endpoint for question-related operations
public class QuestionController {

    @Autowired
    private CSVService csvService;

    @Autowired
    private QuestionRepository questionRepository;

    // Endpoint to upload questions in bulk from a CSV file
    @PostMapping("/uploadQuestions")
    public ResponseEntity<String> uploadQuestions() {
        try {
            List<Question> questions = csvService.readQuestionsFromCSV();
            questionRepository.saveAll(questions);
            return ResponseEntity.ok("Questions uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading questions: " + e.getMessage());
        }
    }

    // Add a single question
    @PostMapping
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        Question savedQuestion = questionRepository.save(question);
        return ResponseEntity.ok(savedQuestion);
    }

    // Fetch all questions
    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        return ResponseEntity.ok(questions);
    }

    // Fetch questions by section
    @GetMapping("/section/{section}")
    public ResponseEntity<List<Question>> getQuestionsBySection(@PathVariable String section) {
        List<Question> questions = questionRepository.findBySection(section);
        if (questions.isEmpty()) {
            return ResponseEntity.noContent().build(); // Returns 204 if no questions are found
        }
        return ResponseEntity.ok(questions);
    }

    // Update an existing question
    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable String id, @RequestBody Question updatedQuestion) {
        Optional<Question> existingQuestion = questionRepository.findById(id);
        if (existingQuestion.isPresent()) {
            Question question = existingQuestion.get();
            // Updating fields
            question.setSection(updatedQuestion.getSection());
            question.setQuestionText(updatedQuestion.getQuestionText());
            question.setOptionA(updatedQuestion.getOptionA());
            question.setOptionB(updatedQuestion.getOptionB());
            question.setOptionC(updatedQuestion.getOptionC());
            question.setOptionD(updatedQuestion.getOptionD());
            question.setCorrectAnswer(updatedQuestion.getCorrectAnswer());
            Question savedQuestion = questionRepository.save(question);
            return ResponseEntity.ok(savedQuestion);
        }
        return ResponseEntity.notFound().build();
    }

    // Delete a question by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable String id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

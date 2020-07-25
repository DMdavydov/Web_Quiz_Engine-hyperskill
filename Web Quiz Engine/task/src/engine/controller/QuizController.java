package engine.controller;

import engine.controller.dto.AnswerDto;
import engine.controller.dto.AnswerResponse;
import engine.domain.Quiz;
import engine.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/api/quizzes/{id}/solve")
    public ResponseEntity<AnswerDto> sendAnswer(@PathVariable Long id, @RequestBody AnswerResponse answer) {
        return quizService.postAnswer(id, answer.getAnswer());
    }

    @PostMapping("/api/quizzes")
    public void createQuiz(@Valid @RequestBody Quiz quiz, Principal principal) {
        quizService.createQuiz(quiz, principal.getName());
    }

    @GetMapping("/api/quizzes/{id}")
    public Quiz getQuizById(@PathVariable Long id) {
        return quizService.getQuizById(id);
    }

    @DeleteMapping("/api/quizzes/{id}")
    public Quiz deleteQuizById(@PathVariable Long id) {
        return quizService.getQuizById(id);
    }

    @GetMapping("/api/quizzes")
    public List<Quiz> getQuizzes() {
        return quizService.getQuizzes();
    }
}

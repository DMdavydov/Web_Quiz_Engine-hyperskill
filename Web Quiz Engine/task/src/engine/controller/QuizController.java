package engine.controller;

import engine.controller.dto.AnswerDto;
import engine.controller.dto.AnswerResponse;
import engine.controller.dto.CompletedResponse;
import engine.domain.CompletedQuiz;
import engine.domain.Quiz;
import engine.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    @GetMapping
    public Page<Quiz> getQuizzes(@RequestParam int page) {
        return quizService.getQuizzes(page);
    }

    @GetMapping("/completed")
    public Page<CompletedResponse> getCompletedQuizzes(@RequestParam int page, Principal principal) {
        return quizService.getCompletedQuizzes(page, principal.getName());
    }

    @PostMapping
    public Quiz createQuiz(@Valid @RequestBody Quiz quiz, Principal principal) {
        return quizService.createQuiz(quiz, principal.getName());
    }

    @GetMapping("/{id}")
    public Quiz getQuizById(@PathVariable Long id) {
        return quizService.getQuizById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteQuizById(@PathVariable Long id, Principal principal) {
        quizService.deleteQuizById(id, principal.getName());
    }

    @PostMapping("/{id}/solve")
    public ResponseEntity<AnswerDto> sendAnswer(@PathVariable Long id, @RequestBody AnswerResponse answer, Principal principal) {
        return quizService.postAnswer(id, answer.getAnswer(), principal.getName());
    }
}

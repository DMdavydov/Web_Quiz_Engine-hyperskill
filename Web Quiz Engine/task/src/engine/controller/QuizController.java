package engine.controller;

import engine.controller.dto.AnswerDto;
import engine.domain.Quiz;
import engine.service.QuizService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping(value = "/api/quizzes/{id}/solve",
            consumes = {
                    MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            }
    )
    public ResponseEntity<AnswerDto> sendAnswer(@PathVariable int id, @RequestParam int answer) {
        return quizService.postAnswer(id, answer);
    }

    @PostMapping("/api/quizzes")
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return quizService.createQuiz(quiz);
    }

    @GetMapping("/api/quizzes/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable int id) {
        return quizService.getQuizById(id);
    }

    @GetMapping("/api/quizzes")
    public List<Quiz> getQuizzes() {
        return quizService.getQuizzes();
    }
}

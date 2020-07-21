package engine.controller;

import engine.controller.dto.AnswerDto;
import engine.domain.Quiz;
import engine.service.QuizService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/api/quiz")
    public Quiz getQuiz() {
        return quizService.getQuiz();
    }

    @PostMapping("/api/quiz")
    public AnswerDto sendAnswer(@RequestParam("answer") int answer) {
        return quizService.postAnswer(answer);
    }
}

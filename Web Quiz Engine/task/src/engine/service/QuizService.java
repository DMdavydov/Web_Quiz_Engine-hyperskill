package engine.service;

import engine.controller.dto.AnswerDto;
import engine.controller.dto.AnswerResponse;
import engine.domain.Quiz;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class QuizService {

    private final List<Quiz> quizzes = new ArrayList<>();
    private final AtomicInteger atomicInteger = new AtomicInteger(1);

    public ResponseEntity<AnswerDto> postAnswer(int id, AnswerResponse answer) {
        Quiz quiz = quizzes.stream()
                .filter(p -> id == p.getId())
                .findFirst()
                .orElse(null);
        if (quiz == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (quiz.getAnswer().equals(answer.getAnswer())) {
            return new ResponseEntity<>(new AnswerDto(true, "Correct"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new AnswerDto(false, "Incorrect"), HttpStatus.OK);
    }

    public Quiz createQuiz(Quiz quiz) {
        quiz.setId(atomicInteger.getAndIncrement());
        quizzes.add(quiz);
        return quiz;
    }

    public Quiz getQuizById(int id) {
        return quizzes.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }
}

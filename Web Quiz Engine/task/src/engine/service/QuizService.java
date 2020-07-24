package engine.service;

import engine.controller.dto.AnswerDto;
import engine.domain.Quiz;
import engine.repository.QuizRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public ResponseEntity<AnswerDto> postAnswer(Long id, List<Integer> answer) {
        if (answer.equals(getQuizById(id).getAnswer())) {
            return new ResponseEntity<>(new AnswerDto(true, "Correct"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new AnswerDto(false, "Incorrect"), HttpStatus.OK);
    }

    public Quiz createQuiz(Quiz quiz) {
        quizRepository.save(quiz);
        return quiz;
    }

    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Quiz> getQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        quizRepository.findAll().forEach(quizzes::add);
        return quizzes;
    }
}

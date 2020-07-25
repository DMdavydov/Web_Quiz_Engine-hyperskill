package engine.service;

import engine.controller.dto.AnswerDto;
import engine.domain.Quiz;
import engine.domain.User;
import engine.repository.QuizRepository;
import engine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    public ResponseEntity<AnswerDto> postAnswer(Long id, List<Integer> answer) {
        if (answer.equals(getQuizById(id).getAnswer())) {
            return new ResponseEntity<>(AnswerDto.builder().success(true).feedback("Correct").build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(AnswerDto.builder().success(false).feedback("Incorrect").build(), HttpStatus.OK);
    }

    public void createQuiz(Quiz quiz, String email) {
        User author = userRepository.findByEmail(email);
        if (author == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User not found");
        }
        quiz.setAuthor(author);
        quizRepository.save(quiz);
    }

    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Quiz> getQuizzes() {
        return new ArrayList<>(quizRepository.findAll());
    }
}

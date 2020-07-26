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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;


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

    public Quiz createQuiz(Quiz quiz, String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User not found");
        }
        quiz.setUser(user);
        return quizRepository.save(quiz);
    }

    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void deleteQuizById(Long id, String email) {
        User author = userRepository.findByEmail(email);
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!author.getEmail().equals(quiz.getUser().getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        quizRepository.delete(quiz);
    }

    public List<Quiz> getQuizzes() {
        return quizRepository.findAll();
    }
}

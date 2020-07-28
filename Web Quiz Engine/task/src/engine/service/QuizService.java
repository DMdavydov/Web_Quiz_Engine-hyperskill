package engine.service;

import engine.controller.dto.AnswerDto;
import engine.controller.dto.CompletedResponse;
import engine.domain.CompletedQuiz;
import engine.domain.Quiz;
import engine.domain.User;
import engine.repository.CompletedQuizRepository;
import engine.repository.QuizRepository;
import engine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final CompletedQuizRepository completedQuizRepository;

    public ResponseEntity<AnswerDto> postAnswer(Long id, List<Integer> answer, String email) {
        User user = userRepository.findByEmail(email);
        Quiz quiz = getQuizById(id);

        if (answer.equals(quiz.getAnswer())) {
            completedQuizRepository.save(
                    CompletedQuiz.builder()
                            .quiz(quiz)
                            .user(user)
                            .completedAt(LocalDateTime.now())
                            .build()
            );
            return new ResponseEntity<>(AnswerDto.builder().success(true).feedback("Correct").build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(AnswerDto.builder().success(false).feedback("Incorrect").build(), HttpStatus.OK);
    }

    public Quiz createQuiz(Quiz quiz, String email) {
        User user = userRepository.findByEmail(email);
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

    public Page<Quiz> getQuizzes(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return quizRepository.findAll(pageable);
    }

    public Page<CompletedResponse> getCompletedQuizzes(int page, String email) {
        Pageable pageable = PageRequest.of(page, 10);
        return completedQuizRepository.findAllByUserOrderByCompletedAtDesc(email, pageable)
                .map(completedQuiz -> CompletedResponse.builder()
                        .id(completedQuiz.getQuiz().getId())
                        .completedAt(completedQuiz.getCompletedAt())
                        .build());
    }
}

package engine.service;

import engine.controller.dto.AnswerDto;
import engine.domain.Quiz;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    public Quiz getQuiz() {
        return new Quiz("The Java Logo",
                "What is depicted on the Java logo?",
                new String[]{"Robot", "Tea leaf", "Cup of coffee", "Bug"});
    }

    public AnswerDto postAnswer(int answer) {
        String[] quiz = getQuiz().getOptions();
        if (quiz[answer].equals("Cup of coffee")) {
            return new AnswerDto(true, "Correct");
        }
        return new AnswerDto(false, "Incorrect");
    }
}

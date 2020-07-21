package engine.controller.dto;

public class AnswerDto {

    private boolean success;
    private String feedback;


    public AnswerDto(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

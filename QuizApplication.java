import java.io.*;
import java.util.*;

public class QuizApplication {
    private List<Question> questions;
    private int score;

    public QuizApplication(String filePath) {
        questions = new ArrayList<>();
        loadQuestions(filePath);
        score = 0;
    }

    private void loadQuestions(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                questions.add(new Question(parts[0], parts[1], Arrays.copyOfRange(parts, 2, parts.length)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startQuiz() {
        Scanner scanner = new Scanner(System.in);
        for (Question question : questions) {
            System.out.println(question.getQuestionText());
            String[] options = question.getOptions();
            for (int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ". " + options[i]);
            }
            System.out.print("Your answer (1-" + options.length + "): ");
            int answer = scanner.nextInt();
            if (options[answer - 1].equals(question.getCorrectAnswer())) {
                score++;
            }
        }
        System.out.println("Your final score: " + score + "/" + questions.size());
        scanner.close();
    }

    public static void main(String[] args) {
        QuizApplication quiz = new QuizApplication("questions.txt");
        quiz.startQuiz();
    }
}

class Question {
    private String questionText;
    private String correctAnswer;
    private String[] options;

    public Question(String questionText, String correctAnswer, String[] options) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.options = options;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String[] getOptions() {
        return options;
    }
}

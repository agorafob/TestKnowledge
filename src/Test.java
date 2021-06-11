import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {
    private List<ArrayList<String>> testStorage = new ArrayList<>();
    private ArrayList<String> allQuestions = new ArrayList<>();
    private File file = new File("C:\\PROJECT_HILEL_2ND\\TestKnowledge\\src\\java1.test");
    private int totalQuestions = 0;
    private int correctAnswers = 0;
    private int wrongAnswers = 0;


    private void readFile() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String s;
            while ((s = in.readLine()) != null) {
                if (!s.isBlank()) {
                    allQuestions.add(s);
                }
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addQuestionsToStorage() {
        ArrayList<String> singleQuestion = new ArrayList<>();
        for (int i = 0; i < allQuestions.size(); i++) {
            if (allQuestions.get(i).equals("@Question")) {
                getQuestion(i + 1, singleQuestion);
                testStorage.add(singleQuestion);
                singleQuestion = new ArrayList<>();
            }
        }
    }

    private void getQuestion(int start, ArrayList<String> q) {
        for (int i = start; i < allQuestions.size(); i++) {
            if (!allQuestions.get(i).equals("@Question")) {
                q.add(allQuestions.get(i));
            } else {
                break;
            }
        }
    }

    private void print() {
        for (int i = 0; i < testStorage.size(); i++) {
            totalQuestions++;
            System.out.println(totalQuestions + "" + ')');
            printSingleQuestion(i);
        }
        System.out.println("Всего вопросов: " + totalQuestions);
        System.out.println("Верных ответов: " + correctAnswers);
        System.out.println("Неверных ответов: " + wrongAnswers);
    }

    private void printSingleQuestion(int questionNumber) {
        ArrayList<String> questionToPrint = testStorage.get(questionNumber);
        if (questionToPrint.contains("</code>")) {
            codeQuestion(questionToPrint);
        } else {
            noCodeQuestion(questionToPrint);
        }
    }

    private void noCodeQuestion(ArrayList<String> questionToPrint) {
        int correct = 0;
        int questionCounter = 1;
        for (int i = 0; i < questionToPrint.size(); i++) {
            if (i == 0) {
                System.out.println(questionToPrint.get(i));
                System.out.println("--------");
            } else if (i == 1) {
                correct = Character.getNumericValue(questionToPrint.get(i).charAt(questionToPrint.get(i).length() - 1));
            } else {
                System.out.println(questionCounter + "-" + questionToPrint.get(i));
                questionCounter++;
            }
        }
        checkAndPrintResult(correct);
    }

    private void codeQuestion(ArrayList<String> questionToPrint) {
        int correct = 0;
        int questionCounter = 1;
        for (int i = 0; i < questionToPrint.size(); i++) {
            if (i == 0) {
                System.out.println(questionToPrint.get(i));
                System.out.println("--------");
            } else if (i == 1) {
                i++;
                while (!questionToPrint.get(i).equals("</code>")) {
                    System.out.println(questionToPrint.get(i));
                    i++;
                }
                System.out.println("------");
            } else if (questionToPrint.get(i).contains("@Key")) {
                correct = Character.getNumericValue(questionToPrint.get(i).charAt(questionToPrint.get(i).length() - 1));
            } else {
                System.out.println(questionCounter + "-" + questionToPrint.get(i));
                questionCounter++;
            }
        }
        checkAndPrintResult(correct);
    }

    private void checkAndPrintResult(int answer) {
        System.out.println();
        System.out.println("Ваш вариант ответа");
        Scanner sc = new Scanner(System.in);
        int userAnswer = sc.nextInt();
        if (userAnswer == answer) {
            System.out.println("Верно!");
            correctAnswers++;
        } else {
            System.out.println("Неверно!");
            wrongAnswers++;
        }
    }

    public void run() {
        readFile();
        addQuestionsToStorage();
        print();
    }
}

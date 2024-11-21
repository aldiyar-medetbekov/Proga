import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class ExamChecking extends Thread {
    private static int examSheets = 1000;
    private static final Object lock = new Object(); 
    private final String assistantName;

    public ExamChecking(String assistantName) {
        this.assistantName = assistantName;
    }

    @Override
    public void run() {
        int checkedSheets = 0;
        while (examSheets > 0) {
            synchronized (lock) {
                if (examSheets >= 25) {
                    examSheets -= 25;
                    checkedSheets += 25;
                    System.out.println(assistantName + " finished checking, at: " +
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss yyyy")) +
                            ", exam sheet count is now " + examSheets);
                } else {
                    break;
                }
            }

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                System.out.println(assistantName + " was interrupted.");
            }
        }
        System.out.println(assistantName + " has finished checking their assigned sheets.");
    }
}

public class ExamChecker {
    public static void main(String[] args) {
        System.out.println("Starting the exam sheet checking process...");

        ExamChecking aldiyar = new ExamChecking("Aldiyar");
        ExamChecking marlen = new ExamChecking("Marlen");
        ExamChecking abunasir = new ExamChecking("Abunasir");

        aldiyar.start();

        try {
            Thread.sleep(2000);
            abunasir.start();

            Thread.sleep(3000);
            marlen.start();
        } catch (InterruptedException e) {
            System.out.println("Main thread was interrupted.");
        }

        try {
            aldiyar.join();
            marlen.join();
            abunasir.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread was interrupted.");
        }

        System.out.println("All assistants have completed their work.");
    }
}




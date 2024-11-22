import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

public class Main {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\user\\IdeaProjects\\Main2\\src\\Failgo.xlsx";

        List<Student> students = readStudentsFromExcel(filePath);

        for (Student student : students) {
            System.out.printf("Имя: %s, Текущая стипендия: %.2f, Новая стипендия: %.2f, Увеличение: %.2f%n",
                    student.getName(),
                    student.getCurrentScholarship(),
                    student.getNewScholarship(),
                    student.getScholarshipIncrease());
        }
    }

    public static List<Student> readStudentsFromExcel(String filePath) {
        List<Student> students = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(filePath));
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell nameCell = row.getCell(0);
                Cell currentScholarshipCell = row.getCell(1);
                Cell newScholarshipCell = row.getCell(2);

                String name = (nameCell != null) ? nameCell.getStringCellValue() : "";
                double currentScholarship = (currentScholarshipCell != null) ? currentScholarshipCell.getNumericCellValue() : 0.0;
                double newScholarship = (newScholarshipCell != null) ? newScholarshipCell.getNumericCellValue() : 0.0;

                students.add(new Student(name, currentScholarship, newScholarship));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return students;
    }
}

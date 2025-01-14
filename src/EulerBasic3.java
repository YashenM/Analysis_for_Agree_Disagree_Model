import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.Scanner;


public class EulerBasic3 {

    public static void main(String[] args) {

        //Taking values for the parameters of the differential equations from the keyboard
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter alpha_1:");
        double alpha1 = scanner.nextDouble();

        System.out.println("Enter alpha_2:");
        double alpha2 = scanner.nextDouble();

        System.out.println("Enter beta_1:");
        double beta1 = scanner.nextDouble();

        System.out.println("Enter beta_2:");
        double beta2 = scanner.nextDouble();

        System.out.println("Enter gamma_1:");
        double gamma1 = scanner.nextDouble();

        System.out.println("Enter gamma_2:");
        double gamma2 = scanner.nextDouble();

        // Close the scanner after reading all inputs
        scanner.close();

        // Calculating R_D0
        double numeratorRD0 = alpha2 * beta1 - alpha2 * gamma1 + beta2 * gamma1;
        double denominatorRD0 = alpha1 * beta1 - alpha1 * gamma1 + beta1 * gamma2;
        double RD0 = numeratorRD0 / denominatorRD0;


        // Calculating R_A0
        double numeratorRA0 = alpha1 * beta2 - alpha1 * gamma2 + beta1 * gamma2;
        double denominatorRA0 = alpha2 * beta2 - alpha2 * gamma2 + beta2 * gamma1;
        double RA0 = numeratorRA0 / denominatorRA0;

        System.out.println("R_D0 = " + RD0);
        System.out.println("R_A0 = " + RA0);

        double N = 100.0;  // Total population

        // Initial values of I, A and D
        double I = 10.0;
        double A = 45.0;
        double D = 45.0;

        // Time parameters for euler method
        double dt = 0.1;  // Time step
        double T = 13;  // Total time

        // Number of iterations
        int steps = (int)(T / dt);

        // Creating a new Workbook
        Workbook workbook = new XSSFWorkbook();
        // Creating a new sheet
        Sheet sheet = workbook.createSheet("Euler Method Results");

        // Creating header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Time (t)");
        header.createCell(1).setCellValue("Ignorant (I)");
        header.createCell(2).setCellValue("Agree (A)");
        header.createCell(3).setCellValue("Disagree (D)");

        // Euler method loop
        for (int i = 0; i <= steps; i++) {
            double time = i * dt;

            // Creating a new row in the sheet
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(time);
            row.createCell(1).setCellValue(I);
            row.createCell(2).setCellValue(A);
            row.createCell(3).setCellValue(D);

            double I_prime = -beta1 * (A * I) / N - beta2 * (D * I) / N + gamma1 * A + gamma2 * D;
            double A_prime = beta1 * (A * I) / N + alpha1 * (A * D) / N - alpha2 * (A * D) / N - gamma1 * A;
            double D_prime = beta2 * (D * I) / N + alpha2 * (A * D) / N - alpha1 * (A * D) / N - gamma2 * D;

            // Update values
            I += I_prime * dt;
            A += A_prime * dt;
            D += D_prime * dt;
        }

        // Writing the output to an external excel file
        try (FileOutputStream fileOut = new FileOutputStream("EulerResults.xlsx")) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Results can be seen on EulerResults.xlsx");
    }
}

/*basic Floyd's Triangle */

import java.util.Scanner;

public class FloydTriangle {
    public static void main(String[] args) {
        Scanner idk = new Scanner(System.in);

        System.out.print("Enter the number of rows: ");
        int rows = idk.nextInt();

        System.out.println("Floyd's Triangle with "+ rows+ " rows: ");
        printFloydTriangle(rows);

        idk.close();
    }

    public static void printFloydTriangle(int rows) {
        int number = 1;
        for (int i=1; i<=rows; i++ ){
            for(int j = 1; j<= i;j++){
                System.out.print(number+ " ");
                number++;
            }
            System.out.println();
        }
    }
    
}

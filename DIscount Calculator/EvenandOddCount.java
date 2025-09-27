import java.util.Scanner;

public class EvenandOddCount {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numelements,evencount=0,oddcount=0;

        System.out.print("Enter the number of elements: ");
        numelements = sc.nextInt();

        int[] numbers = new int[numelements];
        System.out.print("Enter the elements:");

        for (int i = 0; i < numelements; i++) { 
            numbers[i] = sc.nextInt();
            if(numbers[i] % 2 == 0) {
                evencount++;
            } else {
                oddcount++;
            }
        }

        System.out.println("Even count: " + evencount);
        System.out.println("Odd count: " + oddcount);

         sc.close();
    }
}

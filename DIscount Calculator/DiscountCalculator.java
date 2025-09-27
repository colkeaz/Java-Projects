import java.util.Scanner;
public class DiscountCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double originalPrice, finalPrice,discountpercentage;
        String discountType;

    
        System.out.print("Enter the original price: ");
        originalPrice = sc.nextDouble();
        

        System.out.print("Enter the discount type (regular/member): ");
        discountType = sc.next();

        if(discountType.equalsIgnoreCase("regular")){
            discountpercentage = 0;
        } else if(discountType.equalsIgnoreCase("member")){
        if(originalPrice >= 500){
            discountpercentage = 0.10;
        }else{
            discountpercentage = 0.05;
        }
        } else {
            System.out.println("Invalid discount type. Please enter 'regular' or 'member'.");
            sc.close();
            return;
        }

        finalPrice = calculateFinalPrice(originalPrice, discountpercentage);
        System.out.printf("The final price after discount is: %.2f%n" , finalPrice);

        sc.close();
    }

    public static double calculateFinalPrice(double originalPrice, double discountpercentage) {
        return (originalPrice - (originalPrice * discountpercentage));
    }

}

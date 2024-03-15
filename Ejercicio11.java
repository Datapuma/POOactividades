import java.util.Scanner;

public class EJERCICIO11 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el primer número: ");
        int N1 = scanner.nextInt();
        System.out.print("Ingrese el segundo número: ");
        N2 = scanner.nextInt();
        System.out.print("Ingrese el tercer número: ");
        N3 = scanner.nextInt();
        int MAYOR;
        if (N1 > N2 && N1 > N3) {
            MAYOR = N1;
        } else if (N2 > N3) {
            MAYOR = N2;
        } else {
            MAYOR = N3;
        }
        System.out.println("EL VALOR MAYOR ENTRE:" + N1 + ", " + N2 + " Y " + N3 + " ES: " + MAYOR);
    }
}

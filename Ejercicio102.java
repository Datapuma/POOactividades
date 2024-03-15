import java.util.Scanner;
public class Ejercicio10 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese número de inscripción: ");
        int NI = Integer.parseInt(scanner.nextLine());

        System.out.print("Ingrese nombres: ");
        String NOM = scanner.nextLine();
        System.out.print("Ingrese el valor del Patrimonio.: ");
        Double PAT = Double.parseDouble(scanner.nextLine());
        System.out.print("Ingrese el Estrato social.: ");
        int EST = Integer.parseInt(scanner.nextLine());
        double PAGMAT = 50000; 
        if (PAT > 2000000 && EST > 3) {
            PAGMAT += 0.03 * PAT;
        }
        System.out.println("EL ESTUDIANTE CON NUMERO DE INSCRIPCION" + NI "Y NOMBRE " + NOM + " DEBE PAGAR: $" + PAGMAT);;
    }
}

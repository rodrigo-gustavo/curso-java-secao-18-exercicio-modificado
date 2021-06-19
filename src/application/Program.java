package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

import entities.Contract;
import entities.Installment;
import services.Bradesco;
import services.ContractService;
import services.NuPayment;
import services.PaypalService;
import services.PicPayService;

public class Program {

	public static void main(String[] args) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter contract data");
		System.out.print("Number: ");
		int number = sc.nextInt();
		System.out.print("Date (dd/MM/yyyy): ");
		Date date = sdf.parse(sc.next());
		System.out.print("Contract Value: ");
		double contractValue = sc.nextDouble();

		Contract contract = new Contract(number, date, contractValue);

		System.out.print("Enter number of installments: ");
		int N = sc.nextInt();

		try {
			System.out.println();
			System.out.println("Select which payment method");
			System.out.println("(1) - Paypal");
			System.out.println("(2) - PicPay");
			System.out.println("(3) - Nubank");
			System.out.println("(4) - Bradesco");
			System.out.print("Type here: ");
			int paymentMethod = sc.nextInt();

			switch (paymentMethod) {
			case 1:
				ContractService cs1 = new ContractService(new PaypalService());
				cs1.processContract(contract, N);
				break;
			case 2:
				ContractService cs2 = new ContractService(new PicPayService());
				cs2.processContract(contract, N);
				break;
			case 3:
				ContractService cs3 = new ContractService(new NuPayment());
				cs3.processContract(contract, N);
				break;
			case 4:
				ContractService cs4 = new ContractService(new Bradesco());
				cs4.processContract(contract, N);
				break;
			}
		} catch (InputMismatchException e) {
			System.out.println("Error: " + e.getMessage());
		}

		System.out.println();
		System.out.println("INSTALLMENTS:");
		for (Installment i : contract.getInstallments()) {
			System.out.println(i);
		}

		sc.close();
	}
}

package services;

import java.util.Calendar;
import java.util.Date;

import entities.Contract;
import entities.Installment;

public class ContractService {

	OnlinePaymentService onlinePaymentService;

	public ContractService(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}

	public OnlinePaymentService getOnlinePaymentService() {
		return onlinePaymentService;
	}

	public void setOnlinePaymentService(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}

	public void processContract(Contract contract, int months) {
		double basicPayment = contract.getTotalValue() / months;
		for (int i = 1; i <= months; i++) {
			double updatedPayment = basicPayment + onlinePaymentService.interest(basicPayment, i);

			double fullPayment = updatedPayment + onlinePaymentService.paymentFee(updatedPayment);

			Date date = addMonth(contract.getDate(), i);
			contract.getInstallments().add(new Installment(date, fullPayment));
		}
	}

	public Date addMonth(Date date, int N) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, N);
		return calendar.getTime();
	}
}

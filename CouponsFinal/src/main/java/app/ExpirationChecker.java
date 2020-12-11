package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.beans.Coupon;
import app.beans.Customer;
import app.repositories.CouponRepository;
import app.repositories.CustomerRepository;

@Component
public class ExpirationChecker extends Thread {
	@Autowired
	private CouponRepository editCoupons;
	@Autowired
	private CustomerRepository editcustomers;
	boolean fileDoesNotExist = false;
	static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	String line = "";
	static boolean alive = true;

	@Override
	public void run() {
		while (alive) {
			try {
				FileReader findFile = new FileReader("C:\\Java stuff\\Date Of Last Expiration Check.txt");
				BufferedReader readFile = new BufferedReader(findFile);
				line = readFile.readLine();
				findFile.close();
			} catch (IOException e) {
				fileDoesNotExist = true;
				System.out.println("The system cannot find the path specified, A path will be created!");
			}
			if (fileDoesNotExist || !isValidDate(line)) {
				try {
					File file = new File("C:\\Java stuff\\Date Of Last Expiration Check.txt");
					file.getParentFile().mkdirs();
					FileWriter fileLocation = new FileWriter(file, false);
					// FileWriter fileLocation = new FileWriter("D:\\Java stuff\\Date Of Last
					// Expiration Check.txt", false);
					Date date = Calendar.getInstance().getTime();
					String strDate = sdf.format(date);
					fileLocation.write(strDate);
					fileLocation.close();
					fileDoesNotExist = false;
					CheckExpirations(date);
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			} else {
				Calendar oldDate = Calendar.getInstance();
				try {
					oldDate.setTime(sdf.parse(line));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Calendar cal = Calendar.getInstance();
				if (oldDate.get(Calendar.DATE) < cal.get(Calendar.DATE)
						|| oldDate.get(Calendar.MONTH) < cal.get(Calendar.MONTH)
						|| oldDate.get(Calendar.YEAR) < cal.get(Calendar.YEAR)) {
					Date date = cal.getTime();
					CheckExpirations(date);
					try {
						FileWriter fileLocation = new FileWriter("C:\\Java stuff\\Date Of Last Expiration Check.txt",
								false);
						String strDate = sdf.format(date);
						fileLocation.write(strDate);
						fileLocation.close();
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}
				}
			}
		}
	}

	/**
	 * checks if coupon is expired and deletes it if it is.
	 * 
	 * @param date
	 */
	public synchronized void CheckExpirations(Date date) {
		List<Coupon> coupons = editCoupons.findAll();
		for (int i = 0; i < coupons.size(); i++) {
			if (coupons.get(i).getEndDate().after(date)) {
				List<Customer> customers=editcustomers.findAll();
				for (int j = 0; j < customers.size(); j++) {
					List<Coupon> customerCoupons = customers.get(j).getCoupons();
					for (int k = 0; k < customerCoupons.size(); k++) {
						if(customerCoupons.get(k).getId()==coupons.get(i).getId())
							customerCoupons.remove(k);
					}
					updateCustomerCoupons(customers.get(j).getEmail(), customerCoupons);
				}
				editCoupons.deleteById(coupons.get(i).getId());
			}
		}
	}

	/**
	 * checks if String received is in sdf format.
	 * 
	 * @param inDate
	 */
	public static synchronized boolean isValidDate(String inDate) {
		sdf.setLenient(false);
		try {
			sdf.parse(inDate.trim());
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}
	
	public void updateCustomerCoupons(String email, List<Coupon> customerCoupons){
		Customer check = editcustomers.findByEmail(email);
		check.setCoupons(customerCoupons);
		editcustomers.save(check);
}

	/**
	 * terminates ExpirationChecker
	 */
	public void terminate() {
		alive = false;
	}

}

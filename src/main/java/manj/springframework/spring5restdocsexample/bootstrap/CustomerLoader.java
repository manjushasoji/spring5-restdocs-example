package manj.springframework.spring5restdocsexample.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import manj.springframework.spring5restdocsexample.domain.Customer;
import manj.springframework.spring5restdocsexample.repositories.CustomerRepository;

@Component
public class CustomerLoader implements CommandLineRunner {

	private final CustomerRepository customerRepository;

	public CustomerLoader(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		loadCustomers();
		System.out.println("Customers Loaded :"+ customerRepository.count());

	}
	
	private void loadCustomers() {
		if(customerRepository.count() ==0) {
			customerRepository.save(Customer.builder()
							.customerName("Manj")
							.customerAge(10)
							.build());
			customerRepository.save(Customer.builder()
					.customerName("Mariam")
					.customerAge(10)
					.build());
		}
		
	}

}

package manj.springframework.spring5restdocsexample.web.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import manj.springframework.spring5restdocsexample.repositories.CustomerRepository;
import manj.springframework.spring5restdocsexample.web.mappers.CustomerMapper;
import manj.springframework.spring5restdocsexample.web.model.CustomerDto;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
	
	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CustomerDto getCustomerById(@PathVariable UUID id) {
		return customerMapper.customerToCustomerDto(customerRepository.findById(id).get());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CustomerDto saveNewCustomer(@RequestBody @Validated CustomerDto customerDto) {
		
		return customerMapper.customerToCustomerDto(customerRepository
				.save(customerMapper.customerDtoToCustomer(customerDto)) );
	}

}

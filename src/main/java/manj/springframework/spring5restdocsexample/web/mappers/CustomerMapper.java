package manj.springframework.spring5restdocsexample.web.mappers;

import org.mapstruct.Mapper;

import manj.springframework.spring5restdocsexample.domain.Customer;
import manj.springframework.spring5restdocsexample.web.model.CustomerDto;

@Mapper(uses = DateMapper.class)
public interface CustomerMapper {
	
	Customer customerDtoToCustomer(CustomerDto customerDto);
	
	CustomerDto customerToCustomerDto(Customer customer);

}

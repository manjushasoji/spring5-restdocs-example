package manj.springframework.spring5restdocsexample.repositories;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

import manj.springframework.spring5restdocsexample.domain.Customer;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, UUID>{

}

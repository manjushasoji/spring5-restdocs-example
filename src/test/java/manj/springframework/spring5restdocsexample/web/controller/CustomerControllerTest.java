package manj.springframework.spring5restdocsexample.web.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import manj.springframework.spring5restdocsexample.domain.Customer;
import manj.springframework.spring5restdocsexample.repositories.CustomerRepository;
import manj.springframework.spring5restdocsexample.web.model.CustomerDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
// @AutoConfigureRestDocs(uriScheme = "https", uriHost = "dev.springframework.sample", uriPort = 80)
@WebMvcTest(CustomerController.class)
@ComponentScan(basePackages = "manj.springframework.spring5restdocsexample.web.mappers")
class CustomerControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	CustomerRepository customerRepository;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testGetCustomerById() throws Exception {
		given(customerRepository.findById(any())).willReturn(Optional.of(Customer.builder().customerName("AnnaSoji").customerAge(3).build()));

		mockMvc.perform(get("/api/v1/customer/{id}", UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("v1/customer-get",
						pathParameters(parameterWithName("id").description("UUID of Customer to get.")),
						//requestParameters(parameterWithName("customerName").description("Name of Customer")),
						responseFields(fieldWithPath("id").description("Id of Customer"),
								fieldWithPath("version").description("Version number"),
								fieldWithPath("createdDate").description("Date Created"),
								fieldWithPath("lastModifiedDate").description("Date Updated"),
								fieldWithPath("customerName").description("Customer Name"),
								fieldWithPath("customerAge").description("Customer Age")

						)));
	}

	@Test
	void testSaveNewCustomer() throws Exception {
		CustomerDto customerDto = CustomerDto.builder().customerName("AnnaSoji").customerAge(3).build();
		String CustomerDtoJson = objectMapper.writeValueAsString(customerDto);
		
		ConstrainedFields fields = new ConstrainedFields(CustomerDto.class);

		mockMvc.perform(post("/api/v1/customer/").contentType(MediaType.APPLICATION_JSON).content(CustomerDtoJson))
				.andExpect(status().isCreated())
				.andDo(document("v1/customer-new",
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("version").ignored(),
                                fields.withPath("createdDate").ignored(),
                                fields.withPath("lastModifiedDate").ignored(),
                                fields.withPath("customerName").description("Name of the Customer"),
                                fields.withPath("customerAge").description("Age of Customer")
                                
                        )));
	}
	
	private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }

}

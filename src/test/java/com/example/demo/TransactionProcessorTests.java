package com.example.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@SpringBootTest
class TransactionProcessorTests {

	private  List<Transaction> transactionList;

	@BeforeEach
	public void beforeAll() throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		System.out.println(OffsetDateTime.now());
		transactionList = objectMapper.readValue(TransactionProcessorTests.class.getResource("/testdata.json"),
				new TypeReference<List<Transaction>>(){});
	}

	@Test
	public void test_process() {

		 Map<String, Map<String, Double>> customerTotals = TransactionProcessor.process(transactionList);

		for (Map.Entry<String, Map<String,Double>> entry: customerTotals.entrySet()) {

			System.out.println("CustomerId: "+entry.getKey());

			for (Map.Entry<String, Double> totalsEntry: entry.getValue().entrySet()) {
				System.out.println(totalsEntry.getKey()+" : "+totalsEntry.getValue());

			}
		}
	}
}

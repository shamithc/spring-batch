package com.shamith.springbatch;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Host;
import com.aerospike.client.policy.ClientPolicy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchApplication.class, args);
	}

	@Bean
	public AerospikeClient aerospikeClient() {
		ClientPolicy clientPolicy = new ClientPolicy();
		Host[] hosts = new Host[1];
		hosts[0] = new Host("localhost", 3000);
		AerospikeClient client = new AerospikeClient(clientPolicy, hosts);
		return client;
	}
}

package com.flamelab.shopserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.flamelab.shopserver"})
public class ShopServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopServerApplication.class, args);
	}

}

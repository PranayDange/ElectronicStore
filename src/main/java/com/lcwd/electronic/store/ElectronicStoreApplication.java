package com.lcwd.electronic.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElectronicStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectronicStoreApplication.class, args);
	}

	//1.class---entity -Product
	//2.class---dtos   -ProductDto
	//3.interface---repositories--ProductRepository
	//4.interface--services --ProductServices
	//5.class --service->imple --ProductServiceImpl
	//6.class controller --ProductController


	//for image
	//entity ..property...private String productImageName;
	//product dto ..property...private String productImageName;
	//in application properties give a path product.image.path=images/products/
	//controller two methods 1.upload image and 2. serve image
}

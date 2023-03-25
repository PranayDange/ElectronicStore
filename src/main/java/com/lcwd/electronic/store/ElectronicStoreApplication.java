package com.lcwd.electronic.store;

import com.lcwd.electronic.store.entities.Role;
import com.lcwd.electronic.store.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.UUID;

@SpringBootApplication
@EnableWebMvc
public class ElectronicStoreApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ElectronicStoreApplication.class, args);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    //by using through application.properties
    @Value("${normal.role.id}")
    private String role_normal_id;
    @Value("${admin.role.id}")
    private String role_admin_id;

    @Override
    public void run(String... args) throws Exception {
        System.out.printf(passwordEncoder.encode("abcd"));

        try {
            /*String role_admin_id = "sefsefseff323e";
            String role_normal_id = "esvgg44eggg";*/

            //by using uuid.random
            /*Role roleAdmin = Role.builder()
                    .roleId(UUID.randomUUID().toString())
                    .roleName("ROLE_ADMIN")
                    .build();*/

            //by using through application.properties
            Role roleAdmin = Role.builder()
                    .roleId(role_admin_id)
                    .roleName("ROLE_ADMIN")
                    .build();

            Role roleNormal = Role.builder()
                    .roleId(role_normal_id)
                    .roleName("ROLE_NORMAL")
                    .build();

            roleRepository.save(roleAdmin);
            roleRepository.save(roleNormal);

        } catch (Exception e) {
            e.printStackTrace();
        }

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

    //cart module
    //generally cart is managed on client side
    //user is required to perform cart operations
    //class --entity--Cart
    //class --entity--CartItem
    //creating dtos of cartItem and cart
    //create interface cartservice
    //create interface CartReposifory
    //create class inside impl with name CardServiceImpl


    //order
    //create Order class in entity
    //create OrderItem class in entity

    //creating dto's --OrderDto and OrderItemDto
    //create interface OrderService in services

    //create jpa repository of OrderItemRepository and OrderRepository
    //create OrderserviceImpl
    //create controller


    //added spring-boot-starter-security dependency in pom.xml

}

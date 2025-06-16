package dev.sertan.payroll;

import dev.sertan.payroll.employee.Employee;
import dev.sertan.payroll.employee.EmployeeRepository;
import dev.sertan.payroll.order.Order;
import dev.sertan.payroll.order.OrderRepository;
import dev.sertan.payroll.order.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger logger  = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDB(EmployeeRepository employeeRepository, OrderRepository orderRepository) {
        return args -> {
            employeeRepository.save(new Employee("Bilbo", "Baggins", "burglar"));
            employeeRepository.save(new Employee("Frodo", "Baggins", "thief"));

            employeeRepository.findAll().forEach(employee -> {
                logger.info("Preloaded " + employee);
            });

            orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
            orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));

            orderRepository.findAll().forEach(order -> {
                logger.info("Preloaded " + order);
            });


        };
    }
}

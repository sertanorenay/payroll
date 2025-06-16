package dev.sertan.payroll.employee;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/employees")
@RestController
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final EmployeeModelAssembler employeeModelAssembler;

    EmployeeController(EmployeeRepository employeeRepository, EmployeeModelAssembler employeeModelAssembler) {
        this.employeeRepository = employeeRepository;
        this.employeeModelAssembler = employeeModelAssembler;
    }

    @GetMapping("/{id}")
    EntityModel<Employee> one(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        return employeeModelAssembler.toModel(employee);
    }

    @GetMapping
    CollectionModel<EntityModel<Employee>> all() {
        List<EntityModel<Employee>> employees = employeeRepository.findAll().stream()
                .map(employeeModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }

    @PostMapping
    ResponseEntity<EntityModel<Employee>> newEmployee(@RequestBody Employee newEmployee) {
        EntityModel<Employee> employeeEntityModel = employeeModelAssembler.toModel(employeeRepository.save(newEmployee));

        return ResponseEntity
                .created(employeeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(employeeEntityModel);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<EntityModel<Employee>> updateEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        Employee updatedEmployee = employeeRepository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return employeeRepository.save(employee);
                })
                .orElseGet(() -> {
                    return employeeRepository.save(newEmployee);
                });

        EntityModel<Employee> employeeEntityModel = employeeModelAssembler.toModel(updatedEmployee);

        return ResponseEntity
                .created(employeeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(employeeEntityModel);
    }
}


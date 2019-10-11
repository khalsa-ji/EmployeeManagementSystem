//	Waheguru Ji!

package com.example.employeemanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementSystemApplication.class, args);
	}


			//	Runtime initialisation of data in Designation table.
	//	@Bean
//	public CommandLineRunner initialiseData(DesignationRepository designationRepository) {
//		return args -> {
//			//	Saving initial data into the Designation table.
//			designationRepository.save(new DesignationBuilder()
//					.setLevelID(1)
//					.setDesignation("director")
//					.build());
//			designationRepository.save(new DesignationBuilder()
//					.setLevelID(2)
//					.setDesignation("manager")
//					.build());
//			designationRepository.save(new DesignationBuilder()
//					.setLevelID(3)
//					.setDesignation("lead")
//					.build());
//			designationRepository.save(new DesignationBuilder()
//					.setLevelID(4)
//					.setDesignation("developer")
//					.build());
//			designationRepository.save(new DesignationBuilder()
//					.setLevelID(4)
//					.setDesignation("devops")
//					.build());
//			designationRepository.save(new DesignationBuilder()
//					.setLevelID(4)
//					.setDesignation("qa")
//					.build());
//			designationRepository.save(new DesignationBuilder()
//					.setLevelID(5)
//					.setDesignation("intern")
//					.build());
//		};
//	}
}

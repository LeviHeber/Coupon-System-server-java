package couponsSystem.core;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import couponsSystem.core.controllers.sessions.SessionContext;
import couponsSystem.core.entites.Company;
import couponsSystem.core.entites.Customer;
import couponsSystem.core.filters.ClientFilter;
import couponsSystem.core.services.AdminService;
import couponsSystem.core.services.Impl.LoginServiceImpl.ClientType;
import couponsSystem.core.test.Test;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@PropertySource("application.properties")
@EnableSwagger2
public class AppConfig {


	public static void main(String[] args) {
		Test test =  SpringApplication.run(AppConfig.class, args).getBean(Test.class);
//		test.testAll();

	}

	/**
	 * Initial run at boot of the database for the test
	 * 
	 * @param adminService
	 * @return
	 */
//	@Bean
//	public CommandLineRunner runner(AdminService adminService) {
//		return new CommandLineRunner() {
//			@Override
//			public void run(String... args) throws Exception {
//				Company company = new Company();
//				company.setName("levi");
//				company.setEmail("levi@levi");
//				company.setPassword("levi password");
//				Customer customer = new Customer();
//				customer.setEmail("levi@levi");
//				customer.setPassword("levi password");
//				adminService.addCompany(company);
//				adminService.addCustomer(customer);
//			}
//		};
//	}

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}

	@Bean
	public FilterRegistrationBean<ClientFilter> adminFilterRegistrationBean(SessionContext sessionContext) {
		FilterRegistrationBean<ClientFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new ClientFilter(sessionContext, ClientType.ADMINISTRATOR));
		filterRegistrationBean.setName("adminFilter");
		filterRegistrationBean.addUrlPatterns("/admin/*");
		return filterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean<ClientFilter> companyFilterRegistrationBean(SessionContext sessionContext) {
		FilterRegistrationBean<ClientFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new ClientFilter(sessionContext, ClientType.COMPANY));
		filterRegistrationBean.setName("companyFilter");
		filterRegistrationBean.addUrlPatterns("/company/*");
		return filterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean<ClientFilter> customerFilterRegistrationBean(SessionContext sessionContext) {
		FilterRegistrationBean<ClientFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new ClientFilter(sessionContext, ClientType.CUSTOMER));
		filterRegistrationBean.setName("customerFilter");
		filterRegistrationBean.addUrlPatterns("/customer/*");
		return filterRegistrationBean;
	}

}

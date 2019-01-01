package io.vanachte.jan.bootstrap.jpa;

import io.vanachte.jan.bootstrap.address.AddressRepositoryJpaImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {AddressRepositoryJpaImpl.class})
public class JpaRepositoryConfiguration {
}

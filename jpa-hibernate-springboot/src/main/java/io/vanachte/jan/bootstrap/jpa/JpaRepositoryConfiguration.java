package io.vanachte.jan.bootstrap.jpa;

import io.vanachte.jan.bootstrap.address.AddressReadOnlyRepositoryJpaImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {AddressReadOnlyRepositoryJpaImpl.class})
public class JpaRepositoryConfiguration {
}

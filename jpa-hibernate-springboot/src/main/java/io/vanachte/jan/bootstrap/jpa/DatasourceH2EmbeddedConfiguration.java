package io.vanachte.jan.bootstrap.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class DatasourceH2EmbeddedConfiguration {

    @Bean
    public DataSource dataSource(){

        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase dataSource = builder
                .setType(EmbeddedDatabaseType.H2) //.H2 or .DERBY
//                .addScript("schema.sql") // automagically
//                .addScript("data.sql") // automagically
                .build();
        return dataSource;
    }


}

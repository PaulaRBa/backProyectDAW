package com.daw.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//Esta clase, con las anotaciones que contiene, es necesaria 
//para que funcione el mecanismo de auditoría que nos genera
//las fechas automáticamente
@Configuration
@EnableJpaAuditing
public class JpaConfiguration {

}

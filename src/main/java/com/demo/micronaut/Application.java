package com.demo.micronaut;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
    info = @Info(
        title = "People API",
        version = "1.0",
        description = "Demo People API - JDK17/Micronaut framework",
        contact = @Contact(name = "Cloud Platform team", email = "cloud.platform@travelport365.onmicrosoft.com")
    )
)
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}

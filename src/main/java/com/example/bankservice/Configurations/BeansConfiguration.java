package com.example.bankservice.Configurations;

import com.example.bankservice.Envelope;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

@Configuration
public class BeansConfiguration {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public Marshaller makeMarshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Envelope.class);
        Marshaller marshaller = jaxbContext.createMarshaller();

        return marshaller;
    }

    @Bean
    public Unmarshaller makeUnmarshaller() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Envelope.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        return unmarshaller;
    }
}

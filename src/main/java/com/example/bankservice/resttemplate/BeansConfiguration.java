package com.example.bankservice.resttemplate;

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
    public Marshaller makeMarshaller(Marshaller marshaller) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Envelope.class);
        marshaller = jaxbContext.createMarshaller();

        return marshaller;
    }

    @Bean
    public Unmarshaller makeUnmarshaller(Unmarshaller unmarshaller) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance();
        unmarshaller = jaxbContext.createUnmarshaller();

        return unmarshaller;
    }
}

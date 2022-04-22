package com.example.bankservice.Clients;

import com.example.bankservice.AllData;
import com.example.bankservice.AllDataInfoXML;
import com.example.bankservice.Envelope;
import com.example.bankservice.properties.ExternalProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CBRClient extends WebServiceGatewaySupport {

    private final RestTemplate restTemplate;
    private final ExternalProperties externalProperties;

    private HttpEntity<Map<String, Object>> makeEntity(String writer, HttpHeaders headers) {
        headers.setContentType(MediaType.TEXT_XML);
        headers.setAccept(Collections.singletonList(MediaType.TEXT_XML));
        HttpEntity<Map<String, Object>> entity = new HttpEntity(writer, headers);

        return entity;
    }

    private void makeMarshaller(Envelope envelope, StringWriter writer) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Envelope.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.marshal(envelope, writer);
    }

    private Unmarshaller makeUnmarshaller() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Envelope.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        return unmarshaller;
    }

    private ResponseEntity<String> makeRequest(String writer, HttpHeaders headers){
        ResponseEntity<String> response = restTemplate
                .exchange(externalProperties.getCbr(), HttpMethod.POST, makeEntity(writer, headers), String.class);

        return response;
    }

    public AllData.MainIndicatorsVR getCurrencyData() throws JAXBException {
        Envelope envelope = new Envelope();
        StringWriter writer = new StringWriter();
        HttpHeaders headers = new HttpHeaders();

        envelope.setBody(new Envelope.Body());
        envelope.getBody().setAllDataInfoXML(new AllDataInfoXML());
        makeMarshaller(envelope, writer);

        String responseXML = makeRequest(writer.toString(), headers).getBody();
        Envelope dataInfoXMLResponse = (Envelope) makeUnmarshaller().unmarshal(new StringReader(responseXML));

        return dataInfoXMLResponse.getBody().getAllDataInfoXMLResponse()
                .getAllDataInfoXMLResult().getAllData().getMainIndicatorsVR();
    }
}

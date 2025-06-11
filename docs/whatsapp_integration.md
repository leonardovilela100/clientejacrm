# WhatsApp Integration Guide

This document outlines a possible approach to integrate WhatsApp messaging with the Quarkus back-end. The guide focuses on using the official WhatsApp Cloud API from Meta, but the same concepts apply if using a provider such as Twilio.

## Prerequisites

1. A Facebook developer account with a registered Meta business application.
2. Access to the [WhatsApp Business Cloud API](https://developers.facebook.com/docs/whatsapp/cloud-api).
3. A phone number registered with the WhatsApp Business API.
4. The following information from Meta:
   - **Phone Number ID** – identifies the business phone number.
   - **WhatsApp Business Account ID**.
   - **Permanent Access Token** with the `whatsapp_business_messaging` permission.

## Quarkus Setup

Add the required HTTP client dependency to `pom.xml` if not present:

```xml
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-rest-client</artifactId>
</dependency>
```

(For Twilio integration use the `com.twilio.sdk` dependency instead.)

Create a configuration section in `application.properties` for the WhatsApp API:

```properties
whatsapp.base-url=https://graph.facebook.com/v18.0/
whatsapp.token=<PERMANENT_ACCESS_TOKEN>
whatsapp.phone-id=<PHONE_NUMBER_ID>
```

## REST Client Interface

Define a REST client to call the Cloud API:

```java
package br.com.clientejacrm.whatsapp;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.ClientException;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("{phone-id}/messages")
@RegisterRestClient(configKey = "whatsapp")
public interface WhatsAppClient {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    void sendMessage(MessageRequest request) throws ClientException;
}
```

Where `MessageRequest` is a small DTO representing the WhatsApp payload.

## Service Layer

Create a service that formats the message body and calls the REST client:

```java
package br.com.clientejacrm.whatsapp;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class WhatsAppService {

    @Inject
    @RestClient
    WhatsAppClient client;

    public void sendText(String to, String text) {
        MessageRequest request = MessageRequest.text(to, text);
        client.sendMessage(request);
    }
}
```

## Usage

Inject `WhatsAppService` into existing resources or services. For example, sending a welcome message when a new lead is created:

```java
@Inject
WhatsAppService whatsappService;

@Transactional
public Lead create(Lead lead) {
    lead.setDataCriacao(LocalDateTime.now());
    leadRepository.persist(lead);
    whatsappService.sendText(lead.getTelefone(), "Olá, bem-vindo!");
    return lead;
}
```

## Additional Notes

- Ensure the access token and phone number ID are stored securely (e.g., environment variables or a secrets manager).
- The API uses a versioned base URL; adjust `v18.0` in the properties if Meta releases a new version.
- For production, implement error handling and consider rate limits or webhook callbacks.
- If using Twilio, the overall structure is similar, but the client and credentials will come from the Twilio SDK.


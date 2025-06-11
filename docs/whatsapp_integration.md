# Guia de Integração com WhatsApp

Este documento descreve uma possível abordagem para integrar mensagens do WhatsApp com o back-end em Quarkus. O guia foca no uso da API oficial WhatsApp Cloud da Meta, mas os mesmos conceitos se aplicam se você utilizar um provedor como o Twilio.

## Pré-requisitos

1. Conta de desenvolvedor do Facebook com um aplicativo comercial registrado.
2. Acesso à [WhatsApp Business Cloud API](https://developers.facebook.com/docs/whatsapp/cloud-api).
3. Um número de telefone registrado na API do WhatsApp Business.
4. As seguintes informações fornecidas pela Meta:
   - **Phone Number ID** – identifica o número de telefone comercial.
   - **WhatsApp Business Account ID**.
   - **Permanent Access Token** com a permissão `whatsapp_business_messaging`.

## Configuração do Quarkus

Adicione a dependência do cliente HTTP em `pom.xml` caso ainda não esteja presente:

```xml
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-rest-client</artifactId>
</dependency>
```

(Para integrar com o Twilio utilize a dependência `com.twilio.sdk`.)

Crie uma seção de configuração em `application.properties` para a API do WhatsApp:

```properties
whatsapp.base-url=https://graph.facebook.com/v18.0/
whatsapp.token=<PERMANENT_ACCESS_TOKEN>
whatsapp.phone-id=<PHONE_NUMBER_ID>
```

## Interface do Cliente REST

Defina um cliente REST para chamar a Cloud API:

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

`MessageRequest` é um pequeno DTO que representa o payload do WhatsApp.

## Camada de Serviço

Crie um serviço que formata o corpo da mensagem e chama o cliente REST:

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

## Uso

Injete `WhatsAppService` em recursos ou serviços existentes. Por exemplo, enviar uma mensagem de boas-vindas quando um novo lead é criado:

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

## Notas adicionais

- Garanta que o token de acesso e o phone number ID sejam armazenados de forma segura (por exemplo, variáveis de ambiente ou um gerenciador de segredos).
- A API usa uma URL base versionada; ajuste `v18.0` nas propriedades caso a Meta lance uma nova versão.
- Para produção, implemente tratamento de erros e considere limites de taxa ou callbacks de webhook.
- Se estiver utilizando o Twilio, a estrutura geral é semelhante, mas o cliente e as credenciais virão do SDK do Twilio.

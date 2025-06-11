# Guia de Integração com WhatsApp

Este guia resume como integrar o projeto à API oficial WhatsApp Cloud de forma nativa, sem provedores intermediários.

## Pré-requisitos

1. Conta de desenvolvedor do Facebook com um aplicativo comercial registrado.
2. Acesso à [WhatsApp Business Cloud API](https://developers.facebook.com/docs/whatsapp/cloud-api).
3. Um número de telefone habilitado na API.
4. Informações fornecidas pela Meta:
   - **Phone Number ID**
   - **WhatsApp Business Account ID**
   - **Permanent Access Token** com a permissão `whatsapp_business_messaging`

## Configuração

1. Adicione a dependência `quarkus-rest-client-reactive` ao `pom.xml`.
2. Configure as credenciais em `application.properties`:

```properties
whatsapp.base-url=https://graph.facebook.com/v18.0/
whatsapp.token=<SEU_TOKEN>
whatsapp.phone-id=<SEU_PHONE_ID>
quarkus.rest-client.whatsapp.url=${whatsapp.base-url}
```

## Funcionamento

O projeto contém um cliente REST (`WhatsAppClient`) e um serviço (`WhatsAppService`) responsáveis por enviar mensagens. O `LeadService` utiliza esse serviço para enviar automaticamente uma saudação ao criar um novo lead.

## Notas adicionais

- Guarde o token e o phone ID em um local seguro.
- Ajuste a versão da API conforme a Meta disponibilizar novas releases.
- Monitore limites de envio e implemente tratamento de erros em produção.

# Guia de Integração com WPPConnect

Este guia descreve como utilizar o servidor **wppconnect-serve** para enviar mensagens de WhatsApp. A integração é feita através de uma API REST exposta pelo servidor Node.js, dispensando o uso da API oficial da Meta.

## Configuração

1. Mantenha a dependência `quarkus-rest-client-reactive` no `pom.xml`.
2. Defina em `application.properties` o endereço do serviço:

```properties
wppconnect.base-url=http://localhost:3030/
quarkus.rest-client.wppconnect.url=${wppconnect.base-url}
```

## Funcionamento

O projeto possui um cliente REST (`WhatsAppClient`) e um serviço (`WhatsAppService`) que fazem requisições para o `wppconnect-serve`. Ao criar um novo lead, uma mensagem de saudação é enviada usando essa integração.

## Notas adicionais

- O servidor Node.js precisa estar em execução e com a sessão configurada.
- Guarde as informações de sessão na tabela `whatsapp_session` criada no banco de dados.

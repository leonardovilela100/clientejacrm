package br.com.clientejacrm.whatsapp;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class WhatsAppService {

    @Inject
    @RestClient
    WhatsAppClient client;

    public void sendText(String number, String text) {
        MessageRequest request = MessageRequest.text(number, text);
        client.sendMessage(request);
    }
}

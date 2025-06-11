package br.com.clientejacrm.whatsapp;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class WhatsAppService {

    @Inject
    @RestClient
    WhatsAppClient client;

    @ConfigProperty(name = "whatsapp.phone-id")
    String phoneId;

    public void sendText(String to, String text) {
        MessageRequest request = MessageRequest.text(to, text);
        client.sendMessage(phoneId, request);
    }
}

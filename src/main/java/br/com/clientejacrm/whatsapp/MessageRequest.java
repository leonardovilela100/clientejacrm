package br.com.clientejacrm.whatsapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {

    private String messaging_product = "whatsapp";
    private String to;
    private String type = "text";
    private Text text;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Text {
        private String body;
    }

    public static MessageRequest text(String to, String body) {
        return new MessageRequest("whatsapp", to, "text", new Text(body));
    }
}

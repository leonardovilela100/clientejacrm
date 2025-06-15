package br.com.clientejacrm.whatsapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {

    private String number;
    private String message;

    public static MessageRequest text(String number, String message) {
        return new MessageRequest(number, message);
    }
}

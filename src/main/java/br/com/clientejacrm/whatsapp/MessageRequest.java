package br.com.clientejacrm.whatsapp;


public class MessageRequest {

    private String number;
    private String message;

    public MessageRequest() {
    }

    public MessageRequest(String number, String message) {
        this.number = number;
        this.message = message;
    }

    public static MessageRequest text(String number, String message) {
        return new MessageRequest(number, message);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

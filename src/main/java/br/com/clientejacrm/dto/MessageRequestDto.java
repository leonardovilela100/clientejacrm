package br.com.clientejacrm.dto;



public class MessageRequestDto {

    private String number;
    private String message;

    public MessageRequestDto() {
    }

    public MessageRequestDto(String number, String message) {
        this.number = number;
        this.message = message;
    }

    public static MessageRequestDto text(String number, String message) {
        return new MessageRequestDto(number, message);
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

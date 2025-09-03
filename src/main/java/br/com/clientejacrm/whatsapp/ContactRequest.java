package br.com.clientejacrm.whatsapp;

public class ContactRequest {

    private String number;
    private String name;

    public ContactRequest() {
    }

    public ContactRequest(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public static ContactRequest of(String number, String name) {
        return new ContactRequest(number, name);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package br.com.clientejacrm.service;

import br.com.clientejacrm.dto.ContactRequestDto;
import br.com.clientejacrm.dto.MessageRequestDto;

import br.com.clientejacrm.entity.orm.Lead;
import br.com.clientejacrm.entity.orm.Usuario;
import br.com.clientejacrm.repository.ClienteRepository;
import br.com.clientejacrm.repository.LeadRepository;
import br.com.clientejacrm.restClient.WhatsAppClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.LocalDateTime;


@ApplicationScoped
public class WhatsappService {

    @RestClient
    WhatsAppClient client;

   @Inject
    private LeadRepository leadRepository;

    @Inject
    private ClienteRepository clienteRepository;

    @Inject
    private UsuarioService usuarioService;


    @Transactional
    public void receiveContact(ContactRequestDto contactReques) {

        Usuario usuario = usuarioService.getUsuarioLogado();
        if(leadRepository.findByTelefone(contactReques.getNumber(), usuario) != null) {
            System.out.println("Telefone já cadastrado");
        }

        Lead lead = new Lead();
        lead.setDataCriacao(LocalDateTime.now());
        lead.setProximoFollowUp(LocalDateTime.now());
        lead.setUsuario(usuario);
        lead.setNome(contactReques.getName());
        lead.setTelefone(contactReques.getNumber());
        leadRepository.persist(lead);

    }

    public void sendText(String number, String text) {
        MessageRequestDto request = MessageRequestDto.text(number, text);
        client.sendMessage(request);
    }
}

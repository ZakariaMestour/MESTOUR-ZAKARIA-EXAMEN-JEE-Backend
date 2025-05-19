package poo.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poo.dtos.ClientDTO;
import poo.entities.Client;
import poo.exceptions.ResourceNotFoundException;
import poo.repositories.ClientRepository;
import poo.services.interfaces.ClientService;
import poo.entities.Credit;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;


    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with ID: " + id));
        return convertToDTO(client);
    }

    @Override
    public ClientDTO getClientByEmail(String email) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with email: " + email));
        return convertToDTO(client);
    }

    @Override
    public ClientDTO createClient(ClientDTO clientDTO) {
        Client client = convertToEntity(clientDTO);
        Client savedClient = clientRepository.save(client);
        return convertToDTO(savedClient);
    }

    @Override
    public ClientDTO updateClient(Long id, ClientDTO clientDTO) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with ID: " + id));

        existingClient.setName(clientDTO.getName());
        existingClient.setEmail(clientDTO.getEmail());

        Client updatedClient = clientRepository.save(existingClient);
        return convertToDTO(updatedClient);
    }

    @Override
    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with ID: " + id));
        clientRepository.delete(client);

    }
    // Helper methods for entity-DTO conversion
    private ClientDTO convertToDTO(Client client) {
        List<Long> creditIds = client.getCredits().stream()
                .map(Credit::getId)
                .collect(Collectors.toList());

        return new ClientDTO(
                client.getId(),
                client.getName(),
                client.getEmail(),
                creditIds
        );
    }

    private Client convertToEntity(ClientDTO clientDTO) {
        Client client = new Client();
        client.setId(clientDTO.getId());
        client.setName(clientDTO.getName());
        client.setEmail(clientDTO.getEmail());
        return client;
    }
}

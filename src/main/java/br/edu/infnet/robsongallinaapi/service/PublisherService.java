package br.edu.infnet.robsongallinaapi.service;

import br.edu.infnet.robsongallinaapi.model.Publisher;
import org.springframework.stereotype.Service;

import br.edu.infnet.robsongallinaapi.repository.PublisherRepository;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Publisher findOrCreateByNameAndCountry(String name, String country) {
        return publisherRepository.findByNameIgnoreCase(name)
                .orElseGet(() -> {
                    Publisher newPublisher = new Publisher();
                    newPublisher.setName(name);
                    newPublisher.setCountry(country);
                    return publisherRepository.save(newPublisher);
                });
    }
}
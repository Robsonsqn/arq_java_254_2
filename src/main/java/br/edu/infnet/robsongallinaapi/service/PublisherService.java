package br.edu.infnet.robsongallinaapi.service;

import br.edu.infnet.robsongallinaapi.model.Publisher;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PublisherService {
    private final Map<Long, Publisher> publishers = new ConcurrentHashMap<>();
    private final Map<String, Publisher> publishersByName = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public Publisher save(Publisher publisher) {
        if (publisher.getId() == null) {
            publisher.setId(idGenerator.incrementAndGet());
        }
        publishers.put(publisher.getId(), publisher);
        publishersByName.put(publisher.getName().toLowerCase(), publisher);
        return publisher;
    }

    public Optional<Publisher> findById(Long id) {
        return Optional.ofNullable(publishers.get(id));
    }

    public Publisher findOrCreateByNameAndCountry(String name, String country) {
        String lowerCaseName = name.toLowerCase();
        if (publishersByName.containsKey(lowerCaseName)) {
            return publishersByName.get(lowerCaseName);
        } else {
            Publisher newPublisher = new Publisher(null, name, country);
            return save(newPublisher);
        }
    }
}

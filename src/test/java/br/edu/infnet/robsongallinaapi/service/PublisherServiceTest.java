package br.edu.infnet.robsongallinaapi.service;

import br.edu.infnet.robsongallinaapi.model.Publisher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PublisherServiceTest {

    @Test
    void save_assignsIdAndCachesCaseInsensitive() {
        PublisherService service = new PublisherService();

        Publisher publisher = new Publisher(null, "Editora A", "BR");
        Publisher saved = service.save(publisher);

        assertNotNull(saved.getId(), "Id deve ser atribuído ao salvar");
        assertSame(saved, service.findById(saved.getId()).orElse(null), "Deve retornar a mesma instância ao buscar por id");

        Publisher foundByName = service.findOrCreateByNameAndCountry("editora a", "BR");
        assertSame(saved, foundByName, "Busca por nome (case-insensitive) deve retornar a instância existente");
    }

    @Test
    void findById_notFound_returnsEmpty() {
        PublisherService service = new PublisherService();

        assertFalse(service.findById(999L).isPresent(), "Busca por id inexistente deve retornar Optional vazio");
    }

    @Test
    void findOrCreate_createsNewWhenNotExists() {
        PublisherService service = new PublisherService();

        Publisher created = service.findOrCreateByNameAndCountry("Nova Editora", "BR");

        assertNotNull(created.getId(), "Novo publisher deve receber id");
        assertEquals("Nova Editora", created.getName());
        assertEquals("BR", created.getCountry());

        Publisher foundAgain = service.findOrCreateByNameAndCountry("nova editora", "BR");
        assertSame(created, foundAgain, "Chamadas subsequentes por nome devem retornar a mesma instância");
    }
}

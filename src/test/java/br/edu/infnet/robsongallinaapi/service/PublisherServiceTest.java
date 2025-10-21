package br.edu.infnet.robsongallinaapi.service;

import br.edu.infnet.robsongallinaapi.model.Publisher;
import br.edu.infnet.robsongallinaapi.repository.PublisherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublisherServiceTest {

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherService publisherService;

    @Test
    void findOrCreate_returnsExistingWhenFound() {
        Publisher existing = new Publisher(1L, "Editora A", "BR");
        when(publisherRepository.findByNameIgnoreCase("editora a")).thenReturn(Optional.of(existing));

        Publisher result = publisherService.findOrCreateByNameAndCountry("editora a", "BR");

        assertSame(existing, result);
        verify(publisherRepository).findByNameIgnoreCase("editora a");
        verifyNoMoreInteractions(publisherRepository);
    }

    @Test
    void findOrCreate_createsAndSavesWhenNotFound() {
        when(publisherRepository.findByNameIgnoreCase("Nova Editora")).thenReturn(Optional.empty());

        when(publisherRepository.save(any(Publisher.class))).thenAnswer(invocation -> {
            Publisher p = invocation.getArgument(0);
            p.setId(42L);
            return p;
        });

        Publisher created = publisherService.findOrCreateByNameAndCountry("Nova Editora", "BR");

        assertNotNull(created.getId());
        assertEquals(42L, created.getId());
        assertEquals("Nova Editora", created.getName());
        assertEquals("BR", created.getCountry());

        ArgumentCaptor<Publisher> captor = ArgumentCaptor.forClass(Publisher.class);
        verify(publisherRepository).findByNameIgnoreCase("Nova Editora");
        verify(publisherRepository).save(captor.capture());

        Publisher savedArg = captor.getValue();
        assertEquals("Nova Editora", savedArg.getName());
        assertEquals("BR", savedArg.getCountry());
    }
}

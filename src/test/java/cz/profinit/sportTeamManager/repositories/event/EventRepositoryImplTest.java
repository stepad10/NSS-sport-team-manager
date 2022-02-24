package cz.profinit.sportTeamManager.repositories.event;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import cz.profinit.sportTeamManager.exceptions.EntityAlreadyExistsException;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.mapperMyBatis.event.EventMapperMyBatis;
import cz.profinit.sportTeamManager.model.event.Event;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventRepositoryImplTest {

    @InjectMocks
    private EventRepositoryImpl eventRepository;

    @Mock
    private EventMapperMyBatis eventMapperMyBatis;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void insertEvent() throws EntityAlreadyExistsException {
        Event event = new Event();
        when(eventMapperMyBatis.findEventById(event.getEntityId())).thenReturn(null);
        eventRepository.insertEvent(event);
        verify(eventMapperMyBatis, times(1)).findEventById(event.getEntityId());
        verify(eventMapperMyBatis, times(1)).insertEvent(event);
    }

    @Test
    public void insertEventThatAlreadyExists() {
        Event event = new Event();
        when(eventMapperMyBatis.findEventById(event.getEntityId())).thenReturn(event);
        Exception ex = Assert.assertThrows(EntityAlreadyExistsException.class, () -> eventRepository.insertEvent(event));
        Assert.assertEquals(new EntityAlreadyExistsException("Event").getMessage(), ex.getMessage());
        verify(eventMapperMyBatis, times(1)).findEventById(event.getEntityId());
        verify(eventMapperMyBatis, times(0)).insertEvent(event);
    }

    @Test
    public void findEventById() throws EntityNotFoundException {
        Event event = new Event();
        when(eventMapperMyBatis.findEventById(event.getEntityId())).thenReturn(event);
        eventRepository.findEventById(event.getEntityId());
        verify(eventMapperMyBatis, times(1)).findEventById(event.getEntityId());
    }

    @Test
    public void findEventThatDoesNotExistById() {
        Event event = new Event();
        when(eventMapperMyBatis.findEventById(event.getEntityId())).thenReturn(null);
        Exception ex = Assert.assertThrows(EntityNotFoundException.class, () -> eventRepository.findEventById(event.getEntityId()));
        Assert.assertEquals(new EntityNotFoundException("Event").getMessage(), ex.getMessage());
        verify(eventMapperMyBatis, times(1)).findEventById(event.getEntityId());
    }

    @Test
    public void updateEvent() throws EntityNotFoundException {
        Event event = new Event();
        when(eventMapperMyBatis.findEventById(event.getEntityId())).thenReturn(event);
        eventRepository.updateEvent(event);
        verify(eventMapperMyBatis, times(1)).findEventById(event.getEntityId());
        verify(eventMapperMyBatis, times(1)).updateEvent(event);
    }

    @Test
    public void updateEventThatDoesNotExist() {
        Event event = new Event();
        when(eventMapperMyBatis.findEventById(event.getEntityId())).thenReturn(null);
        Exception ex = Assert.assertThrows(EntityNotFoundException.class, () -> eventRepository.updateEvent(event));
        Assert.assertEquals(new EntityNotFoundException("Event").getMessage(), ex.getMessage());
        verify(eventMapperMyBatis, times(1)).findEventById(event.getEntityId());
        verify(eventMapperMyBatis, times(0)).updateEvent(event);
    }
}

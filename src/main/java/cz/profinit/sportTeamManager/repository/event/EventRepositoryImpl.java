package cz.profinit.sportTeamManager.repository.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import cz.profinit.sportTeamManager.exception.EntityAlreadyExistsException;
import cz.profinit.sportTeamManager.exception.EntityNotFoundException;
import cz.profinit.sportTeamManager.mapperMyBatis.event.EventMapperMyBatis;
import cz.profinit.sportTeamManager.model.event.Event;

@Repository
@Profile("Main")
public class EventRepositoryImpl implements EventRepository {

    @Autowired
    private EventMapperMyBatis eventMapperMyBatis;

    private static final String EX_MSG = "Event";

    @Override
    public void insertEvent(Event event) throws EntityAlreadyExistsException {
        if (eventMapperMyBatis.findEventById(event.getEntityId()) != null) {
            throw new EntityAlreadyExistsException(EX_MSG);
        }
        eventMapperMyBatis.insertEvent(event);
    }

    @Override
    public Event findEventById(Long id) throws EntityNotFoundException {
        Event foundEvent = eventMapperMyBatis.findEventById(id);
        if (foundEvent == null) {
            throw new EntityNotFoundException(EX_MSG);
        }
        return foundEvent;
    }

    @Override
    public void updateEvent(Event event) throws EntityNotFoundException {
        if (eventMapperMyBatis.findEventById(event.getEntityId()) == null) {
            throw new EntityNotFoundException(EX_MSG);
        }
        eventMapperMyBatis.updateEvent(event);
    }
}
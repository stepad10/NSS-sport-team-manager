package cz.profinit.sportTeamManager.repositories;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.event.Event;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("Main")
public class EventRepositoryImpl implements EventRepository {
    @Override
    public Event createNewEvent(Event event) {
        return null;
    }

    @Override
    public Event findEventById(Long id) throws EntityNotFoundException {
        return null;
    }

    @Override
    public Event updateEvent(Event event) {
        return null;
    }
}

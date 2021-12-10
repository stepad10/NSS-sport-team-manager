/*
 * StubEventRepository
 *
 * 0.1
 *
 * Author: M. Halamka
 */


package cz.profinit.sportTeamManager.stubRepositories;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.event.Event;
import cz.profinit.sportTeamManager.model.event.Place;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.model.user.User;
import cz.profinit.sportTeamManager.repositories.EventRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StubEventRepository implements EventRepository {

    List <Event> events;
    Event event;
    User loggedUser;



    public StubEventRepository() {
        events = new ArrayList<>();
        Place place = new Place("Profinit","Tychonova 2");
        event = new Event(LocalDateTime.now(),place,6,false,loggedUser,new ArrayList<>(),new ArrayList<>());
        event.setEntityId(0L);
        loggedUser = new RegisteredUser("Ivan", "Stastny", "pass", "is@gmail.com",RoleEnum.USER);
    }

    @Override
    public Event createNewEvent(Event event) {
        events.add(event);
        return event;
    }

    @Override
    public Event findEventById(Long id) throws EntityNotFoundException {
        if (id == event.getEntityId()){
            return event;
        } else {
            throw new EntityNotFoundException("Event entity not found!");
        }
    }

    @Override
    public Event updateEvent(Event event) {
        Integer i = (int) (long) event.getEntityId();
        events.add(i,event);
        return events.get(i);
    }
}

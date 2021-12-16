/*
 * StubEventRepository
 *
 * 0.1
 *
 * Author: M. Halamka
 */


package cz.profinit.sportTeamManager.stubs.stubRepositories;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.event.Event;
import cz.profinit.sportTeamManager.model.event.Message;
import cz.profinit.sportTeamManager.model.event.Place;
import cz.profinit.sportTeamManager.model.invitation.Invitation;
import cz.profinit.sportTeamManager.model.invitation.StatusEnum;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import cz.profinit.sportTeamManager.model.user.User;
import cz.profinit.sportTeamManager.repositories.EventRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Repository
public class StubEventRepository implements EventRepository {

    Event event;
    User loggedUser;

    public StubEventRepository() {
        Place place = new Place("Profinit","Tychonova 2");
        event = new Event(LocalDateTime.now(),place,6,false,loggedUser,new ArrayList<>(),new ArrayList<>());
        event.setEntityId(0L);
        loggedUser = new RegisteredUser("Ivan", "Stastny", "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2", "is@gmail.com",RoleEnum.USER);
        event.getListOfMessages().add(new Message(loggedUser,"Testuji",LocalDateTime.now()));
        event.getListOfInvitation().add(new Invitation(LocalDateTime.now(),LocalDateTime.now(), StatusEnum.PENDING,loggedUser));
    }

    @Override
    public Event createNewEvent(Event event) {
        this.event = event;
        return event;
    }

    @Override
    public Event findEventById(Long id) throws EntityNotFoundException {
        if (id == event.getEntityId()){
            return event;
        } else {
            throw new EntityNotFoundException("Event");
        }
    }

    @Override
    public Event updateEvent(Event event) {
        this.event = event;
        return event;
    }
}

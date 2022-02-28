/*
 * StubEventRepository
 *
 * 0.1
 *
 * Author: M. Halamka
 */


package cz.profinit.stm.repository.event;

import cz.profinit.stm.exception.EntityNotFoundException;
import cz.profinit.stm.model.event.Event;
import cz.profinit.stm.model.event.Message;
import cz.profinit.stm.model.event.Place;
import cz.profinit.stm.model.invitation.Invitation;
import cz.profinit.stm.model.invitation.StatusEnum;
import cz.profinit.stm.model.user.RegisteredUser;
import cz.profinit.stm.model.user.RoleEnum;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Repository
@Profile("stub_repository")
public class EventRepositoryStub implements EventRepository {

    Event event;
    RegisteredUser loggedUser;

    public EventRepositoryStub() {
        Place place = new Place("Profinit","Tychonova 2", 1L);
        loggedUser = new RegisteredUser("Ivan", "Stastny", "$2a$10$ruiQYEnc3bXdhWuCC/q.E.D.1MFk2thcPO/fVrAuFDuugjm3XuLZ2", "is@gmail.com", RoleEnum.USER);
        event = new Event(LocalDateTime.now(), 6, false, place, loggedUser, new ArrayList<>(), new ArrayList<>());
        event.setEntityId(0L);
        event.getMessageList().add(new Message(loggedUser,"Testuji",LocalDateTime.now(), event.getEntityId()));
        event.getInvitationList().add(new Invitation(LocalDateTime.now(), LocalDateTime.now(), StatusEnum.PENDING, loggedUser, event.getEntityId()));
    }

    @Override
    public void insertEvent(Event event) {
        this.event = event;
    }

    @Override
    public Event findEventById(Long id) throws EntityNotFoundException {
        if (id.equals(event.getEntityId())){
            return event;
        } else {
            throw new EntityNotFoundException("Event");
        }
    }

    @Override
    public void updateEvent(Event event) {
        this.event = event;
    }
}
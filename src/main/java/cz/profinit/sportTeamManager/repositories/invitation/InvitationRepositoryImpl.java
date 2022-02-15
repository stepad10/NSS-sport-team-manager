package cz.profinit.sportTeamManager.repositories.invitation;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import cz.profinit.sportTeamManager.exceptions.EntityAlreadyExistsException;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.mapperMyBatis.invitation.InvitationMapperMyBatis;
import cz.profinit.sportTeamManager.model.invitation.Invitation;
import cz.profinit.sportTeamManager.repositories.event.EventRepository;
import cz.profinit.sportTeamManager.repositories.user.UserRepository;
import lombok.NonNull;

@Repository
@Profile("Main")
public class InvitationRepositoryImpl implements InvitationRepository {

    @Autowired
    private InvitationMapperMyBatis invitationMapperMyBatis;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Override
    public void insertInvitation(@NonNull Invitation invitation) throws EntityNotFoundException, EntityAlreadyExistsException {
        if (invitationMapperMyBatis.findInvitationById(invitation.getEntityId()) != null) {
            throw new EntityAlreadyExistsException("Invitation");
        }
        userRepository.findUserById(invitation.getRecipient().getEntityId());
        eventRepository.findEventById(invitation.getEventId());
        invitationMapperMyBatis.insertInvitation(invitation);
    }

    @Override
    public void updateInvitation(@NonNull Invitation invitation) throws EntityNotFoundException {
        if (invitationMapperMyBatis.findInvitationById(invitation.getEntityId()) == null) {
            throw new EntityNotFoundException("Invitation");
        }
        invitation.setChanged(LocalDateTime.now());
        invitationMapperMyBatis.updateInvitation(invitation);
    }

    @Override
    public Invitation findInvitationById(Long id) throws EntityNotFoundException {
        Invitation foundInvitation = invitationMapperMyBatis.findInvitationById(id);
        if (foundInvitation == null) {
            throw new EntityNotFoundException("Invitation");
        }
        return foundInvitation;
    }

    @Override
    public Invitation findInvitationByEventIdAndUserEmail(Long eventId, String userEmail) throws EntityNotFoundException {
        Invitation foundInvitation = invitationMapperMyBatis.findInvitationByEventIdAndUserId(eventId, userRepository.findUserByEmail(userEmail).getEntityId());
        if (foundInvitation == null) {
            throw new EntityNotFoundException("Invitation");
        }
        return foundInvitation;
    }

    @Override
    public void deleteInvitation(String userEmail, Long eventId) throws EntityNotFoundException {
        invitationMapperMyBatis.deleteInvitationById(findInvitationByEventIdAndUserEmail(eventId, userEmail).getEntityId());
    }

    @Override
    public boolean isUserInvitedToEvent(String userEmail, Long eventId) throws EntityNotFoundException {
        return invitationMapperMyBatis.findInvitationByEventIdAndUserId(eventId, userRepository.findUserByEmail(userEmail).getEntityId()) != null;
    }
}

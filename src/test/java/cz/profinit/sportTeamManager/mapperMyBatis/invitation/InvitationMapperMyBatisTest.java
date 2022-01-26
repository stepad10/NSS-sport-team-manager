package cz.profinit.sportTeamManager.mapperMyBatis.invitation;

import cz.profinit.sportTeamManager.configuration.MyBatisConfigurationTest;
import cz.profinit.sportTeamManager.mapperMyBatis.user.UserMapperMyBatis;
import cz.profinit.sportTeamManager.model.event.Message;
import cz.profinit.sportTeamManager.model.invitation.Invitation;
import cz.profinit.sportTeamManager.model.invitation.StatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ContextConfiguration(classes = MyBatisConfigurationTest.class)
@TestPropertySource("/test.properties")
public class InvitationMapperMyBatisTest {

    @Autowired
    private InvitationMapperMyBatis invitationMapperMyBatis;

    @Autowired
    private UserMapperMyBatis userMapperMyBatis;

    @Test
    public void insertInvitation() {
        Long userId = 13L;
        Long eventId = 4L;
        Invitation invitation = new Invitation(LocalDateTime.now(), null, StatusEnum.ACCEPTED, userMapperMyBatis.findUserById(userId), eventId);
        invitationMapperMyBatis.insertInvitation(invitation);
        Assert.assertNotNull(invitation.getEntityId());
        Assert.assertNotNull(invitationMapperMyBatis.findInvitationById(invitation.getEntityId()));
    }

    @Test
    public void deletePlaceById() {
        Long invitationId = 1L;
        invitationMapperMyBatis.deleteInvitationById(invitationId);
        Assert.assertNull(invitationMapperMyBatis.findInvitationById(invitationId));
    }

    @Test
    public void findPlaceById() {
        Long invitationId = 2L;
        Invitation invitation = invitationMapperMyBatis.findInvitationById(invitationId);
        Assert.assertNotNull(invitation);
    }

     @Test
     public void updateMessage() {
     Long invitationId = 3L;
     Invitation invitation = invitationMapperMyBatis.findInvitationById(invitationId);
     StatusEnum prevStatus = invitation.getStatus();
     invitation.setStatus(StatusEnum.PENDING);
     invitationMapperMyBatis.updateInvitation(invitation);
     Assert.assertNotEquals(prevStatus, invitation.getStatus());
     }
}

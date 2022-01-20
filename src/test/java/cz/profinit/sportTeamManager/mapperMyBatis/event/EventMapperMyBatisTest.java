package cz.profinit.sportTeamManager.mapperMyBatis.event;

import cz.profinit.sportTeamManager.configuration.MyBatisConfigurationTest;
import cz.profinit.sportTeamManager.mapperMyBatis.place.PlaceMapperMyBatis;
import cz.profinit.sportTeamManager.mapperMyBatis.user.UserMapperMyBatis;
import cz.profinit.sportTeamManager.model.event.Event;
import cz.profinit.sportTeamManager.model.event.Place;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MyBatisConfigurationTest.class)
@ActiveProfiles("database")
public class EventMapperMyBatisTest {

    @Autowired
    private EventMapperMyBatis eventMapperMyBatis;

    @Autowired
    private PlaceMapperMyBatis placeMapperMyBatis;

    @Autowired
    private UserMapperMyBatis userMapperMyBatis;

    private static int counter = 0;

    private Event insertEventHelper() {
        counter++;
        RegisteredUser owner = new RegisteredUser("Tom" + counter, "Sad" + counter, "pass" + counter, "a@b.c" + counter, RoleEnum.USER);
        userMapperMyBatis.insertUser(owner);
        Place place = new Place("Main hala", "Juliska 6");

        return null;
    }
}

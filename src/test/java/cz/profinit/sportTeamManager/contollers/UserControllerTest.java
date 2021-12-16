/*
 * UserControllerTest
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.contollers;

import cz.profinit.sportTeamManager.SportTeamManagerApplication;
import cz.profinit.sportTeamManager.configuration.WebApplicationConfigurationUnitTests;
import cz.profinit.sportTeamManager.dto.RegisteredUserDTO;
import cz.profinit.sportTeamManager.dto.UserDetailsDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

/**
 * Test user controller.
 * TODO dodelat
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SportTeamManagerApplication.class)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@ContextConfiguration(classes = WebApplicationConfigurationUnitTests.class)
@WebAppConfiguration
@AutoConfigureMockMvc
@ActiveProfiles({"stub_repository","stub_services","webTest","test"})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private RegisteredUserDTO newUser;
    private String newUserString;
    private UserDetailsDTO newUserDetails;

    //    @Autowired
//    private WebApplicationContext applicationContext;
//

    /**
     * Before tests create DTO.
     */
    @Before
    public void setup() throws JAXBException {
        newUser = new RegisteredUserDTO("Ivan", "Stastny", "mail@mail.com");
        newUserDetails = new UserDetailsDTO(newUser.getEmail(), "pass");
    }

    /**
     * Tests registering a new user.
     */
    @Test
    public void registrationOfNewUserSuccessful() throws Exception {
        newUserDetails.setUserName(newUser.getName());
        newUserDetails.setSurname(newUser.getSurname());
        JAXBContext jaxbContext = JAXBContext.newInstance(UserDetailsDTO.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(newUserDetails, sw);
        newUserString = sw.toString();


        String uri = "/user/registration";
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .content(newUserString)
                        .header("Content-Type", "application/xml")).
                andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Tests registering a new user whose email is already in database.
     */
    @Test
    public void registrationOfAlreadyExistingUser() throws Exception {
        newUserDetails.setName("email@gmail.com");
        newUserDetails.setUserName(newUser.getName());
        newUserDetails.setSurname(newUser.getSurname());
        JAXBContext jaxbContext = JAXBContext.newInstance(UserDetailsDTO.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(newUserDetails, sw);
        newUserString = sw.toString();
        String uri = "/user/registration";
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .content(newUserString)
                        .header("Content-Type", "application/xml")).
                andExpect(MockMvcResultMatchers.status().isBadRequest()).
                andExpect(MockMvcResultMatchers.status().reason("Account with e-mail address email@gmail.comalready exists."));
    }

    /**
     * Tests authentication.
     */
    @Test
    public void authenticate() throws Exception {
        UserDetailsDTO user = new UserDetailsDTO("is@gmail.com", "pass");
        JAXBContext jaxbContext = JAXBContext.newInstance(UserDetailsDTO.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(user, sw);
        String loginCreditors = sw.toString();
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/login")
                                .content(loginCreditors).header("Content-Type", "application/xml"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}
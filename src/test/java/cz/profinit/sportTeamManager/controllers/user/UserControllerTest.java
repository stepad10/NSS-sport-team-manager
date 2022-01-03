/*
 * UserControllerTest
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.controllers.user;

import cz.profinit.sportTeamManager.SportTeamManagerApplicationTests;
import cz.profinit.sportTeamManager.configuration.WebApplicationConfigurationUnitTests;
import cz.profinit.sportTeamManager.dto.user.RegisteredUserDTO;
import cz.profinit.sportTeamManager.dto.user.UserDetailsDTO;
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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test user controller.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SportTeamManagerApplicationTests.class)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@ContextConfiguration(classes = WebApplicationConfigurationUnitTests.class)
@WebAppConfiguration
@AutoConfigureMockMvc
@ActiveProfiles({"stub_repository","stub_services","webTest","test","authentication"})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private RegisteredUserDTO newUser;
    private String newUserString;
    private UserDetailsDTO newUserDetails;
    private RegisteredUserDTO loggedUser;

    /**
     * Before tests create DTO.
     */
    @Before
    public void setup() throws JAXBException {
        newUser = new RegisteredUserDTO("Ivan", "Stastny", "mail@mail.com");
        loggedUser = new RegisteredUserDTO("Adam", "Stastny", "email@gmail.com");
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
        mockMvc.perform(post(uri)
                        .content(newUserString)
                        .header("Content-Type", "application/xml")).
                andExpect(status().isOk());
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
        mockMvc.perform(post(uri)
                        .content(newUserString)
                        .header("Content-Type", "application/xml")).
                andExpect(status().isBadRequest()).
                andExpect(status().reason("Account with e-mail address email@gmail.comalready exists."));
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
                        post("/login")
                                .content(loginCreditors).header("Content-Type", "application/xml"))
                .andExpect(status().isOk());

    }

    /**
     * Tests change name of user.
     */
    @Test
    public void changeUserName() throws Exception {

        loggedUser.setName("Emil");
        JAXBContext jaxbContext = JAXBContext.newInstance(RegisteredUserDTO.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(loggedUser, sw);
        newUserString = sw.toString();
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/user/email@gmail.com/name/Emil")
                                .header("Content-Type", "application/xml"))
                .andExpect(status().isOk())
                .andExpect(content().string(newUserString));

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/user/emai@gmail.com/name/Emil")
                                .header("Content-Type", "application/xml"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("User entity not found!"));
    }

    /**
     * Tests change surname of user.
     */
    @Test
    public void changeUserSurname() throws Exception {
        loggedUser.setSurname("Kypry");
        JAXBContext jaxbContext = JAXBContext.newInstance(RegisteredUserDTO.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(loggedUser, sw);
        newUserString = sw.toString();
        loggedUser.setSurname("Stastny");
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/user/email@gmail.com/surname/Kypry")
                                .header("Content-Type", "application/xml"))
                .andExpect(status().isOk())
                .andExpect(content().string(newUserString));

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/user/emai@gmail.com/surname/Kypry")
                                .header("Content-Type", "application/xml"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("User entity not found!"));
    }

    /**
     * Tests change email of user.
     */
    @Test
    public void changeUserEmail() throws Exception {
        loggedUser.setEmail("mymail@gmail.com");
        JAXBContext jaxbContext = JAXBContext.newInstance(RegisteredUserDTO.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(loggedUser, sw);
        newUserString = sw.toString();
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/user/email@gmail.com/email/mymail@gmail.com")
                                .header("Content-Type", "application/xml"))
                .andExpect(status().isOk())
                .andExpect(content().string(newUserString));

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/user/emai@gmail.com/email/mymail@gmail.com")
                                .header("Content-Type", "application/xml"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("User entity not found!"));

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/user/email@gmail.com/email/is@gmail.com")
                                .header("Content-Type", "application/xml"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Account with e-mail address is@gmail.com already exists."));


    }




}

/*
 * TeamControllerTest
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.contollers;

import cz.profinit.sportTeamManager.SportTeamManagerApplication;
import cz.profinit.sportTeamManager.dto.RegisteredUserDTO;
import cz.profinit.sportTeamManager.dto.SubgroupDTO;
import cz.profinit.sportTeamManager.dto.TeamDTO;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;

/**
 * Test team controller.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SportTeamManagerApplication.class)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@WebAppConfiguration
@AutoConfigureMockMvc
@ActiveProfiles({"stub_repository","stub_services","webTest","authentication"})
public class TeamControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private TeamDTO team;
    private RegisteredUserDTO user;
    private SubgroupDTO subgroupA;
    private SubgroupDTO subgroupC;
    private String teamXml;

    /**
     * Creates Team data transfer object for comparing with request results.
     */
    @Before
    public void setUp() throws JAXBException {
        user = new RegisteredUserDTO("Adam", "Stastny", "email@gmail.com");
        List<RegisteredUserDTO> userList1 = new ArrayList<>();
        userList1.add(user);
        List<RegisteredUserDTO> userList2 = new ArrayList<>();
        userList2.add(user);
        subgroupA = new SubgroupDTO("All Users", userList1);
        subgroupC = new SubgroupDTO("Coaches", userList2);
        List<SubgroupDTO> subgroupList = new ArrayList<>();
        subgroupList.add(subgroupA);
        subgroupList.add(subgroupC);
        team = new TeamDTO(10L, "Ateam", "golf", subgroupList, user);
    }

    /**
     * Tests creating a new team with selected name and sport.
     */
    @Test
    public void createNewTeam() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(TeamDTO.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(team, sw);
        String teamXml = sw.toString();

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/team/createTeam/Ateam/golf")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string(teamXml));
    }

    /**
     * Tests refreshing of team
     */
    @Test
    public void refreshTeam() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(TeamDTO.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(team, sw);
        String teamXml = sw.toString();

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/team/10")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string(teamXml));
    }


    /**
     * Tests refreshing of non-existent team
     */
    @Test
    public void refreshNotExistentTeam() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(TeamDTO.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(team, sw);
        String teamXml = sw.toString();

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/team/30")).
                andExpect(MockMvcResultMatchers.status().isBadRequest()).
                andExpect(MockMvcResultMatchers.status().reason("Team entity not found!"));
    }


    /**
     * Tests changing name of a team.
     */
    @Test
    public void changeTeamName() throws Exception {
        team.setName("Novy tym");
        JAXBContext jaxbContext = JAXBContext.newInstance(TeamDTO.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(team, sw);
        String newTeamXml = sw.toString();

        mockMvc.perform(
                        MockMvcRequestBuilders.
                                put("/team/10/teamName/Novy tym").
                                header("Content-Type", "application/xml")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string(newTeamXml));

        mockMvc.perform(
                        MockMvcRequestBuilders.
                                put("/team/30/teamName/Novy tym").
                                header("Content-Type", "application/xml")).
                andExpect(MockMvcResultMatchers.status().isBadRequest()).
                andExpect(MockMvcResultMatchers.status().reason("Team entity not found!"));

    }

    /**
     * Tests a changing a sport of a team.
     */
    @Test
    public void changeTeamSport() throws Exception {
        team.setSport("Rugby");
        JAXBContext jaxbContext = JAXBContext.newInstance(TeamDTO.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(team, sw);
        String newTeamXml = sw.toString();

        mockMvc.perform(
                        MockMvcRequestBuilders.
                                put("/team/10/teamSport/Rugby").
                                header("Content-Type", "application/xml")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string(newTeamXml));

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/team/30/teamSport/Rugby")).
                andExpect(MockMvcResultMatchers.status().isBadRequest()).
                andExpect(MockMvcResultMatchers.status().reason("Team entity not found!"));

    }

    /**
     * Changes owner of a team
     */
    @Test
    public void changeTeamOwner() throws Exception {
        RegisteredUserDTO newOwner = new RegisteredUserDTO("Tomas", "Smutny", "ts@gmail.com");
        team.getTeamSubgroup("All Users").addUser(newOwner);
        team.getTeamSubgroup("Coaches").addUser(newOwner);
        team.setOwner(newOwner);
        team.setId(20L);
        JAXBContext jaxbContext = JAXBContext.newInstance(TeamDTO.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(team, sw);
        String newTeamXml = sw.toString();

        mockMvc.perform(
                        MockMvcRequestBuilders.
                                put("/team/20/teamOwner/ts@gmail.com").
                                header("Content-Type", "application/xml")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string(newTeamXml));

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/team/30/teamOwner/ts@gmail.com")).
                andExpect(MockMvcResultMatchers.status().isBadRequest()).
                andExpect(MockMvcResultMatchers.status().reason("Team entity not found!"));

    }

    /**
     * Changes owner of a team who is not in team
     */
    @Test
    public void changeTeamOwnerWhoIsNotInTeam() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(TeamDTO.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(team, sw);
        String teamXmlWithAddedMember = sw.toString();

        mockMvc.perform(
                        MockMvcRequestBuilders.
                                put("/team/10/teamOwner/ts@gmail.com").
                                header("Content-Type", "application/xml")).
                andExpect(MockMvcResultMatchers.status().isBadRequest()).
                andExpect(MockMvcResultMatchers.status().reason("User is not in team"));
    }

    /**
     * Adds a new subgroup defined by name to team
     */
    @Test
    public void addNewSubgroup() throws Exception {
        team.getListOfSubgroups().add(new SubgroupDTO("Empty"));
        JAXBContext jaxbContext = JAXBContext.newInstance(TeamDTO.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(team, sw);
        String addedSubgroupXml = sw.toString();

        mockMvc.perform(
                        MockMvcRequestBuilders.
                                post("/team/10/subgroup/Empty").
                                header("Content-Type", "application/xml")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string(addedSubgroupXml));

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/team/30/subgroup/Empty")).
                andExpect(MockMvcResultMatchers.status().isBadRequest()).
                andExpect(MockMvcResultMatchers.status().reason("Team entity not found!"));
    }

    /**
     * Tries to add a new subgroup defined by name to team
     */
    @Test
    public void addNewSubgroupWhatAlreadyExists() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.
                                post("/team/10/subgroup/Coaches").
                                header("Content-Type", "application/xml")).
                andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(
                        MockMvcResultMatchers
                                .status().
                                reason((containsString("Subgroup already exists"))));
    }


    /**
     * Tests a deletion of a subgroup defined by name from team
     */
    @Test
    public void deleteSubgroup() throws Exception {
        team.getListOfSubgroups().remove(1);
        JAXBContext jaxbContext = JAXBContext.newInstance(TeamDTO.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(team, sw);
        String addedSubgroupXml = sw.toString();

        mockMvc.perform(
                        MockMvcRequestBuilders.
                                delete("/team/10/subgroup/Coaches").
                                header("Content-Type", "application/xml")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string(addedSubgroupXml));

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/team/30/subgroup/Coaches")).
                                andExpect(MockMvcResultMatchers.status().isBadRequest()).
                andExpect(MockMvcResultMatchers.status().reason("Team entity not found!"));
    }

    /**
     * Tries to delete of a subgroup defined by name from team where this subgroup is not present
     */
    @Test
    public void deleteNotExistingSubgroup() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.
                                delete("/team/10/subgroup/Empty").
                                header("Content-Type", "application/xml")).
                andExpect(MockMvcResultMatchers.status().isBadRequest()).
                andExpect(MockMvcResultMatchers.status().reason("Subgroup entity not found!"));
    }


    /**
     * Tests a subgroup name change.
     */
    @Test
    public void changeSubgroupName() throws Exception {
        team.getListOfSubgroups().get(1).setName("Empty");
        JAXBContext jaxbContext = JAXBContext.newInstance(TeamDTO.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(team, sw);
        String addedSubgroupXml = sw.toString();

        mockMvc.perform(
                        MockMvcRequestBuilders.
                                put("/team/10/subgroup/Coaches/Empty").
                                header("Content-Type", "application/xml")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string(addedSubgroupXml));

        mockMvc.perform(
                        MockMvcRequestBuilders.
                                put("/team/30/subgroup/Coaches/Empty").
                                header("Content-Type", "application/xml")).
                andExpect(MockMvcResultMatchers.status().isBadRequest()).
                andExpect(MockMvcResultMatchers.status().reason("Team entity not found!"));
    }

    /**
     * Tests a subgroup name change to the name of already existing subgroup
     */
    @Test
    public void changeSubgroupNameToAlreadyExisting() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.
                                put("/team/10/subgroup/Coaches/All Users").
                                header("Content-Type", "application/xml")).
                andExpect(MockMvcResultMatchers.status().isBadRequest()).
                andExpect(MockMvcResultMatchers.status().reason("Subgroup of new name already exists"));
    }

    /**
     * Tests adding user ot the team
     */
    @Test
    public void addUserToTeam() throws Exception {
        RegisteredUserDTO user2 = new RegisteredUserDTO("Tomas", "Smutny", "ts@gmail.com");
        team.getTeamSubgroup("All Users").addUser(user2);
        JAXBContext jaxbContext = JAXBContext.newInstance(TeamDTO.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(team, sw);
        String addedSubgroupXml = sw.toString();

        mockMvc.perform(
                        MockMvcRequestBuilders.
                                put("/team/10/user/ts@gmail.com").
                                header("Content-Type", "application/xml")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string(addedSubgroupXml));

        mockMvc.perform(
                        MockMvcRequestBuilders.
                                put("/team/30/user/ts@gmail.com").
                                header("Content-Type", "application/xml")).
                andExpect(MockMvcResultMatchers.status().isBadRequest()).
                andExpect(MockMvcResultMatchers.status().reason("Team entity not found!"));
    }

    /**
     * Tests deleting user ot the team
     */
    @Test
    public void deleteUserFromTeam() throws Exception {

        team.getTeamSubgroup("All Users").getUserList().remove(user);
        team.getTeamSubgroup("Coaches").getUserList().remove(user);
        JAXBContext jaxbContext = JAXBContext.newInstance(TeamDTO.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(team, sw);
        String addedSubgroupXml = sw.toString();

        mockMvc.perform(
                        MockMvcRequestBuilders.
                                delete("/team/10/user/email@gmail.com").
                                header("Content-Type", "application/xml")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string(addedSubgroupXml));

        mockMvc.perform(
                        MockMvcRequestBuilders.
                                delete("/team/30/user/email@gmail.com").
                                header("Content-Type", "application/xml")).
                andExpect(MockMvcResultMatchers.status().isBadRequest()).
                andExpect(MockMvcResultMatchers.status().reason("Team entity not found!"));
    }

    /**
     * Tests to remove a user who is not in team.
     */
    @Test
    public void deleteUserFromTeamWhoisNotInTeam() throws Exception {

        team.getTeamSubgroup("All Users").getUserList().remove(user);
        team.getTeamSubgroup("Coaches").getUserList().remove(user);
        JAXBContext jaxbContext = JAXBContext.newInstance(TeamDTO.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(team, sw);
        String addedSubgroupXml = sw.toString();

        mockMvc.perform(
                        MockMvcRequestBuilders.
                                delete("/team/10/user/ts@gmail.com").
                                header("Content-Type", "application/xml")).
                andExpect(MockMvcResultMatchers.status().isBadRequest()).
                andExpect(MockMvcResultMatchers.status().reason("User is not in team"));
    }

    /**
     * Tests deleting user ot the subgroup who is not present in subgroup
     */
    @Test
    public void deleteNoExistentUserFromSubgroup() throws Exception {

        team.getTeamSubgroup("Coaches").getUserList().remove(user);
        JAXBContext jaxbContext = JAXBContext.newInstance(TeamDTO.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(team, sw);
        String addedSubgroupXml = sw.toString();

        mockMvc.perform(
                        MockMvcRequestBuilders.
                                delete("/team/10/Coaches/user//email@gmail.com").
                                header("Content-Type", "application/xml")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string(addedSubgroupXml));
    }

    /**
     * Tests delete user form a subgroup
     */
    @Test
    public void deleteUserFromSubgroup() throws Exception {
        team.getTeamSubgroup("Coaches").getUserList().remove(user);
        JAXBContext jaxbContext = JAXBContext.newInstance(TeamDTO.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(team, sw);
        String addedSubgroupXml = sw.toString();

        mockMvc.perform(
                        MockMvcRequestBuilders.
                                delete("/team/10/Coaches/user//email@gmail.com").
                                header("Content-Type", "application/xml")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string(addedSubgroupXml));
    }

    /**
     * Tests adding user ot the team who is already in team
     */
    @Test
    public void addUserToTeamWhoIsInTeam() throws Exception {
        String uri = "/team/10/user/email@gmail.com";
        mockMvc.perform(
                        MockMvcRequestBuilders.
                                put(uri).
                                header("Content-Type", "application/xml")).
                andExpect(
                        MockMvcResultMatchers.
                                status().
                                isBadRequest()).
                andExpect(
                        MockMvcResultMatchers
                                .status().
                                reason((containsString("User is already in team"))));
    }

    /**
     * Tests adding user ot the team who is non-existent
     */
    @Test
    public void addUserToTeamWhoIsNotExists() throws Exception {
        String uri = "/team/10/user/em@gmail.com";
        mockMvc.perform(
                        MockMvcRequestBuilders.
                                put(uri).
                                header("Content-Type", "application/xml")).
                andExpect(
                        MockMvcResultMatchers.
                                status().
                                isNotFound()).
                andExpect(
                        MockMvcResultMatchers
                                .status().
                                reason((containsString("User entity not found!"))));
    }


    /**
     * Tests adding a user to the subgroup.
     */
    @Test
    public void addUserToSubgroup() throws Exception {
        RegisteredUserDTO user2 = new RegisteredUserDTO("Tomas", "Smutny", "ts@gmail.com");
        team.getTeamSubgroup("All Users").addUser(user2);
        team.getTeamSubgroup("Coaches").addUser(user2);
        JAXBContext jaxbContext = JAXBContext.newInstance(TeamDTO.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(team, sw);
        String addedSubgroupXml = sw.toString();

        mockMvc.perform(
                        MockMvcRequestBuilders.
                                put("/team/10/Coaches/user/ts@gmail.com").
                                header("Content-Type", "application/xml")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string(addedSubgroupXml));

        mockMvc.perform(
                        MockMvcRequestBuilders.
                                put("/team/30/Coaches/user/ts@gmail.com").
                                header("Content-Type", "application/xml")).
                andExpect(MockMvcResultMatchers.status().isBadRequest()).
                andExpect(MockMvcResultMatchers.status().reason("Team entity not found!"));
    }

    /**
     * Tests adding user to the subgroup when he is already in subgroup
     */
    @Test
    public void addUserToSubgroupWhoIsInSubgroup() throws Exception {
        String uri = "/team/10/Coaches/user/email@gmail.com";
        mockMvc.perform(
                        MockMvcRequestBuilders.
                                put(uri).
                                header("Content-Type", "application/xml")).
                andExpect(
                        MockMvcResultMatchers.
                                status().
                                isBadRequest()).
                andExpect(
                        MockMvcResultMatchers
                                .status().
                                reason((containsString("User is already in subgroup"))));
    }

    /**
     * Tests adding user oto the subgroup when user do not exists
     */
    @Test
    public void addUserToSubgroupWhoIsNotExists() throws Exception {
        String uri = "/team/10/Coaches/user/em@gmail.com";
        mockMvc.perform(
                        MockMvcRequestBuilders.
                                put(uri).
                                header("Content-Type", "application/xml")).
                andExpect(
                        MockMvcResultMatchers.
                                status().
                                isNotFound()).
                andExpect(
                        MockMvcResultMatchers
                                .status().
                                reason((containsString("User entity not found!"))));
    }


}
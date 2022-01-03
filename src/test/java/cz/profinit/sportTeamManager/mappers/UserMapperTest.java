/*
 * UserMapper
 *
 * 0.1
 *
 * Author: J. Janský
 */

package cz.profinit.sportTeamManager.mappers;

import cz.profinit.sportTeamManager.configuration.ApplicationConfigurationTest;
import cz.profinit.sportTeamManager.dto.user.RegisteredUserDTO;
import cz.profinit.sportTeamManager.dto.user.UserDetailsDTO;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for Subgroup class
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationConfigurationTest.class)
@ActiveProfiles({"stub"})
public class UserMapperTest {
    private RegisteredUser registeredUser1;
    private RegisteredUserDTO registeredUserDTO1;
    private RegisteredUser registeredUser2;
    private RegisteredUserDTO registeredUserDTO2;
    private List<RegisteredUser> registeredUserList;
    private List<RegisteredUserDTO> registeredUserDTOList;
    private UserDetailsDTO userDetailsDTO;
//    @Autowired
//    private UserMapper mapper;

    @Before
    public void setUp() {
        registeredUser1 = new RegisteredUser("Tomas", "Smutny", "pass1", "ts@gmail.com", RoleEnum.USER);
        registeredUser2 = new RegisteredUser("Ivan", "Stastny", "pass2", "is@gmail.com", RoleEnum.USER);
        registeredUserDTO1 = new RegisteredUserDTO("Tomas", "Smutny", "ts@gmail.com");
        registeredUserDTO2 = new RegisteredUserDTO("Ivan", "Stastny", "is@gmail.com");
        registeredUserList = new ArrayList<>();
        registeredUserDTOList = new ArrayList<>();
        registeredUserList.add(registeredUser1);
        registeredUserList.add(registeredUser2);
        registeredUserDTOList.add(registeredUserDTO1);
        registeredUserDTOList.add(registeredUserDTO2);
        userDetailsDTO = new UserDetailsDTO("ts@gmail.com", "pass1", "Tomas", "Smutny");
    }

    @Test
    public void mapRegistredUserToRegistredUserDTO() {
        assertEquals(registeredUserDTO1, UserMapper.mapRegistredUserToRegistredUserDTO(registeredUser1));
    }

    @Test
    public void mapRegistredUserDTOToRegistredUser() {
        registeredUser1.setPassword(null);
        assertEquals(registeredUser1, UserMapper.mapRegistredUserDTOToRegistredUser(registeredUserDTO1));
    }

    @Test
    public void mapRegistredUserDTOListToRegistredUserList() {
        registeredUserList.get(0).setPassword(null);
        registeredUserList.get(1).setPassword(null);
        assertEquals(registeredUserList, UserMapper.mapRegistredUserDTOListToRegistredUserList(registeredUserDTOList));

    }

    @Test
    public void mapRegistredUserListToRegistredUserDTOList() {
        assertEquals(registeredUserDTOList, UserMapper.mapRegistredUserListToRegistredUserDTOList(registeredUserList));
    }

    @Test
    public void mapUserDetailsDTOToRegisteredUser() {
        assertEquals(registeredUser1, UserMapper.mapUserDetailsDTOToRegisteredUser(userDetailsDTO));
    }
}
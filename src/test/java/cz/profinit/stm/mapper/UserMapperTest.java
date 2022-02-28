/*
 * UserMapper
 *
 * 0.1
 *
 * Author: J. Janský
 */

package cz.profinit.stm.mapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import cz.profinit.stm.dto.user.RegisteredUserDto;
import cz.profinit.stm.dto.user.UserDetailsDto;
import cz.profinit.stm.model.user.RegisteredUser;
import cz.profinit.stm.model.user.RoleEnum;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for Subgroup class
 */
@RunWith(SpringRunner.class)
public class UserMapperTest {
    private RegisteredUser registeredUser1;
    private RegisteredUserDto registeredUserDto1;
    private List<RegisteredUser> registeredUserList;
    private List<RegisteredUserDto> registeredUserDtoList;
    private UserDetailsDto userDetailsDTO;

    @Before
    public void setUp() {
        registeredUser1 = new RegisteredUser("Tomas", "Smutny", "pass1", "ts@gmail.com", RoleEnum.USER);
        RegisteredUser registeredUser2 = new RegisteredUser("Ivan", "Stastny", "pass2", "is@gmail.com", RoleEnum.USER);
        registeredUserDto1 = new RegisteredUserDto("Tomas", "Smutny", "ts@gmail.com");
        RegisteredUserDto registeredUserDto2 = new RegisteredUserDto("Ivan", "Stastny", "is@gmail.com");
        registeredUserList = new ArrayList<>();
        registeredUserDtoList = new ArrayList<>();
        registeredUserList.add(registeredUser1);
        registeredUserList.add(registeredUser2);
        registeredUserDtoList.add(registeredUserDto1);
        registeredUserDtoList.add(registeredUserDto2);
        userDetailsDTO = new UserDetailsDto("ts@gmail.com", "pass1", "Tomas", "Smutny");
    }

    @Test
    public void mapRegistredUserToRegistredUserDTO() {
        Assert.assertEquals(registeredUserDto1, UserMapper.mapRegisteredUserToRegisteredUserDTO(registeredUser1));
    }

    @Test
    public void mapRegistredUserDTOToRegistredUser() {
        registeredUser1.setPassword(null);
        Assert.assertEquals(registeredUser1, UserMapper.mapRegisteredUserDTOToRegisteredUser(registeredUserDto1));
    }

    @Test
    public void mapRegistredUserDTOListToRegistredUserList() {
        registeredUserList.get(0).setPassword(null);
        registeredUserList.get(1).setPassword(null);
        assertEquals(registeredUserList, UserMapper.mapRegisteredUserDTOListToRegisteredUserList(registeredUserDtoList));

    }

    @Test
    public void mapRegistredUserListToRegistredUserDTOList() {
        assertEquals(registeredUserDtoList, UserMapper.mapRegisteredUserListToRegisteredUserDTOList(registeredUserList));
    }

    @Test
    public void mapUserDetailsDTOToRegisteredUser() {
        Assert.assertEquals(registeredUser1, UserMapper.mapUserDetailsDTOToRegisteredUser(userDetailsDTO));
    }
}
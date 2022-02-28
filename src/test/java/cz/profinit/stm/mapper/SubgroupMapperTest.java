package cz.profinit.stm.mapper;

import cz.profinit.stm.dto.team.SubgroupDto;
import cz.profinit.stm.dto.team.TeamDto;
import cz.profinit.stm.dto.user.RegisteredUserDto;
import cz.profinit.stm.model.team.Subgroup;
import cz.profinit.stm.model.team.Team;
import cz.profinit.stm.model.user.RegisteredUser;
import cz.profinit.stm.model.user.RoleEnum;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SubgroupMapperTest {
    private Subgroup subgroup1;
    private List<Subgroup> subgroupList;
    private SubgroupDto subgroupDto1;
    private List<SubgroupDto> subgroupDtoList;
    private TeamDto teamDTO;
    private Team team;

    @Before
    public void setUp() {
        RegisteredUser registeredUser1 = new RegisteredUser("Tomas", "Smutny", null, "ts@gmail.com", RoleEnum.USER);
        RegisteredUser registeredUser2 = new RegisteredUser("Ivan", "Stastny", null, "is@gmail.com", RoleEnum.USER);
        RegisteredUserDto registeredUserDto1 = new RegisteredUserDto("Tomas", "Smutny", "ts@gmail.com");
        RegisteredUserDto registeredUserDto2 = new RegisteredUserDto("Ivan", "Stastny", "is@gmail.com");
        List<RegisteredUser> registeredUserList = new ArrayList<>();
        List<RegisteredUser> registeredUserList2 = new ArrayList<>();
        List<RegisteredUserDto> registeredUserDtoList = new ArrayList<>();
        List<RegisteredUserDto> registeredUserDtoList2 = new ArrayList<>();
        registeredUserList.add(registeredUser1);
        registeredUserList.add(registeredUser2);
        registeredUserList2.add(registeredUser1);
        registeredUserDtoList.add(registeredUserDto1);
        registeredUserDtoList.add(registeredUserDto2);
        registeredUserDtoList2.add(registeredUserDto1);
        subgroup1 = new Subgroup("Players", 10L);
        subgroup1.setUserList(registeredUserList);
        Subgroup subgroup2 = new Subgroup("Coaches", 10L);
        subgroup2.setUserList(registeredUserList2);
        subgroupDto1 = new SubgroupDto("Players", 10L);
        subgroupDto1.setUserList(registeredUserDtoList);
        SubgroupDto subgroupDto2 = new SubgroupDto("Coaches", 10L);
        subgroupDto2.setUserList(registeredUserDtoList2);
        subgroupList = new ArrayList<>();
        subgroupList.add(subgroup1);
        subgroupList.add(subgroup2);
        subgroupDtoList = new ArrayList<>();
        subgroupDtoList.add(subgroupDto1);
        subgroupDtoList.add(subgroupDto2);
    }

    @Test
    public void mapSubgroupDTOToSubgroup() {
        Assert.assertEquals(subgroup1, SubgroupMapper.mapSubgroupDTOToSubgroup(subgroupDto1));
    }

    @Test
    public void mapSubgroupToSubgroupDTO() {
        Assert.assertEquals(subgroupDto1, SubgroupMapper.mapSubgroupToSubgroupDTO(subgroup1));
    }

    @Test
    public void mapSubgroupDTOListToSubgroupList() {
        assertEquals(subgroupList, SubgroupMapper.mapSubgroupDTOListToSubgroupList(subgroupDtoList));
    }

    @Test
    public void mapSubgroupListToSubgroupDTOList() {
        assertEquals(subgroupDtoList, SubgroupMapper.mapSubgroupListToSubgroupDTOList(subgroupList));
    }
}
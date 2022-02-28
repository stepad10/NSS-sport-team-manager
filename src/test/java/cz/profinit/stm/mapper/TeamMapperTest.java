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

public class TeamMapperTest {
    private Team team;
    private TeamDto teamDTO;

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

        List<Subgroup> subgroupList = new ArrayList<>();
        List<SubgroupDto> subgroupDtoList = new ArrayList<>();
        team = new Team("A team", "golf", subgroupList, registeredUser1);
        team.setEntityId(10L);
        teamDTO = new TeamDto(10L, "A team", "golf", subgroupDtoList, registeredUserDto1);

        Subgroup subgroup1 = new Subgroup("Players", team.getEntityId());
        subgroup1.setUserList(registeredUserList);
        Subgroup subgroup2 = new Subgroup("Coaches", team.getEntityId());
        subgroup2.setUserList(registeredUserList2);
        SubgroupDto subgroupDto1 = new SubgroupDto("Players", teamDTO.getId());
        subgroupDto1.setUserList(registeredUserDtoList);
        SubgroupDto subgroupDto2 = new SubgroupDto("Coaches", teamDTO.getId());
        subgroupDto2.setUserList(registeredUserDtoList2);

        subgroupList.add(subgroup1);
        subgroupList.add(subgroup2);
        subgroupDtoList.add(subgroupDto1);
        subgroupDtoList.add(subgroupDto2);
        team.setSubgroupList(subgroupList);
        teamDTO.setListOfSubgroups(subgroupDtoList);
    }

    @Test
    public void mapTeamDtoToTeam() {
        Assert.assertEquals(team, TeamMapper.mapTeamDtoToTeam(teamDTO));
    }

    @Test
    public void mapTeamToTeamDto() {
        Assert.assertEquals(teamDTO, TeamMapper.mapTeamToTeamDto(team));
    }
}
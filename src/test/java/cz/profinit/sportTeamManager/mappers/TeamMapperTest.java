package cz.profinit.sportTeamManager.mappers;

import cz.profinit.sportTeamManager.dto.user.RegisteredUserDTO;
import cz.profinit.sportTeamManager.dto.team.SubgroupDTO;
import cz.profinit.sportTeamManager.dto.team.TeamDTO;
import cz.profinit.sportTeamManager.model.team.Subgroup;
import cz.profinit.sportTeamManager.model.team.Team;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TeamMapperTest {
    private Subgroup subgroup1;
    private Subgroup subgroup2;
    private List<Subgroup> subgroupList;
    private SubgroupDTO subgroupDTO1;
    private SubgroupDTO subgroupDTO2;
    private List<SubgroupDTO> subgroupDTOList;
    private Team team;
    private TeamDTO teamDTO;

    @Before
    public void setUp() {
        RegisteredUser registeredUser1 = new RegisteredUser("Tomas", "Smutny", null, "ts@gmail.com", RoleEnum.USER);
        RegisteredUser registeredUser2 = new RegisteredUser("Ivan", "Stastny", null, "is@gmail.com", RoleEnum.USER);
        RegisteredUserDTO registeredUserDTO1 = new RegisteredUserDTO("Tomas", "Smutny", "ts@gmail.com");
        RegisteredUserDTO registeredUserDTO2 = new RegisteredUserDTO("Ivan", "Stastny", "is@gmail.com");
        List<RegisteredUser> registeredUserList = new ArrayList<>();
        List<RegisteredUser> registeredUserList2 = new ArrayList<>();
        List<RegisteredUserDTO> registeredUserDTOList = new ArrayList<>();
        List<RegisteredUserDTO> registeredUserDTOList2 = new ArrayList<>();
        registeredUserList.add(registeredUser1);
        registeredUserList.add(registeredUser2);
        registeredUserList2.add(registeredUser1);
        registeredUserDTOList.add(registeredUserDTO1);
        registeredUserDTOList.add(registeredUserDTO2);
        registeredUserDTOList2.add(registeredUserDTO1);

        subgroupList = new ArrayList<>();
        subgroupDTOList = new ArrayList<>();
        team = new Team("A team", "golf", subgroupList, registeredUser1);
        team.setEntityId(10L);
        teamDTO = new TeamDTO(10L, "A team", "golf", subgroupDTOList, registeredUserDTO1);

        subgroup1 = new Subgroup("Players", team.getEntityId());
        subgroup1.setUserList(registeredUserList);
        subgroup2 = new Subgroup("Coaches", team.getEntityId());
        subgroup2.setUserList(registeredUserList2);
        subgroupDTO1 = new SubgroupDTO("Players", teamDTO.getId());
        subgroupDTO1.setUserList(registeredUserDTOList);
        subgroupDTO2 = new SubgroupDTO("Coaches", teamDTO.getId());
        subgroupDTO2.setUserList(registeredUserDTOList2);

        subgroupList.add(subgroup1);
        subgroupList.add(subgroup2);
        subgroupDTOList.add(subgroupDTO1);
        subgroupDTOList.add(subgroupDTO2);
        team.setListOfSubgroups(subgroupList);
        teamDTO.setListOfSubgroups(subgroupDTOList);
    }

    @Test
    public void mapTeamDtoToTeam() {
        assertEquals(team, TeamMapper.mapTeamDtoToTeam(teamDTO));
    }

    @Test
    public void mapTeamToTeamDto() {
        assertEquals(teamDTO, TeamMapper.mapTeamToTeamDto(team));
    }
}
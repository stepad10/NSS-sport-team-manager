/*
 * TeamMapper
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.mappers;

import cz.profinit.sportTeamManager.dto.TeamDTO;
import cz.profinit.sportTeamManager.model.team.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Maps a subgroup data transfer object to subgroup entity and back.
 */
@Component
@Profile({"test", "Main"})
public class TeamMapper {
    @Autowired
    private static UserMapper userMapper;
    @Autowired
    private static SubgroupMapper subgroupMapper;


    /**
     * Maps a single teamDTO to team.
     *
     * @param teamDto team data transfer object to be mapped
     * @return mapped subgroup data transfer object to subgroup
     */
    public static Team mapTeamDtoToTeam(TeamDTO teamDto) {
        Team team = new Team(teamDto.getName(),
                teamDto.getSport(),
                SubgroupMapper.mapSubgroupDTOListToSubgroupList(teamDto.getListOfSubgroups()),
                UserMapper.mapRegistredUserDTOToRegistredUser(teamDto.getOwner()));
        team.setEntityId(teamDto.getId());
        return team;
    }

    /**
     * Maps a single team to teamDTO.
     *
     * @param team team to be mapped
     * @return mapped subgroup data transfer object to subgroup
     */
    public static TeamDTO mapTeamToTeamDto(Team team) {
        return new TeamDTO(team.getEntityId(), team.getName(),
                team.getSport(),
                SubgroupMapper.mapSubgroupListToSubgroupDTOList(team.getListOfSubgroups()),
                UserMapper.mapRegistredUserToRegistredUserDTO(team.getOwner()));
    }
}

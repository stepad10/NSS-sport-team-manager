package cz.profinit.sportTeamManager.mappers;

import cz.profinit.sportTeamManager.dto.TeamDto;
import cz.profinit.sportTeamManager.model.team.Team;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {
    public Team mapTeamDtoToTeam(TeamDto teamDto) {
        return new Team(teamDto.getName(),teamDto.getSport(),teamDto.getListOfSubgroups(),teamDto.getOwner());
    }

    public TeamDto mamTeamToTeamDto(Team team) {
        return new TeamDto(team.getName(),team.getSport(),team.getListOfSubgroups(),team.getOwner());
    }
}

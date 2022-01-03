package cz.profinit.sportTeamManager.repositories.team;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.team.Team;

public interface TeamRepository {
    Team findTeamByName(String teamName);

    void updateTeam(Team team);

    Team saveTeam(Team team);

    void delete(Team team);

    Team findTeamById(Long teamId) throws EntityNotFoundException;
}

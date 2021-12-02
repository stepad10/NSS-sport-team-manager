package cz.profinit.sportTeamManager.repositories;

import cz.profinit.sportTeamManager.model.team.Team;

public interface TeamRepository {
    Team findTeamByName(String teamName);
    void updateTeam(Team team);
}

package cz.profinit.sportTeamManager.service;


import cz.profinit.sportTeamManager.model.team.Team;
import cz.profinit.sportTeamManager.model.user.User;

public interface TeamService {
    Team addUserToTeam(Team team, User user);
    Team createNewTeam(String teamName, String sport, User owner);
}

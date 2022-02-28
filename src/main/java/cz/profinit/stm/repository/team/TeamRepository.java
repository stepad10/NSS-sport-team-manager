package cz.profinit.stm.repository.team;

import cz.profinit.stm.exception.EntityNotFoundException;
import cz.profinit.stm.model.team.Team;

import java.util.List;

public interface TeamRepository {

    void insertTeam(Team team);

    void deleteTeam(Team team) throws EntityNotFoundException;

    void updateTeam(Team team) throws EntityNotFoundException;

    List<Team> findTeamsByName(String teamName) throws EntityNotFoundException;

    Team findTeamById(Long teamId) throws EntityNotFoundException;
}

package cz.profinit.sportTeamManager.repositories;

import cz.profinit.sportTeamManager.model.team.Team;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("Main")
public class TeamRepositoryImpl implements TeamRepository {
    @Override
    public Team findTeamByName(String teamName) {
        return null;
    }

    @Override
    public void updateTeam(Team team) {

    }

    @Override
    public Team saveTeam(Team team) {
        return null;
    }

    @Override
    public void delete(Team team) {
    }


    @Override
    public Team findTeamById(Long teamId) {
        return null;
    }

}

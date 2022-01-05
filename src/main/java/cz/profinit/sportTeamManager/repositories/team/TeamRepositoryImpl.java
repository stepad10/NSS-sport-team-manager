package cz.profinit.sportTeamManager.repositories.team;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.mapperMyBatis.subgroup.SubgroupMapperMyBatis;
import cz.profinit.sportTeamManager.mapperMyBatis.team.TeamMapperMyBatis;
import cz.profinit.sportTeamManager.model.team.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("Main")
public class TeamRepositoryImpl implements TeamRepository {

    @Autowired
    private TeamMapperMyBatis teamMapperMyBatis;

    @Autowired
    private SubgroupMapperMyBatis subgroupMapperMyBatis;

    @Override
    public Team insertTeam(Team team) {
        team.getListOfSubgroups().forEach(s -> subgroupMapperMyBatis.insertSubgroup(s));//TODO dat do subgroup repo
        return teamMapperMyBatis.insertTeam(team);
    }

    @Override
    public Team deleteTeam(Team team) throws EntityNotFoundException {
        Team foundTeam = findTeamById(team.getEntityId());
        foundTeam.getListOfSubgroups().forEach(s -> subgroupMapperMyBatis.deleteSubgroupById(s.getEntityId()));
        return teamMapperMyBatis.deleteTeamById(foundTeam.getEntityId());
    }

    @Override
    public Team updateTeam(Team team) throws EntityNotFoundException {
        findTeamById(team.getEntityId());
        return teamMapperMyBatis.updateTeam(team);
    }

    @Override
    public List<Team> findTeamsByName(String teamName) throws EntityNotFoundException {
        List<Team> teams = teamMapperMyBatis.findTeamsByName(teamName);
        if (teams.size() == 0) throw new EntityNotFoundException("Team");
        teams.forEach(t -> t.setListOfSubgroups(subgroupMapperMyBatis.findSubgroupsByTeamId(t.getEntityId())));
        return teams;
    }

    @Override
    public Team findTeamById(Long teamId) throws EntityNotFoundException {
        Team team = teamMapperMyBatis.findTeamById(teamId);
        if (team == null) throw new EntityNotFoundException("Team");
        team.setListOfSubgroups(subgroupMapperMyBatis.findSubgroupsByTeamId(team.getEntityId()));
        return team;
    }
}

package cz.profinit.sportTeamManager.repositories.subgroup;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.team.Subgroup;
import cz.profinit.sportTeamManager.model.team.Team;

import java.util.List;

public interface SubgroupRepository {
    void insertSubgroup(Subgroup subgroup);

    void updateSubgroup(Subgroup subgroup);

    void deleteSubgroup(Subgroup subgroup);

    void deleteAllTeamSubgroups(Team team);

    Subgroup findTeamSubgroupByName(Team team, String subgroupName) throws EntityNotFoundException;

    List<Subgroup> findTeamSubgroups(Team team) throws EntityNotFoundException;
}

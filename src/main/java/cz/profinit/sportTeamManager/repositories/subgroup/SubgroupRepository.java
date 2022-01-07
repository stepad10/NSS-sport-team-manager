package cz.profinit.sportTeamManager.repositories.subgroup;

import cz.profinit.sportTeamManager.model.team.Subgroup;
import cz.profinit.sportTeamManager.model.team.Team;

import java.util.List;

public interface SubgroupRepository {
    Subgroup insertSubgroup(Subgroup subgroup);

    Subgroup updateSubgroup(Subgroup subgroup);

    Subgroup deleteSubgroup(Subgroup subgroup);

    List<Subgroup> findTeamSubgroups(Team team);

    void deleteAllTeamSubgroups(Team team);
}

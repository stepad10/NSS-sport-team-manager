package cz.profinit.sportTeamManager.repositories.subgroup;

import cz.profinit.sportTeamManager.exceptions.EntityAlreadyExistsException;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.team.Subgroup;
import cz.profinit.sportTeamManager.model.team.Team;

import java.util.List;

public interface SubgroupRepository {

    void insertSubgroup(Subgroup subgroup) throws EntityAlreadyExistsException;

    /**
     * Updates only name of a subgroup
     * @param subgroup to update
     * @throws EntityAlreadyExistsException if team already has a subgroup with new name
     * @throws EntityNotFoundException if subgroup couldn't be found
     */
    void updateSubgroup(Subgroup subgroup) throws EntityAlreadyExistsException, EntityNotFoundException;

    void deleteSubgroup(Subgroup subgroup) throws EntityNotFoundException;

    void deleteAllTeamSubgroups(Team team) throws EntityNotFoundException;

    Subgroup findTeamSubgroupByName(Team team, String subgroupName) throws EntityNotFoundException;

    /**
     * finds all subgroups of a team and fill userLists of subgroups
     * @param team parent of subgroups
     * @return list of subgroups belonging to team
     */
    List<Subgroup> findTeamSubgroups(Team team);
}

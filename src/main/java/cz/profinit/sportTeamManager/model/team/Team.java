/*
 * Team
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.model.team;

import java.util.List;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.entity.Entity;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class containing all team information such as team name, team sport, list of subgroups and owner.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team extends Entity {
    private String name;
    private String sport;
    private List<Subgroup> listOfSubgroups;
    private RegisteredUser owner;


    /**
     * Create a new subgroup and adds it to the list of subgroups.
     *
     * @param subgroupName name of the subgroup
     */
    public void addNewSubgroup(String subgroupName) {
        listOfSubgroups.add(new Subgroup(subgroupName, this.getEntityId()));
    }

    /**
     * Obtains a subgroup defined by name from a list of the subgroups.
     *
     * @param subgroupName name of the subgroup
     * @return found subgroup
     */

    public Subgroup getTeamSubgroup(String subgroupName) throws EntityNotFoundException {
        for (Subgroup subgroup : listOfSubgroups) {
            if (subgroup.getName().equals(subgroupName)) {
                return subgroup;
            }
        }
        throw new EntityNotFoundException("Subgroup");
    }

    /**
     * Removes a subgroup defined by name from
     *
     * @param subgroupName subgroup name what should be removed
     */
    public void deleteSubgroup(String subgroupName) throws EntityNotFoundException {
        Subgroup subgroup = getTeamSubgroup(subgroupName);
        listOfSubgroups.remove(subgroup);
    }

    /**
     * Checks if a subgroup is in team by subgroup name.
     *
     * @param subgroupName string of subgroup name
     * @return true if subgroup of selected name is in team, false otherwise
     */
    public boolean isSubgroupInTeam(String subgroupName) {
        try {
            this.getTeamSubgroup(subgroupName);
        } catch (EntityNotFoundException e) {
            return false;
        }
        return true;
    }
}

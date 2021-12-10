/*
 * Team
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.model.team;

import cz.profinit.sportTeamManager.model.entity.Entity;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
        listOfSubgroups.add(new Subgroup(subgroupName));
    }

    /**
     * Obtains a subgroup defined by name from a list of the subgroups.
     *
     * @param subgroupName name of the subgroup
     * @return found subgroup
     */
    public Subgroup getTeamSubgroup(String subgroupName) {
        for (Subgroup subgroup : listOfSubgroups) {
            if (subgroup.getName().equals(subgroupName)) {
                return subgroup;
            }
        }
        throw new RuntimeException("No subgroup found");
    }

    /**
     * Removes a subgroup defined by name from
     *
     * @param subgroupName subgroup name what should be removed
     */
    public void deleteSubgroup(String subgroupName) {
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
        } catch (Exception e) {
            return  !(e.getMessage().equals("No subgroup found"));
        }
        return true;
    }
}

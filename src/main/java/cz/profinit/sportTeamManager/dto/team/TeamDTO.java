/*
 * TeamDTO
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.dto.team;

import cz.profinit.sportTeamManager.dto.user.RegisteredUserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Data transfer object for team entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamDTO {
    @XmlElement
    private Long id;
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "sport")
    private String sport;
    @XmlElement(name = "listOfSubgroups")
    private List<SubgroupDTO> listOfSubgroups;
    @XmlElement(name = "owner")
    private RegisteredUserDTO owner;

    /**
     * Obtains a subgroup defined by name from a list of the subgroups.
     *
     * @param subgroupName name of the subgroup
     * @return found subgroup
     */
    public SubgroupDTO getTeamSubgroup(String subgroupName) {
        for (SubgroupDTO subgroup : listOfSubgroups) {
            if (subgroup.getName().equals(subgroupName)) {
                return subgroup;
            }
        }
        throw new RuntimeException("No subgroup found");
    }
}

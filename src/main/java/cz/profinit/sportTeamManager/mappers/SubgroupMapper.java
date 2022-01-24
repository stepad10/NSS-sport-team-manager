/*
 * SubgroupMapper
 *
 * 0.1
 *
 * Author: J. Janský
 */

package cz.profinit.sportTeamManager.mappers;


import cz.profinit.sportTeamManager.dto.team.SubgroupDTO;
import cz.profinit.sportTeamManager.model.team.Subgroup;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Maps a subgroup data transfer object to subgroup entity and back.
 */
@Component
@Profile({"test", "Main","stub_services"})
public class SubgroupMapper {

    /**
     * Maps a single subgroupDTO to subgroup.
     *
     * @param subgroupDTO subgroup data transfer object to be mapped
     * @return mapped subgroup data transfer object to subgroup
     */
    public static Subgroup mapSubgroupDTOToSubgroup(SubgroupDTO subgroupDTO) {
        Subgroup subgroup = new Subgroup(subgroupDTO.getName(), subgroupDTO.getTeamId());
        subgroup.setUserList(UserMapper.mapRegisteredUserDTOListToRegisteredUserList(subgroupDTO.getUserList()));
        return subgroup;
    }

    /**
     * Maps a single subgroup to subgroupDTO.
     *
     * @param subgroup subgroup to be mapped
     * @return mapped subgroup to  subgroup data transfer object
     */
    public static SubgroupDTO mapSubgroupToSubgroupDTO(Subgroup subgroup) {
        SubgroupDTO subgroupDTO = new SubgroupDTO(subgroup.getName(), subgroup.getTeamId());
        subgroupDTO.setUserList(UserMapper.mapRegisteredUserListToRegisteredUserDTOList(subgroup.getUserList()));
        return subgroupDTO;
    }

    /**
     * Maps a list of subgroupDTOs to list of subgroups.
     *
     * @param subgroupDTOList list of subgroup data transfer objects to be mapped
     * @return mapped list of subgroup data transfer objects to list of subgroups
     */
    public static List<Subgroup> mapSubgroupDTOListToSubgroupList(List<SubgroupDTO> subgroupDTOList) {
        return subgroupDTOList.stream().map(SubgroupMapper::mapSubgroupDTOToSubgroup).collect(Collectors.toList());
    }

    /**
     * Maps a list of subgroups to list of subgroupDTOs.
     *
     * @param subgroupList list of subgroups to be mapped
     * @return mapped list of subgroups to list of subgroup data transfer objects
     */
    public static List<SubgroupDTO> mapSubgroupListToSubgroupDTOList(List<Subgroup> subgroupList) {
        return subgroupList.stream().map(SubgroupMapper::mapSubgroupToSubgroupDTO).collect(Collectors.toList());
    }

}

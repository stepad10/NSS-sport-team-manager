package cz.profinit.sportTeamManager.dto;

import cz.profinit.sportTeamManager.model.team.Subgroup;
import cz.profinit.sportTeamManager.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TeamDto {
    private String name;
    private String sport;
    private List<Subgroup> listOfSubgroups;
    private User owner;
}

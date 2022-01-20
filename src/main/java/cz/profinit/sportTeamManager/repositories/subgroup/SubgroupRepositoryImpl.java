package cz.profinit.sportTeamManager.repositories.subgroup;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.mapperMyBatis.subgroup.SubgroupMapperMyBatis;
import cz.profinit.sportTeamManager.model.team.Subgroup;
import cz.profinit.sportTeamManager.model.team.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("Main")
public class SubgroupRepositoryImpl implements SubgroupRepository {

    @Autowired
    private SubgroupMapperMyBatis subgroupMapperMyBatis;

    @Override
    public void insertSubgroup(Subgroup subgroup) {

    }

    @Override
    public void updateSubgroup(Subgroup subgroup) {

    }

    @Override
    public void deleteSubgroup(Subgroup subgroup) {

    }

    @Override
    public void deleteAllTeamSubgroups(Team team) {

    }

    @Override
    public Subgroup findTeamSubgroupByName(Team team, String subgroupName) throws EntityNotFoundException {
        List<Subgroup> teamSubgroups = findTeamSubgroups(team);
        for (Subgroup s : teamSubgroups) {
            if (s.getName().equals(subgroupName)) return s;
        }
        throw new EntityNotFoundException("Subgroup");
    }

    @Override
    public List<Subgroup> findTeamSubgroups(Team team) throws EntityNotFoundException {
        List<Subgroup> subgroupList = subgroupMapperMyBatis.findSubgroupsByTeamId(team.getEntityId());
        if (subgroupList.isEmpty()) throw new EntityNotFoundException("Subgroup");
        return subgroupList;
    }

}

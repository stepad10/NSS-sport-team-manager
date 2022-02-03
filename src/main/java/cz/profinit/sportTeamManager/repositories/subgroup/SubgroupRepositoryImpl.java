package cz.profinit.sportTeamManager.repositories.subgroup;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.mapperMyBatis.subgroup.SubgroupMapperMyBatis;
import cz.profinit.sportTeamManager.mapperMyBatis.subgroupUser.SubgroupUserMapperMyBatis;
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

    @Autowired
    private SubgroupUserMapperMyBatis subgroupUserMapperMyBatis;

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
        Subgroup subgroup = subgroupMapperMyBatis.findSubgroupByNameAndTeamId(subgroupName, team.getEntityId());
        if (subgroup == null) throw new EntityNotFoundException("Subgroup");
        subgroup.setUserList(subgroupUserMapperMyBatis.findUsersBySubgroupId(subgroup.getEntityId()));
        return subgroup;
    }

    @Override
    public List<Subgroup> findTeamSubgroups(Team team) {
        List<Subgroup> subgroupList = subgroupMapperMyBatis.findSubgroupsByTeamId(team.getEntityId());
        for (Subgroup s : subgroupList) {
            s.setUserList(subgroupUserMapperMyBatis.findUsersBySubgroupId(s.getEntityId()));
        }
        return subgroupList;
    }
}

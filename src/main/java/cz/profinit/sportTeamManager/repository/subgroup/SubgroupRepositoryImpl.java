package cz.profinit.sportTeamManager.repository.subgroup;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import cz.profinit.sportTeamManager.exception.EntityAlreadyExistsException;
import cz.profinit.sportTeamManager.exception.EntityNotFoundException;
import cz.profinit.sportTeamManager.mapperMyBatis.subgroup.SubgroupMapperMyBatis;
import cz.profinit.sportTeamManager.mapperMyBatis.subgroupUser.SubgroupUserMapperMyBatis;
import cz.profinit.sportTeamManager.model.team.Subgroup;
import cz.profinit.sportTeamManager.model.team.Team;

@Repository
@Profile("Main")
public class SubgroupRepositoryImpl implements SubgroupRepository {

    @Autowired
    private SubgroupMapperMyBatis subgroupMapperMyBatis;

    @Autowired
    private SubgroupUserMapperMyBatis subgroupUserMapperMyBatis;

    private static final String EX_MSG = "Subgroup";

    @Override
    public void insertSubgroup(Subgroup subgroup) throws EntityAlreadyExistsException {
        if (subgroupMapperMyBatis.findSubgroupByNameAndTeamId(subgroup.getName(), subgroup.getTeamId()) != null) {
            throw new EntityAlreadyExistsException(EX_MSG);
        }
        subgroupMapperMyBatis.insertSubgroup(subgroup);
    }

    @Override
    public void updateSubgroup(Subgroup subgroup) throws EntityAlreadyExistsException, EntityNotFoundException {
        if (subgroupMapperMyBatis.findSubgroupById(subgroup.getEntityId()) == null) {
            throw new EntityNotFoundException(EX_MSG);
        }
        if (subgroupMapperMyBatis.findSubgroupByNameAndTeamId(subgroup.getName(), subgroup.getTeamId()) != null) {
            throw new EntityAlreadyExistsException(EX_MSG);
        }
        subgroupMapperMyBatis.updateSubgroup(subgroup);
    }

    @Override
    public void deleteSubgroup(Subgroup subgroup) throws EntityNotFoundException {
        if (subgroupMapperMyBatis.findSubgroupById(subgroup.getEntityId()) == null) throw new EntityNotFoundException(EX_MSG);
        subgroupUserMapperMyBatis.deleteAllSubgroupUsers(subgroup.getEntityId());
        subgroupMapperMyBatis.deleteSubgroupById(subgroup.getEntityId());
    }

    @Override
    public void deleteAllTeamSubgroups(Team team) throws EntityNotFoundException {
        for (Subgroup s : subgroupMapperMyBatis.findSubgroupsByTeamId(team.getEntityId())) {
            deleteSubgroup(s);
        }
    }

    @Override
    public Subgroup findTeamSubgroupByName(Team team, String subgroupName) throws EntityNotFoundException {
        Subgroup subgroup = subgroupMapperMyBatis.findSubgroupByNameAndTeamId(subgroupName, team.getEntityId());
        if (subgroup == null) throw new EntityNotFoundException(EX_MSG);
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

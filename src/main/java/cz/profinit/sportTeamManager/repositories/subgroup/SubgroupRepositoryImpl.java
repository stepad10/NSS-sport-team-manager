package cz.profinit.sportTeamManager.repositories.subgroup;

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
    public Subgroup insertSubgroup(Subgroup subgroup) {
        return null;
    }

    @Override
    public Subgroup updateSubgroup(Subgroup subgroup) {
        return null;
    }

    @Override
    public Subgroup deleteSubgroup(Subgroup subgroup) {
        return null;
    }

    @Override
    public List<Subgroup> findTeamSubgroups(Team team) {
        return null;
    }

    @Override
    public void deleteAllTeamSubgroups(Team team) {

    }
}

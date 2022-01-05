package cz.profinit.sportTeamManager.repositories.subgroup;

import cz.profinit.sportTeamManager.model.team.Subgroup;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

//TODO implement
@Repository
@Profile("Main")
public class SubgroupRepositoryImpl implements SubgroupRepository {
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
    public List<Subgroup> findSubgroupsByTeamID(Long teamId) {
        return null;
    }
}

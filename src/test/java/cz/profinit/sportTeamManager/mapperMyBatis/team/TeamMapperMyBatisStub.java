package cz.profinit.sportTeamManager.mapperMyBatis.team;

import cz.profinit.sportTeamManager.model.team.Team;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class TeamMapperMyBatisStub implements TeamMapperMyBatis {

    private final Logger logger = Logger.getLogger(String.valueOf(getClass()));

    @Override
    public Team insertTeam(Team team) {
        logger.info("STUB: Inserting team to database!");
        return team;
    }

    @Override
    public Team updateTeam(Team team) {
        logger.info("STUB: Updating team in database!");
        return team;
    }

    @Override
    public Team deleteTeamById(Long id) {
        logger.info("STUB: deleting team in database!");
        if (id == 0L) return null;
        return new Team("Profinit", "Famfrpal", new ArrayList<>(), new RegisteredUser());
    }

    @Override
    public List<Team> findTeamsByName(String name) {
        logger.info("STUB: Finding teams by name in database!");
        if (name.equals("urMamaTeam")) return null;
        List<Team> teams = new ArrayList<Team>();
        teams.add(new Team(name, "Famfrpal", new ArrayList<>(), new RegisteredUser()));
        teams.add(new Team(name, "Pokopana", new ArrayList<>(), new RegisteredUser()));
        return teams;
    }

    @Override
    public Team findTeamById(Long id) {
        logger.info("STUB: Finding team by id in database!");
        if (id == 0L) return null;
        return new Team("Profinit", "Famfrpal", new ArrayList<>(), new RegisteredUser());
    }
}

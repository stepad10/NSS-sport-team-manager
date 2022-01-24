package cz.profinit.sportTeamManager.mapperMyBatis.event;

import cz.profinit.sportTeamManager.model.event.Event;

public interface EventMapperMyBatis {

    /**
     * insert Event to database and update it's entityId
     * @param event to insert
     */
    void insertEvent(Event event);

    /**
     *
     * @param event to be updated
     */
    void updateEvent(Event event);

    /**
     *
     * @param id to find Event by
     */
    void deleteEventById(Long id);

    /**
     *
     * @param id to find Event by
     * @return found Event or null
     */
    Event findEventById(Long id);
}

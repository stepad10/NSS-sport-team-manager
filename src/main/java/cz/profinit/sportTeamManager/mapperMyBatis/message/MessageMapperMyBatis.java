package cz.profinit.sportTeamManager.mapperMyBatis.message;

import cz.profinit.sportTeamManager.model.event.Message;

public interface MessageMapperMyBatis {

    /**
     * insert Message to database and update it's entityId
     * @param message to insert
     */
    void insertMessage(Message message);

    /**
     *
     * @param message to update
     */
    void updateMessage(Message message);

    /**
     *
     * @param id to find Message by
     */
    void deleteMessageById(Long id);

    /**
     *
     * @param id to find Message by
     * @return found Message or null
     */
    Message findMessageById(Long id);

}

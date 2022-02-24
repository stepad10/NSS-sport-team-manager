/*
 * PlaceMapper
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.sportTeamManager.mapper;

import cz.profinit.sportTeamManager.dto.event.PlaceDto;
import cz.profinit.sportTeamManager.model.event.Place;

public class PlaceMapper {

    /**
     * Map Entity to DTO
     *
     * @param place Entity that should be converted
     * @return DTO of given Entity
     */
    public static PlaceDto toDto(Place place) {
        return new PlaceDto(place.getName(), place.getAddress(), place.getTeamId());
    }

    /**
     * Map DTO to Entity
     *
     * @param placeDto DTO that should be converted
     * @return Entity of given DTO
     */
    public static Place toPlace(PlaceDto placeDto) {
        return new Place(placeDto.getName(), placeDto.getAddress(), placeDto.getTeamId());
    }

}

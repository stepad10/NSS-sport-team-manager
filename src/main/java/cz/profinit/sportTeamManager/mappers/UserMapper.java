/*
 * UserMapper
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.sportTeamManager.mappers;

import cz.profinit.sportTeamManager.dto.RegisteredUserDTO;
import cz.profinit.sportTeamManager.dto.UserDetailsDTO;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import cz.profinit.sportTeamManager.model.user.RoleEnum;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Provides mapping from RegisteredUser data transfer object to RegisteredUser entity in both ways.
 */
@Component
@Profile({"test", "Main"})
@NoArgsConstructor
public class UserMapper {

    /**
     * Maps a single RegisteredUser to its data transfer object.
     *
     * @param registeredUser RegisteredUser to be mapped
     * @return mapped RegisteredUser to RegisteredUser  data transfer object
     */
    public static RegisteredUserDTO mapRegistredUserToRegistredUserDTO(RegisteredUser registeredUser) {
        RegisteredUserDTO registeredUserDTO = new RegisteredUserDTO(registeredUser.getName(),
                registeredUser.getSurname(),
                registeredUser.getEmail());
        return registeredUserDTO;
    }

    /**
     * Maps a single RegisteredUser data transfer object to a single RegisteredUser.
     *
     * @param registeredUserDTO RegisteredUser data transfer object to be mapped
     * @return mapped RegisteredUser data transfer object to RegisteredUser
     */
    public static RegisteredUser mapRegistredUserDTOToRegistredUser(RegisteredUserDTO registeredUserDTO) {
        RegisteredUser registeredUser = new RegisteredUser(registeredUserDTO.getName(),
                registeredUserDTO.getSurname(),
                null,
                registeredUserDTO.getEmail(),
                RoleEnum.USER);
        return registeredUser;
    }

    /**
     * Maps list of  RegisteredUser to its data transfer object.
     *
     * @param registeredUserList list of RegisteredUser to be mapped
     * @return mapped list of RegisteredUser to list of RegisteredUser data transfer object
     */
    public static List<RegisteredUserDTO> mapRegistredUserListToRegistredUserDTOList(List<RegisteredUser> registeredUserList) {
        return registeredUserList.stream().map(UserMapper::mapRegistredUserToRegistredUserDTO).collect(Collectors.toList());
    }

    /**
     * Maps a list of RegisteredUser data transfer object to list of RegisteredUser.
     *
     * @param registeredUserList list of RegisteredUser data transfer object to be mapped
     * @return mapped list of RegisteredUser data transfer object to list of RegisteredUser
     */
    public static List<RegisteredUser> mapRegistredUserDTOListToRegistredUserList(List<RegisteredUserDTO> registeredUserList) {
        return registeredUserList.stream().map(UserMapper::mapRegistredUserDTOToRegistredUser).collect(Collectors.toList());
    }

    /**
     * Maps a UserDetailsDTO to Registered user.
     *
     * @param userDetailsDTO user details data transfer object to be mapped
     * @return mapped RegisteredUser
     */
    public static RegisteredUser mapUserDetailsDTOToRegisteredUser(UserDetailsDTO userDetailsDTO) {
        return new RegisteredUser(userDetailsDTO.getUserName(),
                userDetailsDTO.getSurname(),
                userDetailsDTO.getPassword(),
                userDetailsDTO.getName(),
                RoleEnum.USER);
    }

}

/*
 * UserMapper
 *
 * 0.1
 *
 * Author: J. Janský
 */
package cz.profinit.stm.mapper;

import cz.profinit.stm.dto.user.RegisteredUserDto;
import cz.profinit.stm.dto.user.UserDetailsDto;
import cz.profinit.stm.model.user.RegisteredUser;
import cz.profinit.stm.model.user.RoleEnum;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Provides mapping from RegisteredUser data transfer object to RegisteredUser entity in both ways.
 */
@Component
@Profile({"test", "Main","stub_services"})
@NoArgsConstructor
public class UserMapper {

    /**
     * Maps a single RegisteredUser to its data transfer object.
     *
     * @param registeredUser RegisteredUser to be mapped
     * @return mapped RegisteredUser to RegisteredUser  data transfer object
     */
    public static RegisteredUserDto mapRegisteredUserToRegisteredUserDTO(RegisteredUser registeredUser) {
        return new RegisteredUserDto(registeredUser.getName(),
                registeredUser.getSurname(),
                registeredUser.getEmail());
    }

    /**
     * Maps a single RegisteredUser data transfer object to a single RegisteredUser.
     *
     * @param registeredUserDTO RegisteredUser data transfer object to be mapped
     * @return mapped RegisteredUser data transfer object to RegisteredUser
     */
    public static RegisteredUser mapRegisteredUserDTOToRegisteredUser(RegisteredUserDto registeredUserDTO) {
        return new RegisteredUser(registeredUserDTO.getName(),
                registeredUserDTO.getSurname(),
                null,
                registeredUserDTO.getEmail(),
                RoleEnum.USER);
    }

    /**
     * Maps list of  RegisteredUser to its data transfer object.
     *
     * @param registeredUserList list of RegisteredUser to be mapped
     * @return mapped list of RegisteredUser to list of RegisteredUser data transfer object
     */
    public static List<RegisteredUserDto> mapRegisteredUserListToRegisteredUserDTOList(List<RegisteredUser> registeredUserList) {
        return registeredUserList.stream().map(UserMapper::mapRegisteredUserToRegisteredUserDTO).collect(Collectors.toList());
    }

    /**
     * Maps a list of RegisteredUser data transfer object to list of RegisteredUser.
     *
     * @param registeredUserList list of RegisteredUser data transfer object to be mapped
     * @return mapped list of RegisteredUser data transfer object to list of RegisteredUser
     */
    public static List<RegisteredUser> mapRegisteredUserDTOListToRegisteredUserList(List<RegisteredUserDto> registeredUserList) {
        return registeredUserList.stream().map(UserMapper::mapRegisteredUserDTOToRegisteredUser).collect(Collectors.toList());
    }

    /**
     * Maps a UserDetailsDTO to Registered user.
     *
     * @param userDetailsDTO user details data transfer object to be mapped
     * @return mapped RegisteredUser
     */
    public static RegisteredUser mapUserDetailsDTOToRegisteredUser(UserDetailsDto userDetailsDTO) {
        return new RegisteredUser(userDetailsDTO.getUserName(),
                userDetailsDTO.getSurname(),
                userDetailsDTO.getPassword(),
                userDetailsDTO.getName(),
                RoleEnum.USER);
    }

}
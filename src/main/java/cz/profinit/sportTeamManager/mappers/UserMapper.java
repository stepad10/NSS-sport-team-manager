package cz.profinit.sportTeamManager.mappers;

import cz.profinit.sportTeamManager.dto.RegisteredUserDTO;
import cz.profinit.sportTeamManager.model.user.RegisteredUser;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public RegisteredUserDTO mapRegistredUserToRegistredUserDTO(RegisteredUser registeredUser) {
        RegisteredUserDTO registeredUserDTO = new RegisteredUserDTO(registeredUser.getName(),
                registeredUser.getSurname(),
                registeredUser.getPassword(),
                registeredUser.getEmail(),
                registeredUser.getRole());
        return registeredUserDTO;
    }

    public RegisteredUser mapRegistredUserDTOToRegistredUser(RegisteredUserDTO registeredUserDTO) {
        RegisteredUser registeredUser = new RegisteredUser(registeredUserDTO.getName(),
                registeredUserDTO.getSurname(),
                registeredUserDTO.getPassword(),
                registeredUserDTO.getEmail(),
                registeredUserDTO.getRole());
        return registeredUser;
    }
}

package eu.profinit.stm.dto.user;

import lombok.Value;

import java.util.List;

@Value
public class UserInfo {
	String name, surname, email;
	List<String> roles;
}
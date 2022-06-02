export enum Role {
  ADMIN,
  USER,
  GUEST,
}

export class User {
  name: string;
  surname: string;
  email: string;
  role: Role;

  constructor(name: string, surname: string, email: string, role: Role) {
    this.surname = surname;
    this.name = name;
    this.email = email;
    this.role = role;
  }
}

import { HttpHeaders } from '@angular/common/http';

export class AppConstants {
  public static BASE_URL = `http://localhost:4200`;
  private static API_BASE_URL = `http://localhost:8080`;
  private static OAUTH2_URL = `${this.API_BASE_URL}/oauth2/authorization`;
  private static REDIRECT_URL = `?redirect_uri=${this.BASE_URL}/login`;

  public static API_URL = `${this.API_BASE_URL}/api`;
  public static AUTH_API = `${this.API_URL}/auth`;

  public static GOOGLE_AUTH_URL = `${this.OAUTH2_URL}/google${this.REDIRECT_URL}`;
  public static FACEBOOK_AUTH_URL = `${this.OAUTH2_URL}/facebook${this.REDIRECT_URL}`;
  public static GITHUB_AUTH_URL = `${this.OAUTH2_URL}/github${this.REDIRECT_URL}`;

  public static HTTP_OPTIONS = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };
}

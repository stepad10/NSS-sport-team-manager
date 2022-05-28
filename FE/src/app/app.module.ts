import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { MdbCollapseModule } from 'mdb-angular-ui-kit/collapse';
import { AppRoutingModule } from './app-routing/app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { ProcessHttpMsgService } from './services/process-http-msg.service';
import { UserService } from './services/user.service';
import { baseURL } from './shared/base-url';
import { RegisterComponent } from './user/register/register.component';
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HeaderComponent,
    HomeComponent,
    RegisterComponent,
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    MdbCollapseModule,
    ReactiveFormsModule,
  ],
  providers: [
    UserService,
    ProcessHttpMsgService,
    { provide: 'BaseURL', useValue: baseURL },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}

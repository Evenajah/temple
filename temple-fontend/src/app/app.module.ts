import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { HttpClientModule } from '@angular/common/http'
import { CoreModule } from './core/core.module';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ContentModule } from './content/content.module';
import { AuthModule } from './auth/auth.module';
import { AuthService } from './shared/service/auth.service';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';

import { AngularFireModule } from '@angular/fire';
import { environment } from '../environments/environment.prod';
import { AngularFirestore,AngularFirestoreModule  } from '@angular/fire/firestore';
@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    AuthModule,
    ContentModule,
    CoreModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    AngularFireModule.initializeApp(environment.firebase),
    AngularFirestoreModule
  ],
  providers: [AuthService,
    { provide: LocationStrategy, useClass: HashLocationStrategy },AngularFirestore
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

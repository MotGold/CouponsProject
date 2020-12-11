import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SessionToken } from '../models/session-token';

@Injectable({
  providedIn: 'root'
})
export class LoginManagerService {

  constructor(private client: HttpClient) { }

  public connectToServer(email:string, pass:string, type:number) {
    return this.client.get<SessionToken>("http://localhost:8080/bob/login/"+email+"/"+pass+"/"+type); //....+type,{responseType:'text'});
  }

  public logout(token:string){
  return this.client.get("http://localhost:8080/bob/logout/"+ token, { responseType: 'text' });
}
}

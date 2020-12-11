import { Component, OnInit } from '@angular/core';
import { LoginManagerService } from 'src/app/services/login-manager.service';
import { Router } from '@angular/router'; 

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  email:string;
  pass:string;
  type:number=null;

  constructor(private router: Router, private service: LoginManagerService) { }

  ngOnInit(): void {
  }

  public login() {
    this.service.connectToServer(this.email,this.pass,this.type).subscribe(
      (serverOutput)=>{
//if(serverOutput!=null) {...
          sessionStorage.clear();
          sessionStorage.setItem(serverOutput.token, JSON.stringify(serverOutput.currentTime));
          if(this.type==1)
          this.router.navigateByUrl('/admin');
          if(this.type==2)
          this.router.navigateByUrl('/company');
          if(this.type==3)
          this.router.navigateByUrl('/customer');
//}else{ alert("Wrong input entered"); }
      },
      (err)=>alert("Wrong input entered")//console.log(err)
    );
  }

}

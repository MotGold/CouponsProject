import { Component, OnInit } from '@angular/core';
import { LoginManagerService } from 'src/app/services/login-manager.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-company-layout',
  templateUrl: './company-layout.component.html',
  styleUrls: ['./company-layout.component.css']
})
export class CompanyLayoutComponent implements OnInit {
  
  constructor(private service: LoginManagerService, private router: Router) { }

  ngOnInit(): void {
  }

  /**
   * logout
   */
  public logout() {
    this.service.logout(sessionStorage.key(0)).subscribe(
      () => {
        alert("logout successful");
        this.router.navigateByUrl("/login");
      },
      err => {
        alert(err.error);
      }
    );
  }

}

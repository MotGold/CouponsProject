import { Component, OnInit } from '@angular/core';
import { LoginManagerService } from 'src/app/services/login-manager.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-customer-layout',
  templateUrl: './customer-layout.component.html',
  styleUrls: ['./customer-layout.component.css']
})
export class CustomerLayoutComponent implements OnInit {

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

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AdminControllerService } from 'src/app/services/admin-controller.service';
import { Company } from 'src/app/models/company';

@Component({
  selector: 'app-add-company',
  templateUrl: './add-company.component.html',
  styleUrls: ['./add-company.component.css']
})

export class AddCompanyComponent implements OnInit {

  name: string = null;
  email: string = null;
  password: string = null;

  constructor(private router: Router, private service: AdminControllerService) { }

  ngOnInit(): void {
  }

  public addCompany() {
    if (this.name == null)
      alert("please enter a name!")
    else if (this.email == null)
      alert("please enter an email!")
    else if (this.password == null)
      alert("please enter a password!")
    else {
      let aCompany = new Company(0, this.name, this.email, this.password);
      this.service.postBean(sessionStorage.key(0), aCompany).subscribe(
        company => {
          if (company.name != "DuplicateException")
            alert("Company added! its new id is: " + company.id);
          else
            alert("A company already exists with the entered name, please choose another name");
        },
        err => {
          alert("your session token has expired!");
          this.router.navigateByUrl("/login");
        }
      );
    }
  }

}

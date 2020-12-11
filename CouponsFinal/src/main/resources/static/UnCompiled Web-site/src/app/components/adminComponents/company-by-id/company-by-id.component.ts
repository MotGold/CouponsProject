import { Component, OnInit } from '@angular/core';
import { Company } from 'src/app/models/company';
import { Router } from '@angular/router';
import { AdminControllerService } from 'src/app/services/admin-controller.service';

@Component({
  selector: 'app-company-by-id',
  templateUrl: './company-by-id.component.html',
  styleUrls: ['./company-by-id.component.css']
})
export class CompanyByIdComponent implements OnInit {

  company: Company = null;
  id: number = 0;
  email: string;
  password: string;
  option: boolean = false;


  constructor(private router: Router, private service: AdminControllerService) { }

  ngOnInit(): void {
  }

  public getCompany() {
    this.option = false;
    this.service.getOneCompany(this.id, sessionStorage.key(0)).subscribe(
      (company) => {
        if (company.name != "DoesNotExistException"){
          this.company = company;
          this.email = company.email;
          this.password=company.password;
        }
        else
          alert("no company exists with the entered id");
      },
      (err) => {
        alert("your session token has expired!");
        this.router.navigateByUrl("/login");
      }
    );
  }


  public delete1Company() {
    this.service.deleteCompany(this.id, sessionStorage.key(0)).subscribe(
      () => {
        alert("company deleted");
        this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
          this.router.navigate(['admin/oneCompany']);
        });
      },
      (err) => {
        if (this.company == null) {
          alert("no such ship exists!");
        } else {
          alert("your session token has expired!");
          this.router.navigateByUrl("/login");
        }
      }
    );
  }

  public updateCompany() {
    let aCompany = this.company;
    aCompany.password=this.password;
    aCompany.email=this.email;
    this.service.updateCompany(sessionStorage.key(0), aCompany).subscribe(
      num => {
        if (num == '3') {
          alert("company updated!");
          this.getCompany();
        }
        if (num == '2')
          alert("error! this company does not exist");
        if (num == '1')
          alert("error! the email you chosen already exists, please choose another one");
      },
      err => {
        alert("your session token has expired!");
        this.router.navigateByUrl("/login");
      }
    );
  }

  public editComp() {
    this.option = true;
  }
}

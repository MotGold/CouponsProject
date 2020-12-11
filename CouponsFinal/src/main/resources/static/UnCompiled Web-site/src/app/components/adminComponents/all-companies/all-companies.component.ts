import { Component, OnInit } from '@angular/core';
import { AdminControllerService } from 'src/app/services/admin-controller.service';
import { Company } from 'src/app/models/company';
import { Router } from '@angular/router';


@Component({
  selector: 'app-all-companies',
  templateUrl: './all-companies.component.html',
  styleUrls: ['./all-companies.component.css']
})
export class AllCompaniesComponent implements OnInit {

  companies: Company[];
  name: string;
  email: string;
  password: string;
  id: number;

  constructor(private service: AdminControllerService, private router: Router) { }

  ngOnInit(): void {
    this.service.recieveAllCompanies(sessionStorage.key(0)).subscribe(
      allcompanies => this.companies = allcompanies,
      err => {
        alert("your session token has expired!");
        this.router.navigateByUrl("/login");
      }
    );
  }

  public delete1Company(id: number) {
    this.service.deleteCompany(id, sessionStorage.key(0)).subscribe(
      () => {
        let index = -1;
        for (let i = 0; i < this.companies.length; i++) {
          if (this.companies[i].id == id) {
            index = i;
            break;
          }
        }
        if (index > -1)
          this.companies.splice(index, 1); // deletes one company from index position in companies array
      },
      err => {
        alert("your session token has expired!");
        this.router.navigateByUrl("/login");
      }
    );
  }

  public updateCompany() {
    if (this.email == "" || this.email==null)
      alert("please enter an email!")
    else if (this.password == "" || this.password==null)
      alert("please enter a password!")
    else {
    let aCompany = new Company(this.id, this.name, this.email, this.password);
    this.service.updateCompany(sessionStorage.key(0), aCompany).subscribe(
      num => {
        if (num == '3') {
          this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
            this.router.navigate(['admin/allCompanies']);
        }); 
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
  }

  public editComp(id: number, name: string, email: string, password: string) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
  }


}

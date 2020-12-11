import { Component, OnInit } from '@angular/core';
import { CompanyControllerService } from 'src/app/services/company-controller.service';
import { Router } from '@angular/router';
import { Company } from 'src/app/models/company';

@Component({
  selector: 'app-get-company-details',
  templateUrl: './get-company-details.component.html',
  styleUrls: ['./get-company-details.component.css']
})
export class GetCompanyDetailsComponent implements OnInit {

  company:Company;

  constructor(private service: CompanyControllerService, private router:Router) { }

  ngOnInit(): void {
    this.service.getOneCompany(sessionStorage.key(0)).subscribe(
      company => this.company = company,
      err => {
        alert("your session token has expired!");
        this.router.navigateByUrl("/login");
      }
    );
  }

}

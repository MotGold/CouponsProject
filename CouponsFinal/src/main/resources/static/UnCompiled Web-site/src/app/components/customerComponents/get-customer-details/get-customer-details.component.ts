import { Component, OnInit } from '@angular/core';
import { Customer } from 'src/app/models/customer';
import { CustomerControllerService } from 'src/app/services/customer-controller.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-get-customer-details',
  templateUrl: './get-customer-details.component.html',
  styleUrls: ['./get-customer-details.component.css']
})
export class GetCustomerDetailsComponent implements OnInit {

  customer:Customer;

  constructor(private service: CustomerControllerService, private router:Router) { }

  ngOnInit(): void {
    this.service.getCustomer(sessionStorage.key(0)).subscribe(
      customer => this.customer = customer,
      err => {
        alert("your session token has expired!");
        this.router.navigateByUrl("/login");
      }
    );
  }

}
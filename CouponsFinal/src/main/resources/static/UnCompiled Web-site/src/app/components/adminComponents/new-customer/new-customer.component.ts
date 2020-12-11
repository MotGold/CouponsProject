import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AdminControllerService } from 'src/app/services/admin-controller.service';
import { Customer } from 'src/app/models/customer';


@Component({
  selector: 'app-new-customer',
  templateUrl: './new-customer.component.html',
  styleUrls: ['./new-customer.component.css']
})
export class NewCustomerComponent implements OnInit {

  name: string;
  familyName: string;
  email: string;
  password: string;

  constructor(private router: Router, private service: AdminControllerService) { }

  ngOnInit(): void {
  }

  public addCustomer() {
    if (this.email == "" || this.email == null)
      alert("please enter an email!")
    else if (this.password == "" || this.password == null)
      alert("please enter a password!")
    else if (this.name == "" || this.name == null)
      alert("please enter a name!")
    else if (this.familyName == "" || this.familyName == null)
      alert("please enter a family name!")
    else {
      let aCustomer = new Customer(0, this.name, this.familyName, this.email, this.password, null)
      this.service.postCustomer(sessionStorage.key(0), aCustomer).subscribe(
        customer => {
          if (customer.email != "fail@error")
            alert("customer added! its new id is: " + customer.id);
          else
            alert("A customer already exists with the entered e-mail, please choose another e-mail adress");
        },
        err => {
          alert("your session token has expired!");
          this.router.navigateByUrl("/login");
        }
      );
    }
  }

}

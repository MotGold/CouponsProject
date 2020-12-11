import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AdminControllerService } from 'src/app/services/admin-controller.service';
import { Customer } from 'src/app/models/customer';


@Component({
  selector: 'app-customer-by-id',
  templateUrl: './customer-by-id.component.html',
  styleUrls: ['./customer-by-id.component.css']
})
export class CustomerByIdComponent implements OnInit {

  customer: Customer = null;
  id: number = 0;
  email: string;
  password: string;
  firstName: string;
  lastName:string;
  option: boolean = false;


  constructor(private router: Router, private service: AdminControllerService) { }


  ngOnInit(): void {
  }

  public getCustomer() {
    this.option = false;
    this.service.getOneCustomer(this.id, sessionStorage.key(0)).subscribe(
      (customer) => {
        if (customer.firstName != "DoesNotExistException"){
          this.customer = customer;
          this.firstName = customer.firstName;
          this.email = customer.email;
          this.password=customer.password;
          this.lastName=customer.lastName
        }
        else
          alert("no customer exists with the entered id");
      },
      (err) => {
        alert("your session token has expired!");
        this.router.navigateByUrl("/login");
      }
    );
  }


  public delete1Customer() {
    this.service.deleteCustomer(this.id, sessionStorage.key(0)).subscribe(
      () => {
        alert("Customer deleted!");
        this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
          this.router.navigate(['admin/oneCustomer']);
        });
      },
      (err) => {
        if (this.customer == null) {
          alert("no such Customer exists!");
        } else {
          alert("your session token has expired!");
          this.router.navigateByUrl("/login");
        }
      }
    );
  }

  public updateCustomer() {
    let aCustomer = this.customer;
    aCustomer.password=this.password;
    aCustomer.email=this.email;
    aCustomer.firstName=this.firstName;
    aCustomer.lastName=this.lastName;
    if (this.email == "" || this.email==null)
    alert("please enter an email!")
  else if (this.password == "" || this.password==null)
    alert("please enter a password!")
    else if (this.firstName == "" || this.firstName==null)
    alert("please enter a name!")
    else if (this.lastName == "" || this.lastName==null)
    alert("please enter a family name!")
  else {
    this.service.customerUpdate(sessionStorage.key(0), aCustomer).subscribe(
      num => {
        if (num == '3') {
          this.getCustomer();
        }
        if (num == '2')
          alert("error! this Customer does not exist");
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

  public editCustomer() {
    this.option = true;
  }
}

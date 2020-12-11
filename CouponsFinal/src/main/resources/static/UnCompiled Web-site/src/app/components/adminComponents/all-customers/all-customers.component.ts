import { Component, OnInit } from '@angular/core';
import { AdminControllerService } from 'src/app/services/admin-controller.service';
import { Router } from '@angular/router';
import { Customer } from 'src/app/models/customer';


@Component({
  selector: 'app-all-customers',
  templateUrl: './all-customers.component.html',
  styleUrls: ['./all-customers.component.css']
})
export class AllCustomersComponent implements OnInit {

  customers: Customer[];
  firstName: string;
  lastName:string;
  email: string;
  password: string;
  id: number;

  constructor(private service: AdminControllerService, private router: Router) { }

  ngOnInit(): void {
    this.service.recieveAllCustomers(sessionStorage.key(0)).subscribe(
      allCustomers => this.customers = allCustomers,
      err => {
        alert("your session token has expired!");
        this.router.navigateByUrl("/login");
      }
    );
  }

  public delete1Customer(id: number) {
    this.service.deleteCustomer(id, sessionStorage.key(0)).subscribe(
      () => {
        let index = -1;
        for (let i = 0; i < this.customers.length; i++) {
          if (this.customers[i].id == id) {
            index = i;
            break;
          }
        }
        if (index > -1)
          this.customers.splice(index, 1); // deletes one customer from index position in customers array
      },
      err => {
        alert("your session token has expired!");
        this.router.navigateByUrl("/login");
      }
    );
  }

  public updateBuyer() {
    if (this.email == "" || this.email==null)
    alert("please enter an email!")
  else if (this.password == "" || this.password==null)
    alert("please enter a password!")
    else if (this.firstName == "" || this.firstName==null)
    alert("please enter a name!")
    else if (this.lastName == "" || this.lastName==null)
    alert("please enter a family name!")
  else {
    let aCustomer = new Customer(this.id,this.firstName,this.lastName,this.email,this.password,null);
    this.service.customerUpdate(sessionStorage.key(0),aCustomer).subscribe(
      num =>{
        if (num == '3') {
          this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
            this.router.navigate(['admin/allCustomers']);
        }); 
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

  public editCustomer(id: number, name: string, lastName:string, email: string, password: string) {
    this.id = id;
    this.firstName = name;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
  }


}

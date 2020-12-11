import { Component, OnInit } from '@angular/core';
import { CustomerControllerService } from 'src/app/services/customer-controller.service';
import { Router } from '@angular/router';
import { Coupon } from 'src/app/models/coupon';

@Component({
  selector: 'app-all-customer-coupons',
  templateUrl: './all-customer-coupons.component.html',
  styleUrls: ['./all-customer-coupons.component.css']
})
export class AllCustomerCouponsComponent implements OnInit {

  coupons: Coupon[];
  ok: boolean = false;
  listOption: string;
  typeSelection: string = null;
  priceSelection: number = null;

  constructor(private service: CustomerControllerService, private router: Router) { }

  ngOnInit(): void {
  }

  public listTypes() {

    switch (this.listOption) {
      case '1': {
        this.service.recieveAllCustomerCoupons(sessionStorage.key(0)).subscribe(
          x => this.coupons = x,
          err => {
            alert("your session token has expired!");
            this.router.navigateByUrl("/login");
          }
        );
        this.ok = true;
        break;
      }
      case '2': {
        if (this.priceSelection != null) {
          this.service.recieveCheapCoupons(sessionStorage.key(0), this.priceSelection).subscribe(
            x => this.coupons = x,
            err => {
              alert("your session token has expired!");
              this.router.navigateByUrl("/login");
            }
          );
          this.ok = true;
        } else
          alert("please choose a price!");
        break;
      }
      case '3': {
        if (this.typeSelection != null) {
          this.service.recieveTypeCoupons(sessionStorage.key(0), this.typeSelection).subscribe(
            x => this.coupons = x,
            err => {
              alert("your session token has expired!");
              this.router.navigateByUrl("/login");
            }
          );
          this.ok = true;
        } else
          alert("please choose an option!");
        break;
      }
      default: {
        alert("please choose an option!");
        break;
      }
    }

  }



}

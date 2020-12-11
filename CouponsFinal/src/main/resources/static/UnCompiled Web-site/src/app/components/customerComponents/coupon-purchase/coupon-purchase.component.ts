import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CustomerControllerService } from 'src/app/services/customer-controller.service';
import { Coupon } from 'src/app/models/coupon';
import { Customer } from 'src/app/models/customer';

@Component({
  selector: 'app-coupon-purchase',
  templateUrl: './coupon-purchase.component.html',
  styleUrls: ['./coupon-purchase.component.css']
})
export class CouponPurchaseComponent implements OnInit {

  constructor(private service: CustomerControllerService, private router: Router) { }

  coupons: Coupon[];
  coupon: Coupon;
  customer: Customer;

  ngOnInit(): void {
    this.service.getCustomer(sessionStorage.key(0)).subscribe(
      customer => this.customer = customer,
      err => {
        alert("your session token has expired!");
        this.router.navigateByUrl("/login");
      }
    );

    this.service.recieveAllCoupon(sessionStorage.key(0)).subscribe(
      allCoupons => this.coupons = allCoupons,
      err => {
        alert("your session token has expired!");
        this.router.navigateByUrl("/login");
      }
    );
  }


  getCoupon(id: number) {
    this.service.findCoupon(id, sessionStorage.key(0)).subscribe(
      aCoupon => {
        this.coupon = aCoupon;
        this.service.purchaseCoupon(this.customer.email, sessionStorage.key(0), this.coupon).subscribe(
          num => {
            if (num == '1') {
              alert("coupon purchased");
              this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
                this.router.navigate(['customer/purchaseCoupon']);
              });
            }
            if (num == '2')
              alert("error! this coupon does not exist");
            if (num == '3')
              alert("error! the coupon you chosen is out of stock");
            if (num == '4')
              alert("error! the coupon you chosen is expired");
            if (num == '5')
              alert("error! the coupon you chosen is already in your possession");
          },
          err => {
            alert("your session token has expired!");
            this.router.navigateByUrl("/login");
          }
        );
      },
      err => {
        alert("your session token has expired!");
        this.router.navigateByUrl("/login");
      }
    );

  }
}

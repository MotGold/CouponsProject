import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CompanyControllerService } from 'src/app/services/company-controller.service';
import { Coupon } from 'src/app/models/coupon';
import { Company } from 'src/app/models/company';

@Component({
  selector: 'app-add-coupon',
  templateUrl: './add-coupon.component.html',
  styleUrls: ['./add-coupon.component.css']
})
export class AddCouponComponent implements OnInit {

  title: string;
  description: string;
  image: string;
  companyId: Company;
  amount: number;
  price: number;
  activation: Date;
  expiration: Date;
  couponType: number = null;

  constructor(private router: Router, private service: CompanyControllerService) { }

  ngOnInit(): void {
    this.service.getOneCompany(sessionStorage.key(0)).subscribe(
      company => this.companyId = company,
      err => {
        alert("your session token has expired!");
        this.router.navigateByUrl("/login");
      }
    );
  }

  public addCoupon() {
    if (this.title == "" || this.title == null)
      alert("please enter a title!")
    else if (this.description == "" || this.description == null)
      alert("please enter a description!")
    else if (this.image == "" || this.image == null)
      alert("please enter a name!")
    else if (this.amount == null)
      alert("please enter an amount!")
    else if (this.price == null)
      alert("please enter a price")
    else if (this.activation == null)
      alert("please enter an activation date!")
    else if (this.expiration == null)
      alert("please enter an expiration date!")
    else {
      let coup = new Coupon(0, this.title, this.description, this.image, this.companyId, this.amount, this.price, this.activation, this.expiration, null);
      this.service.postCoupon(sessionStorage.key(0), coup, this.couponType).subscribe(
        coupon => {
          if (coupon.title != "DuplicateException")
            alert("Coupon added! its new id is: " + coupon.id);
          else
            alert("A Coupon already exists with the entered title, please choose another name");
        },
        err => {
          alert("your session token has expired!");
          this.router.navigateByUrl("/login");
        }
      );
    }
  }

}

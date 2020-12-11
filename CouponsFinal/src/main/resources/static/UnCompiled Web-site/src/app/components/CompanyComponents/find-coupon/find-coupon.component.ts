import { Component, OnInit } from '@angular/core';
import { Coupon } from 'src/app/models/coupon';
import { Company } from 'src/app/models/company';
import { CompanyControllerService } from 'src/app/services/company-controller.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-find-coupon',
  templateUrl: './find-coupon.component.html',
  styleUrls: ['./find-coupon.component.css']
})
export class FindCouponComponent implements OnInit {

  coupon: Coupon;
  companyId: Company;
  id: number;
  title: string;
  description: string;
  image: string;
  amount: number;
  price: number;
  activation: Date;
  expiration: Date;
  couponType: string;
  option: boolean = false;


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

  public getCoupon() {
    this.option = false;
    this.service.getOneCoupon(this.id, sessionStorage.key(0)).subscribe(
      (coupon) => {
        if (coupon.title != "DoesNotExistException") {
          this.coupon = coupon;
          this.title=coupon.title;
          this.description=coupon.description;
          this.image=coupon.image;
          this.amount=coupon.amount;
          this.price=coupon.price;
          this.activation=coupon.startDate;
          this.expiration=coupon.endDate;
          this.couponType=coupon.type;

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


  public delete1Coupon() {
    this.service.deleteCoupon(this.id, sessionStorage.key(0)).subscribe(
      () => {
        alert("coupon deleted");
        this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
          this.router.navigate(['company/oneCoupon']);
        });
      },
      (err) => {
        if (this.coupon == null) {
          alert("no such coupon exists!");
        } else {
          alert("your session token has expired!");
          this.router.navigateByUrl("/login");
        }
      }
    );
  }

  public updateCoupon() {
    let aCoupon = this.coupon;
    aCoupon.title=this.title;
    aCoupon.amount=this.amount;
    aCoupon.description=this.description;
    aCoupon.endDate=this.expiration;
    aCoupon.image=this.image;
    aCoupon.price=this.price;
    aCoupon.startDate=this.activation;
    aCoupon.type=this.couponType;
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
    this.service.updateCoupon(sessionStorage.key(0), aCoupon, this.couponType).subscribe(
      num => {
        if (num == '1') {
          alert("coupon updated!");
          this.getCoupon();
        }
        if (num == '2')
          alert("error! this coupon does not exist");
      },
      err => {
        alert("your session token has expired!");
        this.router.navigateByUrl("/login");
      }
    );
    }
  }

  public editCoupon() {
    this.option = true;
  }
}

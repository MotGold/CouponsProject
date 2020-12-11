import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CompanyControllerService } from 'src/app/services/company-controller.service';
import { Coupon } from 'src/app/models/coupon';
import { Company } from 'src/app/models/company';

@Component({
  selector: 'app-all-company-coupons',
  templateUrl: './all-company-coupons.component.html',
  styleUrls: ['./all-company-coupons.component.css']
})
export class AllCompanyCouponsComponent implements OnInit {

  coupons: Coupon[];
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
  ok: boolean = false;
  listOption: string;
  typeSelection: string = null;
  priceSelection: number = null;

  constructor(private service: CompanyControllerService, private router: Router) { }

  ngOnInit(): void {
    this.service.getOneCompany(sessionStorage.key(0)).subscribe(
      company => this.companyId = company,
      err => {
        alert("your session token has expired!");
        this.router.navigateByUrl("/login");
      }
    );
  }

  public listTypes() {

    switch (this.listOption) {
      case '1': {
        this.service.recieveAllCoupons(sessionStorage.key(0)).subscribe(
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

  public delete1Coupon(id: number) {
    this.service.deleteCoupon(id, sessionStorage.key(0)).subscribe(
      () => {
        let index = -1;
        for (let i = 0; i < this.coupons.length; i++) {
          if (this.coupons[i].id == id) {
            index = i;
            break;
          }
        }
        if (index > -1)
          this.coupons.splice(index, 1); // deletes one coupon from index position in coupons array
      },
      err => {
        alert("your session token has expired!");
        this.router.navigateByUrl("/login");
      }
    );
  }

  public updateCoupon() {
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
      let cop = new Coupon(this.id, this.title, this.description, this.image, this.companyId, this.amount, this.price, this.activation, this.expiration, null);
      this.service.updateCoupon(sessionStorage.key(0), cop, this.couponType).subscribe(
        num => {
          if (num == '1') {
            alert("coupon updated!");
            this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
              this.router.navigate(['company/companyCoupons']);
            });
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

  public editCoupon(id: number, title: string, description: string, image: string, amount: number, price: number, activation: Date, expiration: Date, couponType: string) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.image = image;
    this.amount = amount;
    this.price = price;
    this.activation = activation;
    this.expiration = expiration;
    this.couponType = couponType;
  }

}

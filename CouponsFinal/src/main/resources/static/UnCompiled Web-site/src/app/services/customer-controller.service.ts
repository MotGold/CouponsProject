import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Customer } from '../models/customer';
import { Coupon } from '../models/coupon';

@Injectable({
  providedIn: 'root'
})
export class CustomerControllerService {

  constructor(private client: HttpClient) { }

  public getCustomer(token: string) {
    return this.client.get<Customer>("http://localhost:8080/customerManager/customerInfo/" + token);
  }

  public purchaseCoupon(email: string, token: string, coupon: Coupon) {
    return this.client.post("http://localhost:8080/customerManager/purchaseCoupon/" + email + "/" + token, coupon, { responseType: 'text' });
  }

  public recieveAllCoupon(token: string) {
    return this.client.get<Coupon[]>("http://localhost:8080/customerManager/allCoupons/" + token);
  }

  public findCoupon(id: number, token: string) {
    return this.client.get<Coupon>("http://localhost:8080/customerManager/findCoupon/" + id + "/" + token);
  }

  public recieveAllCustomerCoupons(token: string) {
    return this.client.get<Coupon[]>("http://localhost:8080/customerManager/clientCoupons/" + token);
  }

  public recieveTypeCoupons(token: string, type: string) {
    return this.client.get<Coupon[]>("http://localhost:8080/customerManager/typeCoupons/" + type + "/" + token);
  }

  public recieveCheapCoupons(token: string, maxPrice: number) {
    return this.client.get<Coupon[]>("http://localhost:8080/customerManager/cheapCoupons/" + maxPrice + "/" + token);
  }
}

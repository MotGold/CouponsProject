import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Company } from '../models/company';
import { Coupon } from '../models/coupon';

@Injectable({
  providedIn: 'root'
})
export class CompanyControllerService {

  constructor(private client: HttpClient) { }

  public getOneCompany(token: string) {
    return this.client.get<Company>("http://localhost:8080/companyManager/companyInfo/" + token);
  }

  public recieveAllCoupons(token: string) {
    return this.client.get<Coupon[]>("http://localhost:8080/companyManager/allCoupons/" + token);
  }

  public recieveTypeCoupons(token: string, type: string) {
    return this.client.get<Coupon[]>("http://localhost:8080/companyManager/typeCoupons/" + type + "/" + token);
  }

  public recieveCheapCoupons(token: string, maxPrice: number) {
    return this.client.get<Coupon[]>("http://localhost:8080/companyManager/cheapCoupons/" + maxPrice + "/" + token);
  }

  public postCoupon(token: string, coupon: Coupon, couponType: number) {
    return this.client.post<Coupon>("http://localhost:8080/companyManager/addCoupon/" + couponType + "/" + token, coupon);
  }

  public deleteCoupon(id: number, token: string) {
    return this.client.delete("http://localhost:8080/companyManager/allCoupons/delete/" + id + "/" + token);
  }

  public updateCoupon(token: string, coupon: Coupon, couponType: string) {
    return this.client.post("http://localhost:8080/companyManager/updateCoupon/" + couponType + "/" + token, coupon, { responseType: 'text' });
  }

  public getOneCoupon(id:number, token:string) {
    return this.client.get<Coupon>("http://localhost:8080/companyManager/allCoupons/"+id+"/"+token);
  }
}

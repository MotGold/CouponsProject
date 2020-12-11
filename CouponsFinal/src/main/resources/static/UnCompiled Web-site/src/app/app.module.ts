import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { LoginComponent } from './components/login/login.component';
import { AdminLayoutComponent } from './components/admin-layout/admin-layout.component';
import {HttpClientModule} from '@angular/common/http';
import { MainPageComponent } from './components/main-page/main-page.component';
import { CompanyLayoutComponent } from './components/company-layout/company-layout.component';
import { CustomerLayoutComponent } from './components/customer-layout/customer-layout.component';
import { AddCompanyComponent } from './components/adminComponents/add-company/add-company.component';
import { AllCompaniesComponent } from './components/adminComponents/all-companies/all-companies.component';
import { CompanyByIdComponent } from './components/adminComponents/company-by-id/company-by-id.component';
import { NewCustomerComponent } from './components/adminComponents/new-customer/new-customer.component';
import { AllCustomersComponent } from './components/adminComponents/all-customers/all-customers.component';
import { CustomerByIdComponent } from './components/adminComponents/customer-by-id/customer-by-id.component';
import { AddCouponComponent } from './components/CompanyComponents/add-coupon/add-coupon.component';
import { FindCouponComponent } from './components/CompanyComponents/find-coupon/find-coupon.component';
import { GetCompanyDetailsComponent } from './components/CompanyComponents/get-company-details/get-company-details.component';
import { AllCompanyCouponsComponent } from './components/CompanyComponents/all-company-coupons/all-company-coupons.component';
import { CouponPurchaseComponent } from './components/customerComponents/coupon-purchase/coupon-purchase.component';
import { GetCustomerDetailsComponent } from './components/customerComponents/get-customer-details/get-customer-details.component';
import { AllCustomerCouponsComponent } from './components/customerComponents/all-customer-coupons/all-customer-coupons.component';


@NgModule({
  declarations: [
    LoginComponent,
    AdminLayoutComponent,
    MainPageComponent,
    CompanyLayoutComponent,
    CustomerLayoutComponent,
    AddCompanyComponent,
    AllCompaniesComponent,
    CompanyByIdComponent,
    NewCustomerComponent,
    AllCustomersComponent,
    CustomerByIdComponent,
    AddCouponComponent,
    FindCouponComponent,
    GetCompanyDetailsComponent,
    AllCompanyCouponsComponent,
    CouponPurchaseComponent,
    GetCustomerDetailsComponent,
    AllCustomerCouponsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [MainPageComponent]
})
export class AppModule { }

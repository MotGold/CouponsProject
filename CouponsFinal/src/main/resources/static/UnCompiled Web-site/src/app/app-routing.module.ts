import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { AdminLayoutComponent } from './components/admin-layout/admin-layout.component';
import { CompanyLayoutComponent } from './components/company-layout/company-layout.component';
import { CustomerLayoutComponent } from './components/customer-layout/customer-layout.component';
import { AddCompanyComponent } from './components/adminComponents/add-company/add-company.component';
import { AllCompaniesComponent } from './components/adminComponents/all-companies/all-companies.component';
import { CompanyByIdComponent } from './components/adminComponents/company-by-id/company-by-id.component';
import { NewCustomerComponent } from './components/adminComponents/new-customer/new-customer.component';
import { AllCustomersComponent } from './components/adminComponents/all-customers/all-customers.component';
import { CustomerByIdComponent } from './components/adminComponents/customer-by-id/customer-by-id.component';
import { AddCouponComponent } from './components/CompanyComponents/add-coupon/add-coupon.component';
import { AllCompanyCouponsComponent } from './components/CompanyComponents/all-company-coupons/all-company-coupons.component';
import { FindCouponComponent } from './components/CompanyComponents/find-coupon/find-coupon.component';
import { GetCompanyDetailsComponent } from './components/CompanyComponents/get-company-details/get-company-details.component';
import { AllCustomerCouponsComponent } from './components/customerComponents/all-customer-coupons/all-customer-coupons.component';
import { GetCustomerDetailsComponent } from './components/customerComponents/get-customer-details/get-customer-details.component';
import { CouponPurchaseComponent } from './components/customerComponents/coupon-purchase/coupon-purchase.component';


const routes: Routes = [
  { path: "login", component: LoginComponent },
  {
    path: "admin", component: AdminLayoutComponent,
    children: [
      { path: "newCompany", component: AddCompanyComponent },
      { path: "allCompanies", component: AllCompaniesComponent },
      { path: "oneCompany", component: CompanyByIdComponent },
      { path: "newCustomer", component: NewCustomerComponent },
      { path: "allCustomers", component: AllCustomersComponent },
      { path: "oneCustomer", component: CustomerByIdComponent }
    ]
  },
  {
    path: "company", component: CompanyLayoutComponent,
    children: [
      { path: "newCoupon", component: AddCouponComponent },
      { path: "companyCoupons", component: AllCompanyCouponsComponent },
      { path: "oneCoupon", component: FindCouponComponent },
      { path: "theCompany", component: GetCompanyDetailsComponent }
    ]
  },
  {
    path: "customer", component: CustomerLayoutComponent,
    children: [
      { path: "purchaseCoupon", component: CouponPurchaseComponent },
      { path: "customerCoupons", component: AllCustomerCouponsComponent },
      { path: "theCustomer", component: GetCustomerDetailsComponent }
    ]
  },
  { path: "", redirectTo: "login", pathMatch: "full" },
  { path: "**", component: LoginComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

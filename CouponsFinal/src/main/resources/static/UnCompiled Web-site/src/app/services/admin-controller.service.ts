import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Company } from '../models/company';
import { Customer } from '../models/customer';

@Injectable({
  providedIn: 'root'
})
export class AdminControllerService {

  constructor(private client: HttpClient) { }

  public recieveAllCompanies(token:string) {
    return this.client.get<Company[]>("http://localhost:8080/adminManager/companies/"+token);
  }

  public deleteCompany(id:number, token:string){
    return this.client.delete("http://localhost:8080/adminManager/companies/delete/"+id+"/"+token);
  }

  public postBean(token:string, company:Company) {
    return this.client.post<Company>("http://localhost:8080/adminManager/addCompany/"+token, company);
  }

  public getOneCompany(id:number, token:string) {
    return this.client.get<Company>("http://localhost:8080/adminManager/companies/"+id+"/"+token);
  }

  public updateCompany(token:string, company:Company) {
    return this.client.post("http://localhost:8080/adminManager/updateCompany/"+token, company, {responseType:'text'});
  }

//------------------------------------------------------------------------------------------------------------------------------

  public postCustomer(token:string, customer:Customer) {
    return this.client.post<Customer>("http://localhost:8080/adminManager/addCustomer/"+token, customer);
  }

  public recieveAllCustomers(token:string) {
    return this.client.get<Customer[]>("http://localhost:8080/adminManager/customers/"+token);
  }

  public deleteCustomer(id:number, token:string){
    return this.client.delete("http://localhost:8080/adminManager/customers/delete/"+id+"/"+token);
  }

  public customerUpdate(token:string, buyer:Customer) {
    return this.client.post("http://localhost:8080/adminManager/updateCustomer/"+token, buyer, {responseType:'text'});
  }

  public getOneCustomer(id:number, token:string) {
    return this.client.get<Customer>("http://localhost:8080/adminManager/customers/"+id+"/"+token);
  }

}

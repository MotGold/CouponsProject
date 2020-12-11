import { Company } from './company';

export class Coupon {

    constructor(public id: number,
        public title: string,
        public description: string,
        public image: string,
        public companyId: Company,
        public amount: number,
        public price: number,
        public startDate: Date,
        public endDate: Date,
        public type: string) { }
}

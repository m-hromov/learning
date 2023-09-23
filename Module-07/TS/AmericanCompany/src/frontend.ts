import { Employee } from '../../EuropeCompany/src/employee'

export class Frontend implements Employee{

    constructor(private name : string, private  project : string) {
    }

    get getCurrentProject(): string {
        return this.project;
    }

    get getName(): string {
        return this.name;
    }
}
export class Employee {

    constructor(private name : string) {}

    getCurrentProject() {
        return "EuropeCompany"
    }

    get getName() {
        return this.name
    }
}
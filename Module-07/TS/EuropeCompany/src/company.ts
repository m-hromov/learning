import { Employee } from "./Employee"

class Company {

    constructor(private employees : Employee[]) {}

    getCurrentProject() {
        return "EuropeCompany"
    }

    public set add(employee : Employee) {
        this.employees.push(employee)
    }
}
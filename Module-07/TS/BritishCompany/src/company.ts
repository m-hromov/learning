import { Employee } from '../../EuropeCompany/src/employee'

export class Company<LType> {
    private employees : Employee[] = []

    constructor(private location : LType) {
    }

    public add(...employees : Employee[]) {
        for (const employee of employees) {
            this.employees.push(employee)
        }
    }

    public get getProjectList() : string[] {
        return this.employees.map(e => e.getCurrentProject)
    }

    public get getNameList() : string[] {
        return this.employees.map(e => e.getName)
    }
}
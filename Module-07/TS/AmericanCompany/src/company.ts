import { IEmployee } from "./IEmployee"

export class Company {
    private employees : IEmployee[] = []

    public add(...employees : IEmployee[]) {
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
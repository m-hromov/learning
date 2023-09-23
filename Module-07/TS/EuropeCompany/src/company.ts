import { Employee } from "./employee"

export class Company {
    private employees : Employee[] = []

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
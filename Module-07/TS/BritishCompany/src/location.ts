import { Employee } from "../../EuropeCompany/src/employee"


let LocalStorage = require('node-localstorage').LocalStorage;
const localStorage = new LocalStorage('./scratch');

export class ArrayLocation implements ILocation{
    private employees : Employee[] = []

    public addPerson(...employees: Employee[]): void {
        for (const employee of employees) {
            this.employees.push(employee)
        }
    }

    public getCount(): number {
        return this.employees.length;
    }

    public getPerson(id: number): Employee {
        return this.employees[id];
    }
}

export class LocalStorageLocation implements ILocation{
    private employeesKey : string = 'empkey123'

    public addPerson(...employees: Employee[]): void {
        let arr = []
        for (const employee of employees) {
            arr.push(employee)
        }
        localStorage.setItem(this.employeesKey, JSON.stringify(arr))
    }

    public getCount(): number {
        return JSON.parse(localStorage.getItem(this.employeesKey)).length;
    }

    public getPerson(id: number): Employee {
        return JSON.parse(localStorage.getItem(this.employeesKey))[id];
    }
}

export interface ILocation {
    addPerson(...employees : Employee[]) : void

    getPerson(id : number) : Employee

    getCount() : number
}
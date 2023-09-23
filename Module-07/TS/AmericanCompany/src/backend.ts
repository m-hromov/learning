<<<<<<< HEAD
import { IEmployee } from "./IEmployee"

export class Backend implements IEmployee{
=======
import { Employee } from "./employee"

export class Backend implements Employee{
>>>>>>> master

    constructor(private name : string, private  project : string) {
    }

    get getCurrentProject(): string {
        return this.project;
    }

    get getName(): string {
        return this.name;
    }
}
import { IEmployee } from "./IEmployee"

export class Frontend implements IEmployee{

    constructor(private name : string, private  project : string) {
    }

    get getCurrentProject(): string {
        return this.project;
    }

    get getName(): string {
        return this.name;
    }
}
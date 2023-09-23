export class Employee {
    constructor(private name : string, private project : string) {}

    public get getCurrentProject() {
        return this.project
    }

    public get getName() {
        return this.name
    }
}
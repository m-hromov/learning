import { Employee } from "../../EuropeCompany/src/employee"
import { Company } from "./company"
import { ArrayLocation, LocalStorageLocation } from "./location"

const e1 = new Employee("nameFront1", "projFront1")
const e2 = new Employee("nameFront2", "projFront2")
const e3 = new Employee("nameFront3", "projFront3")

const l1 = new ArrayLocation()
l1.addPerson(e1)
console.log(`Location1 persons: ${l1.getPerson(0)}`)
console.log(`Location1 count: ${l1.getCount()}`)

const l2 = new LocalStorageLocation()
l2.addPerson(e2, e3)
console.log(`Location2 persons: ${l2.getPerson(0)}   ${l2.getPerson(1)}`)
console.log(`Location2 count: ${l2.getCount()}`)

const company1 = new Company<ArrayLocation>(l1)
const company2 = new Company<LocalStorageLocation>(l2)
company1.add(e1, e2)
company2.add(e1, e3)

console.log(`Names company1: ${company1.getNameList}`)
console.log(`Projects company1: ${company1.getProjectList}`)

console.log(`Names company2: ${company2.getNameList}`)
console.log(`Projects company2: ${company2.getProjectList}`)
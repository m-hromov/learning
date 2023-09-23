import { Frontend } from "./frontend"
import { Backend } from "./backend"
import { Company } from "./company"

const f1 = new Frontend("nameFront1", "projFront1")
const f2 = new Frontend("nameFront2", "projFront2")
const f3 = new Frontend("nameFront3", "projFront3")

const b1 = new Backend("nameBack1", "projBack1")
const b2 = new Backend("nameBack2", "projBack2")
const b3 = new Backend("nameBack3", "projBack3")

const company = new Company()
company.add(f1, f2, f3, b1, b2, b3)

console.log(`Names: ${company.getNameList}`)
console.log(`Projects: ${company.getProjectList}`)
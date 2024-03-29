"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var employee_1 = require("../../EuropeCompany/src/employee");
var company_1 = require("./company");
var location_1 = require("./location");
var e1 = new employee_1.Employee("nameFront1", "projFront1");
var e2 = new employee_1.Employee("nameFront2", "projFront2");
var e3 = new employee_1.Employee("nameFront3", "projFront3");
var l1 = new location_1.ArrayLocation();
l1.addPerson(e1);
console.log("Location1 persons: ".concat(l1.getPerson(0)));
console.log("Location1 count: ".concat(l1.getCount()));
var l2 = new location_1.LocalStorageLocation();
l2.addPerson(e2, e3);
console.log("Location2 persons: ".concat(l2.getPerson(0), "   ").concat(l2.getPerson(1)));
console.log("Location2 count: ".concat(l2.getCount()));
var company1 = new company_1.Company(l1);
var company2 = new company_1.Company(l2);
company1.add(e1, e2);
company2.add(e1, e3);
console.log("Names company1: ".concat(company1.getNameList));
console.log("Projects company1: ".concat(company1.getProjectList));
console.log("Names company2: ".concat(company2.getNameList));
console.log("Projects company2: ".concat(company2.getProjectList));

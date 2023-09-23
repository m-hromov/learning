"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.LocalStorageLocation = exports.ArrayLocation = void 0;
var LocalStorage = require('node-localstorage').LocalStorage;
var localStorage = new LocalStorage('./scratch');
var ArrayLocation = /** @class */ (function () {
    function ArrayLocation() {
        this.employees = [];
    }
    ArrayLocation.prototype.addPerson = function () {
        var employees = [];
        for (var _i = 0; _i < arguments.length; _i++) {
            employees[_i] = arguments[_i];
        }
        for (var _a = 0, employees_1 = employees; _a < employees_1.length; _a++) {
            var employee = employees_1[_a];
            this.employees.push(employee);
        }
    };
    ArrayLocation.prototype.getCount = function () {
        return this.employees.length;
    };
    ArrayLocation.prototype.getPerson = function (id) {
        return this.employees[id];
    };
    return ArrayLocation;
}());
exports.ArrayLocation = ArrayLocation;
var LocalStorageLocation = /** @class */ (function () {
    function LocalStorageLocation() {
        this.employeesKey = 'empkey123';
    }
    LocalStorageLocation.prototype.addPerson = function () {
        var employees = [];
        for (var _i = 0; _i < arguments.length; _i++) {
            employees[_i] = arguments[_i];
        }
        var arr = [];
        for (var _a = 0, employees_2 = employees; _a < employees_2.length; _a++) {
            var employee = employees_2[_a];
            arr.push(employee);
        }
        localStorage.setItem(this.employeesKey, JSON.stringify(arr));
    };
    LocalStorageLocation.prototype.getCount = function () {
        return JSON.parse(localStorage.getItem(this.employeesKey)).length;
    };
    LocalStorageLocation.prototype.getPerson = function (id) {
        return JSON.parse(localStorage.getItem(this.employeesKey))[id];
    };
    return LocalStorageLocation;
}());
exports.LocalStorageLocation = LocalStorageLocation;

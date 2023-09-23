"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Employee = void 0;
var Employee = /** @class */ (function () {
    function Employee(name, project) {
        this.name = name;
        this.project = project;
    }
    Object.defineProperty(Employee.prototype, "getCurrentProject", {
        get: function () {
            return this.project;
        },
        enumerable: false,
        configurable: true
    });
    Object.defineProperty(Employee.prototype, "getName", {
        get: function () {
            return this.name;
        },
        enumerable: false,
        configurable: true
    });
    return Employee;
}());
exports.Employee = Employee;

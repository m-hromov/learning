"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Company = void 0;
var Company = /** @class */ (function () {
    function Company(location) {
        this.location = location;
        this.employees = [];
    }
    Company.prototype.add = function () {
        var employees = [];
        for (var _i = 0; _i < arguments.length; _i++) {
            employees[_i] = arguments[_i];
        }
        for (var _a = 0, employees_1 = employees; _a < employees_1.length; _a++) {
            var employee = employees_1[_a];
            this.employees.push(employee);
        }
    };
    Object.defineProperty(Company.prototype, "getProjectList", {
        get: function () {
            return this.employees.map(function (e) { return e.getCurrentProject; });
        },
        enumerable: false,
        configurable: true
    });
    Object.defineProperty(Company.prototype, "getNameList", {
        get: function () {
            return this.employees.map(function (e) { return e.getName; });
        },
        enumerable: false,
        configurable: true
    });
    return Company;
}());
exports.Company = Company;

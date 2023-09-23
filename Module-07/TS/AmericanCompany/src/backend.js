"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Backend = void 0;
var Backend = /** @class */ (function () {
    function Backend(name, project) {
        this.name = name;
        this.project = project;
    }
    Object.defineProperty(Backend.prototype, "getCurrentProject", {
        get: function () {
            return this.project;
        },
        enumerable: false,
        configurable: true
    });
    Object.defineProperty(Backend.prototype, "getName", {
        get: function () {
            return this.name;
        },
        enumerable: false,
        configurable: true
    });
    return Backend;
}());
exports.Backend = Backend;

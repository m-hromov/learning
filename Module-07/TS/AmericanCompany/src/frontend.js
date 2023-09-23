"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Frontend = void 0;
var Frontend = /** @class */ (function () {
    function Frontend(name, project) {
        this.name = name;
        this.project = project;
    }
    Object.defineProperty(Frontend.prototype, "getCurrentProject", {
        get: function () {
            return this.project;
        },
        enumerable: false,
        configurable: true
    });
    Object.defineProperty(Frontend.prototype, "getName", {
        get: function () {
            return this.name;
        },
        enumerable: false,
        configurable: true
    });
    return Frontend;
}());
exports.Frontend = Frontend;

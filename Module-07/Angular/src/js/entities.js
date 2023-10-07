
class Certificate {
    cpName
    decription
    expiry
    price
    imgPath
    createdAt

    constructor(cpName, decription, expiry, price, imgPath, createdAt) {
        this.cpName = cpName;
        this.decription = decription;
        this.expiry = expiry;
        this.price = price;
        this.imgPath = imgPath;
        this.createdAt = createdAt;
    }


    get cpName() {
        return this.cpName;
    }

    set cpName(value) {
        this.cpName = value;
    }

    get decription() {
        return this.decription;
    }

    set decription(value) {
        this.decription = value;
    }

    get expiry() {
        return this.expiry;
    }

    set expiry(value) {
        this.expiry = value;
    }

    get price() {
        return this.price;
    }

    set price(value) {
        this.price = value;
    }

    get imgPath() {
        return this.imgPath;
    }

    set imgPath(value) {
        this.imgPath = value;
    }

    get createdAt() {
        return this.createdAt;
    }

    set createdAt(value) {
        this.createdAt = value;
    }
}
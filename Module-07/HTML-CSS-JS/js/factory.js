function createCouponItem(cpName, decription, expiry, price, imgPath) {
    let col1 = createCol(cpName, null)
    let col2 = createCol('favorite', ' ml-auto material-icons')
    let col3 = createCol(decription, null)
    let col4 = createCol(expiry, null)
    let col5 = createCol(price, null)
    let btn = Object.assign(
        document.createElement('button'),
        {
            className : "btn btn-gray col ml-auto",
            innerHTML : "Add to cart"
        }
    )

    let row1 = createRow(null, null)
    row1.appendChild(col1)
    row1.appendChild(col2)

    let row2 = createRow(null, null)
    row2.appendChild(col3)
    row2.appendChild(col4)

    let row3 = createRow(null, null)
    row3.appendChild(col5)
    row3.appendChild(btn)
    let couponDescription = Object.assign(
        document.createElement('div'),
        {
            className : 'coupon-description-box'
        }
    )
    couponDescription.appendChild(row1)
    couponDescription.appendChild(row2)
    couponDescription.appendChild(row3)

    let img = Object.assign(
        document.createElement('img'),
        {
            className : 'row',
            src : imgPath
        }
    )

    let div = Object.assign(
        document.createElement('div'),
        { className : 'coupon-item-box'}
    )
    div.appendChild(img)
    div.appendChild(couponDescription)

    return div
}

function createRow(content, classes) {
    return Object.assign(
        document.createElement('div'),
        {
            className : 'row ' + classes,
            innerHTML : content
        })
}

function createCol(content, classes) {
    return Object.assign(
        document.createElement('div'),
        {
            className : 'col ' + classes,
            innerHTML : content
        })
}
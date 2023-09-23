const content = document.querySelector(".container-coupon");
const loading = document.querySelector(".loading");
const certificates = JSON.parse(localStorage.certificates)
let counter = 20

function scroller() {
        for (let i = counter; i < certificates.length && i < (counter + 20); i++) {
            let cert = certificates[i]
            content.appendChild(createCouponItem(cert.cpName, cert.decription, cert.expiry, cert.price, cert.imgPath))
        }
        counter += 20
        loading.style.display = "none"
}

let callback = _.throttle(scroller, 3000)

function scrollerThrottle() {
    const endOfPage = window.innerHeight + window.scrollY >= document.body.offsetHeight
    if (endOfPage) {
        loading.style.display = "block"
        loading.scrollIntoView()
        callback()
    }
}

window.addEventListener('scroll', scrollerThrottle)

for (let i = 0; i < counter; i++) {
    let cert = certificates[i]
    content.appendChild(createCouponItem(cert.cpName, cert.decription, cert.expiry, cert.price, cert.imgPath))
}
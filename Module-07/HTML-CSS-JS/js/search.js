const searchInput = document.querySelector(".search-box");


function search(str) {
    if (!str) {
        for (let i = 0; i < counter; i++) {
            let cert = certificates[i]
            content.appendChild(createCouponItem(cert.cpName, cert.decription, cert.expiry, cert.price, cert.imgPath))
        }
        return
    }
    content.innerHTML = ''
    let sorted = []
    for (const cert of certificates) {
        if (cert.cpName.toLowerCase().includes(str.toLowerCase()) || cert.decription.toLowerCase().includes(str.toLowerCase())
        ) {
            sorted.push(cert)
        }
    }
    for (const cert of sorted) {
        content.appendChild(createCouponItem(cert.cpName, cert.decription, cert.expiry, cert.price, cert.imgPath))
    }
}

searchInput.addEventListener('input', onInput);

function onInput(){
    let duration = 1000;
    clearTimeout(searchInput._timer);
    searchInput._timer = setTimeout(()=>{
        search(searchInput.value);
    }, duration);
}


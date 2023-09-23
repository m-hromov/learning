const LAST_POSITION_KEY = 'last-scroll-position'
const lastScrollBtn = document.getElementById("lastScrollBtn")

function updateLastScrollPosition() {
    lastScrollBtn.style.display = "none"
    sessionStorage.setItem(LAST_POSITION_KEY, document.documentElement.scrollTop.toString())
}


lastScrollBtn.addEventListener("click", function () {
    window.scrollTo({ top: sessionStorage.getItem(LAST_POSITION_KEY) || 0, behavior: 'smooth' });
    lastScrollBtn.style.display = "none"
})

window.addEventListener("scroll", updateLastScrollPosition)

window.addEventListener("load", function () {
    const lastScrollPos = sessionStorage.getItem(LAST_POSITION_KEY) || 0;
    if (lastScrollPos > 0) {
        lastScrollBtn.style.display = "block"
    }
})

if ('scrollRestoration' in history) {
    history.scrollRestoration = 'manual';
}
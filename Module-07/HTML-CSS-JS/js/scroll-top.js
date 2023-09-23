const SHOW_BTN_POSITION = 200
const scrollBtn = document.getElementById("topBtn")

window.onscroll = function() {scrollFunction()}

function scrollFunction() {
    if (document.body.scrollTop > SHOW_BTN_POSITION || document.documentElement.scrollTop > SHOW_BTN_POSITION) {
        scrollBtn.style.display = "block"
    } else {
        scrollBtn.style.display = "none"
    }
}

function topFunction() {
    window.scrollTo({ top: 0});
}

scrollBtn.onclick = function () {topFunction()}
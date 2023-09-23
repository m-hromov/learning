const LAST_POSITION_KEY = 'last-scroll-position'

function updateLastScrollPosition(evt) {
    sessionStorage.setItem(LAST_POSITION_KEY, evt.target.scrollTop.toString())
}

window.onscroll = function (evt) {updateLastScrollPosition(evt)}

window.onload = function () {
    const lastPosition = sessionStorage.getItem(LAST_POSITION_KEY) || 0
    window.scrollTo(0, lastPosition)
}
import Vue from 'vue'
import axios from 'axios'

window.axios = axios
window.axios.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest'

Vue.component('google-map', require('./components/GoogleMap').default)

new Vue({
    el: '#app'
})

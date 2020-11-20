<template>
    <section class="map-container">
        <h1>Hello, This is the Map Component!</h1>
        <br>
        <div id="map" ref="mapContainer"></div>
    </section>
</template>

<script>
    export default {
        name: "GoogleMap",
        data() {
            return {
              markers: [],
              map: null
            }
          },
        mounted() {
            window.gmapsCallback = () => this.initMap()
            this.gmapsInit()
        },
        methods: {
            gmapsInit: function() {
              const apiKey = 'AIzaSyBWHqxFV3h5m4DuSSmQgHm3QO5CDjEWLjE';
              const callbackName = 'gmapsCallback';

              const script = document.createElement('script');
              script.async = true;
              script.defer = true;
              script.src = `https://maps.googleapis.com/maps/api/js?key=${apiKey}&callback=${callbackName}&libraries=geometry`;
              document.querySelector('head').appendChild(script);
            },
            initMap: function() {
              this.map = new google.maps.Map( this.$refs.mapContainer, {
                center: {
                  lat: 51.513329,
                  lng: -0.088950
                },
                zoom: 14
              });
            }
        }
    }
</script>

<style scoped>
    #map {
        height: 100%;
    }

    .map-container {
        height: 100%;
    }

</style>

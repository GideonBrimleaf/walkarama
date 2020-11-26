<template>
    <section class="map-container">
        <h1>Create A New Walk</h1>
        <br>
        <div id="map" ref="mapContainer"></div>
        <h3 v-if="distanceInMetres">The distance between the two points is {{this.distanceInMetres}}m</h3>
        <form v-if="distanceInMetres">
          <label for="walk-name">Give your walk a name:</label>
          <input id="walk-name" type="text" v-model="form.name">
          <input type="submit" value="SMASH THAT BUTTON!">
        </form>
        <h3 v-if="!distanceInMetres">Click on the map to create a walk</h3>
    </section>
</template>

<script>
    import Form from '../form'
    export default {
        name: "GoogleMap",
        data() {
            return {
              markers: [],
              map: null,
              form: new Form({name: '', distanceInMetres: 0})
            }
        },
        computed: {
          distanceInMetres: function() {
            if (this.markers.length === 2) {

            const result = parseFloat(google.maps.geometry.spherical.computeDistanceBetween(
              this.markers[0].getPosition(),
              this.markers[1].getPosition()
            ).toFixed(2))

            this.form.distanceInMetres = result

            return result
            }
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

              this.map.addListener('click', (event) => this.addMarker(event))
            },
            addMarker: function(event) {
              const position = event.latLng.toJSON()

              if (this.markers.length < 2) {
                const marker = new google.maps.Marker({
                  position: {
                    lat: position.lat,
                    lng: position.lng
                  },
                  draggable: true,
                  map: this.map,
                  title: `I'm Mary Poppins Y'All!`,
                  id: position.lat + '' + position.lng
                })

                this.markers.push(marker)

                marker.addListener('click', () => {
                  this.markers = this.markers.filter(mapMarker => mapMarker.id === marker.id)
                  marker.setMap(null)
                })
              }
            }
        }
    }
</script>

<style scoped>
    #map {
        height: 90%;
    }

    .map-container {
        height: 90%;
    }

</style>

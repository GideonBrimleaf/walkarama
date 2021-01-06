<template>
    <div>
      <div id="map" ref="mapContainer"></div>
      <section v-if="!existingWalk && markers.length === 2">
        <p>Distance selected: {{ distanceInMetres }}m</p>

        <form v-on:submit.prevent="createWalk">
          <label for="new-item">Give your walk a name:</label>
          <input id="new-walk" type="text" v-model="form.name" required>
          <input type="submit" value="Create Walk">
        </form>
      </section>
    </div>
</template>

<script>
    import Form from '../form'
    export default {
        name: "GoogleMap",
        props: {
          existingWalk: {type: Object, required: false, default: () => {}}
        },
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

              this.map.addListener('click', (event) => {
                console.log('click event lat long is', event.latLng.toJSON())
                const position = event.latLng.toJSON() 
                this.addMarker(position)
              })

              if (this.existingWalk) {
                this.initMarkers()
              }
            },
            initMarkers: function() {
              const startPoint = {lat: this.existingWalk.startPointLat, lng: this.existingWalk.startPointLong}
              this.addMarker(startPoint)
              const endPoint = {lat: this.existingWalk.endPointLat, lng: this.existingWalk.endPointLong}
              this.addMarker(endPoint)
            },
            addMarker: function(position) {
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
            },
            createWalk: function() {
              this.form.post(`/walks`)
            }
        }
    }
</script>

<style scoped>
    #map {
        height: 70vh;
    }

</style>

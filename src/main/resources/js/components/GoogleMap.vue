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

      <section v-else>
        <h1>{{ existingWalk.name }} - {{ existingWalk.totalDistance }}</h1>
        <p v-if="existingWalk.distanceLeftToTravel != 0">Distance left: {{ existingWalk.distanceLeftToTravel }}</p>
        <p v-else>Distance left: Completed!</p>

        <form v-on:submit.prevent="updateWalk">
          <label for="add-steps">Add some steps:</label>
          <input id="add-steps" type="number" v-model.number="stepsAdded">
          <input type="submit" value="Update Walk Distance">
          <p v-if="distanceInMetres != existingWalk.totalDistance">Walk changed to {{ distanceInMetres }}</p>
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
              form: new Form({
                name: '',
                distanceInMetres: 0,
                startPointLat: null, 
                startPointLng: null,
                endPointLat: null, 
                endPointLng: null,
                distanceLeftToTravel: 0
              }),
              stepsAdded: 0
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
                const position = event.latLng.toJSON() 
                this.addMarker(position)
              })

              if (this.existingWalk) {
                this.initMarkers()

                this.form.name = this.existingWalk.name
                this.form.distanceInMetres = this.existingWalk.distanceInMetres
                this.form.startPointLat = this.existingWalk.startPointLat
                this.form.startPointLng = this.existingWalk.startPointLong
                this.form.endPointLat = this.existingWalk.endPointLat
                this.form.endPointLng = this.existingWalk.endPointLong
                this.form.distanceLeftToTravel = this.existingWalk.distanceLeftToTravel
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
              this.form.startPointLat = this.markers[0].position.lat()
              this.form.startPointLng = this.markers[0].position.lng()
              this.form.endPointLat = this.markers[1].position.lat()
              this.form.endPointLng = this.markers[1].position.lng()
              this.form.post(`/walks`)
            },
            updateWalk: function() {
              this.form.distanceLeftToTravel -= this.stepsAdded
              this.form.patch(`/walks/${this.existingWalk.id}`)
            }
        }
    }
</script>

<style scoped>
    #map {
        height: 70vh;
    }

</style>

module.exports = {
    plugins: [],
    theme: {
      extend: {
        backgroundImage: theme => ({
          'home-page-hero': "url('/images/hero-image-cropped.jpg')"
        }),
        minHeight: {
         '2/3': '66vh',
        }
      }
     },
    variants: {}
}
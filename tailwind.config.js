module.exports = {
    plugins: [],
    theme: {
      extend: {
        backgroundImage: theme => ({
          'home-page-hero': "url('/images/hero-image-cropped.jpg')"
        }),
        minHeight: {
         '2/3': '66vh',
         '3/4': '75vh',
         '9.2/10': '92vh',
         '9.8/10': '98vh'
        },
        minWidth: {
         '0': '0',
         '1/4': '2vw',
         '1/2': '50vw',
         '3/4': '75vw',
         'full': '100vw',
        },
        height: {
          '95': '95vh'
        },
        margin: {
         '28': '7rem',
        },
        screens: {
          'sm': '640px',
          'md': '768px',
          'lg': '1024px',
          'xl': '1280px',
          '2xl': '1536px',
        }
      }
     },
    variants: {}
}
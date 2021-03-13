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
        },
        height: {
          '95': '95vh'
        },
        margin: {
         '28': '7rem',
        },
        screens: {
          'sm': '640px',
          // => @media (min-width: 640px) { ... }

          'md': '768px',
          // => @media (min-width: 768px) { ... }

          'lg': '1024px',
          // => @media (min-width: 1024px) { ... }

          'xl': '1280px',
          // => @media (min-width: 1280px) { ... }

          '2xl': '1536px',
          // => @media (min-width: 1536px) { ... }
        }
      }
     },
    variants: {}
}
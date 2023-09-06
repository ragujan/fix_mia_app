/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,jsp,js}"],
  theme: {
    extend: {
      colors:{
        "maintheme":'#F89D32'
      },
    },
  },
  plugins: [
    require('@tailwindcss/aspect-ratio'),
  ],
}
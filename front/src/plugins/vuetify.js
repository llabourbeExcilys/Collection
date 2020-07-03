import Vue from 'vue';
import Vuetify from 'vuetify/lib';
import fr from 'vuetify/es5/locale/fr';
import '@mdi/font/css/materialdesignicons.css'; // Ensure you are using css-loader

Vue.use(Vuetify);

export default new Vuetify({
	lang: {
		locales: { fr },
		current: 'fr'
	},
	icons: {
		iconfont: 'mdi' // default - only for display purposes
	}
});

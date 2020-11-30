<template>
	<div>
		<v-container>
			<v-row align="center" justify="center">
				<v-col :cols="9">
					<v-tabs centered grow>
						<v-tab @click="changeSelectedRoute(ROUTES.SERIE)" :to="{ name: ROUTES.SERIE }">Series</v-tab>
						<v-tab @click="changeSelectedRoute(ROUTES.AUTHOR)" :to="{ name: ROUTES.AUTHOR }">Auteurs</v-tab>
						<v-tab @click="changeSelectedRoute(ROUTES.GENRE)" :to="{ name: ROUTES.GENRE }">Genres</v-tab>
						<v-tab @click="changeSelectedRoute(ROUTES.EDITOR)" :to="{ name: ROUTES.EDITOR }">Editeurs</v-tab>
					</v-tabs>
				</v-col>
			</v-row>
		</v-container>
		<router-view></router-view>
	</div>
</template>

<script>
export default {
	name: 'manage',
	components: {},
	data: function() {
		return {
			ROUTES: {
				SERIE: 'Serie',
				AUTHOR: 'Author',
				GENRE: 'Genre',
				EDITOR: 'Editor'
			}
		};
	},
	computed: {},
	mounted() {},
	methods: {
		changeSelectedRoute(routeName) {
			this.$store.commit('setManageSelectedTab', routeName);
		}
	}
};
</script>
<style scoped></style>

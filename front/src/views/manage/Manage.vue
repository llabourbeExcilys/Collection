<template>
	<div>
		<v-tabs>
			<v-tab :to="{ name: 'Serie' }">Series</v-tab>
			<v-tab :to="{ name: 'Author' }">Autheurs</v-tab>
			<v-tab :to="{ name: 'Genre' }">Genres</v-tab>
			<v-tab :to="{ name: 'Editor' }">Editeurs</v-tab>
		</v-tabs>
		<router-view></router-view>
	</div>
</template>

<script>
export default {
	name: 'manage',
	components: {},
	data: function() {
		return {};
	},
	computed: {},
	mounted() {},
	methods: {}
};
</script>
<style scoped></style>

<template>
	<div>
		<v-tabs>
			<v-tab @click="push('Serie')">Series</v-tab>
			<v-tab @click="push('Author')">Autheurs</v-tab>
			<v-tab @click="push('Genre')">Genres</v-tab>
			<v-tab @click="push('Editor')">Editeurs</v-tab>
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
	methods: {
		push(name) {
			this.$router.push({ name: name });
		}
	}
};
</script>
<style scoped></style>

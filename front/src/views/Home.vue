<template>
	<div>
		<v-navigation-drawer v-model="drawer" :mini-variant="miniVariant" :clipped="clipped" fixed app>
			<v-list>
				<v-list-item v-for="(item, i) in items" :key="i" :to="item.to">
					<v-list-item-action>
						<v-icon>{{ item.icon }}</v-icon>
					</v-list-item-action>
					<v-list-item-content>
						<v-list-item-title v-text="item.title" />
					</v-list-item-content>
				</v-list-item>
			</v-list>
		</v-navigation-drawer>
		<v-app-bar :clipped-left="clipped" fixed app>
			<v-app-bar-nav-icon @click.stop="drawer = !drawer" />
			<v-btn icon @click.stop="miniVariant = !miniVariant">
				<v-icon>mdi-{{ `chevron-${miniVariant ? 'right' : 'left'}` }}</v-icon>
			</v-btn>
			<v-btn icon @click.stop="fixed = !fixed">
				<v-icon>mdi-minus</v-icon>
			</v-btn>
			<v-toolbar-title v-text="title" />
			<v-spacer />
			<v-btn icon @click.stop="rightDrawer = !rightDrawer">
				<v-icon>mdi-menu</v-icon>
			</v-btn>
		</v-app-bar>
		<v-main>
			<router-view></router-view>
		</v-main>
		<v-navigation-drawer v-model="rightDrawer" :right="right" temporary fixed>
			<!-- <v-list>
				<v-list-item @click.native="right = !right">
					<v-list-item-action>
						<v-icon light>
							mdi-repeat
						</v-icon>
					</v-list-item-action>
					<v-list-item-title>Switch drawer (click me)</v-list-item-title>
				</v-list-item>
			</v-list> -->
		</v-navigation-drawer>
		<v-footer :fixed="fixed" app>
			<span>&copy; {{ new Date().getFullYear() }}</span>
		</v-footer>
	</div>
</template>

<script>
export default {
	data() {
		return {
			clipped: false,
			drawer: true,
			fixed: false,
			items: [
				{
					icon: 'mdi-file-import-outline',
					title: 'Importer',
					to: '/collection/import'
				},
				{
					icon: 'mdi-pencil',
					title: 'Editer',
					to: '/collection/manage/serie'
				},
				{
					icon: 'mdi-library-shelves',
					title: 'Gérer',
					to: '/collection/arrange'
				}
			],
			miniVariant: true,
			right: true,
			rightDrawer: false,
			title: 'Collection'
		};
	},
	computed: {},
	watch: {
		'$store.state.manageSelectedTab'(value) {
			this.items[1].to = { name: value };
		}
	}
};
</script>

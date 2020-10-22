import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '@/views/Home.vue';

Vue.use(VueRouter);

const routes = [
	{
		path: '/',
		redirect: '/collection/manage/serie'
	},
	{
		path: '/collection',
		name: 'Home',
		component: Home,
		children: [
			{
				path: 'arrange',
				name: 'Arrange',
				// route level code-splitting
				// this generates a separate chunk (about.[hash].js) for this route
				// which is lazy-loaded when the route is visited.
				component: () => import(/* webpackChunkName: "about" */ '@/views/Arrange.vue')
			},
			{
				path: 'import',
				name: 'Import',
				component: () => import(/* webpackChunkName: "about" */ '@/views/Import.vue')
			},
			{
				path: 'manage',
				name: 'Manage',
				component: () => import(/* webpackChunkName: "about" */ '@/views/manage/Manage.vue'),
				children: [
					{
						path: 'serie',
						name: 'Serie',
						component: () => import(/* webpackChunkName: "about" */ '@/views/manage/ManageSerie.vue')
					},
					{
						path: 'author',
						name: 'Author',
						component: () => import(/* webpackChunkName: "about" */ '@/views/manage/ManageAuthor.vue')
					},
					{
						path: 'editor',
						name: 'Editor',
						component: () => import(/* webpackChunkName: "about" */ '@/views/manage/ManageEditor.vue')
					},
					{
						path: 'genre',
						name: 'Genre',
						component: () => import(/* webpackChunkName: "about" */ '@/views/manage/ManageGenre.vue')
					}
				]
			}
		]
	}
];

const router = new VueRouter({
	routes
});

export default router;

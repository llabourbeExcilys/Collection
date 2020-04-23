<template>
	<div>
		<v-card class="mx-auto" max-width="1000">
			<v-container fluid>
				<v-row dense align="center" justify="center" no-gutters>
					<v-col :cols="4">
						<v-card-title>
							{{ item.title }}
						</v-card-title>
						<v-card-subtitle>
							{{ item.edition }} ({{ item.editeur }})
						</v-card-subtitle>
					</v-col>

					<v-col :cols="2"> {{ item.owned }} / {{ item.published }} </v-col>
					<v-col :cols="3">
						{{ item.type }}
					</v-col>
					<v-col :cols="2">
						{{ item.genre }}
					</v-col>
					<v-col :cols="1">
						<v-card-actions>
							<v-spacer />
							<v-btn icon @click="show = !show">
								<v-icon>{{
									show ? 'mdi-chevron-up' : 'mdi-chevron-down'
								}}</v-icon>
							</v-btn>
						</v-card-actions>
					</v-col>
				</v-row>
				<v-row dense align="center" justify="center" no-gutters>
					<v-divider v-show="show" />
				</v-row>
				<v-row>
					<v-col :cols="5">
						<v-expand-transition>
							<div v-show="show">
								<threebook :title="item.title" :length="item.published" />
							</div>
						</v-expand-transition>
					</v-col>
				</v-row>
			</v-container>
		</v-card>
	</div>
</template>

<script>
import threebook from '@/components/ThreeBook';

export default {
	name: 'manga-serie',
	components: {
		threebook
	},
	props: {
		item: {
			type: Object,
			required: true
		}
	},
	data() {
		return {
			show: false
		};
	},
	mounted() {},
	methods: {}
};
</script>
<style scoped></style>

<template>
	<div>
		<v-card>
			<v-container fluid>
				<v-row dense align="center" justify="center" no-gutters>
					<v-col :cols="4">
						<v-card-title>
							{{ item.title }}
						</v-card-title>
						<v-card-subtitle>
							{{ item.edition }} ({{ item.editor }})
						</v-card-subtitle>
					</v-col>
					<v-col :cols="2">{{ item.autor }}</v-col>
					<v-col :cols="1">
						<v-chip
							v-if="item.owned === item.published"
							class="ma-2"
							color="green"
							text-color="white"
						>
							{{ item.owned }} / {{ item.published }}
						</v-chip>
						<v-chip v-else class="ma-2" color="orange" text-color="white">
							{{ item.owned }} / {{ item.published }}
						</v-chip>
					</v-col>
					<v-col :cols="1">
						{{ item.type }}
					</v-col>
					<v-col :cols="3">
						<v-row no-gutters>
							<v-col>
								<v-chip
									v-for="genre in item.genres"
									v-bind:key="genre"
									class="ma-2"
									small
									dense
									color="grey lighten-1"
									text-color="grey darken-4"
								>
									{{ genre }}
								</v-chip>
							</v-col>
						</v-row>
					</v-col>
					<v-col>
						<v-card-actions>
							<v-btn icon @click="show = !show">
								<v-icon>{{
									show ? 'mdi-chevron-up' : 'mdi-chevron-down'
								}}</v-icon>
							</v-btn>
						</v-card-actions>
					</v-col>
				</v-row>
				<v-row v-if="show" dense align="center" justify="center">
					<v-divider />
				</v-row>
				<div v-if="show">
					<v-row>
						<v-col :cols="5">
							<v-expand-transition>
								<threebook :title="item.title" :length="item.published" />
							</v-expand-transition>
						</v-col>
					</v-row>
				</div>
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

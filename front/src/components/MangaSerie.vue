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
					<v-col :cols="2">
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
				<v-row v-if="show" dense align="center" justify="center">
					<v-divider />
				</v-row>
				<v-row v-if="show">
					<v-col :cols="5">
						<v-expand-transition>
							<div>
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

<template>
	<v-dialog v-model="dialog" persistent max-width="600px">
		<template v-slot:activator="{ on, attrs }">
			<v-hover v-slot="{ hover }">
				<v-card v-on="on" v-bind="attrs" :elevation="hover ? 16 : 2" :min-height="height">
					<v-card-title>
						<v-container class="pa-0">
							<v-row align="center" justify="space-between">
								<v-col cols="8" offset="1" class="d-flex justify-start">
									{{ item.name }}
								</v-col>
								<v-col cols="2">
									<v-btn icon>
										<v-icon color="red lighten-2" v-show="hover" @click="deleteGenre"
											>mdi-close-circle</v-icon
										>
									</v-btn>
								</v-col>
							</v-row>
						</v-container>
					</v-card-title>
					<v-card-text>
						{{ item.description }}
					</v-card-text>
				</v-card>
			</v-hover>
		</template>
		<v-form v-model="formValid">
			<v-card>
				<v-card-title>Modifier un genre</v-card-title>
				<v-card-text>
					<v-text-field
						v-model="item.name"
						height="40"
						label="Intitulé"
						:rules="[rules.requiredField]"
					></v-text-field>
					<v-textarea
						v-model="item.description"
						auto-grow
						counter
						label="Desctiption"
						:rules="[rules.requiredField]"
					></v-textarea>
				</v-card-text>
				<v-card-actions>
					<v-spacer></v-spacer>
					<v-btn :disabled="!formValid || !isModified" color="success" class="mr-4" @click="editGenre">
						Modifier
					</v-btn>
					<v-btn color="error" class="mr-4" @click="cancelEditItem">
						Annuler
					</v-btn>
				</v-card-actions>
			</v-card>
		</v-form>
	</v-dialog>
</template>

<script>
import { isEmpty } from 'lodash';
import restorableItem from '@/components/extends/RestorableItem';

export default {
	name: 'Genre',
	components: {},
	mixins: [restorableItem],
	props: {
		height: {
			type: Number,
			required: false
		}
	},
	data: function() {
		return {
			genre: null,
			dialog: false,
			formValid: false,
			rules: {
				requiredField: value => (!isEmpty(value) && !!value) || 'Champs requis'
			}
		};
	},
	computed: {},
	mounted() {},
	methods: {
		cancelEditItem() {
			this.dialog = false;
			this.restore();
		},
		deleteGenre() {
			this.$emit('delete-genre-event', this.item);
		},
		editGenre() {
			this.dialog = false;
			this.$emit('edit-genre-event', this.item);
		}
	}
};
</script>
<style scoped>
.v-card__text {
	text-align: justify;
}
</style>

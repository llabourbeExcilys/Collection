<template>
	<v-hover v-slot="{ hover }">
		<v-card :elevation="hover ? 16 : 2">
			<v-dialog v-model="dialog" persistent max-width="600px">
				<template v-slot:activator="{ on, attrs }">
					<v-img
						v-on="on"
						v-bind="attrs"
						src="https://vignette.wikia.nocookie.net/just-cause-fan-fiction/images/1/12/Unnamed_scientist.jpg/revision/latest?cb=20170701000707"
						class="white--text d-flex"
						gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
						height="300px"
					>
						<v-row>
							<v-spacer />
							<v-col cols="3">
								<v-btn icon>
									<v-icon color="red" v-show="hover" @click="deleteAuthor">mdi-close-circle</v-icon>
								</v-btn>
							</v-col>
						</v-row>
						<v-row>
							<v-col cols="12"></v-col>
							<v-col cols="12"></v-col>
							<v-col cols="12"></v-col>
							<v-col cols="12"></v-col>
							<v-col cols="12"></v-col>
							<v-col cols="12"></v-col>
						</v-row>
						<v-row>
							<v-col cols="12">
								<v-card-title class="justify-center">
									<p>{{ item.firstName + ' ' + item.lastName }}</p>
								</v-card-title>
							</v-col>
						</v-row>
					</v-img>
				</template>
				<v-form v-model="formValid">
					<v-card>
						<v-card-title>Modifier un auteur</v-card-title>
						<v-card-text>
							<v-text-field
								v-model="item.firstName"
								:rules="[rules.requiredField]"
								height="40"
								label="Prénom"
							></v-text-field>
							<v-text-field
								v-model="item.lastName"
								:rules="[rules.requiredField]"
								height="40"
								label="Nom"
							></v-text-field>
							<v-text-field height="40" label="Image (url)"></v-text-field>
						</v-card-text>
						<v-card-actions>
							<v-spacer></v-spacer>
							<v-btn :disabled="!formValid || !isModified" color="success" class="mr-4" @click="editAuthor">
								Modifier
							</v-btn>
							<v-btn color="error" class="mr-4" @click="cancelEditItem">
								Annuler
							</v-btn>
						</v-card-actions>
					</v-card>
				</v-form>
			</v-dialog>
		</v-card>
	</v-hover>
</template>

<script>
import { isEmpty } from 'lodash';
import restorableItem from '@/components/extends/RestorableItem';

export default {
	name: 'ManageAuthors',
	components: {},
	mixins: [restorableItem],
	props: {},
	data: function() {
		return {
			author: null,
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
		deleteAuthor() {
			this.$emit('delete-author-event', this.item);
		},
		editAuthor() {
			this.dialog = false;
			this.$emit('edit-author-event', this.item);
		}
	}
};
</script>
<style scoped></style>

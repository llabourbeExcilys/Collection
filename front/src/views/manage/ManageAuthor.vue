<template>
	<v-container>
		<v-row align="center" justify="center">
			<v-col :cols="9">
				<v-data-iterator
					hide-default-footer
					row
					wrap
					:items-per-page="8"
					:items="authors"
					:page.sync="page"
					:search="search"
					:sort-by="['firstName', 'lastName']"
				>
					<template v-slot:header>
						<v-card color="grey lighten-3">
							<v-container>
								<v-row align="center">
									<v-col cols="8">
										<v-text-field
											v-model="search"
											clearable
											flat
											solo
											hide-details
											append-icon="mdi-magnify"
											label="Chercher un auteur"
										/>
									</v-col>
									<v-spacer></v-spacer>
									<v-col>
										<v-dialog v-model="dialog" persistent max-width="600px">
											<template v-slot:activator="{ on, attrs }">
												<v-btn v-on="on" v-bind="attrs" large depressed color="grey lighten-1">
													Ajouter
												</v-btn>
											</template>
											<v-form v-model="formValid">
												<v-card>
													<v-card-title>Nouvel auteur</v-card-title>
													<v-card-text>
														<v-text-field
															v-model="newItem.firstName"
															:rules="[rules.requiredField]"
															height="40"
															label="Prénom"
														></v-text-field>
														<v-text-field
															v-model="newItem.lastName"
															:rules="[rules.requiredField]"
															height="40"
															label="Nom"
														></v-text-field>
														<v-text-field height="40" label="Image (url)"></v-text-field>
													</v-card-text>
													<v-card-actions>
														<v-spacer></v-spacer>
														<v-btn :disabled="!formValid" color="success" class="mr-4" @click="addNewAuthor">
															Ajouter
														</v-btn>
														<v-btn color="error" class="mr-4" @click="cancelNewItem">
															Annuler
														</v-btn>
													</v-card-actions>
												</v-card>
											</v-form>
										</v-dialog>
									</v-col>
								</v-row>
							</v-container>
						</v-card>
					</template>

					<template v-slot:default="props">
						<v-row>
							<v-col v-for="item in props.items" :key="item.id" cols="3">
								<author :baseItem="item" @delete-author-event="deleteAuthor" @edit-author-event="editAuthor">
								</author>
							</v-col>
						</v-row>
					</template>
					<template v-slot:footer="{ options, pagination, updateOptions }">
						<v-data-footer
							:class="'d-flex justify-center'"
							:options="options"
							:pagination="pagination"
							@update:options="updateOptions"
						>
						</v-data-footer>
					</template> </v-data-iterator
			></v-col>
		</v-row>
	</v-container>
</template>

<script>
import mangaService from '@/services/MangaService';
import author from '@/components/Author';
import { isEmpty, remove } from 'lodash';

export default {
	name: 'ManageAuthors',
	components: { author },
	data: function() {
		return {
			authors: [],
			dialog: false,
			formValid: false,
			newItem: {
				id: -1,
				firstName: '',
				lastName: ''
			},
			page: 1,
			search: '',
			searchedAuthor: null,
			rules: {
				requiredField: value => (!isEmpty(value) && !!value) || 'Champs requis'
			}
		};
	},
	computed: {},
	mounted() {
		this.loadAuthors();
	},
	methods: {
		addNewAuthor() {
			console.log('new item', this.newItem);
			mangaService
				.addAuthor(this.newItem)
				.then(() => {
					this.createEmptyNewItem();
					this.loadAuthors();
					this.dialog = false;
				})
				.catch(error => console.log(error));
		},
		createEmptyNewItem() {
			this.newItem = {
				id: -1,
				firstName: '',
				lastName: ''
			};
		},
		cancelNewItem() {
			this.createEmptyNewItem();
			this.dialog = false;
		},
		deleteAuthor(author) {
			if (author.id === -1) {
				this.authors = remove(this.authors, element => element.id === author.id);
			} else {
				mangaService
					.deleteAuthor(author)
					.then(() => this.loadAuthors())
					.catch(error => console.log(error));
			}
		},
		editAuthor(author) {
			mangaService
				.updateAuthor(author)
				.then(() => this.loadAuthors())
				.catch(error => console.log(error));
		},
		loadAuthors() {
			mangaService
				.getAuthors()
				.then(response => {
					this.authors = response;
				})
				.catch(error => console.log(error));
		}
	}
};
</script>
<style scoped></style>

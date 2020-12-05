<template>
	<v-container>
		<v-row align="center" justify="center">
			<v-col :cols="9">
				<v-data-iterator
					hide-default-footer
					row
					wrap
					:items-per-page="8"
					:items="genres"
					:page.sync="page"
					:search="search"
					:sort-by="['name']"
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
											label="Chercher un genre"
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
													<v-card-title>Nouveau genre</v-card-title>
													<v-card-text>
														<v-text-field
															v-model="newItem.name"
															:rules="[rules.requiredField]"
															height="40"
															label="Intitulé"
														></v-text-field>
														<v-text-field
															v-model="newItem.description"
															:rules="[rules.requiredField]"
															height="40"
															label="Description"
														></v-text-field>
													</v-card-text>
													<v-card-actions>
														<v-spacer></v-spacer>
														<v-btn :disabled="!formValid" color="success" class="mr-4" @click="addNewGenre">
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
							<v-col v-for="item in props.items" :key="item.id" :cols="colWidth">
								<genre
									:baseItem="item"
									:height="genreCardHeight"
									@delete-genre-event="deleteGenre"
									@edit-genre-event="editGenre"
								>
								</genre>
							</v-col>
						</v-row>
					</template>
					<template v-slot:footer="{ options, pagination, updateOptions }">
						<v-data-footer
							:class="'d-flex justify-center'"
							:items-per-page-options="itemsPerPageArray"
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
import genre from '@/components/Genre';
import { isEmpty, remove } from 'lodash';

export default {
	name: 'ManageGenres',
	components: { genre },
	data: function() {
		return {
			dialog: false,
			colWidth: 3,
			formValid: false,
			genres: [],
			newItem: {
				id: -1,
				name: '',
				description: ''
			},
			page: 1,
			search: '',
			searchedGenre: null,
			rules: {
				requiredField: value => (!isEmpty(value) && !!value) || 'Champs requis'
			}
		};
	},
	computed: {
		genreCardHeight() {
			let maxDescLength = 0;
			this.genres.forEach(element => {
				let descLength = element.description.length;
				if (descLength > maxDescLength) {
					maxDescLength = descLength;
				}
			});
			let charPerLigne = 180 * (this.colWidth / 12);
			let ligne = Math.ceil(maxDescLength / charPerLigne);
			return 55 + ligne * 35;
		},
		itemsPerPageArray() {
			let itemPerPage = 12 / this.colWidth;
			return [itemPerPage, itemPerPage * 2, itemPerPage * 3, -1];
		}
	},
	mounted() {
		this.loadGenres();
	},
	methods: {
		addNewGenre() {
			mangaService
				.addGenre(this.newItem)
				.then(() => {
					this.createEmptyNewItem();
					this.loadGenres();
					this.dialog = false;
				})
				.catch(error => console.log(error));
		},
		createEmptyNewItem() {
			this.newItem = {
				id: -1,
				name: '',
				description: ''
			};
		},
		cancelNewItem() {
			this.createEmptyNewItem();
			this.dialog = false;
		},
		deleteGenre(genre) {
			if (genre.id === -1) {
				this.genres = remove(this.genres, element => element.id === genre.id);
			} else {
				mangaService
					.deleteGenre(genre)
					.then(() => this.loadGenres())
					.catch(error => console.log(error));
			}
		},
		editGenre(genre) {
			mangaService
				.updateGenre(genre)
				.then(() => this.loadGenres())
				.catch(error => console.log(error));
		},
		loadGenres() {
			mangaService
				.getGenres()
				.then(response => {
					this.genres = response;
				})
				.catch(error => console.log(error));
		}
	}
};
</script>
<style scoped></style>

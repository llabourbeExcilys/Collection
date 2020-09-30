<template>
	<div>
		<v-container>
			<v-row align="center" justify="center">
				<v-col :cols="9">
					<v-data-iterator
						hide-default-footer
						row
						wrap
						:items-per-page="5"
						:items="filteredItems"
						:page.sync="page"
						:search="search"
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
												label="Chercher une oeuvre, un éditeur, un genre..."
											/>
										</v-col>
										<v-spacer></v-spacer>
										<v-col>
											<v-btn v-if="!isNewItem" large depressed color="grey lighten-1" @click="addBlankItem"
												>Ajouter</v-btn
											>
										</v-col>
										<v-col>
											<v-btn large depressed color="grey lighten-1" @click="toggleFilters"
												>Filtrer {{ showFilters ? '-' : '+' }}</v-btn
											>
										</v-col>
									</v-row>
									<v-expand-transition>
										<v-row v-show="showFilters">
											<v-col>
												<v-autocomplete
													v-model="searchedEditor"
													:items="editors"
													:item-text="item => item.name"
													background-color="white"
													dense
													outlined
													clearable
													label="Editeur"
												></v-autocomplete>
											</v-col>
											<v-col>
												<v-autocomplete
													v-model="searchedType"
													:items="types"
													background-color="white"
													dense
													outlined
													clearable
													label="Type"
												></v-autocomplete>
											</v-col>
											<v-col>
												<v-autocomplete
													v-model="searchedAutor"
													:items="authors"
													:item-text="item => item.firstName + ' ' + item.lastName"
													background-color="white"
													dense
													outlined
													clearable
													label="Auteur"
												></v-autocomplete>
											</v-col>

											<v-col>
												<v-autocomplete
													v-model="searchedGenre"
													:items="genres"
													:item-text="item => item.name"
													background-color="white"
													dense
													outlined
													clearable
													label="Genres"
													multiple
												></v-autocomplete>
											</v-col>

											<v-col>
												<v-btn-toggle v-model="searchGenreType" mandatory>
													<v-btn> ET </v-btn>
													<v-btn> OU </v-btn>
												</v-btn-toggle>
											</v-col>
										</v-row>
									</v-expand-transition>
								</v-container>
							</v-card>
						</template>

						<template v-slot:default="props">
							<v-row>
								<v-col v-for="item in props.items" :key="item.id" cols="12">
									<MangaSerie
										:item="item"
										:isNewItem="item.isNewItem"
										:possibleAuthors="authors"
										:possibleEditors="editors"
										:possibleGenres="genres"
										:possibleTypes="types"
										@edit-item="manageEdit"
										@remove-item="manageRemove"
										@click-add-new-item="manageAddItem"
										@click-cancel-new-item="manageCancelNewItem"
									/>
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
	</div>
</template>

<script>
import MangaSerie from '@/components/MangaSerie';
import mangaService from '@/services/MangaService';

export default {
	name: 'manage',
	components: {
		MangaSerie
	},
	data: function() {
		return {
			authors: [],
			books: [],
			editors: [],
			// seriesEditedMap: {},
			genres: [],
			isNewItem: false,
			mangaSeries: [],
			page: 1,
			search: '',
			searchedEditor: '',
			searchedType: '',
			searchedAutor: '',
			searchedGenre: [],
			searchGenreType: 1,
			showFilters: false,
			types: []
		};
	},
	computed: {
		filteredItems() {
			var filteredItems = this.mangaSeries;

			if (!this.showFilters) {
				// ascending filter on 'title' property
				return filteredItems.sort(this.compareValues('title'));
			}

			if (this.searchedEditor) {
				filteredItems = filteredItems.filter(item => item.editor === this.searchedEditor);
			}
			if (this.searchedType) {
				filteredItems = filteredItems.filter(item => item.type === this.searchedType);
			}
			if (this.searchedAutor) {
				filteredItems = filteredItems.filter(item => item.autor === this.searchedAutor);
			}
			if (this.searchedGenre.length > 0) {
				/* OU */
				if (this.searchGenreType === 1) {
					filteredItems = filteredItems.filter(item => item.genres.some(x => this.searchedGenre.includes(x)));
					/* ET */
				} else if (this.searchGenreType === 0) {
					filteredItems = filteredItems.filter(item =>
						this.searchedGenre.every(x => item.genres.includes(x))
					);
				}
			}
			return filteredItems.sort(this.compareValues('title'));
		}
	},
	mounted() {
		this.loadMangaSeries();

		mangaService
			.getGenres()
			.then(response => {
				this.genres = response;
			})
			.catch(error => console.log(error));

		mangaService
			.getTypes()
			.then(response => {
				this.types = response;
			})
			.catch(error => console.log(error));

		mangaService
			.getAuthors()
			.then(response => {
				this.authors = response;
			})
			.catch(error => console.log(error));

		mangaService
			.getEditors()
			.then(response => {
				this.editors = response;
			})
			.catch(error => console.log(error));
	},
	methods: {
		addBlankItem() {
			this.mangaSeries.push({
				id: -1,
				autor: '',
				color: '#1565C0FF',
				title: '',
				edition: '',
				editor: '',
				owned: 0,
				published: 0,
				type: '',
				genres: [],
				isNewItem: true
			});
			this.page = 1;
			this.isNewItem = true;
		},
		compareValues(key, order = 'asc') {
			return function innerSort(a, b) {
				if (!Object.prototype.hasOwnProperty.call(a, key) || !Object.prototype.hasOwnProperty.call(b, key)) {
					// property doesn't exist on either object
					return 0;
				}

				// if id === -1 then it is a new item and must be in first position
				if (a['id'] === -1) {
					return -1;
				}
				if (b['id'] === -1) {
					return 1;
				}

				const varA = typeof a[key] === 'string' ? a[key].toUpperCase() : a[key];
				const varB = typeof b[key] === 'string' ? b[key].toUpperCase() : b[key];
				let comparison = 0;
				if (varA > varB) {
					comparison = 1;
				} else if (varA < varB) {
					comparison = -1;
				}
				return order === 'asc' ? comparison : -comparison;
			};
		},
		loadMangaSeries() {
			mangaService
				.getSeries()
				.then(response => {
					this.mangaSeries = response;
				})
				.catch(error => console.log(error));
		},
		toggleFilters() {
			this.showFilters = !this.showFilters;
			if (!this.showFilters) {
				this.searchedEditor = '';
				this.searchedType = '';
				this.searchedAutor = '';
				this.searchedGenre = [];
			}
		},
		manageAddItem() {
			var newItem = this.mangaSeries.find(item => item.id === -1);
			delete newItem.isNewItem;
			delete newItem.id;
			console.log('newItem', newItem);
			mangaService.addSerie(newItem).then(
				mangaService
					.getSeries()
					.then(response => {
						this.mangaSeries = response;
					})
					.catch(error => console.log(error))
			);
			this.isNewItem = false;
		},
		manageCancelNewItem() {
			this.mangaSeries = this.mangaSeries.filter(item => item.id != -1);
			this.isNewItem = false;
		},
		manageEdit(item) {
			mangaService.updateSerie(item).then(() => this.loadMangaSeries());
		},
		manageRemove(item) {
			mangaService.removeSerie(item.id).then(() => this.loadMangaSeries());
		}
	}
};
</script>
<style scoped>
#canvas {
	border: 1px solid black;
	display: inline-block;
	/* position: static; 
    top: 100px;
    left: 100px; */
}

/* .v-data-footer {
	display: flex;
	justify-content: center;
} */
</style>

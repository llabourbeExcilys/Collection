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
											<v-col :cols="7">
												<v-row align="stretch" justify="space-around">
													<v-col :cols="4">
														<v-autocomplete
															v-model="searchedEditor"
															:items="editors"
															:item-text="item => item.name"
															:item-value="item => item.id"
															background-color="white"
															dense
															outlined
															clearable
															label="Editeur"
														></v-autocomplete>
													</v-col>
													<v-col :cols="4">
														<v-autocomplete
															v-model="searchedType"
															:items="types"
															background-color="white"
															clearable
															dense
															label="Type"
															outlined
															return-object
														></v-autocomplete>
													</v-col>
													<v-col :cols="4">
														<v-autocomplete
															v-model="searchedAuthor"
															:items="authors"
															:item-text="item => item.firstName + ' ' + item.lastName"
															:item-value="item => item.id"
															background-color="white"
															clearable
															dense
															outlined
															label="Auteur"
														></v-autocomplete>
													</v-col>
												</v-row>
											</v-col>

											<v-col>
												<v-row align="stretch" justify="center">
													<v-col :cols="8">
														<v-autocomplete
															v-model="searchedGenre"
															:items="genres"
															:item-text="item => item.name"
															:item-value="item => item.id"
															background-color="white"
															clearable
															dense
															outlined
															label="Genres"
															multiple
														></v-autocomplete>
													</v-col>

													<v-col :cols="3">
														<v-btn-toggle dense v-model="searchOperator" mandatory>
															<v-btn> ET </v-btn>
															<v-btn> OU </v-btn>
														</v-btn-toggle>
													</v-col>
												</v-row>
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
										:baseItem="item"
										:gltfScene="gltfScene"
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
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader';

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
			gltfScene: null,
			isNewItem: false,
			mangaSeries: [],
			page: 1,
			search: '',
			searchedEditor: null,
			searchedType: '',
			searchedAuthor: null,
			searchedGenre: [],
			searchOperator: 1,
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
				filteredItems = filteredItems.filter(serie => serie.editor.id === this.searchedEditor);
			}
			if (this.searchedType) {
				filteredItems = filteredItems.filter(serie => serie.type === this.searchedType);
			}
			if (this.searchedAuthor) {
				filteredItems = filteredItems.filter(serie =>
					serie.authors.some(author => author.id === this.searchedAuthor)
				);
			}
			if (this.searchedGenre.length > 0) {
				/* OR operator */
				if (this.searchOperator === 1) {
					filteredItems = filteredItems.filter(serie =>
						serie.genres.some(genre => this.searchedGenre.includes(genre.id))
					);
					/* AND operator */
				} else if (this.searchOperator === 0) {
					filteredItems = filteredItems.filter(serie =>
						this.searchedGenre.every(id => serie.genres.some(genre => genre.id === id))
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
		const loader = new GLTFLoader();
		loader.load(
			'/models/scene.gltf',
			gltf => {
				this.gltfScene = gltf.scene;

				var parent = this.gltfScene.children[0].children[0].children[0];
				// console.log('parent', parent);

				parent.remove(parent.children[0]);
				// console.log('parent.children[0])', parent.children[0]);
				// parent.remove(parent.children[0]);
				// console.log('parent.children[0])', parent.children[0]);
				var object = parent.children[0];
				console.log('object)', object);

				object.position.set(0, 0, 0);
				object.scale.set(100, 100, 100);
				object.rotateZ(1.5708);
			},
			xhr => console.log((xhr.loaded / xhr.total) * 100 + '% loaded'),
			error => console.error(error)
		);
	},
	methods: {
		addBlankItem() {
			this.mangaSeries.push({
				id: -1,
				authors: [],
				color: '#1565C0FF',
				title: '',
				edition: '',
				editor: '',
				owned: 0,
				published: 0,
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
				this.searchedEditor = null;
				this.searchedType = '';
				this.searchedAuthor = null;
				this.searchedGenre = [];
			}
		},
		manageAddItem() {
			var newItem = this.mangaSeries.find(item => item.id === -1);
			delete newItem.isNewItem;
			delete newItem.id;
			mangaService.addSerie(newItem).then(() => {
				mangaService
					.getSeries()
					.then(response => {
						this.mangaSeries = response;
					})
					.catch(error => console.log(error));
			});
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

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
							<v-row><v-col></v-col></v-row>
							<v-row>
								<v-col v-for="item in props.items" :key="item.title" cols="12">
									<MangaSerie :item="item" />
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
import cloneDeep from 'lodash';
import axios from 'axios';

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
			genres: [],
			mangaSeries: [],
			search: '',
			searchedEditor: '',
			searchedType: '',
			searchedAutor: '',
			searchedGenre: [],
			searchGenreType: 1,
			showFilters: false,
			types: [],
			genres: []
		};
	},
	computed: {
		filteredItems() {
			if (!this.showFilters) {
				return this.mangaSeries;
			}
			var filteredItems = this.mangaSeries;

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
			return filteredItems;
		}
	},
	mounted() {
		axios
			.get('http://localhost:3000/series')
			.then(response => {
				this.mangaSeries = response.data;
			})
			.catch(error => console.log(error));

		axios
			.get('http://localhost:3000/genres')
			.then(response => {
				this.genres = response.data;
			})
			.catch(error => console.log(error));
		axios
			.get('http://localhost:3000/types')
			.then(response => {
				this.types = response.data;
			})
			.catch(error => console.log(error));
		axios
			.get('http://localhost:3000/authors')
			.then(response => {
				this.authors = response.data;
			})
			.catch(error => console.log(error));
		axios
			.get('http://localhost:3000/editors')
			.then(response => {
				this.editors = response.data;
			})
			.catch(error => console.log(error));
	},
	methods: {
		toggleFilters() {
			this.showFilters = !this.showFilters;
			if (!this.showFilters) {
				this.searchedEditor = '';
				this.searchedType = '';
				this.searchedAutor = '';
				this.searchedGenre = [];
			}
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

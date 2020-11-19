<template>
	<v-container>
		<v-row align="center" justify="center">
			<v-col :cols="9">
				<v-form v-if="isNewItem" v-model="formValid">
					<v-row justify="center" align="center">
						<v-col cols="4">
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
									<v-text-field :rules="[rules.requiredField]" height="40" label="Image (url)"></v-text-field>
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
						</v-col>
					</v-row>
				</v-form>
				<v-data-iterator
					v-else
					hide-default-footer
					row
					wrap
					:items-per-page="8"
					:items="authors"
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
											label="Chercher un auteur"
										/>
									</v-col>
									<v-spacer></v-spacer>
									<v-col>
										<v-btn
											v-if="!isNewItem"
											large
											depressed
											color="grey lighten-1"
											@click="createEmptyNewItem"
										>
											Ajouter
										</v-btn>
									</v-col>
								</v-row>
							</v-container>
						</v-card>
					</template>

					<template v-slot:default="props">
						<v-row>
							<v-col v-for="item in props.items" :key="item.id" cols="3">
								<v-hover v-slot="{ hover }">
									<v-card :elevation="hover ? 16 : 2" :class="{ 'v-image--on-hover': hover }">
										<v-img
											src="https://vignette.wikia.nocookie.net/just-cause-fan-fiction/images/1/12/Unnamed_scientist.jpg/revision/latest?cb=20170701000707"
											class="white--text d-flex"
											gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
											height="300px"
										>
											<v-row>
												<v-spacer />
												<v-col cols="3">
													<v-btn icon>
														<v-icon color="red" v-show="hover" @click="deleteAuthor(item)"
															>mdi-close-circle</v-icon
														>
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
													<v-card-title
														class="justify-center"
														v-text="item.firstName + ' ' + item.lastName"
													/>
												</v-col>
											</v-row>
										</v-img>
									</v-card>
								</v-hover>
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
import { isEmpty, remove } from 'lodash';

export default {
	name: 'ManageAuthors',
	components: {},
	data: function() {
		return {
			authors: [],
			formValid: false,
			isNewItem: false,
			newItem: null,
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
					this.isNewItem = false;
					this.loadAuthors();
				})
				.catch(error => console.log(error));
		},
		createEmptyNewItem() {
			this.newItem = {
				id: -1,
				firstName: '',
				lastName: ''
			};
			this.page = 1;
			this.isNewItem = true;
		},
		cancelNewItem() {
			this.newItem = null;
			this.isNewItem = false;
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
<style scoped>
.v-card.on-hover.theme--dark {
	background-color: rgba(0, 0, 0, 1);
}
</style>

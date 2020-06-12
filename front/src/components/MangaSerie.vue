<template>
	<div>
		<v-card>
			<v-container fluid>
				<v-row dense :align="edit ? 'baseline' : 'center'" justify="center" no-gutters>
					<v-col :cols="4">
						<v-card-title>
							<v-text-field v-if="edit" v-model="item.title" outlined label="Titre"></v-text-field>
							<div v-else>{{ item.title }}</div>
						</v-card-title>
						<v-card-subtitle>
							<v-text-field v-if="edit" v-model="item.editor" outlined dense label="Edition"></v-text-field>
							<div v-else>{{ item.edition }} ({{ item.editor }})</div>
						</v-card-subtitle>
					</v-col>
					<v-col :cols="2">
						<div v-if="edit">
							<v-text-field v-model="item.autor" outlined label="Auteur"></v-text-field>
							<v-row align="start">
								<v-col :cols="6">
									<v-text-field
										v-model="item.owned"
										v-if="edit"
										outlined
										dense
										label="Possédé"
										type="number"
										@input="item.owned = parseInt($event)"
									></v-text-field
								></v-col>
								<v-col :cols="6">
									<v-text-field
										v-model="item.published"
										v-if="edit"
										outlined
										dense
										label="Publié"
										type="number"
										@input="item.published = parseInt($event)"
									></v-text-field
								></v-col>
							</v-row>
						</div>
						<div v-else>{{ item.autor }}</div>
					</v-col>

					<v-col :cols="1">
						<v-chip
							v-if="!edit"
							class="ma-2"
							:color="item.owned === item.published ? 'green' : 'orange'"
							text-color="white"
						>
							{{ item.owned }} / {{ item.published }}
						</v-chip>
					</v-col>
					<v-col :cols="1">
						<div v-if="edit">
							<v-select
								v-model="item.type"
								outlined
								:items="possibleTypes"
								:rules="[v => !!v || 'Le genre est requis']"
								label="Genre"
								required
							></v-select>
						</div>
						<div v-else>{{ item.type }}</div>
					</v-col>
					<v-col :cols="3">
						<v-row no-gutters>
							<v-col>
								<div v-if="edit">
									<v-combobox
										v-model="item.genres"
										:items="possibleGenres"
										label="Genres"
										multiple
										chips
									></v-combobox>
								</div>
								<div v-else>
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
								</div>
							</v-col>
						</v-row>
					</v-col>
					<v-col>
						<v-card-actions>
							<v-tooltip :disabled="show" top>
								<template v-slot:activator="{ on }">
									<v-btn v-on="on" icon @click="show = !show">
										<v-icon>{{ show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
									</v-btn>
								</template>
								<span>Afficher les détails</span>
							</v-tooltip>
							<v-tooltip v-if="show" :disabled="edit" top>
								<template v-slot:activator="{ on }">
									<v-btn v-on="on" @click="clickEdit" icon>
										<v-icon small>{{ edit ? 'mdi-pencil-off' : 'mdi-pencil' }}</v-icon>
									</v-btn>
								</template>
								<span>Editer l'élément</span>
							</v-tooltip>
						</v-card-actions>
					</v-col>
				</v-row>
				<v-row v-if="show" dense align="center" justify="center">
					<v-divider />
				</v-row>
				<div v-if="show">
					<v-expand-transition>
						<v-container>
							<v-row>
								<v-col :cols="6">
									<threebook
										:title="item.title"
										:numberPublished="item.published"
										:numberPossessed="item.owned"
										:mangaDimensions="mangaDimensions"
										:pickedColor="pickedColor"
									/>
								</v-col>
								<v-col :cols="2">
									<v-row>
										<v-col :cols="12">
											<v-subheader class="pl-0">Largeur (mm)</v-subheader>
										</v-col>
										<v-col :cols="12">
											<v-slider
												v-model="mangaWidth"
												thumb-label="always"
												thumb-size="25"
												min="10"
												max="100"
												hide-details
											/>
										</v-col>
										<v-col :cols="12">
											<v-subheader class="pl-0">Profondeur (mm)</v-subheader>
										</v-col>
										<v-col :cols="12">
											<v-slider
												v-model="mangaDepth"
												thumb-label="always"
												thumb-size="25"
												min="100"
												max="300"
												hide-details
											/>
										</v-col>
										<v-col :cols="12">
											<v-subheader class="pl-0">Hauteur (mm)</v-subheader>
										</v-col>
										<v-col :cols="12">
											<v-slider
												v-model="mangaHeight"
												thumb-label="always"
												thumb-size="25"
												min="150"
												max="350"
												hide-details
											/>
										</v-col>
									</v-row>
								</v-col>
								<v-col :cols="4">
									<v-col>
										<v-color-picker
											v-model="pickedColor"
											class="ma-2"
											dot-size="15"
											hide-canvas
											show-swatches
											swatches-max-height="250"
										></v-color-picker> </v-col
								></v-col>
							</v-row>
						</v-container>
					</v-expand-transition>
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
		},
		possibleTypes: {
			type: Array,
			default: []
		},
		possibleGenres: {
			type: Array,
			default: []
		}
	},
	data() {
		return {
			edit: false,
			show: false,
			mangaWidth: 17,
			mangaHeight: 184,
			mangaDepth: 130,
			pickedColor: ''
		};
	},
	computed: {
		mangaDimensions() {
			return {
				width: this.mangaWidth,
				height: this.mangaHeight,
				depth: this.mangaDepth
			};
		}
	},
	mounted() {},
	methods: {
		clickEdit() {
			if (this.edit) {
				this.$emit('clickEdit', this.item);
			}
			this.edit = !this.edit;
		}
	}
};
</script>
<style scoped></style>

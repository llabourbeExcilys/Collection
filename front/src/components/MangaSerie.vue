<template>
	<div>
		<v-card>
			<v-container fluid>
				<template v-if="edit">
					<v-row align="stretch" justify="space-around" no-gutters>
						<v-col :cols="5">
							<v-text-field v-model="item.title" outlined label="Titre"></v-text-field>
						</v-col>
						<v-col :cols="3">
							<v-text-field v-model="item.autor" outlined label="Auteur"></v-text-field>
						</v-col>
						<v-col :cols="2">
							<v-text-field v-model="item.editor" outlined label="Edition"></v-text-field>
						</v-col>
						<v-col :cols="1">
							<v-row align="center" justify="center">
								<v-col :cols="5">
									<v-tooltip :disabled="show" top>
										<template v-slot:activator="{ on }">
											<v-btn v-on="on" icon @click="show = !show">
												<v-icon large>{{ show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
											</v-btn>
										</template>
										<span>Afficher les détails</span>
									</v-tooltip>
								</v-col>
								<v-col :cols="5">
									<v-btn @click="clickEdit" icon>
										<v-icon>{{ 'mdi-pencil-off' }}</v-icon>
									</v-btn>
								</v-col>
							</v-row>
						</v-col>
					</v-row>

					<v-row align="baseline" justify="space-around" no-gutters>
						<v-col :cols="2">
							<v-text-field
								v-model="item.owned"
								v-if="edit"
								outlined
								label="Possédé"
								type="number"
								@input="item.owned = parseInt($event)"
								:rules="[rules.numberOwnedCantBeSupToPublished]"
							>
							</v-text-field>
						</v-col>
						<v-col :cols="2">
							<v-text-field
								v-model="item.published"
								v-if="edit"
								outlined
								label="Publié"
								type="number"
								@input="item.published = parseInt($event)"
							></v-text-field>
						</v-col>
						<v-col :cols="2">
							<v-select
								v-model="item.type"
								outlined
								:items="possibleTypes"
								:rules="[v => !!v || 'Le type est requis']"
								label="Type"
								required
							></v-select>
						</v-col>
						<v-col :cols="5">
							<v-combobox
								v-model="item.genres"
								:items="possibleGenres"
								label="Genres"
								chips
								outlined
								multiple
								small-chips
							></v-combobox>
						</v-col>
					</v-row>
					<v-row v-if="show" dense align="center" justify="center">
						<v-divider />
					</v-row>
					<v-row v-if="show" justify="center">
						<v-col :cols="6">
							<threebook
								:editMode="edit"
								:title="item.title"
								:numberPublished="item.published"
								:numberPossessed="item.owned"
								:mangaDimensions="mangaDimensions"
								:color="item.color"
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
						<v-col :cols="3">
							<v-color-picker
								v-model="item.color"
								class="ma-2"
								dot-size="15"
								hide-canvas
								show-swatches
								swatches-max-height="250"
							></v-color-picker>
						</v-col>
					</v-row>
				</template>

				<template v-else>
					<v-row dense align="center" justify="center" no-gutters>
						<v-col :cols="4">
							<v-card-title>
								{{ item.title }}
							</v-card-title>
							<v-card-subtitle> {{ item.edition }} ({{ item.editor }}) </v-card-subtitle>
						</v-col>
						<v-col :cols="2">
							{{ item.autor }}
						</v-col>
						<v-col :cols="1">
							<v-chip
								class="ma-2"
								:color="item.owned === item.published ? 'green' : 'orange'"
								text-color="white"
							>
								{{ item.owned }} / {{ item.published }}
							</v-chip>
						</v-col>
						<v-col :cols="1">
							{{ item.type }}
						</v-col>
						<v-col :cols="3">
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
						</v-col>
						<v-col>
							<v-card-actions>
								<v-tooltip :disabled="show" top>
									<template v-slot:activator="{ on }">
										<v-btn v-on="on" icon @click="show = !show">
											<v-icon large>{{ show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
										</v-btn>
									</template>
									<span>Afficher les détails</span>
								</v-tooltip>
								<v-tooltip v-if="show" :disabled="edit" top>
									<template v-slot:activator="{ on }">
										<v-btn v-on="on" @click="clickEdit" icon>
											<v-icon>{{ 'mdi-pencil' }}</v-icon>
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
					<v-row v-if="show" justify="center">
						<v-col :cols="10">
							<threebook
								:editMode="edit"
								:title="item.title"
								:numberPublished="item.published"
								:numberPossessed="item.owned"
								:mangaDimensions="mangaDimensions"
								:color="item.color"
							/>
						</v-col>
					</v-row>
				</template>
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
			default: () => []
		},
		possibleGenres: {
			type: Array,
			default: () => []
		}
	},
	data() {
		return {
			edit: false,
			show: false,
			mangaWidth: 17,
			mangaHeight: 184,
			mangaDepth: 130,
			rules: {
				numberOwnedCantBeSupToPublished: owned =>
					owned <= this.item.published || 'Ne peut dépasser le nombre publié.'
			}
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

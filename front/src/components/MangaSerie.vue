<template>
	<div>
		<v-card>
			<v-container fluid>
				<!-- Edit Mode -->
				<template v-if="edit">
					<v-form ref="form">
						<v-row align="stretch" justify="space-around" no-gutters>
							<v-col :cols="5">
								<v-text-field
									v-model="item.title"
									:rules="[rules.requiredField]"
									outlined
									label="Titre"
								></v-text-field>
							</v-col>
							<v-col :cols="3">
								<v-combobox
									v-model="item.authors"
									:items="possibleAuthors"
									:item-text="item => item.firstName + ' ' + item.lastName"
									:rules="[rules.requiredGenre]"
									:label="item.authors.length > 1 ? 'Auteurs' : 'Autheur'"
									outlined
									multiple
								></v-combobox>
							</v-col>
							<v-col :cols="2">
								<v-select
									v-model="item.editor"
									:items="possibleEditors"
									:item-text="item => item.name"
									:rules="[rules.requiredField]"
									label="Editeur"
									return-object
									outlined
								></v-select>
							</v-col>
							<v-col :cols="1">
								<v-row align="center" justify="start">
									<!-- Adding a new item -->
									<template v-if="isNewItem">
										<v-col :cols="4">
											<v-tooltip top>
												<template v-slot:activator="{ on }">
													<v-btn v-on="on" icon @click="clickValidateEdition">
														<v-icon color="green">mdi-check-bold</v-icon>
													</v-btn>
												</template>
												<span>Valider</span>
											</v-tooltip>
										</v-col>
										<v-col :cols="4">
											<v-tooltip top>
												<template v-slot:activator="{ on }">
													<v-btn v-on="on" icon @click="$emit('click-cancel-new-item')">
														<v-icon color="red">mdi-close-circle</v-icon>
													</v-btn>
												</template>
												<span>Supprimer</span>
											</v-tooltip>
										</v-col>
									</template>
									<!-- Editing an existing item -->
									<template v-else>
										<v-col :cols="3">
											<v-tooltip top>
												<template v-slot:activator="{ on }">
													<v-btn v-on="on" icon @click="clickValidateEdition" :disabled="!isModified">
														<v-icon color="green">mdi-check-bold</v-icon>
													</v-btn>
												</template>
												<span>Valider</span>
											</v-tooltip>
										</v-col>
										<v-col :cols="3">
											<v-tooltip top>
												<template v-slot:activator="{ on }">
													<v-btn v-on="on" icon @click="clickRemoveItem">
														<v-icon color="red">mdi-close-circle</v-icon>
													</v-btn>
												</template>
												<span>Supprimer</span>
											</v-tooltip>
										</v-col>
										<v-col :cols="3">
											<v-tooltip top>
												<template v-slot:activator="{ on }">
													<v-btn v-on="on" icon @click="clickCancel">
														<v-icon>mdi-cancel</v-icon>
													</v-btn>
												</template>
												<span>Annuler</span>
											</v-tooltip>
										</v-col>
									</template>
								</v-row>
							</v-col>
						</v-row>

						<v-row align="baseline" justify="space-around" no-gutters>
							<v-col :cols="2">
								<v-text-field
									v-model="item.owned"
									v-if="edit"
									label="Possédé"
									min="0"
									outlined
									type="number"
									:rules="[rules.numberOwnedCantBeSupToPublished]"
									@input="item.owned = parseInt($event)"
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
									min="0"
									:rules="[rules.atLeastOnePublished, rules.numberToPublishedCantBeInfToOwned]"
									@input="item.published = parseInt($event)"
								></v-text-field>
							</v-col>
							<v-col :cols="2">
								<v-text-field
									v-model="item.edition"
									:rules="[rules.requiredField]"
									outlined
									label="Edition"
								></v-text-field>
							</v-col>
							<v-col :cols="5">
								<v-combobox
									v-model="item.genres"
									:items="possibleGenres"
									:item-text="item => item.name"
									:rules="[rules.requiredGenre]"
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
									:gltfScene="gltfScene"
									:title="item.title"
									:numberPublished="item.published"
									:numberPossessed="item.owned"
									:mangaDimensions="mangaDimensions"
									:color="item.defaultColor"
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
									v-model="item.defaultColor"
									class="ma-2"
									dot-size="15"
									hide-canvas
									show-swatches
									swatches-max-height="250"
								></v-color-picker>
							</v-col>
						</v-row>
					</v-form>
				</template>
				<!-- View Mode -->
				<template v-else>
					<v-row dense align="center" no-gutters>
						<v-col :cols="3">
							<v-card-title>
								{{ item.title }}
							</v-card-title>
							<v-card-subtitle class="d-flex justify-start">
								{{ item.edition + ' (' + item.editor.name + ')' }}
							</v-card-subtitle>
						</v-col>
						<v-col :cols="2" class="d-flex flex-column">
							<v-card-text class="pa-1" v-for="author in item.authors" v-bind:key="author.id">
								{{ author.firstName + ' ' + author.lastName }}
							</v-card-text>
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
						<v-col :cols="4">
							<v-chip
								v-for="genre in item.genres"
								v-bind:key="genre.id"
								v-text="genre.name"
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
							<v-row align="center" justify="center">
								<v-col :cols="4">
									<v-tooltip :disabled="show" top>
										<template v-slot:activator="{ on }">
											<v-btn v-on="on" icon @click="show = !show">
												<v-icon large>{{ show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
											</v-btn>
										</template>
										<span>Afficher les détails</span>
									</v-tooltip>
								</v-col>
								<v-col :cols="4">
									<v-tooltip v-if="show" top>
										<template v-slot:activator="{ on }">
											<v-btn v-on="on" @click="clickEdit" icon>
												<v-icon>{{ 'mdi-pencil' }}</v-icon>
											</v-btn>
										</template>
										<span>Editer l'élément</span>
									</v-tooltip>
								</v-col>
							</v-row>
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
								:color="item.defaultColor"
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
import restorableItem from '@/components/extends/RestorableItem';
import { isEmpty } from 'lodash';

export default {
	name: 'manga-serie',
	components: {
		threebook
	},
	mixins: [restorableItem],
	props: {
		isNewItem: {
			type: Boolean,
			default: false
		},
		possibleGenres: {
			type: Array,
			default: () => []
		},
		possibleAuthors: {
			type: Array,
			default: () => []
		},
		possibleEditors: {
			type: Array,
			default: () => []
		}
	},
	data() {
		return {
			validatingItem: false,
			edit: this.isNewItem,
			show: this.isNewItem,
			mangaWidth: 17,
			mangaHeight: 184,
			mangaDepth: 130,
			rules: {
				atLeastOnePublished: published => published > 0 || 'Au moins une publication',
				numberOwnedCantBeSupToPublished: owned =>
					owned <= this.item.published || 'Ne peut être supérieur au nombre publié',
				numberToPublishedCantBeInfToOwned: published =>
					published >= this.item.owned || 'Ne peut être inférieur au nombre possédé',
				requiredField: value => !this.validatingItem || (!isEmpty(value) && !!value) || 'Champs requis',
				requiredGenre: value =>
					!this.validatingItem || (!!value && value.length > 0) || 'Au moins un genre requis'
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
		},
		owned() {
			return this.item.owned;
		},
		published() {
			return this.item.published;
		}
	},
	mounted() {
		function validate() {
			if (this.validatingItem) {
				this.$refs.form.validate();
			}
		}
		this.$watch('owned', validate);
		this.$watch('published', validate);
	},
	methods: {
		clickCancel() {
			this.edit = false;
			this.show = false;
			this.restore();
		},
		clickEdit() {
			this.edit = true;
			this.show = true;
		},
		clickRemoveItem() {
			this.$emit('remove-item', this.item);
			this.edit = false;
			this.show = false;
		},
		clickValidateEdition() {
			this.validatingItem = true;
			if (this.$refs.form.validate()) {
				if (this.isNewItem) {
					this.$emit('click-add-new-item', this.item);
				} else {
					this.$emit('edit-item', this.item);
				}
				this.edit = false;
				this.show = false;
			}
		}
	}
};
</script>
<style scoped></style>

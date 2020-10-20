<script>
import { cloneDeep, isEqual } from 'lodash';

export default {
	props: {
		baseItem: {
			type: Object,
			required: true
		},
		isBlocked: {
			type: Boolean,
			default: false
		},
		isNew: {
			type: Boolean,
			default: false
		}
	},
	data() {
		return {
			backupItem: {},
			item: {},
			isValid: null
		};
	},
	computed: {
		defaultItem() {
			return Array.isArray(this.item) ? [] : {};
		},
		isModified() {
			return !isEqual(this.item, this.backupItem);
		}
	},
	watch: {
		baseItem() {
			this.updateBackup(this.item);
		},
		item: {
			handler() {
				this.$emit('update:isModified', this.isModified);
			},
			deep: true
		}
	},
	beforeMount() {
		this.item = cloneDeep(this.baseItem);
		this.updateBackup(this.item);
	},
	methods: {
		clear() {
			this.backupItem = this.defaultItem();
			this.restore();
		},
		restore() {
			this.item = cloneDeep(this.backupItem);
		},
		updateBackup(value) {
			this.backupItem = cloneDeep(value);
		}
	}
};
</script>

<template>
  <validation-observer v-slot="{ handleSubmit, invalid }">
    <form @submit.prevent="handleSubmit(onSubmit)">
      <!-- Название и описание -->
      <div class="row">
        <div class="col-md-6">
          <validation-provider
              name="Название"
              rules="required|min:3|max:100"
              v-slot="{ errors }">
            <base-input
                label="Название колоды*"
                v-model="form.name"
                :error="errors[0]"
                @blur="checkNameUnique"
                placeholder="Моя сильная колода">
              <template #append v-if="nameChecking">
                <i class="ni ni-loading"></i>
              </template>
            </base-input>
          </validation-provider>
        </div>
        <div class="col-md-6">
          <base-input
              label="Описание"
              v-model="form.description"
              placeholder="Необязательное описание">
          </base-input>
        </div>
      </div>

      <!-- Выбранные карты -->
      <selected-cards
          :cards="selectedCards"
          @remove="removeCard" />

      <!-- Фильтры и доступные карты -->
      <card-filters
          v-model="filters"
          class="mb-4" />

      <available-cards
          :cards="filteredCards"
          :selected="selectedCards"
          @select="toggleCard" />

      <!-- Кнопки -->
      <div class="form-actions mt-4">
        <base-button
            type="primary"
            native-type="submit"
            :disabled="invalid || !isFormValid"
            :loading="loading">
          {{ isEdit ? 'Обновить' : 'Создать' }} колоду
        </base-button>
        <base-button
            type="default"
            @click="$emit('cancel')"
            class="ml-2">
          Отмена
        </base-button>
      </div>
    </form>
  </validation-observer>
</template>

<script>
import { fetchCards } from '@/api/cards';
import { getCardImage } from '@/utils/cards';

export default {
  props: {
    initialData: {
      type: Object,
      default: () => ({
        name: '',
        description: '',
        cards: []
      })
    },
    isEdit: Boolean
  },
  data() {
    return {
      form: { ...this.initialData },
      selectedCards: [...this.initialData.cards],
      allCards: [],
      filters: {
        rarity: null,
        maxElixir: 10,
        search: ''
      },
      nameChecking: false,
      loading: false
    }
  },
  computed: {
    filteredCards() {
      return this.allCards.filter(card => {
        const matchesRarity = !this.filters.rarity || card.rarity === this.filters.rarity;
        const matchesElixir = card.elixir <= this.filters.maxElixir;
        const matchesSearch = card.name.toLowerCase().includes(this.filters.search.toLowerCase());
        return matchesRarity && matchesElixir && matchesSearch;
      });
    },
    isFormValid() {
      return this.selectedCards.length === 8;
    }
  },
  async mounted() {
    await this.loadCards();
  },
  methods: {
    async loadCards() {
      try {
        this.allCards = await fetchCards();
      } catch (error) {
        this.$emit('error', 'Ошибка загрузки карт');
      }
    },
    async checkNameUnique() {
      if (this.isEdit || !this.form.name) return;

      this.nameChecking = true;
      try {
        const { data } = await this.$axios.get('/decks/check-name', {
          params: { name: this.form.name }
        });
        if (!data.available) {
          this.$notify({
            type: 'warning',
            message: 'Колода с таким названием уже существует'
          });
        }
      } finally {
        this.nameChecking = false;
      }
    },
    toggleCard(cardId) {
      if (this.isSelected(cardId)) {
        this.removeCard(cardId);
      } else if (this.canSelect(cardId)) {
        this.selectedCards.push(cardId);
      }
    },
    removeCard(cardId) {
      this.selectedCards = this.selectedCards.filter(id => id !== cardId);
    },
    isSelected(cardId) {
      return this.selectedCards.includes(cardId);
    },
    canSelect(cardId) {
      return this.selectedCards.length < 8 || this.isSelected(cardId);
    },
    onSubmit() {
      this.loading = true;
      this.$emit('submit', {
        name: this.form.name,
        description: this.form.description,
        cards: this.selectedCards
      });
    },
    getCardImage
  }
}
</script>

<style scoped>
.form-actions {
  display: flex;
  justify-content: flex-end;
}
</style>
<template>
  <div class="deck-generator">
    <card>
      <template #header>
        <h3>Генератор колод AI</h3>
      </template>

      <!-- Форма выбора параметров -->
      <div class="row">
        <div class="col-md-6">
          <base-select
              label="Стратегия"
              :options="strategyOptions"
              v-model="form.strategy">
          </base-select>
        </div>
        <div class="col-md-6">
          <base-input
              label="Ваш текущий рейтинг"
              type="number"
              v-model="form.rating">
          </base-input>
        </div>
      </div>

      <!-- Дополнительные настройки -->
      <collapse v-model="advancedVisible" class="mt-4">
        <template #header>
          <h5 class="mb-0">Дополнительные настройки <i class="ni ni-bold-down"></i></h5>
        </template>
        <div class="row mt-3">
          <div class="col-md-4">
            <base-checkbox v-model="form.includeLegendary">Включая легендарные</base-checkbox>
          </div>
          <div class="col-md-4">
            <base-checkbox v-model="form.balanceElixir">Баланс эликсира</base-checkbox>
          </div>
        </div>
      </collapse>

      <!-- Кнопка генерации -->
      <div class="text-center mt-4">
        <base-button type="primary" @click="generateDeck" :disabled="generating">
          <span v-if="generating">
            <i class="ni ni-loading"></i> Генерация...
          </span>
          <span v-else>
            <i class="ni ni-settings"></i> Сгенерировать колоду
          </span>
        </base-button>
      </div>
    </card>

    <!-- Результаты генерации -->
    <card v-if="generatedDecks.length > 0" class="mt-4">
      <h3 slot="header">Результаты</h3>
      <div class="row">
        <div class="col-md-6" v-for="(deck, idx) in generatedDecks" :key="idx">
          <deck-preview
              :cards="deck.cards"
              :title="`Вариант ${idx + 1}`"
              :win-rate="deck.win_rate">
            <template #actions>
              <base-button size="sm" @click="saveDeck(deck)">
                <i class="ni ni-check-bold"></i> Сохранить
              </base-button>
              <base-button size="sm" type="default" @click="analyzeDeck(deck)">
                <i class="ni ni-zoom-split-in"></i> Анализ
              </base-button>
            </template>
          </deck-preview>
        </div>
      </div>
    </card>
  </div>
</template>

<script>
import { generateDeck, saveGeneratedDeck } from '@/api/decks';
import { loadDeckGenerationModel } from '@/ml/deck-generator';

export default {
  data() {
    return {
      form: {
        strategy: 'balanced',
        rating: 5000,
        includeLegendary: true,
        balanceElixir: true
      },
      strategyOptions: [
        { value: 'balanced', label: 'Сбалансированная' },
        { value: 'offensive', label: 'Атакующая' },
        { value: 'defensive', label: 'Защитная' },
        { value: 'custom', label: 'Кастомная стратегия' }
      ],
      advancedVisible: false,
      generating: false,
      generatedDecks: [],
      generationModel: null
    }
  },
  async mounted() {
    this.generationModel = await loadDeckGenerationModel();
  },
  methods: {
    async generateDeck() {
      try {
        this.generating = true;

        // Используем модель из feature/machine_learning
        const generated = await this.generationModel.generate({
          strategy: this.form.strategy,
          rating: this.form.rating,
          options: {
            allowLegendary: this.form.includeLegendary,
            balanceElixir: this.form.balanceElixir
          }
        });

        this.generatedDecks = generated.slice(0, 2); // Показываем 2 лучших варианта
      } catch (error) {
        console.error('Ошибка генерации:', error);
        this.$notify({
          type: 'danger',
          message: 'Ошибка при генерации колоды'
        });
      } finally {
        this.generating = false;
      }
    },
    async saveDeck(deck) {
      try {
        await saveGeneratedDeck({
          name: `AI ${this.form.strategy}`,
          cards: deck.cards,
          description: `Сгенерировано для рейтинга ${this.form.rating}`
        });
        this.$notify({
          type: 'success',
          message: 'Колода сохранена в вашу коллекцию!'
        });
        this.$router.push('/decks');
      } catch (error) {
        this.$notify({
          type: 'danger',
          message: 'Ошибка при сохранении колоды'
        });
      }
    },
    analyzeDeck(deck) {
      this.$router.push({
        path: '/deck-analysis',
        query: { cards: deck.cards.join(',') }
      });
    }
  }
}
</script>
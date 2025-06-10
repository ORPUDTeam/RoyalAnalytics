<template>
  <div class="dashboard">
    <!-- 1. Основные метрики из API -->
    <div class="row">
      <div class="col-xl-4">
        <stats-card title="Текущие кубки" :value="profile.trophies" icon="ni ni-trophy">
          <template #footer>
            <trend-indicator :value="ratingTrend" />
          </template>
        </stats-card>
      </div>
      <div class="col-xl-4">
        <stats-card title="Награды" :value="profile.rewards.length" icon="ni ni-medal-85">
          <template #footer>
            <router-link to="/profile">Все награды</router-link>
          </template>
        </stats-card>
      </div>
      <div class="col-xl-4">
        <stats-card title="Активность" :value="mlData.activity_level" icon="ni ni-watch-time">
          <template #footer>
            <span>{{ mlData.battle_count }} боев</span>
          </template>
        </stats-card>
      </div>
    </div>

    <!-- 2. Текущая колода -->
    <card class="mt-4">
      <h3 slot="header">Рекомендуемая колода (AI)</h3>
      <div class="row">
        <div class="col-md-8">
          <deck-comparison
              :user-deck="profile.current_deck"
              :ai-deck="mlData.recommended_deck"
              :win-probability="mlData.win_probability" />
        </div>
        <div class="col-md-4">
          <base-button @click="applyDeck" type="primary" block>
            <i class="ni ni-check-bold"></i> Применить колоду
          </base-button>
          <div class="mt-3">
            <h5>Статистика колоды:</h5>
            <ul>
              <li>Сред. эликсир: {{ mlData.avg_elixir }}</li>
              <li>Баланс: {{ mlData.deck_balance }}</li>
              <li>Совместимость: {{ mlData.deck_synergy }}%</li>
            </ul>
          </div>
        </div>
      </div>
    </card>

    <!-- 3. График из rating-history -->
    <card class="mt-4">
      <h3 slot="header">Динамика кубков</h3>
      <trophy-chart :data="ratingHistory" />
    </card>

    <!-- 4. ML предсказания из master ветки -->
    <card class="mt-4">
      <h3 slot="header">Прогноз на неделю</h3>
      <div class="row">
        <div class="col-md-6">
          <h5>Ожидаемый диапазон кубков:</h5>
          <range-slider
              :min="mlData.trophy_range.min"
              :max="mlData.trophy_range.max"
              :current="profile.trophies" />
        </div>
        <div class="col-md-6">
          <h5>Рекомендации:</h5>
          <ul class="list-unstyled">
            <li v-for="(tip, idx) in mlData.tips" :key="idx" class="mb-2">
              <badge :type="tip.type">{{ tip.category }}</badge>
              {{ tip.text }}
            </li>
          </ul>
        </div>
      </div>
    </card>
  </div>
</template>

<script>
import { fetchPlayerProfile, fetchRatingHistory } from '@/api/profile';
import { predictDeck, loadMLModel } from '@/ml-services';
import TrophyChart from '@/components/Charts/TrophyChart';

export default {
  components: { TrophyChart },
  data() {
    return {
      profile: {
        trophies: 0,
        rewards: [],
        current_deck: []
      },
      ratingHistory: [],
      mlData: {
        recommended_deck: [],
        win_probability: 0,
        activity_level: 'Низкая',
        battle_count: 0,
        trophy_range: { min: 0, max: 0 },
        tips: []
      }
    }
  },
  computed: {
    ratingTrend() {
      if (this.ratingHistory.length < 2) return 0;
      const last = this.ratingHistory[this.ratingHistory.length - 1].rating;
      const prev = this.ratingHistory[0].rating;
      return ((last - prev) / prev * 100).toFixed(1);
    }
  },
  async mounted() {
    await Promise.all([
      this.loadProfileData(),
      this.initML()
    ]);
  },
  methods: {
    async loadProfileData() {
      const [profile, history] = await Promise.all([
        fetchPlayerProfile(),
        fetchRatingHistory()
      ]);
      this.profile = profile;
      this.ratingHistory = history;
    },
    async initML() {
      await loadMLModel();
      this.mlData = await predictDeck(this.profile.current_deck);
    },
    async applyDeck() {
      try {
        await this.$axios.post('/decks/new', {
          name: 'AI Оптимизированная',
          cards: this.mlData.recommended_deck
        });
        this.$notify({
          type: 'success',
          message: 'Колода успешно применена!'
        });
        this.profile.current_deck = this.mlData.recommended_deck;
      } catch (error) {
        this.$notify({
          type: 'danger',
          message: 'Ошибка: ' + error.message
        });
      }
    }
  }
}
</script>
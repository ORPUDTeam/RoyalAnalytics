<template>
  <div class="card-detail">
    <div class="row">
      <!-- Основная информация -->
      <div class="col-md-4">
        <card>
          <img :src="card.image_url" class="card-image">
          <div class="text-center mt-3">
            <h2>{{ card.name }}</h2>
            <div class="card-meta">
              <badge :type="getRarityColor(card.rarity)">{{ card.rarity }}</badge>
              <badge type="default">{{ card.elixir }} эликсира</badge>
            </div>
          </div>
        </card>

        <card class="mt-4">
          <h5 slot="header">Статистика</h5>
          <div class="stats">
            <div class="stat-item">
              <span class="label">Винрейт:</span>
              <span class="value">{{ stats.win_rate }}%</span>
              <progress-bar
                  :value="stats.win_rate"
                  :max="100"
                  :type="getWinRateColor(stats.win_rate)" />
            </div>
            <div class="stat-item">
              <span class="label">Использование:</span>
              <span class="value">{{ stats.usage_rate }}%</span>
            </div>
            <div class="stat-item">
              <span class="label">Лучшая арена:</span>
              <span class="value">{{ stats.best_arena }}</span>
            </div>
          </div>
        </card>
      </div>

      <!-- Подробная информация -->
      <div class="col-md-8">
        <card>
          <h3 slot="header">Описание</h3>
          <p>{{ card.description }}</p>

          <div class="row mt-4">
            <div class="col-md-6">
              <h5>Характеристики</h5>
              <table class="table table-sm">
                <tr v-for="(value, key) in card.stats" :key="key">
                  <th>{{ formatStatKey(key) }}</th>
                  <td>{{ value }}</td>
                </tr>
              </table>
            </div>
            <div class="col-md-6">
              <h5>История изменений</h5>
              <ul class="history-list">
                <li v-for="(change, idx) in card.history" :key="idx">
                  <span class="date">{{ change.date }}</span>
                  <span class="text">{{ change.description }}</span>
                </li>
              </ul>
            </div>
          </div>
        </card>

        <!-- Совместимость -->
        <card class="mt-4">
          <h3 slot="header">Лучшие сочетания</h3>
          <div class="row">
            <div class="col-md-4 mb-3" v-for="combo in topCombos" :key="combo.card_id">
              <div class="combo-card">
                <img :src="getCardImage(combo.card_id)" class="combo-image">
                <div class="combo-info">
                  <h6>{{ getCardName(combo.card_id) }}</h6>
                  <p>Совместимость: {{ combo.synergy }}%</p>
                </div>
              </div>
            </div>
          </div>
        </card>
      </div>
    </div>
  </div>
</template>

<script>
import { fetchCardDetails, fetchCardStats } from '@/api/cards';
import { getCardImage, getCardName } from '@/utils/cards';

export default {
  data() {
    return {
      card: {
        name: '',
        rarity: '',
        elixir: 0,
        image_url: '',
        description: '',
        stats: {},
        history: []
      },
      stats: {
        win_rate: 0,
        usage_rate: 0,
        best_arena: ''
      },
      topCombos: []
    }
  },
  async mounted() {
    await this.loadCardData(this.$route.params.id);
  },
  methods: {
    async loadCardData(cardId) {
      try {
        const [details, stats] = await Promise.all([
          fetchCardDetails(cardId),
          fetchCardStats(cardId)
        ]);
        this.card = details;
        this.stats = stats.base_stats;
        this.topCombos = stats.top_combos;
      } catch (error) {
        console.error('Ошибка загрузки данных карты:', error);
      }
    },
    getRarityColor(rarity) {
      const colors = {
        Common: 'default',
        Rare: 'primary',
        Epic: 'warning',
        Legendary: 'danger',
        Champion: 'success'
      };
      return colors[rarity] || 'default';
    },
    getWinRateColor(rate) {
      if (rate > 55) return 'success';
      if (rate > 50) return 'warning';
      return 'danger';
    },
    formatStatKey(key) {
      const map = {
        'damage': 'Урон',
        'hitpoints': 'Здоровье',
        'targets': 'Цели',
        'range': 'Дальность'
      };
      return map[key] || key;
    },
    getCardImage,
    getCardName
  }
}
</script>

<style scoped>
.card-image {
  width: 100%;
  border-radius: 8px;
}
.card-meta {
  display: flex;
  justify-content: center;
  gap: 0.5rem;
  margin-top: 0.5rem;
}
.stats {
  padding: 0 1rem;
}
.stat-item {
  margin-bottom: 1rem;
}
.stat-item .label {
  display: inline-block;
  width: 120px;
  font-weight: bold;
}
.stat-item .value {
  margin-left: 1rem;
}
.history-list {
  list-style: none;
  padding-left: 0;
}
.history-list li {
  margin-bottom: 0.5rem;
  display: flex;
}
.history-list .date {
  font-weight: bold;
  min-width: 80px;
}
.history-list .text {
  flex-grow: 1;
}
.combo-card {
  display: flex;
  align-items: center;
  padding: 0.5rem;
  background: #f8f9fe;
  border-radius: 8px;
}
.combo-image {
  width: 50px;
  height: 60px;
  margin-right: 1rem;
  border-radius: 5px;
}
.combo-info {
  flex-grow: 1;
}
</style>
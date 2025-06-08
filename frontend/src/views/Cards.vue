<template>
  <div class="card-detail">
    <template v-if="loading">
      <loader />
    </template>

    <template v-else>
      <!-- Основная информация -->
      <div class="row">
        <div class="col-md-4">
          <card>
            <img :src="card.image_url" class="card-img-top">
            <div class="card-body text-center">
              <h3>{{ card.name }}</h3>
              <div class="card-meta">
                <badge :type="getRarityColor(card.rarity)">{{ card.rarity }}</badge>
                <badge type="default">{{ card.elixir }} эликсира</badge>
              </div>
              <p class="mt-3">{{ card.description }}</p>
            </div>
          </card>
        </div>

        <div class="col-md-8">
          <!-- Статистика -->
          <card>
            <h3 slot="header">Статистика использования</h3>
            <div class="row">
              <div class="col-md-6">
                <h5>Популярность в колодах</h5>
                <bar-chart :data="usageData" />
              </div>
              <div class="col-md-6">
                <h5>Эффективность</h5>
                <div class="effectiveness">
                  <div class="effectiveness-item">
                    <span class="label">Винрейт:</span>
                    <span class="value">{{ cardStats.win_rate }}%</span>
                    <progress-bar :value="cardStats.win_rate" :max="100" type="success" />
                  </div>
                  <div class="effectiveness-item">
                    <span class="label">Использований:</span>
                    <span class="value">{{ cardStats.usage_count }}</span>
                  </div>
                </div>
              </div>
            </div>
          </card>

          <!-- Совместимость -->
          <card class="mt-4">
            <h3 slot="header">Лучшие сочетания</h3>
            <div class="combo-cards">
              <div v-for="combo in cardStats.top_combos" :key="combo.card_id" class="combo-item">
                <img :src="getCardImage(combo.card_id)" class="combo-card">
                <div class="combo-info">
                  <h5>{{ getCardName(combo.card_id) }}</h5>
                  <p>Совместная эффективность: {{ combo.synergy }}%</p>
                </div>
              </div>
            </div>
          </card>
        </div>
      </div>

      <!-- Аналитика -->
      <card class="mt-4">
        <h3 slot="header">AI Анализ карты</h3>
        <div class="row">
          <div class="col-md-6">
            <h5>Рекомендации по использованию</h5>
            <ul class="ai-tips">
              <li v-for="(tip, idx) in aiTips" :key="idx">
                <i class="ni ni-check-bold text-success"></i> {{ tip }}
              </li>
            </ul>
          </div>
          <div class="col-md-6">
            <h5>Лучшие стратегии</h5>
            <div class="strategies">
              <badge v-for="strategy in cardStats.best_strategies"
                     :key="strategy"
                     :type="getStrategyBadge(strategy)">
                {{ strategy }}
              </badge>
            </div>
          </div>
        </div>
      </card>
    </template>
  </div>
</template>

<script>
import { fetchCardDetails, fetchCardStats } from '@/api/cards';
import { getCardImage, getCardName } from '@/utils/cards';

export default {
  data() {
    return {
      card: null,
      cardStats: null,
      loading: true,
      aiTips: [],
      usageData: {
        labels: ['0-3000', '3000-4000', '4000-5000', '5000-6000', '6000+'],
        datasets: [{
          label: 'Использование (%)',
          data: [15, 25, 40, 30, 20]
        }]
      }
    }
  },
  async mounted() {
    await this.loadCardData(this.$route.params.id);
  },
  methods: {
    async loadCardData(cardId) {
      try {
        this.loading = true;
        const [details, stats] = await Promise.all([
          fetchCardDetails(cardId),
          fetchCardStats(cardId)
        ]);
        this.card = details;
        this.cardStats = stats;
        this.generateAiTips();
      } catch (error) {
        console.error('Ошибка загрузки карты:', error);
      } finally {
        this.loading = false;
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
    getStrategyBadge(strategy) {
      const map = {
        'Оффензивная': 'danger',
        'Дефензивная': 'info',
        'Контроль': 'warning',
        'Быстрый цикл': 'success'
      };
      return map[strategy] || 'default';
    },
    generateAiTips() {
      const tips = [];
      if (this.card.elixir <= 3) {
        tips.push('Эффективна в быстрых циклах колод');
      }
      if (this.cardStats.win_rate > 55) {
        tips.push('Высокий винрейт - рекомендуем включить в колоду');
      }
      if (this.card.rarity === 'Legendary') {
        tips.push('Легендарная карта - используйте с умом');
      }
      this.aiTips = tips.length > 0 ? tips : ['Карта универсальна в большинстве стратегий'];
    },
    getCardImage,
    getCardName
  }
}
</script>

<style scoped>
.card-meta {
  display: flex;
  gap: 0.5rem;
  justify-content: center;
  margin-bottom: 1rem;
}
.effectiveness-item {
  margin-bottom: 1rem;
}
.effectiveness-item .label {
  display: inline-block;
  width: 120px;
  font-weight: bold;
}
.effectiveness-item .value {
  margin-left: 1rem;
}
.combo-cards {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 1rem;
}
.combo-item {
  display: flex;
  align-items: center;
  padding: 0.5rem;
  border-radius: 8px;
  background: #f8f9fe;
}
.combo-card {
  width: 50px;
  height: 60px;
  margin-right: 1rem;
  border-radius: 5px;
}
.ai-tips {
  list-style-type: none;
  padding-left: 0;
}
.ai-tips li {
  padding: 0.5rem 0;
  border-bottom: 1px solid #eee;
}
.strategies {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
}
</style>
<template>
  <div class="decks-page">
    <!-- Заголовок с кнопкой создания -->
    <div class="row mb-4">
      <div class="col">
        <h1>Мои колоды</h1>
      </div>
      <div class="col-auto">
        <base-button type="primary" @click="$router.push('/decks/new')">
          <i class="ni ni-fat-add"></i> Создать колоду
        </base-button>
      </div>
    </div>

    <!-- Табы для переключения между типами колод -->
    <card>
      <div class="nav-wrapper">
        <ul class="nav nav-pills nav-fill flex-column flex-md-row" role="tablist">
          <li class="nav-item">
            <a class="nav-link mb-sm-3 mb-md-0 active" data-toggle="tab"
               href="#user-decks" role="tab" aria-selected="true">
              <i class="ni ni-collection mr-2"></i> Мои колоды
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link mb-sm-3 mb-md-0" data-toggle="tab"
               href="#generated-decks" role="tab" aria-selected="false">
              <i class="ni ni-vector mr-2"></i> Сгенерированные
            </a>
          </li>
        </ul>
      </div>

      <div class="tab-content">
        <!-- Пользовательские колоды -->
        <div class="tab-pane fade show active" id="user-decks" role="tabpanel">
          <div class="table-responsive">
            <table class="table align-items-center">
              <thead class="thead-light">
              <tr>
                <th scope="col">Название</th>
                <th scope="col">Карты</th>
                <th scope="col">Статистика</th>
                <th scope="col">Действия</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="deck in userDecks" :key="'user-'+deck.id">
                <th scope="row">
                  <span class="font-weight-bold">{{ deck.name }}</span>
                  <p class="text-sm text-muted mb-0" v-if="deck.description">
                    {{ deck.description }}
                  </p>
                </th>
                <td>
                  <div class="avatar-group">
                      <span v-for="(cardId, idx) in deck.cards.slice(0, 5)" :key="idx"
                            class="avatar avatar-sm rounded-circle">
                        <img :src="getCardImage(cardId)" class="rounded-circle">
                      </span>
                    <span v-if="deck.cards.length > 5" class="avatar avatar-sm bg-secondary rounded-circle">
                        +{{ deck.cards.length - 5 }}
                      </span>
                  </div>
                </td>
                <td>
                  <div class="d-flex flex-wrap">
                    <badge class="mr-2 mb-1" type="primary">
                      {{ deck.win_rate || 0 }}% побед
                    </badge>
                    <badge type="info">
                      {{ deck.usage_count || 0 }} использований
                    </badge>
                  </div>
                </td>
                <td class="td-actions">
                  <base-button size="sm" type="default"
                               @click="$router.push(`/decks/${deck.id}/edit`)">
                    <i class="ni ni-ruler-pencil"></i>
                  </base-button>
                  <base-button size="sm" type="danger" @click="confirmDelete(deck)">
                    <i class="ni ni-fat-remove"></i>
                  </base-button>
                  <base-button size="sm" type="success" @click="applyDeck(deck)">
                    <i class="ni ni-check-bold"></i>
                  </base-button>
                </td>
              </tr>
              <tr v-if="userDecks.length === 0">
                <td colspan="4" class="text-center py-4">
                  <p class="text-muted">У вас пока нет созданных колод</p>
                  <base-button type="primary" @click="$router.push('/decks/new')">
                    Создать первую колоду
                  </base-button>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>

        <!-- Сгенерированные колоды -->
        <div class="tab-pane fade" id="generated-decks" role="tabpanel">
          <div class="table-responsive">
            <table class="table align-items-center">
              <thead class="thead-light">
              <tr>
                <th scope="col">Название</th>
                <th scope="col">Карты</th>
                <th scope="col">Дата создания</th>
                <th scope="col">Действия</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="deck in generatedDecks" :key="'gen-'+deck.id">
                <th scope="row">
                  <span class="font-weight-bold">{{ deck.name }}</span>
                  <badge type="info" class="ml-2">AI</badge>
                  <p class="text-sm text-muted mb-0" v-if="deck.description">
                    {{ deck.description }}
                  </p>
                </th>
                <td>
                  <div class="avatar-group">
                      <span v-for="(cardId, idx) in deck.cards.slice(0, 5)" :key="idx"
                            class="avatar avatar-sm rounded-circle">
                        <img :src="getCardImage(cardId)" class="rounded-circle">
                      </span>
                  </div>
                </td>
                <td>
                  {{ formatDate(deck.created_at) }}
                </td>
                <td>
                  <base-button size="sm" type="success" @click="applyDeck(deck)">
                    <i class="ni ni-check-bold"></i> Применить
                  </base-button>
                </td>
              </tr>
              <tr v-if="generatedDecks.length === 0">
                <td colspan="4" class="text-center py-4">
                  <p class="text-muted">Нет сгенерированных колод</p>
                  <base-button type="primary" @click="$router.push('/decks/generate')">
                    Сгенерировать колоду
                  </base-button>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </card>

    <!-- Модальное окно подтверждения удаления -->
    <modal :show="deleteModal.show" @close="deleteModal.show = false">
      <h5 slot="header" class="modal-title">Удалить колоду?</h5>
      <div class="py-3">
        <p>Вы уверены, что хотите удалить колоду <strong>"{{ deleteModal.deckName }}"</strong>?</p>
        <p class="text-sm text-muted">Это действие нельзя отменить.</p>
      </div>
      <template #footer>
        <base-button type="secondary" @click="deleteModal.show = false">
          Отмена
        </base-button>
        <base-button type="danger" @click="deleteDeck">
          Удалить
        </base-button>
      </template>
    </modal>
  </div>
</template>

<script>
import { fetchUserDecks, fetchGeneratedDecks } from '@/api/decks';
import { getCardImage } from '@/utils/cards';

export default {
  data() {
    return {
      userDecks: [],
      generatedDecks: [],
      deleteModal: {
        show: false,
        deckId: null,
        deckName: ''
      }
    }
  },
  async mounted() {
    await this.loadDecks();
  },
  methods: {
    async loadDecks() {
      try {
        const [userDecks, generatedDecks] = await Promise.all([
          fetchUserDecks(),
          fetchGeneratedDecks()
        ]);
        this.userDecks = userDecks;
        this.generatedDecks = generatedDecks;
      } catch (error) {
        console.error('Ошибка загрузки колод:', error);
        this.$notify({
          type: 'danger',
          message: 'Не удалось загрузить колоды'
        });
      }
    },
    confirmDelete(deck) {
      this.deleteModal = {
        show: true,
        deckId: deck.id,
        deckName: deck.name
      };
    },
    async deleteDeck() {
      try {
        await this.$axios.delete(`/decks/${this.deleteModal.deckId}`);
        this.userDecks = this.userDecks.filter(d => d.id !== this.deleteModal.deckId);
        this.deleteModal.show = false;
        this.$notify({
          type: 'success',
          message: 'Колода успешно удалена'
        });
      } catch (error) {
        console.error('Ошибка удаления колоды:', error);
        this.$notify({
          type: 'danger',
          message: 'Не удалось удалить колоду'
        });
      }
    },
    async applyDeck(deck) {
      try {
        await this.$axios.patch('/profile/current-deck', { deck_id: deck.id });
        this.$notify({
          type: 'success',
          message: 'Колода успешно применена!'
        });
      } catch (error) {
        console.error('Ошибка применения колоды:', error);
        this.$notify({
          type: 'danger',
          message: 'Не удалось применить колоду'
        });
      }
    },
    getCardImage,
    formatDate(date) {
      return new Date(date).toLocaleDateString('ru-RU');
    }
  }
}
</script>

<style scoped>
.avatar-group {
  display: flex;
  align-items: center;
}
.avatar {
  position: relative;
  width: 36px;
  height: 36px;
  margin-right: -10px;
  transition: all 0.3s;
  border: 2px solid #fff;
}
.avatar:hover {
  transform: translateY(-5px);
  z-index: 1;
}
.avatar-sm {
  width: 32px;
  height: 32px;
}
.td-actions {
  white-space: nowrap;
  width: 1%;
}
.td-actions .btn {
  margin-right: 5px;
}
.nav-wrapper {
  padding: 1rem;
}
.tab-content {
  padding: 0 1rem 1rem;
}
</style>
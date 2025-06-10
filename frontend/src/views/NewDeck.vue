<template>
  <div class="deck-creator">
    <card>
      <template #header>
        <h3>Создание колоды</h3>
        <p class="text-muted">Выберите 8 карт для своей колоды</p>
      </template>

      <deck-form
          @submit="createDeck"
          @cancel="$router.push('/decks')" />
    </card>
  </div>
</template>

<script>
import DeckForm from '@/views/components/Form.vue';

export default {
  components: { DeckForm },
  methods: {
    async createDeck(deckData) {
      try {
        await this.$axios.post('/decks/new', deckData);
        this.$notify({
          type: 'success',
          message: 'Колода создана!'
        });
        this.$router.push('/decks');
      } catch (error) {
        this.handleError(error, 'Ошибка создания колоды');
      }
    },
    handleError(error, defaultMsg) {
      const message = error.response?.data?.message || defaultMsg;
      this.$notify({ type: 'danger', message });
    }
  }
}
</script>
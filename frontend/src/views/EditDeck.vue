<template>
  <div class="deck-editor">
    <card>
      <template #header>
        <h3>Редактирование колоды</h3>
        <p class="text-muted">{{ deck.name }} ({{ deck.cards.length }}/8 карт)</p>
      </template>

      <deck-form
          :initial-data="deck"
          :is-edit="true"
          @submit="updateDeck"
          @cancel="$router.push('/decks')" />
    </card>
  </div>
</template>

<script>
import DeckForm from '@/views/components/Form.vue';

export default {
  components: { DeckForm },
  data() {
    return {
      deck: {
        name: '',
        description: '',
        cards: []
      }
    }
  },
  async mounted() {
    await this.loadDeck(this.$route.params.deck_id);
  },
  methods: {
    async loadDeck(id) {
      try {
        const response = await this.$axios.get(`/decks/${id}`);
        this.deck = response.data;
      } catch (error) {
        if (error.response?.status === 404) {
          this.$router.replace('/decks');
        }
        this.handleError(error, 'Ошибка загрузки колоды');
      }
    },
    async updateDeck(deckData) {
      try {
        await this.$axios.patch(`/decks/${this.deck.id}`, deckData);
        this.$notify({
          type: 'success',
          message: 'Колода обновлена!'
        });
        this.$router.push('/decks');
      } catch (error) {
        this.handleError(error, 'Ошибка обновления колоды');
      }
    },
    handleError(error, defaultMsg) {
      const message = error.response?.data?.message || defaultMsg;
      this.$notify({ type: 'danger', message });
    }
  }
}
</script>
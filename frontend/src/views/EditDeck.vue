<template>
  <div>
    <Navbar />
    <Sidebar />
    <div class="p-6 ml-64 max-w-3xl">
      <h1 class="text-2xl font-bold mb-4">Редактировать колоду</h1>

      <form @submit.prevent="submit">
        <label class="block mb-2 font-medium">Название колоды</label>
        <input v-model="name" type="text" class="w-full border p-2 rounded mb-4" required />

        <label class="block mb-2 font-medium">Карты (8 шт):</label>
        <div class="grid grid-cols-4 gap-2 mb-4">
          <div
              v-for="card in cards"
              :key="card.id"
              @click="toggleCard(card)"
              :class="{
              'bg-green-200': selected.some((c) => c.id === card.id),
              'bg-gray-100': !selected.some((c) => c.id === card.id),
            }"
              class="p-2 border rounded cursor-pointer hover:bg-green-100"
          >
            {{ card.name }}
          </div>
        </div>

        <button
            type="submit"
            class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 disabled:opacity-50"
            :disabled="selected.length !== 8 || !name"
        >
          Сохранить изменения
        </button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Navbar from "@/views/components/Navbar.vue";
import Sidebar from "@/views/components/Sidebar.vue";

export default {
  components: {Sidebar, Navbar},
  data() {
    return {
      cards: [],
      selected: [],
      name: "",
    };
  },
  mounted() {
    const id = this.$route.params.deck_id;
    axios.get("/api/cards").then((res) => {
      this.cards = res.data;
    });
    axios.get(`/api/decks/${id}`).then((res) => {
      this.name = res.data.name;
      this.selected = res.data.cards; // Предполагается, что это массив объектов карт
    });
  },
  methods: {
    toggleCard(card) {
      if (this.selected.some((c) => c.id === card.id)) {
        this.selected = this.selected.filter((c) => c.id !== card.id);
      } else if (this.selected.length < 8) {
        this.selected.push(card);
      }
    },
    submit() {
      const id = this.$route.params.deck_id;
      const cardIds = this.selected.map((c) => c.id);
      axios.put(`/api/decks/${id}`, {
        name: this.name,
        cards: cardIds,
      }).then(() => {
        this.$router.push("/decks");
      });
    },
  },
};
</script>

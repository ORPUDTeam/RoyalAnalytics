<template>
  <div>
    <Navbar />
    <Sidebar />
    <div class="p-6 ml-64 max-w-3xl">
      <h1 class="text-2xl font-bold mb-4">Создать колоду</h1>

      <form @submit.prevent="submit">
        <label class="block mb-2 font-medium">Название колоды</label>
        <input v-model="name" type="text" class="w-full border p-2 rounded mb-4" required />

        <label class="block mb-2 font-medium">Выберите 8 карт:</label>
        <div class="grid grid-cols-4 gap-2 mb-4">
          <div
              v-for="card in cards"
              :key="card.id"
              @click="toggleCard(card)"
              :class="{
              'bg-green-200': selected.includes(card),
              'bg-gray-100': !selected.includes(card),
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
          Создать
        </button>
      </form>
    </div>
  </div>
</template>

<script>

import Navbar from "@/views/components/Navbar.vue";
import axios from "axios";
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
    axios.get("/api/cards").then((res) => {
      this.cards = res.data;
    });
  },
  methods: {
    toggleCard(card) {
      if (this.selected.includes(card)) {
        this.selected = this.selected.filter((c) => c !== card);
      } else if (this.selected.length < 8) {
        this.selected.push(card);
      }
    },
    submit() {
      const cardIds = this.selected.map((c) => c.id);
      axios
          .post("/api/decks", {
            name: this.name,
            cards: cardIds,
          })
          .then(() => {
            this.$router.push("/decks");
          });
    },
  },
};
</script>

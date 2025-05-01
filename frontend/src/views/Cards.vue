<template>
  <div>
    <Navbar />
    <Sidebar />
    <div class="p-6 ml-64">
      <h1 class="text-2xl font-bold mb-4">Карты</h1>

      <!-- Фильтры -->
      <div class="flex gap-4 mb-6">
        <select v-model="filters.rarity" class="border rounded px-3 py-1">
          <option value="">Любая редкость</option>
          <option>Common</option>
          <option>Rare</option>
          <option>Epic</option>
          <option>Legendary</option>
          <option>Champion</option>
        </select>

        <input
            v-model.number="filters.elixir"
            type="number"
            min="1"
            max="10"
            placeholder="Эликсир"
            class="border rounded px-3 py-1 w-28"
        />

        <input
            v-model.number="filters.arena"
            type="number"
            min="0"
            max="20"
            placeholder="Арена"
            class="border rounded px-3 py-1 w-28"
        />

        <button @click="applyFilters" class="bg-blue-600 text-white px-4 py-1 rounded hover:bg-blue-700">
          Фильтровать
        </button>
      </div>

      <!-- Список карт -->
      <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
        <div
            v-for="card in filteredCards"
            :key="card.id"
            class="p-4 border bg-white shadow rounded cursor-pointer hover:bg-gray-50"
            @click="goToCard(card.id)"
        >
          <h2 class="text-lg font-semibold">{{ card.name }}</h2>
          <p class="text-sm text-gray-500">Эликсир: {{ card.elixir }}</p>
          <p class="text-sm text-gray-500">Редкость: {{ card.rarity }}</p>
          <p class="text-sm text-gray-500">Арена: {{ card.arena }}</p>
        </div>
      </div>
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
      filters: {
        rarity: "",
        elixir: "",
        arena: "",
      },
    };
  },
  computed: {
    filteredCards() {
      return this.cards.filter((card) => {
        return (
            (!this.filters.rarity || card.rarity === this.filters.rarity) &&
            (!this.filters.elixir || card.elixir === this.filters.elixir) &&
            (!this.filters.arena || card.arena === this.filters.arena)
        );
      });
    },
  },
  methods: {
    applyFilters() {},
    goToCard(id) {
      this.$router.push(`/cards/${id}`);
    },
  },
  mounted() {
    axios.get("/api/cards").then((res) => {
      this.cards = res.data;
    });
  },
};
</script>

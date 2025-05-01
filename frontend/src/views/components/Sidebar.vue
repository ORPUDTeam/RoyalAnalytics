<template>
  <aside class="fixed inset-y-0 left-0 w-64 bg-blue-950 text-white shadow-lg z-50">
    <div class="p-4 text-xl font-bold border-b border-blue-800">
      RoyalAnalytics
    </div>
    <nav class="mt-4 space-y-2 px-4">
      <RouterLink to="/dashboard-default" class="block py-2 px-3 rounded hover:bg-blue-800" active-class="bg-blue-800">Дашборд</RouterLink>
      <RouterLink to="/profile" class="block py-2 px-3 rounded hover:bg-blue-800" active-class="bg-blue-800">Профиль</RouterLink>
      <RouterLink to="/decks" class="block py-2 px-3 rounded hover:bg-blue-800" active-class="bg-blue-800">Мои Колоды</RouterLink>
      <RouterLink to="/decks/generate" class="block py-2 px-3 rounded hover:bg-blue-800" active-class="bg-blue-800">Генерация Колоды</RouterLink>
      <RouterLink to="/tournaments" class="block py-2 px-3 rounded hover:bg-blue-800" active-class="bg-blue-800">Турниры</RouterLink>
      <RouterLink to="/my-tournaments" class="block py-2 px-3 rounded hover:bg-blue-800" active-class="bg-blue-800">Мои Турниры</RouterLink>
      <RouterLink to="/cards" class="block py-2 px-3 rounded hover:bg-blue-800" active-class="bg-blue-800">Карты</RouterLink>
    </nav>
  </aside>
</template>

<script>
import { RouterLink } from "vue-router";

export default {
  name: "Sidebar",
  components: {
    RouterLink,
  },
};
</script>

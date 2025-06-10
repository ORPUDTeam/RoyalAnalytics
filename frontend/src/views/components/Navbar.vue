<template>
  <header class="w-full bg-blue-900 text-white py-3 px-6 shadow-md flex justify-between items-center">
    <h1 class="text-lg font-semibold">Добро пожаловать</h1>
    <div>
      <RouterLink to="/profile" class="hover:underline">Мой профиль</RouterLink>
      <span class="mx-2">|</span>
      <RouterLink to="/signin" class="hover:underline">Выход</RouterLink>
    </div>
  </header>
</template>

<script>
import { RouterLink } from "vue-router";

export default {
  name: "Navbar",
  components: {
    RouterLink,
  },
};
</script>

<template>
  <div id="app">
    <Sidebar />
    <main class="relative md:ml-64 bg-gray-100 min-h-screen">
      <Navbar />
      <div class="px-4 md:px-6 pt-6">
        <router-view />
      </div>
    </main>
  </div>
</template>

<script>
import Sidebar from "@/views/components/Sidebar.vue";
import Navbar from "@/views/components/Navbar.vue";

export default {
  name: "App",
  components: {
    Sidebar,
    Navbar,
  },
};
</script>

<style>
body {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  background-color: #0a0a0a;
  color: #f2f2f2;
}

#app {
  display: flex;
  flex-direction: row;
}
</style>

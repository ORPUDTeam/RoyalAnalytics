<template>
  <div class="admin-users">
    <div class="table-responsive">
      <table class="table align-items-center">
        <thead class="thead-light">
        <tr>
          <th>ID</th>
          <th>Имя</th>
          <th>Email</th>
          <th>Player Tag</th>
          <th>Статус</th>
          <th>Дата регистрации</th>
          <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="user in users" :key="user.id">
          <td>{{ user.id }}</td>
          <td>{{ user.username }}</td>
          <td>{{ user.email }}</td>
          <td>{{ user.player_tag }}</td>
          <td>
            <badge :type="user.status === 'active' ? 'success' : 'danger'">
              {{ user.status === 'active' ? 'Активен' : 'Заблокирован' }}
            </badge>
          </td>
          <td>{{ formatDate(user.registered_at) }}</td>
          <td class="td-actions">
            <base-button
                v-if="user.status === 'active'"
                size="sm"
                type="danger"
                @click="$emit('block', user.id)">
              Блокировать
            </base-button>
            <base-button
                v-else
                size="sm"
                type="success"
                @click="$emit('unblock', user.id)">
              Разблокировать
            </base-button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    users: { type: Array, required: true }
  },
  methods: {
    formatDate(date) {
      return new Date(date).toLocaleDateString('ru-RU');
    }
  }
}
</script>

<style scoped>
.td-actions {
  white-space: nowrap;
}
</style>